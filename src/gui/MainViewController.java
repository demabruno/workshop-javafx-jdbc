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
			//--> Injeta dependência;
			controller.setSellerService(new SellerService()); 
			//--> objeto do tipo controller acessa método da departmentService
			controller.updateTableView(); 
		});
	}
	
	/*
	 * Na chamada do loadView vamos passar uma função do tipo expressão lambda. Documenntado conforme
	 * a seguir:
	 * 	1-  (Department department) : É o tipo da função;
	 *  2- -> é o que inicializa a função lambda
	 *  3- O que está entre {} é a implementação da função.
	 */
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			//--> Injeta dependência;
			controller.setDepartmentService(new DepartmentService()); 
			//--> objeto do tipo controller acessa método da departmentService
			controller.updateTableView(); 
		});
	}
	
	public void onMenuItemAboutAction() {
		//Passa uma função vazia, por enquanto.
		loadView("/gui/About.fxml", x -> {});
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {

	}
	
	/*
	 * Com o Consumer<T> initalizable recebido por parâmetro e com o <T> antes do void, o método
	 * torna-se genérico e apto a receber uma função parâmetro. Dessa forma, vamos abrir as janelas
	 * com um metódo loadView Apenas.
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
			
			//São as linhas que irão abrir a tela 
			T controller = loader.getController();
			initalizableAction.accept(controller);
			
		}
		catch(IOException ioe) {
			Alerts.showAlert("IO Exception", "Error loading view", ioe.getMessage(), AlertType.ERROR);
		}
	}
}
