package edu.global.tp.vote.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import edu.global.tp.vote.dto.MemberDto;
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

	public List<VoteDto> list() {

		ArrayList<VoteDto> dtos = new ArrayList<VoteDto>();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String query = "INSERT INTO tbl_vote_2023 VALUES(?,?,?,?,?,?)";// 가지고 오고자하는 쿼리문을 넣어준다
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

				VoteDto dto = new VoteDto(v_name, v_ssnum, v_age, v_gender, m_no, v_time, v_confirm);

				dtos.add(dto);
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
		return dtos;
	}

	public int insertVote(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String selectMember(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<MemberDto> list = new ArrayList<MemberDto>();
		
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
			
				
			while(rs.next()) {
				MemberDto member = new MemberDto();
				member.setM_no(rs.getString(1));
				member.setM_name(rs.getString(2));
				member.setP_name(rs.getString(3));
				member.setP_school(rs.getString(4));
				member.setM_ssnum(rs.getString(5));
				member.setM_city(rs.getString(6));
				member.setP_tel(rs.getString(7));
					 
				list.add(member);
			}
			request.setAttribute("list",list);
				con.close();
				stmt.close();
				rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}	
		return "memberList.jsp";
	}

	
}
