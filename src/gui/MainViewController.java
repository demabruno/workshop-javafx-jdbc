package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.application.Department;
import model.entities.DepartmentService;
import model.entities.SellerService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;

	public void onMenuItemSellerAction() {
		loadView("/gui/SellerList.fxml", (SellerListController controller) -> {
			//--> Injeta depend�ncia;
			controller.setSellerService(new SellerService()); 
			//--> objeto do tipo controller acessa m�todo da departmentService
			controller.updateTableView(); 
		});
	}
	
	/*
	 * Na chamada do loadView vamos passar uma fun��o do tipo express�o lambda. Documenntado conforme
	 * a seguir:
	 * 	1-  (Department department) : � o tipo da fun��o;
	 *  2- -> � o que inicializa a fun��o lambda
	 *  3- O que est� entre {} � a implementa��o da fun��o.
	 */
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			//--> Injeta depend�ncia;
			controller.setDepartmentService(new DepartmentService()); 
			//--> objeto do tipo controller acessa m�todo da departmentService
			controller.updateTableView(); 
		});
	}
	
	public void onMenuItemAboutAction() {
		//Passa uma fun��o vazia, por enquanto.
		loadView("/gui/About.fxml", x -> {});
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {

	}
	
	/*
	 * Com o Consumer<T> initalizable recebido por par�metro e com o <T> antes do void, o m�todo
	 * torna-se gen�rico e apto a receber uma fun��o par�metro. Dessa forma, vamos abrir as janelas
	 * com um met�do loadView Apenas.
	 */
	private synchronized <T> void loadView(String caminhoAbosoluto, Consumer<T> initalizableAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoAbosoluto));
			VBox newVbox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVbox.getChildren().get(0);
			mainVbox.getChildren().clear();
			mainVbox.getChildren().add(mainMenu);
			mainVbox.getChildren().addAll(newVbox.getChildren());
			
			//S�o as linhas que ir�o abrir a tela 
			T controller = loader.getController();
			initalizableAction.accept(controller);
			
		}
		catch(IOException ioe) {
			Alerts.showAlert("IO Exception", "Error loading view", ioe.getMessage(), AlertType.ERROR);
		}
	}
}
