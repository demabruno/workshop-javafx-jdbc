package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import gui.listeners.DataChangeListner;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.application.Department;
import model.application.Seller;
import model.entities.DepartmentService;
import model.entities.SellerService;
import model.exceptions.ValidationException;

public class SellerFormController implements Initializable {

	private Seller entity;

	private SellerService service;

	private DepartmentService departmentService;

	private List<DataChangeListner> dataChangeListners = new ArrayList();

	@FXML
	private TextField textBoxId;

	@FXML
	private TextField textBoxName;

	@FXML
	private TextField textBoxEmail;

	@FXML
	private DatePicker dpBirthDate;

	@FXML
	private TextField textBoxBaseSalary;

	@FXML
	private Label labelErrName;

	@FXML
	private Label labelErrEmail;

	@FXML
	private Label labelErrBirthDate;

	@FXML
	private Label labelErrBaseSalary;

	@FXML
	private ComboBox<Department> comboboxDepartment;

	@FXML
	private Button btnSalvar;

	@FXML
	private Button btnCancelar;

	@FXML
	private ObservableList<Department> obsList;

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
		} catch (ValidationException e) {
			setErrorMsg(e.getErrors());
		}

	}

	private void notifyDataChangeListners() {
		for (DataChangeListner dcl : dataChangeListners) {
			dcl.onDataChanged();
		}

	}

	private Seller getFormData() throws ValidationException {
		ValidationException exceptionError = new ValidationException("Validation Exception");
		Seller dep = new Seller();
		dep.setId(Utils.tryParseToInt(textBoxId.getText()));

		if (textBoxName.getText() == null || "".equals(textBoxName.getText().trim())) {
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
		Constraints.setTextFieldDouble(textBoxBaseSalary);
		Constraints.setTextFieldMaxLength(textBoxEmail, 60);
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
		
		initializeComboBoxDepartment();
	}

	public void setSeller(Seller obj) {
		this.entity = obj;
	}

	public void setServices(SellerService service, DepartmentService departmentService) {
		this.service = service;
		this.departmentService = departmentService;
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null.");
		}
		textBoxId.setText(String.valueOf(entity.getId()));
		textBoxName.setText(entity.getName());
		textBoxEmail.setText(entity.getEmail());
		Locale.setDefault(Locale.US);
		textBoxBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
		if (entity.getBirthDate() != null) {
			dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
		}
		if(entity.getDepartent() == null) {
			comboboxDepartment.getSelectionModel().selectFirst();
		}
		else {
			comboboxDepartment.setValue(entity.getDepartent());
		}
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

	public void loadAssociatedObjects() {
		if (departmentService == null) {
			throw new IllegalStateException("Department Service Was Null");
		}
		List<Department> lstDep = departmentService.findAll();
		obsList = FXCollections.observableArrayList(lstDep);
		comboboxDepartment.setItems(obsList);
	}

	private void initializeComboBoxDepartment() {
		Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
			@Override
			protected void updateItem(Department item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};
		comboboxDepartment.setCellFactory(factory);
		comboboxDepartment.setButtonCell(factory.call(null));
	}

}
