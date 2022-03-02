package Pack;

import java.io.IOException;


import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Timestamp;



import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;


import javax.servlet.http.HttpSession;

public class ViewDetailBoard extends HttpServlet{
	@Override
	protected void service(HttpServletRequest resquest, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		Integer num =Integer.parseInt(resquest.getParameter("num"));
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter pw = response.getWriter();
		pw.println("<html>");
		pw.println("<head></head>");
		pw.println("<body>");

		try{

			String url = "jdbc:mysql://18.205.188.103:3306/test?&useSSL=false";
		     con = DriverManager.getConnection(url, "lion", "1234");

//			String url = "jdbc:mysql://localhost:3306/test?&useSSL=false";
//			con = DriverManager.getConnection(url, "root", "1234");
			String sql = "select * from boards where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			
				HttpSession session = resquest.getSession();
				
				//여기서 글번호로 인덱싱해야하네;
				rs.next();
				String title = rs.getString("title");
				String wr = rs.getString("wr");
				String content = rs.getString("content");
				Timestamp regdate = rs.getTimestamp("regdate");	

				

			
			pw.println("<form action=\"def\" method=\"post\">\r\n"
					+ "    <table border=\"1\" width=500>\r\n"
					
					+"<tr>"
					+"<td>번호</a></td>"
					+"<td>제목</a></td>"
					+"<td>작성자</td>"
					
					+"</tr>"		
					
					+ "        <tr >\r\n"

					+"<td>" + num + "</td>"
					+"<td>" + title + "</td>"
					+"<td>" + wr + "</td>"
					
				
					+ "        </tr>\r\n"
					
					+ "        <tr >\r\n"
					+ "            <td colspan=\"3\" width = 500 height=400>\r\n"
					+ "                <textarea placeholder=\"내용을 입력하세요.\" name = \"content\" style=\"width: 100%; height: 100%\" disabled>"+content+"</textarea>\r\n"
					+ "            </td>\r\n"
					+ "        </tr>\r\n"
					+ "        <tr>\r\n"
					+ "            <td colspan=\"3\" align=right>\r\n"
					+ "           	 <button type=\"button\" class=\"btn btn-default\" onClick=\"location.href='javascript:history.go(-1)'\">이전페이지</button>\r\n"
					+ "            </td>\r\n"
					+ "        </tr>\r\n"
					+ "<tr>"
					+"<td>등록일</td>"
					+"<td colspan=\"2\">" + regdate + "</td>"
					+"</tr>"
					+ "    </table>\r\n"
					+ "     </form>");
			if(wr.equals(session.getAttribute("memberId"))) {
			pw.println("<tr>");
			pw.println("<td><a href='deleteboard.do?num=" + num + "'>삭제</a></td>");
			pw.println("<td><a href='updateboard.do?num=" + num + "'>수정</a></td>");
			pw.println("</tr>");
			}
			//pw.println("<a href='loginMain.jsp'>메인페이지로 이동</a>");
		} /*catch(ClassNotFoundException ce){
			System.out.println(ce.getMessage());
			}*/catch(SQLException se){
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
	}
}