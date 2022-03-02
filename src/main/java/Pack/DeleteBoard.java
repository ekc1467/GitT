package Pack;

import java.io.IOException;


import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.SQLException;



import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

public class DeleteBoard extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {
		Connection con = null;
		PreparedStatement pstmt=null;
		int n=0;

		request.setCharacterEncoding("UTF-8");
		Integer num =Integer.parseInt(request.getParameter("num"));
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();

		try{

			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://18.205.188.103:3306/test?&useSSL=false";
			con = DriverManager.getConnection(url, "lion", "1234");
			
//			String url = "jdbc:mysql://localhost:3306/test?&useSSL=false";
//			con = DriverManager.getConnection(url, "root", "1234");
			
			
			
			String sql = "delete from boards where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			n= pstmt.executeUpdate();

		}catch(ClassNotFoundException ce){
			System.out.println(ce.getMessage());
		}catch(SQLException se){
			System.out.println(se.getMessage());
		}finally{
			try{
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(SQLException se){
				System.out.println(se.getMessage());
			}
		}

		if(n>0){
			response.sendRedirect("listboard.do");
		}else{
			pw.println("<html>");
			pw.println("<head></head>");
			pw.println("<body>");
			pw.println("�Խñۻ����� �����߽��ϴ�. ");
			pw.println("<a href='javascript:history.go(-1)'>������������ ����</a>");
			pw.println("</body>");
			pw.println("</html>");
			pw.close();
		}
	}
}