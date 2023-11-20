package com.NatureHelp.api.Model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medecin_id") 
    private User medecin;

    @ManyToOne
    @JoinColumn(name = "user_id") 
    private User user;

    @Column(name = "appointment_time")
    private Timestamp appointmentTime;

    public Appointment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getMedecin() {
        return medecin;
    }

    public void setMedecin(User medecin) {
        this.medecin = medecin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Timestamp appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
}
