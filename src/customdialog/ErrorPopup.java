package customdialog;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * 에러용 팝업 메뉴 생성 클래스
 * 사용되는 fxml파일 : errorpopup.fxml
 * @author Root
 *
 */

public class ErrorPopup {
	public static void showPopup(Stage stage, String text){
//		Stage primaryStage = (Stage) login.getScene().getWindow();
		
		Popup popup = new Popup();
		
		
		Parent parent;
		try {
			parent = FXMLLoader.load(ErrorPopup.class.getResource("errorpopup.fxml"));
			Label lblMessage = (Label)parent.lookup("#lblMessage");
  		lblMessage.setText(text);
  		//그냥 항상 메뉴쪽에 뜨게 한다.
//  		double x = stage.getX()+stage.getWidth()/2 - popup.getWidth()/2;
//  		double y = stage.getY()+stage.getHeight()/2 - popup.getHeight()/2;
//  		System.out.println("(x, y) : "+x+", " + y);
  		popup.getContent().add(parent);
  		popup.setAutoHide(true);
  		double x = stage.getX();
  		double y = stage.getY();
  		popup.show(stage,x,y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
