package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.application.Department;
import model.entities.DepartmentService;

public class DepartmentFormController implements Initializable{

	private Department entity;
	
	private DepartmentService service;
	
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
	public void onBotaoSalvar(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity is null");
		}
		if (service == null) {
			throw new IllegalStateException("Service is null");
		}
		entity = getFormData();
		service.saveOrUpdate(entity);
		Utils.currentStage(event).close();
		
	}
	
	private Department getFormData() {
		Department dep = new Department();
		dep.setId(Utils.tryParseToInt(textBoxId.getText()));
		dep.setName(textBoxName.getText());
		return dep;
	}

	@FXML
	public void onBotaoCancelar(ActionEvent event) {
		Utils.currentStage(event).close();
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
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Entity was null.");
		}
		textBoxId.setText(String.valueOf(entity.getId()));
		textBoxName.setText(entity.getName());
	}

}
