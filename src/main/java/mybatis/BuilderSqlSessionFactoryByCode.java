package mybatis;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import mapper.RoleMapper;
import pojo.Role;

/**
 * 硬代码构建SqlSessionFactory
 * @author user
 *
 */
public class BuilderSqlSessionFactoryByCode {

	private static Logger logger = Logger.getLogger(BuilderSqlSessionFactoryByCode.class);
	public static void main(String[] args) {
		logger.info("硬代码构建sqlSessionFactory begin ");
		//构建数据库连接池  <dataSource>
		PooledDataSource dataSource  = new PooledDataSource();
		dataSource.setDriver("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/mybatis_test?zeroDateTimebehavior=convertToNull");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		//构建数据库事物方式<transactionManager type="JDBC" />
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		//创建数据库运行环境<environments default="development">
		Environment environment = new Environment("development", transactionFactory, dataSource);
		//构建configuration
		Configuration configuration = new Configuration(environment);
		//注册一个mybatis上下文别名
		configuration.getTypeAliasRegistry().registerAlias("role",Role.class);
		//加入映射器
		configuration.addMapper(RoleMapper.class);
		//构建sqlSessionFactory
		SqlSessionFactory sessionFactory =new SqlSessionFactoryBuilder().build(configuration);
	}
}
