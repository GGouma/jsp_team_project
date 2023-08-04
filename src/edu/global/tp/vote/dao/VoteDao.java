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

import edu.global.tp.vote.dto.Member;
import edu.global.tp.vote.dto.Vote;

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

	public List<Vote> list() {

		ArrayList<Vote> dtos = new ArrayList<Vote>();

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

				String v_ssnum = rs.getString("v_ssnum");
				String v_name = rs.getString("v_name");
				String m_no = rs.getString("m_no");
				String v_time = rs.getString("v_time");
				String v_area = rs.getString("v_area");
				String v_confirm = rs.getString("v_confirm");

				Vote dto = new Vote(v_ssnum, v_name, m_no, v_time, v_area, v_confirm);

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
		ArrayList<Member> list = new ArrayList<Member>();
		
		try {
			conn = getConnection();
			
			//후보 조회 화면 쿼리
			String sql = "SELECT ";
			       sql+= " M.m_no, ";
			       sql+= " M.m_name, ";
			       sql+= " P.p_name, ";
			       sql+= " DECODE(M.p_school,'1','고졸','2','학사','3','석사','박사') p_school, ";
			       sql+= " substr(M.m_jumin,1,6)|| ";
			       sql+= " '-'||substr(M.m_jumin,7) m_jumin, ";
			       sql+= " M.m_city, ";
			       sql+= " substr(P.p_tel1,1,2)||'-'||P.p_tel2||'-'||";
			       sql+= " (substr(P.p_tel3,4)||";
			       sql+= "  substr(P.p_tel3,4)||";
			       sql+= "  substr(P.p_tel3,4)||";
			       sql+= "  substr(P.p_tel3,4)) p_tel ";
			       sql+= " FROM tbl_member_202005 M, tbl_party_202005 P ";
			       sql+= " WHERE M.p_code = P.p_code";
				 ps = conn.prepareStatement(sql);
				 rs = ps.executeQuery();
				 
			while(rs.next()) {
				Member member = new Member();
				member.setM_no(rs.getString(1));
				member.setM_name(rs.getString(2));
				member.setP_name(rs.getString(3));
				member.setP_school(rs.getString(4));
				member.setM_jumin(rs.getString(5));
				member.setM_city(rs.getString(6));
				member.setP_tel(rs.getString(7));
					 
				list.add(member);
			}
			request.setAttribute("list",list);
				conn.close();
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}	
		return "memberList.jsp";
	}

	
}
