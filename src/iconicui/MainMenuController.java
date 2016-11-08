package iconicui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import customdialog.DialogController;
import customdialog.DraggableFactory;
import customdialog.MainController;
import iconicdata.FriendListDao;
import iconicdata.MySqlConnectionMaker;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * Created by user on 2016-09-25.
 * 추가해야 할 것들 : 각 버튼에대한 행동처리 대부분은 MainApp에서 처리하게 된다.(위임?)
 */
public class MainMenuController implements Initializable {
		
		@FXML
		private Button close;
		@FXML
		private Button minimize;
    @FXML
    private Button option;
    @FXML
    private Button videoCall;
    @FXML
    private AnchorPane navList;
    @FXML
    private ListView<String> friendlist;

    private MainApp application;
    
    private ObservableList<String> items;//for friendlist

    public void setApp(MainApp application){

        this.application = application;
        // 여기서 User 정보를 획득하여 ListView(친구탭)을 초기화 하게 된다.

    }
    
    public void setFriends(String userID){
    	//items에 채워넣음....
    	FriendListDao fDao = new FriendListDao();
    	fDao.setConnectionMaker(new MySqlConnectionMaker());
    	try {
			List<String> f_list = fDao.getAllFriends(userID);
			if(f_list.size() != 0){
				for(String s : f_list){
//					System.out.println(s);
					items.add(s);
				}
			}// if f_list.size() equals 0, there is no items for add
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //여기서 ListView의 아이템을 세팅해준다 그 이후 MainApp에서 setApp과 setFriends를 호출해준다.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	items=FXCollections.observableArrayList();
    	friendlist.setItems(items);
        prepareSlideMenuAnimation();
    }

    //(10/26)빠질 예정인 슬라이드 기능
    private void prepareSlideMenuAnimation(){

        TranslateTransition openNav = new TranslateTransition(new Duration(350), navList);
        openNav.setToX(3);
        TranslateTransition closeNav = new TranslateTransition(new Duration(350), navList);

        option.setOnAction( e-> {
            if(navList.getTranslateX()!=3){//수정
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
    
    @FXML
    public void addFriend(){
    	//내 스테이지를 얻고, 이것을 owner로 하는 stage생성
    	Stage myStage = (Stage)close.getScene().getWindow();
    	Stage dialogStage = new Stage(StageStyle.UNDECORATED);
    	dialogStage.initModality(Modality.WINDOW_MODAL);
    	dialogStage.initOwner(myStage);
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(MainController.class.getResource("../customdialog/blackinputdialog.fxml"));
    	try {
				Parent parent = loader.load();
				//DialogController로 내부에서 필요한 정보인 observablelist의 레퍼런스와 userid/name 을 획득한다.
				DialogController dialogController = (DialogController)loader.getController();
				dialogController.setParentStage(myStage);
				dialogController.setList(items);
				dialogController.setUserName(application.getLoggedUser().getId());
				dialogController.setButtonText("Add");
				DraggableFactory.makeDraggable(dialogStage, parent);
				
				
				Scene scene = new Scene(parent);
				dialogStage.setScene(scene);
				dialogStage.setResizable(false);
				dialogStage.showAndWait();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    	
    	
    } // database update action + observable array edit
    
    //ㅌㅔ 스트 필요
    @FXML
    public void delFriend(){
    //내 스테이지를 얻고, 이것을 owner로 하는 stage생성
    	Stage myStage = (Stage)close.getScene().getWindow();
    	Stage dialogStage = new Stage(StageStyle.UNDECORATED);
    	dialogStage.initModality(Modality.WINDOW_MODAL);
    	dialogStage.initOwner(myStage);
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(MainController.class.getResource("../customdialog/blackinputdialog.fxml"));
    	try {
				Parent parent = loader.load();
				//DialogController로 내부에서 필요한 정보인 observablelist의 레퍼런스와 userid/name 을 획득한다.
				DialogController dialogController = (DialogController)loader.getController();
				dialogController.setParentStage(myStage);
				dialogController.setList(items);
				dialogController.setUserName(application.getLoggedUser().getId());
				dialogController.setButtonText("Del");
				DraggableFactory.makeDraggable(dialogStage, parent);
				
				
				Scene scene = new Scene(parent);
				
				dialogStage.setScene(scene);
				dialogStage.setResizable(false);
				dialogStage.showAndWait();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    
    @FXML
    public void logOut(){
    	application.logOutProcess();
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
    
    //LEAP-A 및 LEAP-B를 위한 코드 필요

}
