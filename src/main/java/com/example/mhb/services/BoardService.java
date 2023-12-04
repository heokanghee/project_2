package com.example.mhb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.example.mhb.daos.BoardDAO;
import com.example.mhb.dto.Board;


@Service
public class BoardService {
	@Autowired
	private final BoardDAO dao;
	
	public BoardService(BoardDAO dao) {
		this.dao = dao;
	}

	public int insert(Board vo) {
		return dao.insertBoard(vo);
	}
	
	public int insertReply(Board vo) {
		return dao.insertReply(vo);
	}
	
	public int update(Board vo) {
		return dao.updateBoard(vo);
	}
	public int delete(int bid) {
		return dao.deleteBoard(bid);
	}
	public List<Board> selectAll(){
		return dao.selectAllBoard();
	}
	public Board select(int bid) {
		return dao.selectOneBoard(bid);
	}
	public int countBgroup(int bgroup,int bstep) {
		return dao.countBgroup(bgroup,bstep);
	}
	public int hitBoard(int bid) {
		return dao.hitBoard(bid);
	}
	public int maxStep(int bgroup) {
		return dao.maxStep(bgroup);
	}
	public int getNextVal() {
		return dao.getNextVal();
	}
	// 페이지에 맞는 게시글 목록 조회
	public List<Board> selectBoardListPerPage(int startRow, int endRow) {
	    return dao.selectBoardListPerPage(startRow, endRow);
	}

	// 게시글 총 개수 조회
	public int selectBoardTotalCount() {
	    return dao.selectBoardTotalCount();
	}
	

}
