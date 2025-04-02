package ma.enset.gestionconsultationbdcc.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ma.enset.gestionconsultationbdcc.dao.ConsultationDao;
import ma.enset.gestionconsultationbdcc.dao.PatientDao;
import ma.enset.gestionconsultationbdcc.entities.Consultation;
import ma.enset.gestionconsultationbdcc.entities.Patient;
import ma.enset.gestionconsultationbdcc.service.CabinetService;
import ma.enset.gestionconsultationbdcc.service.ICabinetService;

import javax.swing.*;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class ConsultationController implements Initializable {
    @FXML private DatePicker DateConsultation;
    @FXML private ComboBox<Patient> ComboPatient;
    @FXML private TextArea TextAreaDescription;
    @FXML private TableView<Consultation> TableConsultation;
    @FXML private TableColumn ColumnID;
    @FXML private TableColumn ColumnDateConsultation;
    @FXML private TableColumn ColumnPatient;
    @FXML private TableColumn ColumnDescription;
    private ICabinetService cabinetService;
    private Consultation selectedConsultation;
    private ObservableList<Consultation> consultations = FXCollections.observableArrayList();
    private ObservableList<Patient> patients = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cabinetService = new CabinetService(new PatientDao(), new ConsultationDao());
        ComboPatient.setItems(patients);
        patients.setAll(cabinetService.getAllPatient());
        ColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColumnDateConsultation.setCellValueFactory(new PropertyValueFactory<>("dateConsultation"));
        ColumnPatient.setCellValueFactory(new PropertyValueFactory<>("patient"));
        ColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        consultations.setAll(cabinetService.getAllConsultation());
        TableConsultation.setItems(consultations);

        // Add a MouseEvent handler to detect clicks on the ComboBox
        ComboPatient.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            patients.setAll(cabinetService.getAllPatient());
            System.out.println("ComboBox clicked!");
        });

      TableConsultation.getSelectionModel().selectedItemProperty().addListener((observable, oldConsultation, newConsultation) -> {
            if (newConsultation != null) {
                ComboPatient.setValue(newConsultation.getPatient());
                DateConsultation.setValue(newConsultation.getDateConsultation().toLocalDate());
                TextAreaDescription.setText(newConsultation.getDescription());
                selectedConsultation = newConsultation;
            }
        });
    }

    public void addConsultation(){
        Consultation consultation = new Consultation();
        consultation.setDescription(TextAreaDescription.getText());
        consultation.setDateConsultation(Date.valueOf(DateConsultation.getValue()));
        consultation.setPatient(ComboPatient.getSelectionModel().getSelectedItem());
        cabinetService.addConsultation(consultation);
        consultations.setAll(cabinetService.getAllConsultation());
    }

    public void deleteConsultation(){
        Consultation consultation = (Consultation) TableConsultation.getSelectionModel().getSelectedItem();
        if (consultation != null){
            cabinetService.deleteConsultation(consultation);
            consultations.remove(consultation);
            //LabelSuccess.setText("La consultation a bien été supprimé.");
        }
    }

    public void updateConsultation(){
        if(selectedConsultation != null){
            selectedConsultation.setPatient(ComboPatient.getValue());
            selectedConsultation.setDateConsultation(Date.valueOf(DateConsultation.getValue()));
            selectedConsultation.setDescription(TextAreaDescription.getText());
            cabinetService.updateConsultation(selectedConsultation);
            consultations.setAll(cabinetService.getAllConsultation());
        }
    }
}