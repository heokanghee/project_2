package com.example.mhb.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mhb.daos.BlogDAO;
import com.example.mhb.dto.Blog;
import com.example.mhb.dto.SlugGenerator;

@Service
public class BlogService {

	@Autowired
	private final BlogDAO dao;
	
	public BlogService(BlogDAO dao) {
		this.dao = dao;
	}
	
	public int insertBlog(Blog blog) {
		blog.setSlug(dao.generateSlug(blog.getTitle()));
		System.out.println("인서트시 다오속의 블로그 정보:"+blog);
		return dao.insertBlog(blog);
	}

	public int updateBlog(Blog blog) {
		blog.setSlug(dao.generateSlug(blog.getTitle()));
		System.out.println("업데이트시 다오속의 블로그 정보:"+blog);
		return dao.updateBlog(blog);
	}
	

	public int delete(long sn) {
		return dao.deleteBlog(sn);
	}
	public List<Blog> selectAll(){
		return dao.selectAllBlog();
	}
	public Blog select(long sn) {
		return dao.selectOneBlog(sn);
	}

	public int getNextVal() {
		return dao.getNextVal();
	}
	// 페이지에 맞는 게시글 목록 조회
	public List<Blog> selectBlogListPerPage(int startRow, int endRow) {
	    return dao.selectBlogListPerPage(startRow, endRow);
	}

	// 블로깅 총 개수 조회
	public int selectBlogTotalCount() {
	    return dao.selectBlogTotalCount();
	}
	
	public List<Blog> searchBlogList(String slug) {
	    return dao.selectBlogBySlug(slug);
	}

}
