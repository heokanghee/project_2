package com.example.mhb.daos;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.example.mhb.dto.Blog;
import com.example.mhb.dto.SlugGenerator;
/*
 *  @Mapper 애너테이션을 사용하면 스프링이 해당 인터페이스를 마이바티스 매퍼로 인식하고 
 *  자동으로 구현체를 생성하여 빈으로 관리합니다. 이것은 스프링과 마이바티스를 통합하는 데 
 *  사용되며, 인터페이스의 메서드를 SQL 쿼리로 매핑하기 위해 마이바티스의 XML 매퍼 파일을 
 *  대신해서 사용 jpa 사용시 @Repository 와 유사함. 
 */
@Mapper
public interface BlogDAO {

    default String generateSlug(String title) {
    	String slug = SlugGenerator.generateSlug(title);
        return slug;
    }
	
	// 블로그 등록 1
	@Insert("INSERT INTO BLOG (SN, TITLE, SLUG, DESCRIPTION, CONTENT, CREATE_DT,MODIFY_DT) "
			+ "VALUES (BLOG_SEQ.NEXTVAL, #{title}, #{slug}, #{description},#{content},sysdate,sysdate)")
	public int insertBlog(Blog blog);

	
	//블로그 수정 2
	@Update("UPDATE BLOG SET TITLE=#{title}, SLUG = #{slug}, DESCRIPTION =#{description}, "
			+ " CONTENT=#{content},  MODIFY_DT = sysdate WHERE SN=#{sn}")
	public int updateBlog(Blog blog);

	// 블로그 삭제 3
	@Delete("DELETE FROM BLOG WHERE SN=#{sn}")
	public int deleteBlog(long sn);

	// 블로그 전체 목록 조회 4
	@Select("SELECT * FROM BLOG order by sn DESC")
	public List<Blog> selectAllBlog();

	// 블로그 상세 조회 5
	@Select("SELECT * FROM BLOG WHERE SN = #{sn}")
	public Blog selectOneBlog(long sn);

	// 블로그 관리번호(sn) 다음것 알아오기  
	@Select("SELECT BLOG_SEQ.NEXTVAL FROM DUAL")
	public int getNextVal();

	// 페이지에 맞는 블로그 목록 조회
	@Select("SELECT * FROM (SELECT ROWNUM AS RNUM, A.* FROM (SELECT * FROM BLOG ORDER BY SN DESC) A WHERE ROWNUM <= #{endRow}) WHERE RNUM >= #{startRow}")
	public List<Blog> selectBlogListPerPage(@Param("startRow") int startRow, @Param("endRow") int endRow);

	// 브로깅 총 개수 조회
	@Select("SELECT COUNT(*) FROM BLOG")
	public int selectBlogTotalCount();

	@Select("SELECT * FROM BLOG WHERE SLUG LIKE '%' || #{slug} || '%'")
	public List<Blog> selectBlogBySlug(String slug);
}
