package com.NatureHelp.api.Repository;


import com.NatureHelp.api.Model.Appointment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT u FROM Appointment u WHERE u.user is null")
    Optional<Appointment> findOne();
}
