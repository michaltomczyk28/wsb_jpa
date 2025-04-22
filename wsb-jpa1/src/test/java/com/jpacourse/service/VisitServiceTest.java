package com.jpacourse.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.jpacourse.dto.VisitTO;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VisitServiceTest
{
    @Autowired
    private VisitService visitService;

    @Transactional
    @Test
    public void testShouldFindAllVisitsByPatientId() {
        Long patientId = 1L;

        List<VisitTO> visits = visitService.findAllByPatientId(patientId);

        assertThat(visits).isNotNull();
        assertThat(visits.size()).isEqualTo(4); // Check if the visits list is not empty

        VisitTO firstVisit = visits.get(0);
        assertThat(firstVisit.getDoctorFirstName()).isEqualTo("Maciej");
        assertThat(firstVisit.getDoctorLastName()).isEqualTo("Kowalewski");
    }
}
