package com.jpacourse.service.impl;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.mapper.VisitMapper;
import com.jpacourse.persistance.dao.VisitDao;
import com.jpacourse.service.VisitService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class VisitServiceImpl implements VisitService
{
    private final VisitDao visitDao;

    @Autowired
    public VisitServiceImpl(VisitDao pVisitDao)
    {
        visitDao = pVisitDao;
    }

    @Override
    public List<VisitTO> findAllByPatientId(Long patientId) {
        return visitDao.findAllByPatientId(patientId)
            .stream()
            .map(VisitMapper::mapToTO)
            .toList();
    }
}
