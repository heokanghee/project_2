package com.example.mhb.daos;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import com.example.mhb.dto.Member;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberDAO {
	// 회원등록
	@Insert("INSERT INTO MEMBER (ID,NAME,EMAIL,PHONE,INDATE,PASSWORD) "+
	" VALUES(#{id},#{name},#{email},#{phone},sysdate,#{password})")
    public int insertMember(Member vo);
  
    // 회원정보수정
	@Update("UPDATE MEMBER SET NAME=#{name}, EMAIL=#{email}, " +
    "PHONE=#{phone},INDATE=sysdate, PASSWORD=#{password} WHERE ID=#{id}")
    public int updateMember(Member vo);
    
    // 회원삭제
	@Delete("DELETE MEMBER WHERE ID = #{id} ")
    public int deleteMember(String id);
    
    // 회원 전체 목록 조회
	@Select("SELECT * FROM MEMBER ")
    public List<Member> selectAllMember();
    
    // 회원정보 조회
	@Select("SELECT * FROM MEMBER WHERE ID=#{id}")
    public Member selectMember(String id);
   
	// 회원 패스워드 얻기 
    @Select("SELECT password FROM member WHERE id = #{id}")
    public String getPassword(String id);
}
