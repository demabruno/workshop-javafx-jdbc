package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import gui.listeners.DataChangeListner;
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
import model.exceptions.ValidationException;

public class DepartmentFormController implements Initializable{

	private Department entity;
	
	private DepartmentService service;
	
	private List<DataChangeListner> dataChangeListners = new ArrayList();
	
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
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListners();
			Utils.currentStage(event).close();
		}
		catch(ValidationException e) {
			setErrorMsg(e.getErrors());
		}
		
	}
	
	private void notifyDataChangeListners() {
		for(DataChangeListner dcl : dataChangeListners) {
			dcl.onDataChanged();
		}
		
	}

	private Department getFormData() throws ValidationException {
		ValidationException exceptionError = new ValidationException("Validation Exception");
		Department dep = new Department();
		dep.setId(Utils.tryParseToInt(textBoxId.getText()));
		
		if(textBoxName.getText() == null || "".equals(textBoxName.getText().trim())){
			exceptionError.addError("name", "field can't be empty");
		}
		dep.setName(textBoxName.getText());
		
		if (exceptionError.getErrors().size() > 0) {
			throw exceptionError;
		}
		
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
	
	public void subscribeDataChangeListner(DataChangeListner dataChangeListner) {
		dataChangeListners.add(dataChangeListner);
	}
	
	private void setErrorMsg(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if (fields.contains("name")) {
			labelErrName.setText(errors.get("name"));
		}
	}

}
