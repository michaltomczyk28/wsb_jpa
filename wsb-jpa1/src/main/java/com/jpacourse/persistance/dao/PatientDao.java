package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface PatientDao extends Dao<PatientEntity, Long> {

    void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitDate, String visitDescription);

    public List<PatientEntity> findByPatientFirstName(String patientName);
    public List<PatientEntity> findByVisitDate(LocalDateTime visitDate);
    public List<PatientEntity> findByPatientLastName(String patientLastName);

    public List<VisitEntity> findAllVisitsByPatientId(Long patientId);
    public List<PatientEntity> findPatientsWithMoreThanXVisits(int visitCount);
}
