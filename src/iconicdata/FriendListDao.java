package iconicdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendListDao {
	private ConnectionMaker connectionMaker;

	public void setConnectionMaker(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

	// 외부에서 sqlexception 처리를 하여 다이얼로그를 생성한다.(없는 친구라거나... 그런것들)\
	//1026 추가 사항 : myid와  friendid 가 같은 경우 SQLException을 발생
	public void add(String myId, String friendId) throws ClassNotFoundException, SQLException {
		String big;
		String small;
		int i;
		if ((i = myId.compareTo(friendId)) > 0) {
			big = myId;
			small = friendId;
		} else if (i==0) {
			throw new SQLException("id1 and id2 are same");
		} else{
			big = friendId;
			small = myId;
		}

		Connection c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("insert into friendlist(id1, id2) values(?,?)");
		
		ps.setString(1, big);
		ps.setString(2, small);
		ps.executeUpdate();

		ps.close();
		c.close();
	}

	public void del(String myId, String friendId) throws ClassNotFoundException, SQLException {
		String big;
		String small;
		if (myId.compareTo(friendId) > 0) {
			big = myId;
			small = friendId;
		} else {
			big = friendId;
			small = myId;
		}

		Connection c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("delete from friendlist where id1=? and id2=?");
		
		ps.setString(1, big);
		ps.setString(2, small);
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}

	// 내 아이디와 추가할 친구 아아디를 비교하여 큰 값이 id1 컬럼에 저장되도록 하여 중복을 방지한다.
	// 친구관계 테이블에 이미 있는지 확인하는 함수
	//1026의문점: 어차피 같은값을 입력(이미 테이블에있는)하면 Error Code: 1062. Duplicate entry 'mucky-mucky' for key 'PRIMARY'
	//와 같은 오류가 나와서 예외를 발생시키는데 그냥 add에서 처리할 것인가? 아니면 관심사의 분리를 위해 여기서 처리 할 것인가..
	public boolean isFriend(String id1, String id2) throws ClassNotFoundException, SQLException {
		String big;
		String small;
		if (id1.compareTo(id2) > 0) {
			big = id1;
			small = id2;
		} else {
			big = id2;
			small = id1;
		}
		Connection c = connectionMaker.makeConnection();
		PreparedStatement ps = c.prepareStatement("select * from friendlist where id1=? and id2=?");
		ps.setString(1, big);
		ps.setString(2, small);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			rs.close();
			ps.close();
			c.close();
			return true;
		}
		rs.close();
		ps.close();
		c.close();
		return false;
	}
}
