package com.wang.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JDBCUtil {
	public static String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useSSL=false&characterEncoding=utf8&allowPublicKeyRetrieval=true";
	public static String dbusername = "root";
	public static String dbpassword = "123321";

	public static Connection conn =null;
	public static PreparedStatement ps = null;
	public static ResultSet resultSet = null;

	// 静态代码块加载驱动， 驱动只在JDBCUtil类加载的时候执行一次
	static {
		try {
			System.out.println("MySql 驱动被加载....");
			//1.加载驱动
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url,dbusername,dbpassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	// 增删改
	public static int update(String sql,Object[] args) {
		// 获取连接
		Connection conn =getConnection();
		PreparedStatement ps = null;
		int res = 0;
		try {
			//准备sql
			ps = conn.prepareStatement(sql);
			// update user set eamil=?,tel=? where id=?;
			for(int n = 1; n<args.length+1;n++) {
				ps.setObject(n, args[n-1]);
			}
			System.out.println(ps.toString());
			res=ps.executeUpdate();
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}finally {
			// 关闭资源
			closeResource(ps,conn);
		}
	}

	/**
	 * 查询
	 * @param sql
	 * @param args
	 * @return
	 */
	// 最好返回一个 list
	public static  ResultSet select(String sql,Object[] args){
		// 获取连接
		conn =getConnection();
		try {
			//准备sql
			ps = conn.prepareStatement(sql);
			if(args != null)
			{
				for(int n = 1; n < args.length+1;n++) {
					ps.setObject(n, args[n-1]);
				}
			}
			System.out.println(ps.toString());
			resultSet=ps.executeQuery();
			return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			// 关闭资源
			//CloseDbResource.closeResource(res,ps,conn);
		}
	}


	public static  List<Object> selectList(String sql,Object[] args,String classname){
		// 获取连接
		conn =getConnection();
		try {
			//准备sql
			ps = conn.prepareStatement(sql);
			if(args != null)
			{
				for(int n = 1; n<args.length+1;n++) {
					ps.setObject(n, args[n-1]);
				}
			}

			System.out.println(ps.toString());
			resultSet=ps.executeQuery();

			//ResultSetMetaData data = resultSet.getMetaData();
			//int n = data.getColumnCount();
			//data.



			List<Object> list = new ArrayList<Object>();
			while(resultSet.next()) {
				// 获取类的字节码
				Class clazz = null;
				try {
					clazz = Class.forName(classname);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//User
				Object obj = null;
				try {
					obj = clazz.getConstructor().newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				Field[] fields = clazz.getDeclaredFields();//获取User类中的所有属性
				for(int i  = 0; i<fields.length;i++) {
					try {
						fields[i].setAccessible(true);
						// 给obj的fields[i]属性赋值
						fields[i].set(obj, resultSet.getObject(fields[i].getName().toLowerCase()));
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(fields[i].getName());
				}

				list.add(obj);
			}

			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			// 关闭资源
			closeResource(resultSet,ps,conn);
		}
	}


	public static void closeResource(AutoCloseable ...ac) {
		for(int i = 0; i<ac.length;i++) {
			if(ac[i] !=null) {
				try {
					ac[i].close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
