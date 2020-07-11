package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListner;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.application.Department;
import model.entities.DepartmentService;

public class DepartmentListController implements Initializable, DataChangeListner {

	// private DepartmentService service = new DepartmentService();
	// Não irá instanciar direto, mas sim pelo método setDepartmentService. Isso irá
	// garantir uma injeção de dependência, como se fosse uma interface sendo
	// implementada.
	private DepartmentService service;

	@FXML
	private TableView<Department> tableViewDepartment;

	@FXML
	private TableColumn<Department, Integer> tableColumId;

	@FXML
	private TableColumn<Department, String> tableColumName;

	@FXML
	private TableColumn<Department, Department> tableColumEdit;

	@FXML
	private TableColumn<Department, Department> tableColumRemove;

	@FXML
	private Button btnNewDepartment;

	@FXML
	private void btnNewDepartmentAction(ActionEvent event) {
		// Retorna o stage de onde partiu o evento (clique do botão)
		Stage parentStage = Utils.currentStage(event);
		Department obj = new Department();
		createDialogForm(obj, "/gui/DepartmentForm.fxml", parentStage);
	}

	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	// Componente pra popular a lista de departamentos na table View
	private ObservableList<Department> obsList;

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		// Comandos para inicializar o comportamento das colunas na tabela
		tableColumId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumName.setCellValueFactory(new PropertyValueFactory<>("Name"));

		// Massete para deixar o table view do tamanho da tela
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was not initializable");
		}

		List<Department> list = service.findAll();
		// Converte uma lista para um objeto do tipo JavaFX
		obsList = FXCollections.observableArrayList(list);
		// Joga a lista do JavaFx pra dentro da view do departamento
		tableViewDepartment.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	public void createDialogForm(Department obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane;
			pane = loader.load();

			DepartmentFormController controller = loader.getController();
			controller.setDepartment(obj);
			controller.setDepartmentService(service);
			// Padrão de projeto Observer
			// Usado para atualizar a lista após salvar um item novo.
			controller.subscribeDataChangeListner(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Department Data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

	// Cria o botão de editar na coluna da tabela.
	private void initEditButtons() {
		tableColumEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumEdit.setCellFactory(param -> new TableCell<Department, Department>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Department obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						// Abrea tela de "cadastro" ao clicar no botão edit
						event -> createDialogForm(obj, "/gui/DepartmentForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		try {
			tableColumRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
			tableColumRemove.setCellFactory(param -> new TableCell<Department, Department>() {
				private final Button button = new Button("remove");

				@Override
				protected void updateItem(Department obj, boolean empty) {
					super.updateItem(obj, empty);
					if (obj == null) {
						setGraphic(null);
						return;
					}
					setGraphic(button);
					button.setOnAction(event -> removeEntity(obj));
				}

			});
		} catch (DbIntegrityException dbe) {
			Alerts.showAlert("DB Exception", "Error on remove Department", dbe.getMessage(), AlertType.ERROR);
		}
	}

	private void removeEntity(Department obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
		//Verific se o botão clicado foi o OK
		if(result.get() == ButtonType.OK) {
			try {
				service.removeEntity(obj);
				updateTableView(); //Atualiza o grid
			}
			catch(DbIntegrityException dbi) {
				Alerts.showAlert("Error removing alert", null, dbi.getMessage(), AlertType.ERROR);
			}
		}
				
	}

}
