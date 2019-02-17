package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.alibaba.fastjson.JSON;

import pojo.Role;

/**
 * 从代码中我们可以看出整个过程大致分为以下几步
 * （1）使用JDBC编程需要连接数据库，注册驱动和数据库信息
 * （2）操作Connection ，打开Statement对象
 * （3）通过Statement执行sql，返回结果到ResultSet对象。
 * （4）使用ResultSet读取数据，然后通过代码转化具体的pojo对象
 * （5）关闭数据库相关资源
 * @author user
 *
 */
public class JDBCExample {

	/**
	 * 获取数据库链接
	 * 
	 * @return
	 */
	private Connection getConnection() {
		Connection connection = null;
		try {
			// 加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/mybatis_test?zeroDateTimebehavior=convertToNull";
			String user = "root";
			String password = "root";
			// 获取连接
			connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException ex) {
			System.out.println("获取数据库连接异常：" + ex.getMessage());
			return null;
		}
		return connection;
	}

	public List<Role> getRole(Long id) {
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Role> list = new ArrayList<>();
		try {
			ps = conn.prepareStatement("select id,role_name,note from t_role where id = ?");
			ps.setLong(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				Long ids = rs.getLong("id");
				String roleName = rs.getString("role_name");
				String note = rs.getString("note");
				Role role = new Role();
				role.setId(ids);
				role.setNote(note);
				role.setRoleName(roleName);
				list.add(role);
			}
		} catch (Exception e) {
		} finally {
			this.close(rs, ps, conn);

		}
		return list;
	}

	private void close(ResultSet rs, Statement stmt, Connection connection) {
		try {
			if(rs !=null && !rs.isClosed()) {
				rs.close();
			}
				
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			if(stmt !=null && !stmt.isClosed()) {
				stmt.close();
			}
				
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			if(connection !=null && !connection.isClosed())
			{
				connection.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void main(String[] args) {
		JDBCExample example = new JDBCExample();
		List<Role> list = example.getRole(1L);
		System.out.println(JSON.toJSON(list));
	}
}
