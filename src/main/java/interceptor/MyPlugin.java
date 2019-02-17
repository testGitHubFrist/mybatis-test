package interceptor;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

/**
 * mybatis插件开发
 * @author user
 *
 */

@Intercepts({@Signature(type=StatementHandler.class, //确定要拦截的对象
             method="prepare",//确定要拦截的方法
             args = {Connection.class}
		)})
public class MyPlugin implements Interceptor {
	
	//限制返回的行数
	private int limit ;

	private String dbType;
	
	private static final String LMT_TABLE_NAME="limit_Table_Name_xxx";
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		//取出被拦截的对象
		StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
		MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
		//分离对象，从而形成多次代理，通过两次循环最原始的被代理对象
		while(metaObject.hasGetter("h")) {
			Object object = metaObject.getValue("h");
			metaObject = SystemMetaObject.forObject(object);
		}
		
		while(metaObject.hasGetter("target")) {
			Object object = metaObject.getValue("target");
			metaObject = SystemMetaObject.forObject(object);
		}
		//取出即将执行的sql
		String sql = (String)metaObject.getValue("delegate.boundSql.sql");
		
		String limitSql;
		if("mysql".equals(this.dbType) && sql.indexOf(LMT_TABLE_NAME) == -1) {
			//去掉空格
			sql = sql.trim();
			limitSql = "select * from ("+sql+")"+LMT_TABLE_NAME +" limit "+ limit ;
			//重新执行sql
			metaObject.setValue("delegate.boundSql.sql", limitSql);
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target,this);
	}

	@Override
	public void setProperties(Properties properties) {

		String strLimit = properties.getProperty("limit","50");
		this.limit = Integer.valueOf(strLimit);
		this.dbType = properties.getProperty("dbType","mysql");
	}

}
