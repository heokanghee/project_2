package com.example.mhb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.mhb.dto.HomeBook;
import com.example.mhb.services.HomeBookService;
import com.example.mhb.services.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/homebook")
public class HomeBookController {
	@Autowired
	private HomeBookService service;
	@Autowired
	private MemberService mservice;
	
	@GetMapping("/list")
    public String memberList(Model model,HttpSession session,HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setHeader("Expires", "0"); // Proxies
		
		String id = (String) session.getAttribute("id");
		if (id == null) {
	        // id가 null일 경우, 적절한 처리를 수행하거나 리디렉션을 사용할 수 있습니다.
	        // 아래는 간단한 메시지를 사용한 경우입니다.
	        model.addAttribute("errorMsg", "로그인이 필요합니다.");
	        return "error"; // 에러 페이지로 리디렉트 또는 포워딩
	    }
		System.out.println(id+"님의 가계부 정보를 출력합니다.");
        List<HomeBook> homeBookList = service.getByMid(id);
        System.out.println("자료수:"+homeBookList.size());     
        model.addAttribute("homebookList", homeBookList);
        return "/homebook/homebooklist";
    }
	@GetMapping("/form")
	public String homebookForm(HttpSession session, Model model) {
		String id = (String) session.getAttribute("id");
		System.out.println("현재가계부사용자:"+id);
		model.addAttribute("sessionId", id);
		return "homebook/homebookForm";
	}
	@PostMapping("/add")
	public String add(@ModelAttribute HomeBook homebook, HttpServletRequest request, Model model){
		String password = request.getParameter("password");
		System.out.println("가계부 기록 하러 왔어요~~~"+password);
		System.out.println("가계부정보:"+homebook);
		String temp = mservice.getPassword(homebook.getMid());
		if(temp.equals(password)) {
			// 날자부분은 디버깅해야 함, 인터페이스 메퍼에는 sysdate로 대체되게 했음. 
			service.insert(homebook);
		} else {
			model.addAttribute("error", "비밀번호가 틀렸습니다.");
	        model.addAttribute("sessionId", homebook.getMid());
	        return "homebook/homebookForm";
		}
		
		return "redirect:/homebook/list";
	}
	@GetMapping("/edit")
	public String editForm(@RequestParam("serialno") int serialno, Model model) {
	    // serialno와 mid 값을 사용하여 필요한 작업을 수행
	    // Model 객체를 사용하여 뷰에 필요한 데이터 전달
		HomeBook homebook = service.getBySerialno(serialno);
		model.addAttribute("homebookVo", homebook);
		String temp = mservice.getPassword(homebook.getMid());
		model.addAttribute("userPwd", temp);
	    
	    return "/homebook/homebookEditForm"; // 뷰 이름 리턴
	}
	@PostMapping("/update")
	public String update(@ModelAttribute HomeBook homebook, HttpServletRequest request, Model model){
		String password = request.getParameter("password");
		System.out.println("가계부 수정 하러 왔어요~~~"+password);
		System.out.println("가계부 수정 정보:"+homebook);
		String temp = mservice.getPassword(homebook.getMid());
		if(temp.equals(password)) {
			// 날자부분은 디버깅해야 함, 인터페이스 메퍼에는 sysdate로 대체되게 했음. 
			
			service.update(homebook);
		} else {
			model.addAttribute("error", "비밀번호가 틀렸습니다.");
	        model.addAttribute("sessionId", homebook.getMid());
	        return "homebook/edit";
		}
		
		return "redirect:/homebook/list";
	}
	
	@PostMapping("/delete")
	public String deleteHomebook(@RequestParam String serialno, @RequestParam String password, @RequestParam String mid) {
		System.out.println("sn:"+serialno+" mid:"+mid +" password:"+password);
		// 입력 받은 비밀번호와 DB에서 가져온 비밀번호를 비교하여 인증 수행
	    boolean isPasswordCorrect = mservice.getPassword(mid).equals(password);
	    if (isPasswordCorrect) {
	        service.delete(Integer.parseInt(serialno));
	    } 
	    return "redirect:/homebook/list";
	}
	
}
