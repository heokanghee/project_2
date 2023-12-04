package com.example.mhb.dto;
import java.io.Serializable;
import java.sql.Date;

public class Board implements Serializable{
	private static final long serialVersionUID = 1L;
	private int bid;// 게시글번호(자동으로 생성되게)
	private String mid;// 글쓴회원 id 
	private String btitle; // 글제목
	private String bcontent;// 글내용
	private Date bdate;// 게시일자
	private int bhit;// 히트수
	private int bgroup;// 글그룹(댓글이 속한 그룹을 표시)
	private int bstep;// 댓글 순서 
	private int bindent; // 들여쓰기를 위한 숫자 
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getBtitle() {
		return btitle;
	}
	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}
	public String getBcontent() {
		return bcontent;
	}
	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}
	public Date getBdate() {
		return bdate;
	}
	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}
	public int getBhit() {
		return bhit;
	}
	public void setBhit(int bhit) {
		this.bhit = bhit;
	}
	public int getBgroup() {
		return bgroup;
	}
	public void setBgroup(int bgroup) {
		this.bgroup = bgroup;
	}
	public int getBstep() {
		return bstep;
	}
	public void setBstep(int bstep) {
		this.bstep = bstep;
	}
	public int getBindent() {
		return bindent;
	}
	public void setBindent(int bindent) {
		this.bindent = bindent;
	}
	@Override
	public String toString() {
		return "Board [bid="+bid + ", mid=" + mid + ", btitle=" + btitle + ", bcontent=" + bcontent + ", bdate="
				+ "자동입력" + ", bhit=" + bhit + ", bgroup=" + bgroup + ", bstep=" + bstep + ", bindent=" + bindent + "]";
	}
	
}