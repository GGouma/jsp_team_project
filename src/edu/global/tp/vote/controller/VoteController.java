package edu.global.tp.vote.controller;



import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.global.tp.vote.dao.VoteDao;

@WebServlet("*.do")
public class VoteController extends HttpServlet {
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		requestPro(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		requestPro(request, response);
	}
	
	protected void requestPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* URL check */
		String uri = request.getRequestURI();
		String context = request.getContextPath();
		String command = uri.substring(context.length());
		String site = null;
		
		System.out.println("command : "+command);
		
		VoteDao vote = new VoteDao();
		
		switch(command) {
		case "/main.do" : 
			
			site = "index.jsp";
			break;
		case "/memberList.do" : 
			site = vote.selectMember(request, response);

			site = "memberList.jsp";
			break;
		case "/voteMember.do" : 
			
			site = "voteMember.jsp";
			break;
		case "/voteList.do" : 
			
			site = "voteList.jsp";
			break;
		case "/voteResult.do" : 
			
			site = "voteResult.jsp";
			break;
		case "/vote.do" : 
			int result = vote.insertVote(request, response);
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			if(result == 1) {
				out.println("<script>");
				out.println("alert('투표하기 정보가 정상적으로 등록 되었습니다!'); location.href='"+context+"'; ");
				out.println("</script>");
				out.flush();
			}else {
				out.println("<script>");
				out.println("alert('등록실패!'); location.href='"+context+"'; ");
				out.println("</script>");
				out.flush();
			}		
			break;			
			
		default : break;
		}
		/* 결과 */
		RequestDispatcher dispatcher = request.getRequestDispatcher(site);
		dispatcher.forward(request, response);
	}
	
	
}
