package iconicdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DB의 사용자 정보를 표현하는 클래스
 * 실제로는 DSD에 있는 Member 테이블을 표현한 클래스를 만들어야 한다.
 */
public class User {
    private String name;
    private String password;
    private int id;
    public User(int id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }

    /**로그인 성공 시 유저의 정보를 담은 User 객체를 MainApp 객체에 설정한다.
     * 여기는 일단 username(id?)가 key인 경우로 가정하였다.*/
//    public static User of(String name){
//        Connection conn = new DBConnection().connect();
//        String sql = "select * from user where username=?";
//        try {
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, name);
//            ResultSet rs = ps.executeQuery();
//            rs.next();
//            return new User(rs.getInt("id"),rs.getString("username"),rs.getString("password"));
//        } catch (Exception ex){
//            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//    }
}
