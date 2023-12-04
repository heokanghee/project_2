package com.example.mhb.services;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.mhb.daos.HomeBookDAO;
import com.example.mhb.dto.HomeBook;

@Service
public class HomeBookSwingService {
	@Autowired
	private final HomeBookDAO dao;
	public HomeBookSwingService(HomeBookDAO dao) {
		this.dao = dao;
	}
	/////////  과제 - Stream을 이용하여 ////////
	// 계정과목별 리스트를 리턴시켜 주는 서비스 만들기 
	// 기간별 조회가 가능하게 (예를 들면 시작:2023-1-1 ~ 2023-3.31)까지 거래내역
	// 기간별 수입금 합계 
	// 기간별 지출금 합계 
	// 계정과목별 집계 (grouping,sum) 
	////////////////////////////////////////
	//풀이 예) 주어진 기간에 해당하는 HomeBook List를 반환 
	public List<HomeBook> getHomeBooksByDateRange(String startDateStr, String endDateStr) {
	    java.util.Date startDate = null;
	    java.util.Date endDate = null;
	    List<HomeBook> list = null;
	    
	    if (startDateStr.isEmpty() || endDateStr.isEmpty()) {
	        // 빈 문자열이 하나라도 있으면 처리할 수 없는 상태로 예외처리 또는 기본값 반환
	        return Collections.emptyList(); // 또는 예외 처리를 수행
	    }
	    
	    try {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        startDate = dateFormat.parse(startDateStr);
	        endDate = dateFormat.parse(endDateStr);
	        list = dao.getAllHomeBooks();
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }

	    if (startDate != null && endDate != null) {
	        final java.util.Date finalStartDate = startDate;
	        final java.util.Date finalEndDate = endDate;
	        
	        return list.stream()
	                .filter(homebook -> homebook.getDay().after(finalStartDate) 
	                        && homebook.getDay().before(finalEndDate))
	                .collect(Collectors.toList());
	    } else {
	        return Collections.emptyList();
	    }
	}
	public List<HomeBook> getHomeBooksByDateRange2(String title,String startDateStr, String endDateStr) {
	    java.util.Date startDate = null;
	    java.util.Date endDate = null;
	    List<HomeBook> list = null;
	    
	    if (startDateStr.isEmpty() || endDateStr.isEmpty()) {
	        // 빈 문자열이 하나라도 있으면 처리할 수 없는 상태로 예외처리 또는 기본값 반환
	        return Collections.emptyList(); // 또는 예외 처리를 수행
	    }
	    
	    try {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        startDate = dateFormat.parse(startDateStr);
	        endDate = dateFormat.parse(endDateStr);
	        list = dao.getAllHomeBooks();
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }

	    if (startDate != null && endDate != null) {
	        final java.util.Date finalStartDate = startDate;
	        final java.util.Date finalEndDate = endDate;
	        
	        return list.stream()
	                .filter(homebook -> homebook.getDay().after(finalStartDate) 
	                        && homebook.getDay().before(finalEndDate))
	                .filter(homebook -> title.equals(homebook.getAccounttitle()))
	                .collect(Collectors.toList());
	    } else {
	        return Collections.emptyList();
	    }
	}

	// 모든 자료 얻어오기 
	public List<HomeBook> getAllMembers(){
		return dao.getAllHomeBooks();
	}
	public List<HomeBook> getAllMembers2(String title){
		return dao.getAllHomeBooks().stream()
				.filter(book -> title.equals(book.getAccounttitle()))
                .collect(Collectors.toList());
	}
	// 사용하지 않았음 
	public double getTotalRevenue(String title) {
		return getAllMembers2(title).stream()
			.filter(book->title.equals(book.getAccounttitle()))
			.mapToDouble(HomeBook::getRevenue).sum(); 
	}
	// 사용하지 않았음 
	public double getTotalExpense(String title) {
		return getAllMembers2(title).stream()
			.filter(book->title.equals(book.getAccounttitle()))
			.mapToDouble(HomeBook::getExpense).sum(); 
	}
	
	// 등록된 모든 계정과목 리스트 얻기 
	public List<String> getAccountTitles(){
		List<String> res = dao.getAllHomeBooks().stream()
				.map(HomeBook::getAccounttitle)
				.distinct()
				.collect(Collectors.toList()); 
		res.add(0,"전체");
		return res;
	} 
	
	// 계정과목별 집계 출력 ===> console 화면에 출력 
	public String dispSumByAcc() {
		Map<String,List<HomeBook>> map = 
				dao.getAllHomeBooks().stream()
					.collect(
						Collectors.groupingBy(b->b.getAccounttitle())
					);
		StringBuffer buffer = new StringBuffer();
		for(String title:map.keySet()) {
			List<HomeBook> x = map.get(title);
			System.out.println(">>> "+title +" <<<");
			buffer.append(">>> "+title +" <<<\n");
			x.stream().forEach(xx->{
				System.out.println(xx);
				buffer.append(xx.toString()+"\n");
			});
		}
		return buffer.toString();
	}
}
