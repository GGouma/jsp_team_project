package edu.global.tp.vote.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface VoteCommand {
	//인터페이스이므로 메소드 선언만 가능하다. 구현은 자손이 한다.
	String execute(HttpServletRequest request, HttpServletResponse response);
	
}
