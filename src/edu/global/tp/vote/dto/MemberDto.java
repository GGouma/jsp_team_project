package edu.global.tp.vote.dto;



public class MemberDto {

	private String m_no;
	private String m_name;
	private String p_name;
	private String p_school;
	private String m_ssnum;
	private String m_city;
	private String p_tel;

	public MemberDto () {}

	public MemberDto(String m_no, String m_name, String p_name, String p_school, String m_ssnum, String m_city,
			String p_tel) {
		super();
		this.m_no = m_no;
		this.m_name = m_name;
		this.p_name = p_name;
		this.p_school = p_school;
		this.m_ssnum = m_ssnum;
		this.m_city = m_city;
		this.p_tel = p_tel;
	}

	public String getM_no() {
		return m_no;
	}

	public void setM_no(String m_no) {
		this.m_no = m_no;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public String getP_school() {
		return p_school;
	}

	public void setP_school(String p_school) {
		this.p_school = p_school;
	}

	public String getM_ssnum() {
		return m_ssnum;
	}

	public void setM_ssnum(String m_ssnum) {
		this.m_ssnum = m_ssnum;
	}

	public String getM_city() {
		return m_city;
	}

	public void setM_city(String m_city) {
		this.m_city = m_city;
	}

	public String getP_tel() {
		return p_tel;
	}

	public void setP_tel(String p_tel) {
		this.p_tel = p_tel;
	}
	
	
}
