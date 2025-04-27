package com.jpacourse.persistance.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

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

        assertThat(updatedPatient.getVisits().size()).isEqualTo(5);

        Collection<VisitEntity> visits = updatedPatient.getVisits();

        VisitEntity lastVisit = visits.stream().skip(visits.size() - 1).findFirst().orElse(null);

        assertThat(lastVisit.getDoctor().getId()).isEqualTo(doctorId);
        assertThat(lastVisit.getTime()).isEqualTo(localDateTime);
        assertThat(lastVisit.getDescription()).isEqualTo(visitDescription);
    }

    @Transactional
    @Test
    public void testFindByPatientFirstName() {
        List<PatientEntity> patients = patientDao.findByPatientFirstName("Jan");
        assertThat(patients).hasSize(2);
        assertThat(patients).extracting("lastName").containsExactlyInAnyOrder("Kowalski", "Wiśniewski");
    }

    @Transactional
    @Test
    public void testFindByLastName() {
        List<PatientEntity> patients = patientDao.findByPatientLastName("Nowak");
        assertThat(patients).hasSize(2);
        assertThat(patients).extracting("firstName").containsExactlyInAnyOrder("Marek", "Anna");
    }

    @Transactional
    @Test
    public void testFindPatientsWithMoreThanXVisits() {
        int visitCount = 3;

        List<PatientEntity> patients = patientDao.findPatientsWithMoreThanXVisits(visitCount);

        assertThat(patients).isNotNull();
        assertThat(patients.size()).isGreaterThan(0);

        for (PatientEntity patient : patients) {
            assertThat(patient.getVisits().size()).isGreaterThan(visitCount);
        }
    }

    @Transactional
    @Test
    public void testFindAllThatUsedToBeMarried() {
        // given
        // when
        List<PatientEntity> patients = patientDao.findAllThatUsedToBeMarried();

        // then
        PatientEntity firstPatient = patients.get(0);

        assertThat(patients).hasSize(2);
        assertThat(firstPatient.getFirstName()).isEqualTo("Ewa");
        assertThat(firstPatient.getLastName()).isEqualTo("Dąbrowska");
    }

    @Transactional
    @Test
    public void testDoesNotAllowConcurrentWriting() throws InterruptedException {
        // given
        AtomicBoolean hasSeenException = new AtomicBoolean(false);

        Thread t1 = new Thread(() -> {
            PatientEntity patient = patientDao.findOne(1L);
            patient.setEmail("task1@gmail.com");

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {
                patientDao.update(patient);
            } catch (ObjectOptimisticLockingFailureException e){
                hasSeenException.set(true);
            }
        });

        Thread t2 = new Thread(() -> {
            PatientEntity patient = patientDao.findOne(1L);
            patient.setEmail("task2@gmail.com");
            patientDao.update(patient);
        });

        // when
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        // then
        assertThat(hasSeenException).isTrue();
    }
}
