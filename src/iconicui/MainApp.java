package iconicui;

//import iconicdata.Authenticator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import iconicdata.Authenticator;
import iconicdata.MyUser;
import iconicdata.User;
import javafx.stage.StageStyle;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by user on 2016-09-25.
 * 전체를 관장하는 메인 클래스
 */


public class MainApp extends Application {

    private Stage window;
    private Group root = new Group();
    private MyUser loggedUser; //log in 된 유저의 정보를 포함하고 있는 객체


/*****************************************************************************
* 실행 코드
******************************************************************************/
    @Override
    public void start(Stage primaryStage) throws Exception {

        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(createContent(), 352, 584));//362 594 사이즈를 실제보다 약간 줄여서 보기좋게 하였다.
        primaryStage.show();

    }
    public static void main(String[] args){
        launch(args);
    }


/*****************************************************************************
 * 여기서부터는 UI생성 및 변경에 관한 코드이다
******************************************************************************/
    /**
     * 실행시 가장 먼저 생성되는 UI인 LogIn을 구성한다
     * @return Parent
     */
    public Parent createContent(){
        gotoLogin();
//        gotoMainMenu();
        return root;
    }

    private void gotoLogin(){

        try {
            LoginController login = (LoginController)replaceSceneContent("LogIn.fxml");
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void gotoMainMenu(){

        try{
            MainMenuController mainMenu = (MainMenuController)replaceSceneContent("MainMenu.fxml");
            mainMenu.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //signup 화면 생성
    private void gotoSignUp(){
    	
    	try{
    		SignUpController signUp = (SignUpController)replaceSceneContent("SignUp.fxml");
    		signUp.setApp(this);
    	} catch(Exception ex) {
    		Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE,null,ex);
    	}
    }

    private Initializable replaceSceneContent(String fxml) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        InputStream in = MainApp.class.getResourceAsStream(fxml);

        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(MainApp.class.getResource(fxml));

        AnchorPane page;

        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }

        root.getChildren().removeAll();
        root.getChildren().addAll(page);
        return (Initializable) loader.getController(); //fxml 파일의 controller 반환
    }

/*****************************************************************************
 * 아래는 로그인 처리 기능이다.
 * @throws SQLException 
 * @throws ClassNotFoundException 
******************************************************************************/
    public boolean userLogging(String userId, String password){
        try {
        	loggedUser = new MyUser();
			if (Authenticator.validate(userId, password, loggedUser)){//Authenticator.validate(userId,password)
			    //gotomainmenu
			    gotoMainMenu();
			    return true;
			} else {
				loggedUser = null;
			    return false;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        loggedUser = null;
        return false;
    }
/*****************************************************************************
* goto 시리즈가 private 이므로 각 컨트롤러 클래스에서 화면전환을 위해 주입받은
* MainApp object 의 public 메소드로 goto시리즈 함수를 호출하는 식이다.
******************************************************************************/
    //로그인에서 sign up 버튼 클릭 시
    public void userSignUp(){
    	gotoSignUp();
    }
    //메인메뉴에서 로그아웃 버튼 클릭시
    public void logOutProcess(){
    	this.loggedUser = null;//로그아웃
    	gotoLogin();
    }
  //signup 화면에서 signup 버튼 입력 성공시 다시 로그인 메뉴로 가는 함수
    public void insertUser(){
    	gotoLogin();
    }
}
