package com.NatureHelp.api.Repository;


import com.NatureHelp.api.Model.Appointment;

import java.security.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

   
}
