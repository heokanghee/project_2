package com.example.mhb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.mhb.dto.Member;
import com.example.mhb.services.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

	@Autowired
	private MemberService service;

	@GetMapping("/")
	public String facade(HttpServletRequest request) {
		return "main";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/loginProc")
	public String loginProc(@RequestParam("id") String id, @RequestParam("password") String password,
			HttpSession session, Model model) {
		String pwd = null;
		try {
			pwd = service.getPassword(id);
			if (pwd.equals(password)) {
				Member vo = service.select(id);
				session.setAttribute("id", id);
				session.setAttribute("password", password);
				session.setAttribute("memberVo", vo);
				session.setMaxInactiveInterval(1800);
				System.out.println("로그인한자:" + vo);
				
				if (id.equals("hgd") && pwd.equals("1234")) {
					session.setAttribute("editer", "true");
				}else {
					session.setAttribute("editer", "false");
				}
				
				
				return "main";
			} else {
				model.addAttribute("errorMsg", "로그인 실패");
				return "error";
			}
		} catch (Exception e) {
			model.addAttribute("errorMsg", "그런 아이디에 그런 패스워드 없음!");
			return "error";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpSession session) {
		session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}

	@PostMapping("/checkid")
	public ResponseEntity<Boolean> isExist(@RequestParam(name = "id") String id) {
		boolean res = service.select(id) != null;
		return ResponseEntity.ok(res);
	}
	
	@GetMapping("/chatclient")
	public String  chatClient(Model model) {
		model.addAttribute("errorMsg", "아직 구현되지않은 서비스!");
		return "error";
	}
}
