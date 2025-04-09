package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.MedicalTreatmentEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;

public final class PatientMapper {

    public static PatientTO mapToTO(final PatientEntity patientEntity) {

        if (patientEntity == null) {
            return null;
        }

        final PatientTO patientTO = new PatientTO();
        patientTO.setId(patientEntity.getId());
        patientTO.setPatientNumber(patientEntity.getPatientNumber());
        patientTO.setFirstName(patientEntity.getFirstName());
        patientTO.setLastName(patientEntity.getLastName());
        patientTO.setMaritalStatus(patientEntity.getMaritalStatus());
        patientTO.setVisits(
            patientEntity
                .getVisits()
                .stream()
                .map(VisitMapper::mapToTO)
                .toList()
        );

        return patientTO;
    }
}
