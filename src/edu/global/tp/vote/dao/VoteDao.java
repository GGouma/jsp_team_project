package edu.global.tp.vote.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import edu.global.tp.vote.dto.MemberDto;
import edu.global.tp.vote.dto.ResultDto;
import edu.global.tp.vote.dto.VoteDto;

public class VoteDao {
	private DataSource datasource = null;
	
	
	public VoteDao() {
		try {
			Context context = new InitialContext();
			datasource = (DataSource) context.lookup("java:comp/env/jdbc/oracle");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public String selectAll() {

		ArrayList<VoteDto> dtos = new ArrayList<VoteDto>();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String query = "SELECT v_name,";
			query+= " '19'||substr(v_ssnum,1,2)||";
			query+= " '년'||substr(v_ssnum,3,2)||";
			query+= " '월'||substr(v_ssnum,5,2)||";
			query+= " '일생' v_ssnum,";
			query+= " '만 '||(to_number(to_char(sysdate,'yyyy'))";
			query+= " - to_number('19'||substr(v_ssnum,1,2)))||'세' v_age,";
			query+= " DECODE(substr(v_ssnum,7,1),'1','남','여') v_gender, ";		   
			query+= " m_no, ";
			query+= " substr(v_time,1,2)||':'||substr(v_time,3,2) v_time, ";
			query+= " DECODE(v_confirm,'Y','확인','미확인') v_confirm ";
			query+= " FROM tbl_vote_2023 ";
			query+= " WHERE v_area='제1투표장'";// 가지고 오고자하는 쿼리문을 넣어준다
			con = datasource.getConnection();
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			// 반복문을 사용해서 ArrayList에 가져온 데이터를 집어넣는다.
			while (rs.next()) {

				String v_name = rs.getString("v_name");
				String v_ssnum = rs.getString("v_ssnum");
				String v_age = rs.getString("v_age");
				String v_gender = rs.getString("v_gender");
				String m_no = rs.getString("m_no");
				String v_time = rs.getString("v_time");
				String v_confirm = rs.getString("v_confirm");

				VoteDto list = new VoteDto(v_name, v_ssnum, v_age, v_gender, m_no, v_time, v_confirm);

				dtos.add(list);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// ※제일 나중에 연거를 먼저 닫아줘야한다. Connection, Statement, ResultSet순서로
			// 열었으므로 거꾸로 닫아준다.
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
		return "voteList.jsp";
	}
	
	public String selectResult(HttpServletRequest request, HttpServletResponse response) {
	   	
		ArrayList<ResultDto> list = new ArrayList<ResultDto>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			//후보자 등수 화면 쿼리
			String query = "SELECT ";
			query+= " M.m_no, M.m_name, count(*) AS v_total";
			query+= " FROM tbl_member_2023 M, tbl_vote_2023 V";
			query+= " WHERE M.m_no = V.m_no AND V.v_confirm = 'Y' ";
			query+= " GROUP BY M.m_no, M.m_name";
			query+= " ORDER BY v_total DESC";
			con = datasource.getConnection();
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
							
				String setM_no = rs.getString("setM_no");
				String setM_name = rs.getString("setM_name");
				String setV_total = rs.getString("setV_total");
				
				ResultDto dto = new ResultDto(setM_no, setM_name, setV_total);

				list.add(dto);				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// ※제일 나중에 연거를 먼저 닫아줘야한다. Connection, Statement, ResultSet순서로
			// 열었으므로 거꾸로 닫아준다.
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
		return "voteResult.jsp";
	}

	public String selectMember(HttpServletRequest request, HttpServletResponse response) {
	  	
		ArrayList<MemberDto> dtos = new ArrayList<MemberDto>();
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			
			//후보 조회 화면 쿼리
			String query = "SELECT ";
			query+= " M.m_no, ";
			query+= " M.m_name, ";
			query+= " P.p_name, ";
			query+= " DECODE(M.p_school,'1','고졸','2','학사','3','석사','박사') p_school, ";
			query+= " substr(M.m_ssnum,1,6)|| ";
			query+= " '-'||substr(M.m_ssnum,7) m_ssnum, ";
			query+= " M.m_city, ";
			query+= " substr(P.p_tel1,1,2)||'-'||P.p_tel2||'-'||";
			query+= " (substr(P.p_tel3,4)||";
			query+= "  substr(P.p_tel3,4)||";
			query+= "  substr(P.p_tel3,4)||";
			query+= "  substr(P.p_tel3,4)) p_tel ";
			query+= " FROM tbl_member_2023 M, tbl_party_2023 P ";
			query+= " WHERE M.p_code = P.p_code";
			
			con = datasource.getConnection();
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			 
			 while(rs.next()) {
				 
				 stmt.setString(1, "setM_no");
				 stmt.setString(2, "setM_name");
				 stmt.setString(3, "setP_name");
				 stmt.setString(4, "setP_school");
				 stmt.setString(5, "setM_ssnum");
				 stmt.setString(6, "setM_no");
				 stmt.setString(7, "setM_no");				
				 				 
				 MemberDto list = new MemberDto();

				 dtos.add(list);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// ※제일 나중에 연거를 먼저 닫아줘야한다. Connection, Statement, ResultSet순서로
			// 열었으므로 거꾸로 닫아준다.
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
		return "memberList.jsp";
	}

	
	public int insertVote(HttpServletRequest request, HttpServletResponse response) {
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			
			String v_ssnum = request.getParameter("v_ssnum");
			String v_name = request.getParameter("v_name");
			String m_no = request.getParameter("m_no");
			String v_time = request.getParameter("v_time");
			String v_area = request.getParameter("v_area");
			String v_confirm = request.getParameter("v_confirm");
			
			String query = "INSERT INTO tbl_vote_2023 VALUES(?,?,?,?,?,?)";
			stmt = con.prepareStatement(query);
			stmt.setString(1, v_ssnum);
			stmt.setString(2, v_name);
			stmt.setString(3, m_no);
			stmt.setString(4, v_time);
			stmt.setString(5, v_area);
			stmt.setString(6, v_confirm);
			
			result = stmt.executeUpdate(); // 0실패, 1성공
			System.out.println(result);	
			con.close();
			stmt.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return result;
	}	


	
}
