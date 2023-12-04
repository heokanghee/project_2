package com.example.mhb.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.mhb.dto.Blog;
import com.example.mhb.services.BlogService;
import com.example.mhb.services.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/blog")

public class BlogController {
	@Autowired
	private BlogService service;
	@Autowired
	private MemberService mservice;

	@GetMapping(value = "/list")
	public String boardList(Model model, HttpSession session) {
		String id = null;
		if (session != null) {
			id = (String) session.getAttribute("id");
		} else {
			return "redirect:/login";
		}
		System.out.println(id + "님의 게시글이 있으면 리스팅됩니다. 없어도 다른사람 글이 리스트됨 ");
		List<Blog> blogList = service.selectAll();
		System.out.println("자료수:" + blogList.size());
		model.addAttribute("blogList", blogList);
		return "blog/bloglist";
	}
	
	@GetMapping("/search")
    public String searchBlog() {
        return "blog/blog_search"; 
    }
	@PostMapping("/search_proc")
    public String search_proc(Model model,@RequestParam("slug") String slug) {
		List<Blog> searchblogs = service.searchBlogList(slug);
		model.addAttribute("searchblogs", searchblogs);
        return "blog/blog_search";
    }
	

	@GetMapping("/blogform")
	public String boardForm(HttpSession session, Model model) {
		String id = (String) session.getAttribute("id");
		System.out.println("현재게시판사용자:" + id);
		model.addAttribute("sessionId", id);
		return "blog/blogForm";
	}

	@PostMapping("/add")
	public String add(@ModelAttribute Blog blog, HttpServletRequest request, Model model) {
		System.out.println("게시글 작성 하러 왔어요~~~:" );//+ password);
		System.out.println("블로깅정보:" + blog);
		service.insertBlog(blog);
		return "redirect:/blog/list";
	}

	@GetMapping("/edit")
	public String editForm(@RequestParam("sn") long sn, Model model) {
		System.out.println("수정 대상 블로그 번호:" + sn);
		Blog blog = service.select(sn);
		System.out.println("수정 대상 블로그 객체:" + blog);
		model.addAttribute("blog", blog);
		return "blog/blogEditForm"; // 뷰 이름 리턴
	}

	@PostMapping("/update")
	public String update(@ModelAttribute Blog blog, HttpServletRequest request, Model model) {
		System.out.println("블로그 수정한것 저장합니다.~~~");
		System.out.println("블로그 수정 정보:" + blog);
		service.updateBlog(blog);
		return "redirect:/blog/list";
	}

	@PostMapping("/delete")
	public String deleteBlog(@RequestParam String sn,Model model,
			RedirectAttributes redirectAttributes) {
		int x = service.delete(Long.parseLong(sn));
		return (x>=1)?"삭제완료":"삭제안됨";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("sn") long sn) {
	    System.out.println("지우러 왔어요!!!!!!!!!!!!!!!");
	    return "redirect:/blog/list";
	}
	
	@GetMapping("/content_view")
	public String content_view(@RequestParam("sn") long sn, Model model) {
		Blog blog = service.select(sn);
		model.addAttribute("content_view", blog);
		return "blog/content_view"; // 뷰 이름 리턴
	}

	@DeleteMapping("/delete/{sn}")
	public ResponseEntity<?> deleteBoard(@PathVariable("sn") long sn) {
		System.out.println("sn: " + sn);
	    service.delete(sn);
	    return ResponseEntity.ok().build();
	}
}