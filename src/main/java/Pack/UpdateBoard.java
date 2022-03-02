package Pack;

import java.io.IOException;

import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;



import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

public class UpdateBoard extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 1. parameter�� ���۵� id���.
		Integer num = Integer.parseInt(req.getParameter("num"));

		// 2. id�� �ش��ϴ� ������ db���� ��ȸ�ؼ� ���.
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = resp.getWriter();
		pw.println("<html>");
		pw.println("<head>    <style>\r\n"
				+ "      * {\r\n"
				+ "        font-size: 16px;\r\n"
				+ "        font-family: Consolas, sans-serif;\r\n"
				+ "      }\r\n"
				+ "    </style></head>");
		pw.println("<body>");

		PreparedStatement pstmt = null;
		Connection con = null;
		ResultSet rs=null;
		try{
			// 2. ���۵� ���� db�� ����.


			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://18.205.188.103:3306/test?&useSSL=false";
		     con = DriverManager.getConnection(url, "lion", "1234");
			
//			String url = "jdbc:mysql://localhost:3306/test?&useSSL=false";
//			con = DriverManager.getConnection(url, "root", "1234");

			String sql = "select * from boards where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);

			//sql���� �����ϱ�
			rs = pstmt.executeQuery();
			rs.next();
			String title = rs.getString("title");
			String content=rs.getString("content");
			String wr = rs.getString("wr");
			System.out.println(content);
			
			pw.println("<div class=\"container\">");
			//pw.println("<div class='container'>");
			pw.println("<form method='post' action='updateokboard.do'>");
			
			pw.println("<input type='hidden' name='num' value='" + num + "'/>");
			pw.println("�� ��ȣ<input type='text' name='num' value='" + num + "' disabled='disabled'/><br/>");
			
			//pw.println("<input type='hidden' name='wr' value='" + wr + "'/>");
			pw.println("�ۼ��� <input type='text' name='wr' value='" + wr + "' disabled='disabled'/><br/>");
			
			pw.println("����  <input type='text' name='title' value='" + title + "'/><br/>");
			pw.println("<textarea rows='10' type='text' name='content'>"+ content +"</textarea><br/>");
			pw.println("<input type='submit' value='����'/><br/>");
			pw.println("</form>");
			pw.println("</div>");
			
		}catch(ClassNotFoundException ce){
			System.out.println(ce.getMessage());
		}catch(SQLException se){
			System.out.println(se.getMessage());
		}finally{
			try{
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(SQLException se){
				System.out.println(se.getMessage());
			}
		}

		pw.println("</body>");
		pw.println("</html>");
		pw.close();
	}
}