package com.example.mhb.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
// VO 클래스 
public class Member {
	private String id; // 회원아이디
	private String name; // 회원이름
	private String email; // 이메일
	private String phone; // 전화번호
	private LocalDate indate; // 가입일자
	private String password; // 패스워드 

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setIndate(LocalDate indate) {
		this.indate = indate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDate getIndate() {
		return indate;
	}
	// 일자/시간 ==> 까다로운 점이 있다. 
	public void setIndate(String indate) {
		// "2023-03-15 "
		if (indate.length() < 12) {
			indate = indate.substring(0, 10) + " 00:00:00";
		}
		this.indate = LocalDate.parse(indate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", indate=" + indate
				+ ", password=" + password + "]";
	}
	
}