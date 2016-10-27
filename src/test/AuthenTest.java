package test;

import java.sql.SQLException;

import iconicdata.*;

public class AuthenTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		
		MyUser user = new MyUser();
		Authenticator.validate("mucky", "1234", user);
		
		System.out.println(user.getId());
		System.out.println(user.getPassword());
		
		
		
	}
}
