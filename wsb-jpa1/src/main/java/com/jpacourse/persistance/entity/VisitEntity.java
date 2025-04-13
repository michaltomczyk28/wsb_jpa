package com.jpacourse.persistance.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "VISIT")
public class VisitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	@Column(nullable = false)
	private LocalDateTime time;

	@ManyToOne()
	@JoinColumn(name = "PATIENT_ID")
	private PatientEntity patient;

	// relacja dwustronna
	@ManyToOne
	@JoinColumn(name = "DOCTOR_ID")
	private DoctorEntity doctor;

    // relacja jednostronna od strony dziecka
	@OneToMany(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY
	)
	@JoinColumn(name = "VISIT_ID")
	private List<MedicalTreatmentEntity> medicalTreatments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public DoctorEntity getDoctor() {
		return doctor;
	}

	public PatientEntity getPatient() {
		return patient;
	}

	public void setPatient(PatientEntity patient) {
		this.patient = patient;
	}

	public void setDoctor(DoctorEntity doctor) {
		this.doctor = doctor;
	}

	public List<MedicalTreatmentEntity> getMedicalTreatments() {
		return medicalTreatments;
	}

	public void setMedicalTreatments(
        List<MedicalTreatmentEntity> medicalTreatments) {
		this.medicalTreatments = medicalTreatments;
	}
}
