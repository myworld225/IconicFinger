package iconicdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//CRUD OPERATIONS
public class MyUserDao {
	
	private ConnectionMaker connectionMaker;
	
	public void setConnectionMaker(ConnectionMaker connectionMaker){
		this.connectionMaker = connectionMaker;
	}
	//CREATE
	public void add(MyUser user) throws ClassNotFoundException, SQLException{
		
		Connection c  = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement(
				"insert into user(id,password) values(?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getPassword());
		
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	//READ
	public MyUser get(String id) throws ClassNotFoundException, SQLException{
		
		Connection c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement(
				"select * from user where id=?");
		ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		MyUser user = new MyUser();
		user.setId(rs.getString("id"));
		user.setPassword(rs.getString("password"));
		
		return user;
	}
	//UPDATE
	public void update(MyUser user) throws ClassNotFoundException, SQLException{
		
	}
	//DELETE
	public void delete(String id) throws ClassNotFoundException, SQLException{
		
	}
	
	//AUTEHNTICATOR (get 기능의 간단한 변형) 내부에서 loggedUser(MainApp의 MyUser 변수)의 초기화도 담당
	public boolean authenticate(String userId, String password, MyUser loggedUser) throws ClassNotFoundException, SQLException{
		
		Connection c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement(
				"select * from user where id=? and password=?");
		ps.setString(1, userId);
		ps.setString(2, password);
		
		ResultSet rs = ps.executeQuery();
		if(rs.next()){//있는 경우 loggedUser 정보 초기화
//			loggedUser = new MyUser();
			loggedUser.setId(rs.getString("id"));
			loggedUser.setPassword(rs.getString("password"));
			return true;
		} else{ 
//			loggedUser = null; // authenticate 실패시 loggedUser는 다시 null 할당.
			System.out.println("in authenticate : loggedUser = " + (loggedUser==null));
			return false;
		}
	}
}
