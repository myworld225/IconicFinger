package iconicui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import iconicdata.MySqlConnectionMaker;
import iconicdata.MyUser;
import iconicdata.MyUserDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class SignUpController implements Initializable{

	@FXML
    TextField userID;
    @FXML
    PasswordField password;
    @FXML
    PasswordField passwordCheck;
    @FXML
    Button signUp;
    @FXML
    Button back;
    @FXML
    Button dupCheckButton;
    @FXML
    Label pwCheck;
    @FXML
    Label dupCheck;
	
    //for db connection
    MyUserDao dao;
    
	private MainApp application;//inject from MainApp class
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		userID.setPromptText("ID");
		password.setPromptText("PASSWORD");
		passwordCheck.setPromptText("PASSWORD CHECK");
	}
	
	public void setApp(MainApp application){

        this.application = application;

    }
	
	//마지막 입력 필드(패스워드체크) 에서 엔터키를 입력 시 
	@FXML
	public void handleKeyPressed(KeyEvent event){
	    if (event.getCode() == KeyCode.ENTER) {
	        // 엔터가 입력되면 회원 등록 절차가 시작 되게 된다.
	    	// 체크해야 하는것: pw와 pw체크가 일치하는지? -- pwCheck 라벨의 값으로 측정 가능하다.
	    	if(pwCheck.getText() == "correct")
	    		signUpButton();
	    }
	}
	
	@FXML
	public void hadleKeyReleased(KeyEvent event){
		String p1 = password.getText();
	    String p2 = passwordCheck.getText();
	    
	    if(p1.equals(p2)){
	    	pwCheck.setText("checked");
	    	pwCheck.setTextFill(Color.GREEN);
	    } else {
	    	pwCheck.setText("incorrect");
	    	pwCheck.setTextFill(Color.RED);
	    }
	}
	
	//check button : db에서 해당 아이디 중복 확인
	@FXML
	public void checkDup(){
		//db code~
		dupCheck.setText("checked");
		dupCheck.setTextFill(Color.GREEN);
	}
	
	//sign up 버튼의 동작
	@FXML
	public void signUpButton(){
		//dbcheck 
		//오류창 또는 성공화면
		//성공 -> 로그인 화면 / 실패 -> 그대로(팝업창에 뭐가 오류인지 밝힌다)
		dao = new MyUserDao();
		dao.setConnectionMaker(new MySqlConnectionMaker());
		
		MyUser user = new MyUser();
		user.setId(userID.getText());
		user.setPassword(password.getText());
		
		try {
			dao.add(user);
			System.out.println("added");
			application.insertUser();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	//back 버튼의 동작
	@FXML
	public void backButton(){
		//그냥 백
		application.insertUser();
	}
}
