package test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import iconicdata.FriendListDao;
import iconicdata.MySqlConnectionMaker;
//FriendListDao.getAllFriends() 테스트
public class GetAllFriendTest {
	//동작 완료?
	@Test
	public void test() throws ClassNotFoundException, SQLException {
		FriendListDao dao = new FriendListDao();
		dao.setConnectionMaker(new MySqlConnectionMaker());
		
		List<String> myFriends;
		
		myFriends = dao.getAllFriends("ducky");
		for (String s : myFriends){
			System.out.println(s);
		}
		assertTrue(myFriends.size() == 0);
	}

}
