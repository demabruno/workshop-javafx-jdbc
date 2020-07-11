package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.application.Department;
import model.entities.DepartmentService;

public class DepartmentListController implements Initializable {

	//private DepartmentService service = new DepartmentService();
	//Não irá instanciar direto, mas sim pelo método setDepartmentService. Isso irá
	//garantir uma injeção de dependência, como se fosse uma interface sendo implementada.
	private DepartmentService service; 
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumId;
	
	@FXML
	private TableColumn<Department, String> tableColumName;
	
	@FXML
	private Button btnNewDepartment;
	
	@FXML
	private void btnNewDepartmentAction() {
		System.out.println("TTT");
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	//Componente pra popular a lista de departamentos na table View
	private ObservableList<Department> obsList;
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		//Comandos para inicializar o comportamento das colunas na tabela
		tableColumId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		
		//Massete para deixar o table view do tamanho da tela
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was not initializable");
		}
		
		List<Department> list = service.findAll();
		//Converte uma lista para um objeto do tipo JavaFX
		obsList = FXCollections.observableArrayList(list);
		//Joga a lista do JavaFx pra dentro da view do departamento
		tableViewDepartment.setItems(obsList);
		
	}

}
