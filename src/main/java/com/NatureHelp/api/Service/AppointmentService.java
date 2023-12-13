package com.NatureHelp.api.Service;

import com.NatureHelp.api.Model.Appointment;
import com.NatureHelp.api.Model.User;
import com.NatureHelp.api.Repository.AppointmentRepository;
import com.NatureHelp.api.Repository.UserRepository;
import com.NatureHelp.api.Config.JwtValidationException;

import io.jsonwebtoken.Claims;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

   
    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;
    

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment createAppointment(String jwtToken, Appointment appointment) {
        Claims claims = jwtTokenService.decodeJwt(jwtToken);

        if (claims == null) {
            throw new JwtValidationException("Le JWT n'est pas valide.");
        }

        String userEmail = claims.getSubject();

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'email : " + userEmail));
        if (user.getRoles() == 2) {
            appointment.setMedecin(user);
            
        return appointmentRepository.save(appointment);
        } else {
            throw new RuntimeException("Vous n'avez pas le droit de faire cette action.");
        }
    }

    public Appointment userJoinAppointment(String jwtToken, Long appointmentId) {
        Claims claims = jwtTokenService.decodeJwt(jwtToken);

        if (claims == null) {
            throw new JwtValidationException("Le JWT n'est pas valide.");
        }

        String userEmail = claims.getSubject();

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'email : " + userEmail));

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Rendez-vous non trouvé avec l'id : " + appointmentId));

        appointment.setUser(user);

        return appointmentRepository.save(appointment);
    }


    public Iterable<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public  Optional<Appointment> findOne() {
        
        Optional<Appointment> appointment = appointmentRepository.findOne();
        if (appointment.isEmpty()) {
            throw new RuntimeException("Aucun rendez-vous trouvé.");
        }else{
            return appointment;
        }

    }

        



 
}
