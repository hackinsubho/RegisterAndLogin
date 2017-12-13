package org.jspiders.registerAndLogin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(urlPatterns="/log",loadOnStartup=5)
public class LoginController extends HttpServlet{
	String driver="com.mysql.jdbc.Driver";
	String pass="pba206";
	String url="jdbc:mysql://localhost:3306";
	String user="root";
	Connection con;
	PreparedStatement ps;
	PrintWriter pw;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pass);
			String qry ="select * from jdbc.logininfo where uname=? and password=?";
			ps= con.prepareStatement(qry);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		pw= resp.getWriter();
	    String UserId= req.getParameter("UserId");
		String password=req.getParameter("password");
	
		try {
			ps.setString(1, UserId);
			ps.setString(2, password);
			ResultSet rs = 	ps.executeQuery();
			if(rs.next()) {
				String out= "<html><body bgcolor=\"yellow\"><h1>Welcome "+rs.getString(3)+"</h1></body></html>";
				pw.println(out);
			}
			else {
				pw.println("Error");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	}
	
	
	

