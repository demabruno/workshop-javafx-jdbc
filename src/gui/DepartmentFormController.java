package gui;

import java.net.URL;
import java.nio.channels.IllegalSelectorException;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.application.Department;

public class DepartmentFormController implements Initializable{

	private Department entity;
	
	@FXML
	private TextField textBoxId;
	
	@FXML
	private TextField textBoxName;
	
	@FXML
	private Label labelErrName;
	
	@FXML
	private Button btnSalvar;
	
	@FXML
	private Button btnCancelar;
	
	@FXML
	public void onBotaoSalvar() {
		System.out.println("Botão Salvar");
	}
	
	@FXML
	public void onBotaoCancelar() {
		System.out.println("Botão Salvar");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(textBoxId);
		Constraints.setTextFieldMaxLength(textBoxName, 30);
	}
	
	public void setDepartment(Department obj) {
		this.entity = obj;
	}
	
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Entity was null.");
		}
		textBoxId.setText(String.valueOf(entity.getId()));
		textBoxName.setText(entity.getName());
	}

}
