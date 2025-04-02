package ma.enset.gestionconsultationbdcc.service;

import ma.enset.gestionconsultationbdcc.entities.Consultation;
import ma.enset.gestionconsultationbdcc.entities.Patient;

import java.sql.SQLException;
import java.util.List;

public interface ICabinetService {

    void addPatient(Patient patient);
    void deletePatient(Patient patient);
    void updatePatient(Patient patient);
    List<Patient> getAllPatient();
    Patient getPatientById(Long id);
    List<Patient> searchPatientByQuery(String query) throws SQLException;

    void addConsultation(Consultation consultation);
    void deleteConsultation(Consultation consultation);
    void updateConsultation(Consultation consultation);
    List<Consultation> getAllConsultation();
    Consultation getConsultationById(Long id);



}
