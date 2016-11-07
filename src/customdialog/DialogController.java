package customdialog;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import iconicdata.FriendListDao;
import iconicdata.MySqlConnectionMaker;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DialogController implements Initializable{
	
	@FXML
	TextField text;
	@FXML
	Button ok;
	@FXML
	Button cancel;
	@FXML
	AnchorPane dragable;
	
	private String userName;//사용자의 아이디
	private ObservableList<String> mylist;
	
	@FXML
	public void cancelButton(){
		System.out.println(mylist);
		printList();
		mylist.add("hello");
//		mylist.clear();
		Stage pStage = (Stage) cancel.getScene().getWindow();
		pStage.close();
	}
	
	//일단 add용만 먼저 만들어 두자.
	@FXML
	public void actionButton(){
		Stage pStage = (Stage) cancel.getScene().getWindow();
		
		if(text.getText() != ""){
			String fid = text.getText();
			FriendListDao f_dao = new FriendListDao();
			f_dao.setConnectionMaker(new MySqlConnectionMaker());
			
			try {//여기도 오류시 팝업을 생성하도록 하자... 팝업생성클래스 참조
				f_dao.add(userName, fid);
				//if success update observable list
				mylist.add("fid");
				//창을 닫고 성공 팝업메시지를 띄우자 느낌표 아이콘 필요
				
			} catch (ClassNotFoundException e) {
				// 실패 메시지 팝업 (x 아이콘)
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		pStage.close();
	}
	
	
	//MainMenuController에서 ObservableList획득
	public void setList(ObservableList<String> mylist){
		this.mylist = mylist;
	}
	//username setter
	public void setName(String name){
		this.userName = name;
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
