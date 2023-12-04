package com.example.mhb.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mhb.daos.HomeBookDAO;
import com.example.mhb.dto.HomeBook;


@Service
public class HomeBookService {

	@Autowired
	private final HomeBookDAO dao;
	
	public HomeBookService(HomeBookDAO dao) {
		this.dao = dao;
	}
	public void insert(HomeBook vo) {
		dao.addHomeBook(vo);
	}
	public List<HomeBook> getAll() {
		return dao.getAllHomeBooks();
	}
	public HomeBook getBySerialno(long sn) {
		return dao.getHomeBookbySN(sn);
	}
	public List<HomeBook> getByMid(String mid){
		return dao.getAllHomeBooksById(mid);
	}
	public int update(HomeBook vo) {
		return dao.updateHomeBook(vo);
	}
	public int delete(long sn) {
		return dao.deleteHomeBook(sn);
	}
}
