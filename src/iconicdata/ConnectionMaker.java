package iconicdata;

import java.sql.Connection;
import java.sql.SQLException;
/**
 * DB connection 획들을 위한 인터페이스
 * @author Root
 *
 */
public interface ConnectionMaker {
	public Connection makeConnection()throws ClassNotFoundException, SQLException;
}
