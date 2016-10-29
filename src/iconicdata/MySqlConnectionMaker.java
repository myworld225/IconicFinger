package iconicdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/*
 * DB 연결을 위한 클래스
 * Class.forName의 매개변수와 DriverManager.getConnection의 매개변수에 사용하는 db의 정보를 입력하면된다.
 * 
 * */
public class MySqlConnectionMaker implements ConnectionMaker{

	@Override
	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/modeldb","root","qkrrl1223");
		return connection;
	}
	
}
