package com.jpacourse.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.dao.DoctorDao;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.dao.VisitDao;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.enums.MaritalStatus;
import com.jpacourse.persistance.enums.TreatmentType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class PatientServiceTest
{
    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private VisitDao visitDao;

    @Autowired
    private DoctorDao doctorDao;

    @Transactional
    @Test
    public void testShouldRemovePatientWithAllVisitsAndShouldNotRemoveDoctors() {
        // given
        long visitsCount = visitDao.count();
        long doctorsCount = doctorDao.count();

        // when
        patientService.removeById(1L);

        // then
        assertThat(patientDao.findOne(1L)).isNull();
        assertThat(visitDao.count()).isEqualTo(visitsCount - 4);
        assertThat(doctorDao.count()).isEqualTo(doctorsCount);
    }

    @Transactional
    @Test
    public void testShouldFindPatientAndMapItToTO() {
        // given

        // when
        PatientTO patient = patientService.findById(1L);

        // then
        assertThat(patient.getId()).isEqualTo(1L);
        assertThat(patient.getPatientNumber()).isEqualTo("PAT12345");
        assertThat(patient.getFirstName()).isEqualTo("Jan");
        assertThat(patient.getLastName()).isEqualTo("Kowalski");
        assertThat(patient.getMaritalStatus()).isEqualTo(MaritalStatus.MARRIED);

        assertThat(patient.getVisits().size()).isEqualTo(4);

        VisitTO firstVisit = patient.getVisits().get(0);
        assertThat(firstVisit.getDoctorFirstName()).isEqualTo("Maciej");
        assertThat(firstVisit.getDoctorLastName()).isEqualTo("Kowalewski");
        assertThat(firstVisit.getTime()).isEqualTo("2024-03-10T09:00:00");

        assertThat(firstVisit.getMedicalTreatments().size()).isEqualTo(1);
        assertThat(firstVisit.getMedicalTreatments().get(0)).isEqualTo(TreatmentType.USG);
    }

    @Transactional
    @Test
    public void testShouldFindAllVisitsByPatientId() {
        Long patientId = 1L;

        List<VisitEntity> visits = patientDao.findAllVisitsByPatientId(patientId);

        assertThat(visits).isNotNull();
        assertThat(visits.size()).isEqualTo(4); // Check if the visits list is not empty

        VisitEntity firstVisit = visits.get(0);
        assertThat(firstVisit.getPatient().getId()).isEqualTo(patientId);
        assertThat(firstVisit.getDescription()).isNotEmpty();
        assertThat(firstVisit.getTime()).isBefore(LocalDateTime.now()); // Checks if the visit date is in the past
    }

}
