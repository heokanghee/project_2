package com.example.mhb.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mhb.daos.MemberDAO;
import com.example.mhb.dto.Member;


@Service
public class MemberService {
	
	@Autowired
	private final MemberDAO dao;// = null;\
	
	public MemberService(MemberDAO dao) {
		this.dao = dao;
	}
	public int insert(Member vo) {
		return dao.insertMember(vo);
	}
	public int update(Member vo) {
		return dao.updateMember(vo);
	}
	public int delete(String id) {
		return dao.deleteMember(id);
	}
	public List<Member> selectAll(){
		return dao.selectAllMember();
	}
	public Member select(String id) {
		return dao.selectMember(id);
	}
	public String getPassword(String id) {
		return dao.getPassword(id);
	}
}
