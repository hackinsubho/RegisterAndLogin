package org.jspiders.registerAndLogin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterController extends HttpServlet {
	String driver;
	String pass;
	String url;
	String user;
	Connection con;
	PreparedStatement ps;
	PrintWriter pw;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		driver = config.getInitParameter("driver");
		pass = config.getInitParameter("pass");
		url = config.getInitParameter("url");
		user = config.getInitParameter("user");
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pass);
			String qry = "insert into jdbc.logininfo values(?,?,?)";
			ps = con.prepareStatement(qry);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		pw = resp.getWriter();
		String uname = req.getParameter("uname");
		String password = req.getParameter("pass");
		String name = req.getParameter("name");
		
		try {
			ps.setString(1, uname);
			ps.setString(2, password);
			ps.setString(3, name);
			int res = ps.executeUpdate();
			if (res > 0) {
				pw.println(
						"<html><body><h3>You Registered Successfully</h3><a href=\"login.html\">Login</a></body></html>");
			} else {
				pw.println(
						"<html><body><h3>Registration Unsuccessfully</h3><a href=\"register.html\">Back</a></body></html>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
