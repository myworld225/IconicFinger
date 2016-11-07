package customdialog;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

//import iconicui.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
//custom dialog simulation class
public class MainController implements Initializable{
	@FXML
	Button getDialog;
	
	List<String> mylist;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		mylist = new ArrayList<String>();
		mylist.add("mucky");
		mylist.add("bucky");
		
	}
	
	
	//여기서 해야할것 == > 컨트롤러 객체를 획득한다 // set메소드로 list를 연결해준다. 끝난 후 리스트 처리된 결과를 반영한다.
	@FXML
	public void actionButton() throws IOException{
//		Stage primaryStage = (Stage) getDialog.getScene().getWindow();
//		//stage for dialog
//		Stage dialog = new Stage(StageStyle.UTILITY);
//		dialog.initModality(Modality.WINDOW_MODAL);
//		dialog.initOwner(primaryStage);
//		dialog.setTitle("확인");
//		
//		
//		
//		FXMLLoader loader = new FXMLLoader();
//    loader.setLocation(MainController.class.getResource("inputdialog.fxml"));
//		
////		dialogdd.getList(Arrays.asList("mucky","duck"));
//		
////	Parent parent = FXMLLoader.load(getClass().getResource("inputdialog.fxml"));
//    Parent parent = loader.load();
//    DialogController dialogdd = (DialogController)loader.getController();
//    
////    dialogdd.setList(mylist);
//    System.out.println(dialogdd);
//	  dialog.initStyle(StageStyle.UNDECORATED);
//	  
//	  
//	  DraggableFactory.makeDraggable(dialog, parent);
//		Scene scene = new Scene(parent);
////		dialogdd.makeDraggable((Stage)scene.getWindow());
//		
//		dialog.setScene(scene);
//		dialog.setResizable(false);
//		dialog.showAndWait();
//		for(String i: mylist){
//			System.out.println(i);
//		}
	}


	@FXML
	public void actionButton2() throws IOException{
		Stage primaryStage = (Stage) getDialog.getScene().getWindow();
		
		Popup popup = new Popup();
		
		Parent parent = FXMLLoader.load(getClass().getResource("errorpopup.fxml"));
		//각기 다른 오류 처리를 위해 image를 세팅하는 부분
		/*
		 * 추후 추가 
		 * */
	
		Label lblMessage = (Label)parent.lookup("#lblMessage");
		lblMessage.setText("로그인 오류");
		
		popup.getContent().add(parent);
		popup.setAutoHide(true);
		popup.show(primaryStage,primaryStage.getX(),primaryStage.getY());
	}
}


