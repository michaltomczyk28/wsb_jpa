package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import java.time.LocalDateTime;
import java.util.List;

public interface PatientDao extends Dao<PatientEntity, Long> {

    void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String visitDescription);

    List<PatientEntity> findByPatientFirstName(String patientName);
    List<PatientEntity> findByPatientLastName(String patientLastName);
    List<PatientEntity> findPatientsWithMoreThanXVisits(int visitCount);
    List<PatientEntity> findAllThatUsedToBeMarried();
}
