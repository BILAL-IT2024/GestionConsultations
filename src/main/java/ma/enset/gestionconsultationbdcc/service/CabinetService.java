package ma.enset.gestionconsultationbdcc.service;

import ma.enset.gestionconsultationbdcc.dao.IConsultationDao;
import ma.enset.gestionconsultationbdcc.dao.IPatientDao;
import ma.enset.gestionconsultationbdcc.dao.PatientDao;
import ma.enset.gestionconsultationbdcc.entities.Consultation;
import ma.enset.gestionconsultationbdcc.entities.Patient;
import java.sql.SQLException;
import java.util.List;

public class CabinetService implements ICabinetService {
    private IPatientDao patientDao;
    private IConsultationDao consultationDao;

    public CabinetService(IPatientDao patientDao, IConsultationDao consultationDao) {
        this.patientDao = patientDao;
        this.consultationDao = consultationDao;
    }

    @Override
    public void addPatient(Patient patient) {
        try {
            patientDao.create(patient);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deletePatient(Patient patient) {
        try {
            patientDao.delete(patient);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePatient(Patient patient) {
        try {
            patientDao.update(patient);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Patient> getAllPatient() {
        List<Patient> patients = null;
        try {
            patients = patientDao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    @Override
    public Patient getPatientById(Long id) {
        Patient patient = null;
        try {
            patient = patientDao.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    @Override
    public List<Patient> searchPatientByQuery(String query) throws SQLException{
        return patientDao.searchByQuery(query);
    }

    @Override
    public void addConsultation(Consultation consultation) {
        try {
            consultationDao.create(consultation);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteConsultation(Consultation consultation) {
        try {
            consultationDao.delete(consultation);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateConsultation(Consultation consultation) {
        try {
            consultationDao.update(consultation);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Consultation> getAllConsultation() {
        List<Consultation> consultations = null;
        try {
            consultations = consultationDao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consultations;
    }

    @Override
    public Consultation getConsultationById(Long id) {
        return null;
    }
}
