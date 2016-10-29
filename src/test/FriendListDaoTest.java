package test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import iconicdata.FriendListDao;
import iconicdata.MySqlConnectionMaker;
public class FriendListDaoTest {
	public FriendListDao friendListDao;
	public String str1;
	public String str2;
	@Before
	public void setUp(){
		friendListDao = new FriendListDao();
		friendListDao.setConnectionMaker(new MySqlConnectionMaker());
		str1 = "mucky";
		str2 = "fucky"; 
	}
	
	//적절한 예외를 발생시키는지에 대한 테스트
	@Test//(expected=SQLException.class)
	public void add() throws ClassNotFoundException, SQLException{
		friendListDao.add(str1, str2);
		assertTrue(friendListDao.isFriend(str1, str2));
	}
//	@Test
//	public void del(){
//		try {
//			friendListDao.del("mucky", "cocky");
//			assertTrue(true);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
