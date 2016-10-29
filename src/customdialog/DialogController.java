package customdialog;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DialogController {
	
	@FXML
	TextField text;
	@FXML
	Button ok;
	@FXML
	Button cancel;
	
	@FXML
	public void cancelButton(){
		Stage pStage = (Stage) cancel.getScene().getWindow();
		pStage.close();
	}

}
