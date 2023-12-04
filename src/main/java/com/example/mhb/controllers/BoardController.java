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
import com.example.mhb.dto.Board;
import com.example.mhb.dto.Member;
import com.example.mhb.services.BoardService;
import com.example.mhb.services.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")

public class BoardController {
	@Autowired
	private BoardService service;
	@Autowired
	private MemberService mservice;

	@GetMapping(value = "/list", produces = "application/json")
	public String boardList(Model model, HttpSession session, HttpServletResponse response) {
		String id = null;
		if (session != null) {
			id = (String) session.getAttribute("id");
		} else {
			// 세션이 없는 경우, 로그인 페이지로 리다이렉트합니다.
			return "redirect:/login";
		}
		System.out.println(id + "님의 게시글이 있으면 리스팅됩니다. 없어도 다른사람 글이 리스트됨 ");
		List<Board> boardList = service.selectAll();
		System.out.println("자료수:" + boardList.size());
		model.addAttribute("boardList", boardList);
		return "board/boardlist";
	}

	@GetMapping("/boardform")
	public String boardForm(HttpSession session, Model model) {
		String id = (String) session.getAttribute("id");
		System.out.println("현재게시판사용자:" + id);
		model.addAttribute("sessionId", id);
		return "board/boardForm";
	}

	@PostMapping("/add")
	public String add(@ModelAttribute Board board, HttpServletRequest request, Model model) {
		String password = request.getParameter("password");
		System.out.println("게시글 작성 하러 왔어요~~~:" + password);
		System.out.println("게시글정보:" + board);
		String temp = mservice.getPassword(board.getMid());
		if (temp.equals(password)) {
			// 날자부분은 디버깅해야 함, 인터페이스 메퍼에는 sysdate로 대체되게 했음.
			service.insert(board);
		} else {
			model.addAttribute("error", "비밀번호가 틀렸습니다.");
			model.addAttribute("sessionId", board.getMid());
			return "board/boardForm";
		}

		return "redirect:/board/list";
	}

	@GetMapping("/edit")
	public String editForm(@RequestParam("bid") int bid, Model model) {
		// bid와 mid 값을 사용하여 필요한 작업을 수행
		// Model 객체를 사용하여 뷰에 필요한 데이터 전달
		System.out.println("수정 대상 게시글 번호:" + bid);
		Board board = service.select(bid);
		System.out.println("수정 대상 게시글 객체:" + board);
		model.addAttribute("board", board);
		String temp = mservice.getPassword(board.getMid());
		model.addAttribute("userPwd", temp);

		return "board/boardEditForm"; // 뷰 이름 리턴
	}

	@PostMapping("/update")
	public String update(@ModelAttribute Board board, HttpServletRequest request, Model model) {
		String password = request.getParameter("password");
		System.out.println("게시글 수정한것 저장합니다.~~~" + password);
		System.out.println("게시글 수정 정보:" + board);
		String temp = mservice.getPassword(board.getMid());
		if (temp.equals(password)) {
			// 날자부분은 디버깅해야 함, 인터페이스 메퍼에는 sysdate로 대체되게 했음.
			service.update(board);
		} else {
			model.addAttribute("error", "비밀번호가 틀렸습니다.");
			model.addAttribute("sessionId", board.getMid());
			return "board/edit";
		}

		return "redirect:/board/list";
	}

	@PostMapping("/delete")
	public String deleteBoard(@RequestParam String bid, @RequestParam String mid, @RequestParam String password,
			@RequestParam String bstep, @RequestParam String bgroup, Model model,
			RedirectAttributes redirectAttributes) {

		// 입력 받은 비밀번호와 DB에서 가져온 비밀번호를 비교하여 인증 수행
		boolean isPasswordCorrect = mservice.getPassword(mid).equals(password);
		// 게시글이 달린 본글인지 확인
		boolean isHasReply = service.countBgroup(Integer.parseInt(bgroup), Integer.parseInt(bstep)) > 0;
		String res = null;
		if (isPasswordCorrect && !isHasReply) {
			System.out.println(
					"bid:" + bid + " mid:" + mid + " password:" + password + " bstep:" + bstep + " bgroup:" + bgroup);
			System.out.println("확실히 지워도 되는 글입니다.");
			service.delete(Integer.parseInt(bid));
			model.addAttribute("bravoMsg", "브라보 잘 성공했습니다!");
			redirectAttributes.addFlashAttribute("bravoMsg", "브라보 잘 성공했습니다!"); // redirectAttributes 사용
			res = "redirect:/board/list";
		} else if (!isPasswordCorrect) {
			model.addAttribute("errorMsg", "비밀번호가 맞지 않습니다.");
			res = "error";
		} else { // isHasReply == true
			model.addAttribute("errorMsg", "내글과 관련된 다른 댓글 들이 있어 삭제할 수 없습니다.");
			res = "error";
		}
		return res;
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("bid") int bid) {
	    System.out.println("지우러 왔어요!!!!!!!!!!!!!!!");
	    return "redirect:/board/list";
	}
	
	@GetMapping("/content_view")
	public String content_view(@RequestParam("bid") int bid, Model model) {
		service.hitBoard(bid);
		Board board = service.select(bid);
		model.addAttribute("content_view", board);
		return "board/content_view"; // 뷰 이름 리턴
	}

	@GetMapping("/reply_view")
	public String reply_view(@RequestParam("bid") int bid, Model model, HttpSession session) {
		Member memberVo = (Member) session.getAttribute("memberVo");
	    String mid = memberVo.getId();
		
		System.out.println("현재 댓글 작성자:" + mid);
		Board board = service.select(bid);
		
		board.setBid(service.getNextVal());
		board.setMid(mid);
		board.setBtitle("<댓글>" + board.getBtitle());
		board.setBcontent(">>>" + board.getBcontent());
		board.setBgroup(board.getBgroup());
		board.setBhit(0);
		board.setBstep(service.maxStep(board.getBgroup()) + 1);
		board.setBindent(board.getBindent() + 1);

		model.addAttribute("reply_view", board);
		
		System.out.println("댓글객체:" + board);
		return "board/reply_view"; // 뷰 이름 리턴
	}

	@PostMapping("/reply")
	public String reply(@ModelAttribute Board board) {
		// 댓글 단것을 디비에 저장하고 다시 전체 리스트 뿌리기
		service.insertReply(board);
		return "redirect:/board/list";
	}

	@DeleteMapping("/delete/{bid}")
	public ResponseEntity<?> deleteBoard(@PathVariable("bid") int bid) {
		System.out.println("bid: " + bid);
	    service.delete(bid);
	    return ResponseEntity.ok().build();
	}

}
