package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.VisitEntity;
import java.util.List;

public interface VisitDao extends Dao<VisitEntity, Long> {
    List<VisitEntity> findAllByPatientId(Long patientId);
}
