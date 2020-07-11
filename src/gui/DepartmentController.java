package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.application.Department;

public class DepartmentController implements Initializable {

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

}
