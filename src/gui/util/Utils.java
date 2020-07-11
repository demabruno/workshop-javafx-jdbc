package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
	public static Stage currentStage(ActionEvent event) {
		//Ao clicar no botão, retorna a scena (palco) origem onde o botão foi clicado.
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
	
	public static Integer tryParseToInt(String str) {
		try
		{
			return Integer.parseInt(str);
		}
		catch(Exception ex) {
			return null;
		}
		
	}
}
