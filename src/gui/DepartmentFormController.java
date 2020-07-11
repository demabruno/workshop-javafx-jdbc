package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentFormController implements Initializable{

	@FXML
	private TextField textBoxId;
	
	@FXML
	private TextField texBoxName;
	
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
		Constraints.setTextFieldMaxLength(texBoxName, 30);
	}

}
