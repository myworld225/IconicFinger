package customdialog;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import iconicdata.FriendListDao;
import iconicdata.MySqlConnectionMaker;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * DialogController
 * MainMenu에서 friend add/del 기능에 사용되는 컨트롤러 클래스
 * 사용되는 fxml파일 : blackinputdialog.fxml
 * */
public class DialogController implements Initializable {

	@FXML
	TextField text;
	@FXML
	Button ok;
	@FXML
	Button cancel;
	@FXML
	AnchorPane dragable;

	private String userName;// 사용자의 아이디
	private ObservableList<String> mylist;
	private Stage parentStage;

	@FXML
	public void cancelButton() {// close the dialog.
		Stage pStage = (Stage) cancel.getScene().getWindow();
		text.setText("");
		pStage.hide();
	}

	// 일단 add용만 먼저 만들어 두자.
	@FXML
	public void actionButton() {
		Stage pStage = (Stage) cancel.getScene().getWindow();
		System.out.println(text.getText().isEmpty());// true when textfield is empty
		System.out.println(text.getText() == null);// usually false
		if (!(text.getText() == null || text.getText().trim().isEmpty())) {
			String fid = text.getText();
			FriendListDao f_dao = new FriendListDao();
			f_dao.setConnectionMaker(new MySqlConnectionMaker());

			try {// 여기도 오류시 팝업을 생성하도록 하자... 팝업생성클래스 참조
				if (ok.getText() == "Add") {
					f_dao.add(userName, fid);
					// if success update observable list
					mylist.add(fid);
					System.out.println("added!");// notification
				} else if (ok.getText() == "Del") {
					f_dao.del(userName, fid);

					if (mylist.contains(fid)) {
						mylist.remove(fid);
						System.out.println("deleted!");
					}
				}

				// 창을 닫고 성공 팝업메시지를 띄우자 느낌표 아이콘 필요
			} catch (ClassNotFoundException e) {
				
				// 실패 메시지 팝업 (x 아이콘)
				System.out.println("ClassNotFoundException");
				ErrorPopup.showPopup(pStage, "ClassNotFound");
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//이미 친구 추가되어있고 mylist에는 없다면 추가 해준다 ( 이 경우는 상대가 먼저 친구 추가 했지만 내 ui에는 업데이트 되지 않은 경우 )
				if (e instanceof MySQLIntegrityConstraintViolationException){
					System.out.println("integrityConstraint");
					if (!mylist.contains(fid)){
						mylist.add(fid);
					}
				}
				System.out.println("SQLException");
				ErrorPopup.showPopup(pStage, "DB 오류");
				e.printStackTrace();
			}
		}
		text.setText("");
		// pStage.close();
	}

	// MainMenuController에서 ObservableList획득
	public void setList(ObservableList<String> mylist) {
		this.mylist = mylist;
	}

	// username setter
	public void setUserName(String name) {
		this.userName = name;
	}

	public void setParentStage(Stage stage) {
		this.parentStage = stage;
	}

	// for toggle
	public void setButtonText(String text) {
		ok.setText(text);
	}

	public void printList() {
		for (String i : mylist) {
			System.out.println(i);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		// 현재 UI의 stage를 갖고온다.
		// 이걸 따로 메소드로 만들고 밖에서 호출해서 세팅하자... show전에...
		text.setPromptText("userID");
	}

}
