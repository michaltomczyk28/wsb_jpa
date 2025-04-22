package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PatientDaoTest
{
    @Autowired
    private PatientDao patientDao;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Test
    public void testShouldAddVisitToPatient() {
        PatientEntity patient = patientDao.findOne(1L);
        Long doctorId = 1L;
        LocalDateTime localDateTime = LocalDateTime.of(2025,4,11,13,15);
        String visitDescription = "Example visit description";

        patientDao.addVisitToPatient(patient.getId(), doctorId, localDateTime, visitDescription);

        PatientEntity updatedPatient = entityManager.find(PatientEntity.class, patient.getId());

        assertThat(updatedPatient.getVisits().size()).isEqualTo(3);

        Collection<VisitEntity> visits = updatedPatient.getVisits();

        VisitEntity lastVisit = visits.stream().skip(visits.size() - 1).findFirst().orElse(null);

        assertThat(lastVisit.getDoctor().getId()).isEqualTo(doctorId);
        assertThat(lastVisit.getTime()).isEqualTo(localDateTime);
        assertThat(lastVisit.getDescription()).isEqualTo(visitDescription);
    }
}
