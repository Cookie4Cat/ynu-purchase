package edu.ynu.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class DBUtil {
	
	private PreparedStatement ps=null;
	private  Statement stmt=null;
	private ResultSet rs= null;
	private String url="jdbc:mysql://113.55.12.93:3306/";
	Connection conn=null;
	
	public DBUtil(String dbname, String username, String password){
		url = url + dbname + "?useUnicode=true&characterEncoding=UTF-8";
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
    public static void main(String arg[]){
    	DBUtil dbUtil =new DBUtil("jijianjiancha","root","root");
    	String sql="SELECT * FROM jijianjiancha.Discipline_teacher where lsmc='李浩'";
    	try{
    	ResultSet rs= dbUtil.Query(sql);
    	while(rs.next()){
			System.out.print(rs.getString("lsmc") + " ");
    	}
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
		System.out.println();
    	String string = "A quick fox jump over the lazy dog";
    }
}
