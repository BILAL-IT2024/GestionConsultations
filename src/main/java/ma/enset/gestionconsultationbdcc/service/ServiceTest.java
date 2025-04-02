package ma.enset.gestionconsultationbdcc.service;

import ma.enset.gestionconsultationbdcc.dao.ConsultationDao;
import ma.enset.gestionconsultationbdcc.dao.PatientDao;
import ma.enset.gestionconsultationbdcc.entities.Patient;

import java.util.List;

public class ServiceTest {
    public static void main(String[] args) {

        ICabinetService service = new CabinetService(new PatientDao(), new ConsultationDao());
        Patient patient = new Patient();
        patient.setNom("LAKRIFI");
        patient.setPrenom("MOHAMMED");
        patient.setTel("0768888888");
        service.addPatient(patient);

        /*List<Patient> patients = service.getAllPatient();
        patients.forEach(patient -> System.out.println(patient));*/

        /*Patient patient = service.getPatientById(2L);
        patient.setTel("0666699666");
        //System.out.println(patient);
        service.updatePatient(patient); */



    }
}
