package iconicdata;

import java.net.Inet4Address;
import java.net.UnknownHostException;
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
				"insert into user(id,password,ip) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getPassword());
		ps.setString(3, user.getIp());
		ps.executeUpdate();
		System.out.println(user.getId() + "added");
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
		user.setIp(rs.getString("ip"));
		System.out.println(user.getId() + "retrieved");
		return user;
	}
	//UPDATE
	public void updateIp(String id, String ip) throws ClassNotFoundException, SQLException{
		
		Connection c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement(
				"update user set ip=? where id=?");
		ps.setString(1, ip);
		ps.setString(2, id);
		
		ps.executeUpdate();
		System.out.println(id+"'s" + " ip changed");
		ps.close();
		c.close();
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
		if(rs.next()){//있는 경우 loggedUser 정보 초기화 + 로그인한 pc의 ip주소로 업데이트
//			loggedUser = new MyUser();
			loggedUser.setId(rs.getString("id"));
			loggedUser.setPassword(rs.getString("password"));
			
			try {
				updateIp(rs.getString("id"),Inet4Address.getLocalHost().getHostAddress());
				loggedUser.setIp(Inet4Address.getLocalHost().getHostAddress());
			} catch (UnknownHostException e) {
				System.err.println("UnknownHostException");
				e.printStackTrace();
			}
			rs.close();
			ps.close();
			c.close();
			return true;
		} else{ 
//			loggedUser = null; // authenticate 실패시 loggedUser는 다시 null 할당.
			System.out.println("in authenticate : loggedUser = " + (loggedUser==null));
			rs.close();
			ps.close();
			c.close();
			return false;
		}
		
		
	}
	
	//SignUpController에 쓰이는 회원 중복 확인 용 메소드(authenticate와 겹치는 부분이 있긴하다.)
	public boolean dupCheck(String userId) throws SQLException, ClassNotFoundException{
		
		Connection c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement(
				"select * from user where id=?");
		ps.setString(1, userId);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			return false;
		} else {
			return true;
		}
		
	}
	
}
