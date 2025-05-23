package com.jpacourse.mapper;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.MedicalTreatmentEntity;
import com.jpacourse.persistance.entity.VisitEntity;

public final class VisitMapper {

    public static VisitTO mapToTO(final VisitEntity visitEntity) {

        if (visitEntity == null) {
            return null;
        }

        DoctorEntity doctorEntity = visitEntity.getDoctor();

        final VisitTO visitTO = new VisitTO();
        visitTO.setTime(visitEntity.getTime());
        visitTO.setDoctorFirstName(doctorEntity.getFirstName());
        visitTO.setDoctorLastName(doctorEntity.getLastName());
        visitTO.setMedicalTreatments(
            visitEntity
                .getMedicalTreatments()
                .stream()
                .map(MedicalTreatmentEntity::getType)
                .toList()
        );

        return visitTO;
    }
}
