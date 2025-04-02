package ma.enset.gestionconsultationbdcc.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.enset.gestionconsultationbdcc.dao.ConsultationDao;
import ma.enset.gestionconsultationbdcc.dao.IConsultationDao;
import ma.enset.gestionconsultationbdcc.dao.PatientDao;
import ma.enset.gestionconsultationbdcc.entities.Consultation;
import ma.enset.gestionconsultationbdcc.entities.Patient;
import ma.enset.gestionconsultationbdcc.service.CabinetService;
import ma.enset.gestionconsultationbdcc.service.ICabinetService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PatientController implements Initializable {

    @FXML private TextField TextFieldNom;
    @FXML private TextField TextFieldPrenom;
    @FXML private TextField TextFieldTelephone;
    @FXML private TextField TextFieldAdresse;
    @FXML private TextField TextFieldSearch;
    @FXML private TableView<Patient> TableViewPatient;
    @FXML private TableColumn<Patient, Long> ColumnID;
    @FXML private TableColumn<Patient, String> ColumnNom;
    @FXML private TableColumn<Patient, String> ColumnPrenom;
    @FXML private TableColumn<Patient, String> ColumnTelephone;
    @FXML private Label LabelSuccess;
    private ICabinetService cabinetService;
    private Patient selectedPatient;
    private ObservableList<Patient> patients = FXCollections.observableArrayList();
    private java.awt.Label LabelFail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cabinetService = new CabinetService(new PatientDao(), new ConsultationDao());
        ColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColumnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        ColumnPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        ColumnTelephone.setCellValueFactory(new PropertyValueFactory<>("tel"));

        patients.setAll(cabinetService.getAllPatient());
        TableViewPatient.setItems(patients);
        TextFieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
           System.out.println(oldValue+" "+newValue);
            try {
                patients.setAll(cabinetService.searchPatientByQuery(newValue));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        TableViewPatient.getSelectionModel().selectedItemProperty().addListener((observable, oldPatient, newPatient) -> {
            //System.out.println(newValue);
            if (newPatient != null) {
                TextFieldNom.setText(newPatient.getNom());
                TextFieldPrenom.setText(newPatient.getPrenom());
                TextFieldTelephone.setText(newPatient.getTel());
                selectedPatient = newPatient;
            }
        });
    }
    public void addPatient(){
        Patient patient = new Patient();
        patient.setNom(TextFieldNom.getText());
        patient.setPrenom(TextFieldPrenom.getText());
        patient.setTel(TextFieldTelephone.getText());
        cabinetService.addPatient(patient);
        //patients.add(patient);
        patients.setAll(cabinetService.getAllPatient()); // cabinetService.getAllPatient() récupère de la base
        //System.out.println(patients);
    }

    public void deletePatient(){
        Patient patient = TableViewPatient.getSelectionModel().getSelectedItem();

        if (patient != null){


            //avant de supprimer le patient, il faut voir si il a des consultations
            List<Consultation> consultations;
            consultations = cabinetService.getAllConsultation();
            for(Consultation cons:consultations) {
                if(cons.getPatient().getId()==patient.getId()) {
                    Alert erreur = new Alert(Alert.AlertType.ERROR, "Le patient a des consultations. Supprimez les avant de supprimer le patient.");
                    erreur.showAndWait();
                    //LabelSuccess.setText("");
                    //LabelFail.setText("Le patient a des consultations. Supprimez les avant de supprimer le patient.");
                    return;
                }
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Supprimer le patient " + patient.getId()+" ?");
            alert.setTitle("Suppression d'un patient");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK) {
                cabinetService.deletePatient(patient);
                patients.remove(patient);
                //LabelFail.setText("");
                //LabelSuccess.setText("Le patient a bien été supprimé.");
            }
        }
    }

    public void updatePatient(){
        if (selectedPatient != null) {
            selectedPatient.setNom(TextFieldNom.getText());
            selectedPatient.setPrenom(TextFieldPrenom.getText());
            selectedPatient.setTel(TextFieldTelephone.getText());
            cabinetService.updatePatient(selectedPatient);
            patients.setAll(cabinetService.getAllPatient());
        }
    }
}