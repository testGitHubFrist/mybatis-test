package pool;
 
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
/**
 * 初始化，模拟加载所有的配置文件
 * @author Ran
 *
 */
public class DBInitInfo {
	public  static List<DBbean>  beans = null;
	private static final String PFILE = "/Users/user/workspace1/mybatis/src/main/resources/jdbcPool.properties";
	static{
		beans = new ArrayList<DBbean>();
		DBbean bean = new DBbean();
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream(new File(PFILE)));
			// 加载配置
			String driverName = properties.getProperty("driverName");
			bean.setDriverName(driverName);
			String url = properties.getProperty("url");
			bean.setUrl(url);
			String userName = properties.getProperty("userName");
			bean.setUserName(userName);
			String password = properties.getProperty("password");
			bean.setPassword(password);
			String poolName = properties.getProperty("poolName");
			bean.setPoolName(poolName);
			int minConnections = Integer.valueOf(properties.getProperty("minConnections"));
			bean.setMinConnections(minConnections);
			int maxConnections = Integer.valueOf(properties.getProperty("maxConnections"));
			bean.setMaxConnections(maxConnections);
			int initConnections = Integer.valueOf(properties.getProperty("initConnections"));
			bean.setInitConnections(initConnections);
			long connTimeOut = Long.parseLong(properties.getProperty("connTimeOut"));
			bean.setConnTimeOut(connTimeOut);
			int maxActiveConnections = Integer.valueOf(properties.getProperty("maxActiveConnections"));
			bean.setMaxActiveConnections(maxActiveConnections);
			long connectionTimeOut = Long.parseLong(properties.getProperty("connectionTimeOut"));
			bean.setConnectionTimeOut(connectionTimeOut);
			boolean isCurrentConnection = Boolean.valueOf(properties.getProperty("isCurrentConnection"));
			bean.setCurrentConnection(isCurrentConnection);
			boolean isCheakPool =  Boolean.valueOf(properties.getProperty("isCheakPool"));
			bean.setCheakPool(isCheakPool);
			long lazyCheck = Long.parseLong(properties.getProperty("lazyCheck"));
			bean.setLazyCheck(lazyCheck);
			long periodCheck = Long.parseLong(properties.getProperty("periodCheck"));
			bean.setPeriodCheck(periodCheck);
		} catch (Exception e) {
			e.printStackTrace();
		}
		beans.add(bean);
	}
}
