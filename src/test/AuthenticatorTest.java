package test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import iconicdata.Authenticator;
import iconicdata.MyUser;

public class AuthenticatorTest {

	@Test
	public void test() throws ClassNotFoundException, SQLException {
		MyUser user = new MyUser();
		
		assertTrue(Authenticator.validate("mucky", "1234", user));
		
//		System.out.println(user==null);
		System.out.println(user.getId());
		System.out.println(user.getIp());
//		assertTrue(user==null);
		assertEquals("mucky",user.getId());
		assertEquals("1234",user.getPassword());
		
		
	}

}
