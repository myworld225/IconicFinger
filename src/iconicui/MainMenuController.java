package iconicui;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by user on 2016-09-25.
 * 추가해야 할 것들 : 각 버튼에대한 행동처리 대부분은 MainApp에서 처리하게 된다.(위임?)
 */
public class MainMenuController implements Initializable {

    @FXML
    private Button option;
    @FXML
    private Button videoCall;
    @FXML
    private AnchorPane navList;

    private MainApp application;

    public void setApp(MainApp application){

        this.application = application;
        // 여기서 User 정보를 획득하여 ListView(친구탭)을 초기화 하게 된다.

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prepareSlideMenuAnimation();
    }

    //(10/26)빠질 예정인 슬라이드 기능
    private void prepareSlideMenuAnimation(){

        TranslateTransition openNav = new TranslateTransition(new Duration(350), navList);
        openNav.setToX(0);
        TranslateTransition closeNav = new TranslateTransition(new Duration(350), navList);

        option.setOnAction( e-> {
            if(navList.getTranslateX()!=0){
                openNav.play();
            } else {
                closeNav.setToX(-(navList.getWidth()));
                closeNav.play();
            }
        });

    }

    /*****************************************************************************
     * 각 버튼에 대한 처리 함수들(총 4가지   화상통화 / 채팅 / 친구추가 / 로그아웃).
     ******************************************************************************/

    //화상통화 실행 코드를 삽입 할 장소
    @FXML
    public void videoCall(){
    	System.out.println("Hello World!");
    }
    
    
    public void addFriend(){} // database update action + observable array edit
    
    public void delFriend(){}
    
    @FXML
    public void logOut(){
    	application.logOutProcess();
    }

}
