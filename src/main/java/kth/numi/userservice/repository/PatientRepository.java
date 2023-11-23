package kth.numi.userservice.repository;

import kth.numi.userservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> { }
