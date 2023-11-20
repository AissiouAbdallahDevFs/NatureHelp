package com.NatureHelp.api.Controller;


import com.NatureHelp.api.Model.Appointment;
import com.NatureHelp.api.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Appointment> createAppointment(@RequestHeader("Authorization") String jwtToken, @RequestBody Appointment appointment) {
        return ResponseEntity.ok(appointmentService.createAppointment(jwtToken, appointment));
    }
    
    @PostMapping("/join/{appointmentId}")
    public ResponseEntity<Appointment> userJoinAppointment(@RequestHeader("Authorization") String jwtToken, @PathVariable Long appointmentId) {
        return ResponseEntity.ok(appointmentService.userJoinAppointment(jwtToken, appointmentId));
    }

}