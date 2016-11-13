package iconicui;

import java.net.Inet4Address;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import customdialog.ErrorPopup;
import iconicdata.MySqlConnectionMaker;
import iconicdata.MyUser;
import iconicdata.MyUserDao;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
/**
 * 회원가입 화면을 담당하는 컨트롤러
 * 사용되는 fxml : SignUp2.fxml
 * @author Root
 *
 */
public class SignUpController implements Initializable {
	// 11-06
	@FXML
	Button close;
	@FXML
	Button minimize;
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
	@FXML
	Label errorMessage;
	@FXML
	ImageView _imageX;

	// for db connection
	private MyUserDao dao;

	private MainApp application;// inject from MainApp class

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		userID.setPromptText("ID");
		password.setPromptText("PASSWORD");
		passwordCheck.setPromptText("PASSWORD CHECK");
		signUp.setDisable(true);// 11-11추가
		// 11-12추가 ( image에 툴팁 설치 후 보이지 않게 설정 )
		errorMessage.setOpacity(0);
		Tooltip.install(_imageX, new Tooltip("패스워드 오류"));
		_imageX.setOpacity(0);
		_imageX.setDisable(true);
	}

	public void setApp(MainApp application) {

		this.application = application;

	}

	// 마지막 입력 필드(패스워드체크) 에서 엔터키를 입력 시
	@FXML
	public void handleKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			// 엔터가 입력되면 회원 등록 절차가 시작 되게 된다.
			// 체크해야 하는것: pw와 pw체크가 일치하는지? -- pwCheck 라벨의 값으로 측정 가능하다.
			if (pwCheck.getText() == "checked" && dupCheck.getText() == "checked")
				signUpButton();
		}
	}

	// 아이디 중복 체크 버튼
	@FXML
	public void handleKeyPressed2(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			checkDup();
		}
	}

	@FXML
	public void hadleKeyReleased(KeyEvent event) {
		String p1 = password.getText();
		String p2 = passwordCheck.getText();

		if (p1.equals(p2) && p1.length() >= 10) {// 둘다 노리끼리한 색으로 // 그렇지않으면 불그스름
			pwCheck.setText("checked");

			// passwordCheck.setStyle("-fx-background-color: transparent, #ffffff,
			// transparent, #ffffcc;");
			// password.setStyle("-fx-background-color: transparent, #ffffff,
			// transparent, #ffffcc;");
			// 11-11추가
			if (dupCheck.getText() == "checked") {
				signUp.setDisable(false);
			}
			errorMessage.setOpacity(0);
			_imageX.setOpacity(0);
			_imageX.setDisable(true);
		} else {
			pwCheck.setText("incorrect");
			// password.setStyle("-fx-background-color: transparent, #909090,
			// transparent, #ffffff;");
			signUp.setDisable(true);

			// passwordCheck.setStyle("-fx-background-color: #ffcccc;");
			if (p1.length() < 10) {
				errorMessage.setText("비밀번호가 너무 짧습니다.");
			} else {
				errorMessage.setText("비밀번호가 일치하지 않습니다.");
			}
			errorMessage.setOpacity(1);
			_imageX.setOpacity(1);
			_imageX.setDisable(false);

			if (p2.length() == 0 && p1.length() >= 10) {
				errorMessage.setOpacity(0);
				_imageX.setOpacity(0);
				_imageX.setDisable(true);
			} else if (p1.length() == 0 && p2.length() == 0) {
				errorMessage.setOpacity(0);
				_imageX.setOpacity(0);
				_imageX.setDisable(true);
			}
		}
	}

	// 11-12 비밀번호 입력창 추가 함수
	@FXML
	public void handelKeyReleased2(KeyEvent event) {
		String p1 = password.getText();
		if (p1.length() == 0) {
			errorMessage.setOpacity(0);
			_imageX.setOpacity(0);
			_imageX.setDisable(true);
		} else if (p1.length() < 10 || p1.length() > 20) {
			errorMessage.setText("비밀번호가 너무 짧습니다.");
			errorMessage.setOpacity(1);
		} else {
			errorMessage.setOpacity(0);
		}
	}

	// check button : db에서 해당 아이디 중복 확인
	@FXML
	public void checkDup() {
		// db code~
		dao = new MyUserDao();
		dao.setConnectionMaker(new MySqlConnectionMaker());
		try {
			if (dao.dupCheck(userID.getText())) {
				dupCheck.setText("checked");
				// dupCheck.setTextFill(Color.GREEN);
				if (pwCheck.getText() == "checked") {
					signUp.setDisable(false);
				}
				userID.setStyle("-fx-background-color: transparent, #ffffff, transparent, #ffffcc;");
			} else {
				dupCheck.setText("dup__ID");
				signUp.setDisable(true);
				// dupCheck.setTextFill(Color.RED);
				userID.setStyle("-fx-background-color: #ffcccc;");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dao = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dao = null;
		}
		dao = null;
	}

	// sign up 버튼의 동작
	@FXML
	public void signUpButton() {
		// dbcheck
		// 오류창 또는 성공화면
		// 성공 -> 로그인 화면 / 실패 -> 그대로(팝업창에 뭐가 오류인지 밝힌다)
		if ((pwCheck.getText() != "checked") || (dupCheck.getText() != "checked")) {
			// error dialog(다이얼로그는 추후 custom dialog로 변경 가능 )
			Stage primaryStage = (Stage) close.getScene().getWindow();

			ErrorPopup.showPopup(primaryStage, "유효하지 않은 입력입니다");

			return;
		}

		dao = new MyUserDao();
		dao.setConnectionMaker(new MySqlConnectionMaker());

		MyUser user = new MyUser();
		user.setId(userID.getText());
		user.setPassword(password.getText());
		try {
			user.setIp(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e1) {
			System.err.println("UnknownHostException");
			e1.printStackTrace();
		}
		try {
			dao.add(user);
			System.out.println("added");
			application.insertUser();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			Stage primaryStage = (Stage) close.getScene().getWindow();

			ErrorPopup.showPopup(primaryStage, "회원등록 오류");
			dao = null;
			// e.printStackTrace();
		}

		dao = null;
	}

	// back 버튼의 동작
	@FXML
	public void backButton() {
		// 그냥 백
		application.insertUser();
	}

	// 11-06
	// close and minimize button function.
	@FXML
	public void close() {
		final Stage stage = (Stage) close.getScene().getWindow();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			}
		});

	}

	@FXML
	public void minimize() {

		if (!Platform.isFxApplicationThread()) // Ensure on correct thread else
																						// hangs X under Unbuntu
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
		Stage stage = (Stage) minimize.getScene().getWindow();
		stage.setIconified(true);
	}

}
