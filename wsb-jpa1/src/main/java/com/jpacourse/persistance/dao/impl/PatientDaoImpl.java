package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    public void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String visitDescription) {
        // Pobranie pacjenta z bazy danych
        PatientEntity patient = entityManager.find(PatientEntity.class, patientId);

        if (patient == null) {
            throw new IllegalArgumentException("Patient with ID " + patientId + " not found.");
        }

        DoctorEntity doctor = entityManager.find(DoctorEntity.class, doctorId);

        // Tworzenie nowej encji wizyty
        VisitEntity visit = new VisitEntity();
        visit.setDoctor(doctor);
        visit.setTime(visitDate);
        visit.setDescription(visitDescription);

        // Dodanie wizyty do pacjenta
        patient.getVisits().add(visit); // Zakładam, że `Patient` ma odpowiednią relację typu OneToMany z `Visit`

        // Synchronizacja zmian
        entityManager.merge(patient); // Dzięki kaskadowemu zapisowi `Visit` zostanie także zapisana
    }

    @Override
    public List<PatientEntity> findByPatientFirstName(String patientName) {
        return entityManager.createQuery(" select pat from PatientEntity pat" + " where pat.firstName like :param1", PatientEntity.class).setParameter("param1", "%" + patientName + "%").getResultList();
    }

    @Override
    public List<PatientEntity> findByVisitDate(LocalDateTime visitDate) {
        return List.of();
    }

    @Override
    public List<PatientEntity> findByPatientLastName(String patientLastName) {
        return entityManager.createQuery(" select pat from PatientEntity pat" + " where pat.lastName like :param1", PatientEntity.class).setParameter("param1", "%" + patientLastName + "%").getResultList();
    }

    @Transactional
    public List<VisitEntity> findAllVisitsByPatientId(Long patientId) {
        return entityManager.createQuery(
                        "SELECT v FROM VisitEntity v WHERE v.patient.id = :patientId", VisitEntity.class)
                .setParameter("patientId", patientId)
                .getResultList();
    }

    @Transactional
    @Override
    public List<PatientEntity> findPatientsWithMoreThanXVisits(int visitCount) {
        return entityManager.createQuery(
                        "SELECT p FROM PatientEntity p JOIN p.visits v GROUP BY p HAVING COUNT(v) > :visitCount",
                        PatientEntity.class)
                .setParameter("visitCount", (long) visitCount)
                .getResultList();
    }
}

