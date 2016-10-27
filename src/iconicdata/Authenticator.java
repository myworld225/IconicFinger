package iconicdata;

import java.sql.SQLException;

public class Authenticator {
	public static boolean validate(String userId, String password, MyUser loggedUser) throws ClassNotFoundException, SQLException{
		MyUserDao userdao = new MyUserDao();
		userdao.setConnectionMaker(new MySqlConnectionMaker());
		 
		return userdao.authenticate(userId, password, loggedUser);
	}
}
