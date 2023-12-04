package com.example.mhb.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.mhb.dto.Member;
import com.example.mhb.services.MemberService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {
	@Autowired
	private MemberService service;
	@GetMapping("/joinForm")
	public String memberJoinForm() {
		return "member/memberJoinForm";
	}
	@PostMapping("/join")
	public String join(@ModelAttribute Member member){
		System.out.println("회원가입하러 왔어요~~~");
		service.insert(member);
		return "redirect:/login";
	}
	@GetMapping("/list")
    public String memberList(Model model, HttpSession session, HttpServletResponse response) {
		if (session == null) {
			return "redirect:/login";
		}
		
        List<Member> memberList = service.selectAll();
        model.addAttribute("memberList", memberList);
        return "/member/memberlist";
    }
	
	// 회원 정보 수정 페이지 호출
    @GetMapping("/updateForm/{id}")
    public String updateForm(@PathVariable("id") String id, Model model) {
        // id를 사용하여 회원 정보를 가져오는 로직을 구현해주세요.
        Member member = service.select(id);
        model.addAttribute("member", member); // member 객체를 모델에 추가
        System.out.println("수정전정보:"+member);
        return "/member/memberEditForm"; // 
    }
	

    @PostMapping("/updateMember")
    public String updateMember(@ModelAttribute Member member) {
        // 입력한 패스워드와 DB에서 가져온 패스워드를 비교
        Member dbMember = service.select(member.getId());
        System.out.println(dbMember.getPassword() +":"+member.getPassword());
        // 패스워드가 일치하면 Member 업데이트
        service.update(member);
        return "redirect:/";
    }
}
