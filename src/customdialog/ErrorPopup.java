package customdialog;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;

//팝업 다이얼로그를 생성하는 클래스

public class ErrorPopup {
	public static void showPopup(Stage stage, String text){
//		Stage primaryStage = (Stage) login.getScene().getWindow();
		
		Popup popup = new Popup();
		
		
		Parent parent;
		try {
			parent = FXMLLoader.load(ErrorPopup.class.getResource("errorpopup.fxml"));
			Label lblMessage = (Label)parent.lookup("#lblMessage");
  		lblMessage.setText(text);
  		//그냥 항상 메뉴쪽에 뜨게 하자... 보기 쉽게?
  		double x = stage.getX();
  		double y = stage.getY();
  		System.out.println("(x, y) : "+x+", " + y);
  		popup.getContent().add(parent);
  		popup.setAutoHide(true);
  		popup.show(stage,x,y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
