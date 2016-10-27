package test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import iconicdata.Authenticator;
import iconicdata.MyUser;

public class UserLoggingTest {

	//loggeduser에 적절한 값이 들어가는가를 테스트...!!
	@Test
	public void test() throws ClassNotFoundException, SQLException {
		MyUser user = new MyUser();
		if(Authenticator.validate("mucky", "1233", user)){
			System.out.println("조회 성공: !!");
		} else{
			user = null;
		}
		
		assertTrue(user == null);
//		assertEquals(user.getId(),"mucky");
//		assertEquals(user.getPassword(),"1234");
	}

}
