package edu.ynu.util;
import edu.ynu.entity.TeacherEntity;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {
	
	private PreparedStatement ps=null;
	private  Statement stmt=null;
	private ResultSet rs= null;
	private String url="";
	Connection conn=null;

	public DBUtil(){
		Properties prop = new Properties();
		InputStream in = getClass().getResourceAsStream("/project.properties");
		try{
			prop.load(in);
		}catch (IOException e){
			e.printStackTrace();
		}
		String dbname = prop.getProperty("jdbc.jdbcUrl1");
		String username = prop.getProperty("jdbc.user1");
		String password = prop.getProperty("jdbc.password1");
		url =  dbname;
		try{
	        Class.forName("com.mysql.jdbc.Driver");
	        conn=DriverManager.getConnection(url,username,password);
	      }
	        catch(ClassNotFoundException e){
	        e.printStackTrace();
	        }
		catch(SQLException e){
			System.out.println("Failed to get connection :"+e.getMessage());
		    e.printStackTrace();
		}	
	}
	
	public ResultSet Query(String sql){
		try{
			stmt=conn.createStatement();
			rs= stmt.executeQuery(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
	    return rs;
	}
	
	public ResultSet query(String sql, String[] paras) {
		try {
			ps=conn.prepareStatement(sql);
			for (int i = 0; i < paras.length; i++) {
		        ps.setString(i+1, paras[i]);		
			}
			rs = ps.executeQuery();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return rs;
	}
	
	public int update(String sql, String[] paras) {
		int value=0;
		try {
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < paras.length; i++) {
				ps.setString(i+1, paras[i]);
			}
			value = ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return value;
	}
	
	public void update(String sql){
		try{
			stmt=conn.createStatement();
			stmt.executeUpdate(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public TeacherEntity findTeacherByUIDAndPwd(String username, String pwd){
		DBUtil dbUtil = new DBUtil();
		TeacherEntity teacher = new TeacherEntity();
		String sql="SELECT * FROM jijianjiancha.Discipline_teacher where jsgh= ? and mm= ?";
		String para[] = {username, pwd};
		try{
			ResultSet rs= dbUtil.query(sql,para);
			if(rs.next()){
				teacher.setUserId(rs.getString("jsgh"));
				teacher.setPassword(rs.getString("mm"));
				teacher.setName(rs.getString("lsmc"));
				teacher.setAcademy(rs.getString("xy"));
				return teacher;
			}else{
				return null;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			dbUtil.close();
		}
		return null;
	}
	
	public void close(){
		try{
			if(rs!=null)
			    rs.close();
			if(stmt!=null)
			    stmt.close();
			if(conn!=null)
			    conn.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
