package pool;

import java.sql.Connection;
import java.sql.SQLException;

public class Client {
	public static void main(String[] args) throws InterruptedException, SQLException {
		IConnectionPool pool= ConnectionPoolManager.getInstance().getPool("testPool");
		Connection coon1=pool.getConnection();
		pool.releaseConn(coon1);
	}
}
