package test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import iconicdata.MySqlConnectionMaker;
import iconicdata.MyUser;
import iconicdata.MyUserDao;

public class UserDaoTest {

	@Test
	public void testAdd() throws ClassNotFoundException, SQLException {
		MyUserDao dao = new MyUserDao();
		dao.setConnectionMaker(new MySqlConnectionMaker());
		MyUser user = new MyUser();
		MyUser user2 = null;
		user.setId("durky");
		user.setPassword("1234"); //null시 어떤 에러가 발생할 것인가?
		
		dao.add(user);
		user2 = dao.get("durky");
		
		assertEquals("durky",user2.getId());
	}

}
