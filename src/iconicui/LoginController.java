package iconicui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import customdialog.ErrorPopup;

/**
 * 11/06 undecorator 세팅
 */
public class LoginController implements Initializable{

    @FXML
    TextField userID;
    @FXML
    PasswordField password;
    @FXML
    Button login;
    @FXML
    Button signUp;
    @FXML
    Label errorMessage;
    //11-06
    @FXML
    Button minimize;
    @FXML
    Button close;

    private MainApp application;

    public void setApp(MainApp application){

        this.application = application;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        errorMessage.setText("");
        userID.setPromptText("ID");
        password.setPromptText("PASSWORD");
    }

    @FXML
    public void processLogin(){

        if(application == null){
            System.out.println("시스템 오류의 경우(발생해서는 안되는 경우이다)");
        } else {
            //MainApp 클래스에서 LoginController 클래스의 userID 와 password 를 갖고 db연결을 통해 비교하여 연결이 올바른지 파악 후 처리
            if(!application.userLogging(userID.getText(), password.getText())){
                //error 오류 다이얼로그 생성
//            	Alert alert = new Alert(AlertType.ERROR);
//            	alert.setTitle("로그인 오류");
//            	alert.setHeaderText("oops!");
//            	alert.showAndWait();
            	//대신 오류 팝업
            	Stage primaryStage = (Stage) login.getScene().getWindow();
          		
          		ErrorPopup.showPopup(primaryStage, "로그인 오류");
          	
            }
        }

    }

    @FXML
    public void processSignUp(){
    	
    	if(application == null){
    		System.out.println("시스템 오류의 경우(발생해서는 안되는 경우이다)");
    	} else {
    		application.userSignUp();    		
    	}
    }
    
    @FXML
	public void handleKeyPressed(KeyEvent event){
	    if (event.getCode() == KeyCode.ENTER) {
	        processLogin();
	    }
	}
    
    
    //11-06
    //close and minimize button function.
    @FXML
    public void close() {
      final Stage stage = (Stage)close.getScene().getWindow();
      Platform.runLater(new Runnable() {
          @Override
          public void run() {
              stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
          }
      });

    }
    @FXML
    public void minimize() {

      if (!Platform.isFxApplicationThread()) // Ensure on correct thread else hangs X under Unbuntu
      {
          Platform.runLater(new Runnable() {
              @Override
              public void run() {
                  _minimize();
              }
          });
      } else {
          _minimize();
      }
    }

    private void _minimize() {
      Stage stage = (Stage)minimize.getScene().getWindow();
      stage.setIconified(true);
    }
    
  
 
}
