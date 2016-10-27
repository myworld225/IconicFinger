package iconicui;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import iconicdata.FriendListDao;
import iconicdata.MySqlConnectionMaker;

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
    
    @FXML
    public void addFriend(){
    	
    	Dialog dialog = new TextInputDialog();
    	dialog.setTitle("ADDFRIEND");
    	dialog.setHeaderText("친구 이름 입력");
    	
    	FriendListDao fDao = new FriendListDao();
    	fDao.setConnectionMaker(new MySqlConnectionMaker());
    	
    	Optional<String> result = dialog.showAndWait();
    	if(result.isPresent()){
    		try {
				fDao.add(application.getLoggedUser().getId(), result.get());
				System.out.println("friend : " + result.get() + " added");
				items.add(result.get());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    } // database update action + observable array edit
    
    public void delFriend(){}
    
    @FXML
    public void logOut(){
    	application.logOutProcess();
    }

}
