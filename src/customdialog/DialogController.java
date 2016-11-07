package customdialog;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

//import customdialog.FrostyTech.Delta;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DialogController implements Initializable{
	
	@FXML
	TextField text;
	@FXML
	Button ok;
	@FXML
	Button cancel;
	@FXML
	AnchorPane dragable;
	
	
	private List<String> mylist;
	
	@FXML
	public void cancelButton(){
		System.out.println(mylist);
		printList();
		mylist.add("hello");
//		mylist.clear();
		Stage pStage = (Stage) cancel.getScene().getWindow();
		pStage.close();
	}
	
	public void getList(List<String> mylist){
		this.mylist = mylist;
	}
	
	public void printList(){
		for(String i : mylist){
			System.out.println(i);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//현재 UI의 stage를 갖고온다.
		//이걸 따로 메소드로 만들고 밖에서 호출해서 세팅하자... show전에...
		
	}
	
}
