package com.example.mhb.dto;

import java.sql.Date;
import java.text.Normalizer;
import java.util.Locale;

public class Blog {
	private Long sn;// 자동생성되게,시리얼번호, 프라이머리키
	private String title;// 블로그 제목
	private String slug;// 색인용 블로그 제목
	private String description;// 블로그 개략적 소개글
	private String content;// 블로그 내용
	private Date create_dt;// 블로그 생성일자
	private Date modify_dt;// 블로그 수정일자 
	
	public Long getSn() {
		return sn;
	}
	public void setSn(Long sn) {
		this.sn = sn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreate_dt() {
		return create_dt;
	}
	public void setCreate_dt(Date create_dt) {
		this.create_dt = create_dt;
	}
	public Date getModify_dt() {
		return modify_dt;
	}
	public void setModify_dt(Date modify_dt) {
		this.modify_dt = modify_dt;
	}
	@Override
	public String toString() {
		return "Blog [sn=" + sn + ", title=" + title + ", slug=" + slug + ", description=" + description + ", content="
				+ content + ", create_dt=" + create_dt + ", modify_dt=" + modify_dt + "]";
	}
	
}
