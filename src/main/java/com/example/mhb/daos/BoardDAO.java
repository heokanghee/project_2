package com.example.mhb.daos;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.example.mhb.dto.Board;

@Mapper
public interface BoardDAO {
	// 게시글 등록 1
	@Insert("INSERT INTO BOARD (BID, MID, BTITLE, BCONTENT, BDATE, BHIT, BGROUP,BSTEP, BINDENT) "
			+ "VALUES (BOARD_SEQ.NEXTVAL, #{mid}, #{btitle}, #{bcontent},sysdate,0,BOARD_SEQ.CURRVAL,1,0)")
	public int insertBoard(Board board);

	// 댓글 등록 2
	@Insert("INSERT INTO BOARD(BID, MID, BTITLE, BCONTENT, BDATE, BHIT, BGROUP,BSTEP, BINDENT)"
			+ "VALUES(BOARD_SEQ.NEXTVAL, #{mid}, #{btitle}, #{bcontent},sysdate,0,#{bgroup},#{bstep},#{bindent})")
	public int insertReply(Board board);

	// 게시글 수정 3
	@Update("UPDATE BOARD SET BTITLE=#{btitle}, BCONTENT=#{bcontent} WHERE BID=#{bid}")
	public int updateBoard(Board board);

	// 게시글 삭제 4
	@Delete("DELETE FROM BOARD WHERE BID=#{bid}")
	public int deleteBoard(int bid);

	// 게시글 전체 목록 조회 5
	@Select("SELECT * FROM BOARD order by bgroup desc, bstep asc")
	public List<Board> selectAllBoard();

	// 게시글 상세 조회 6
	@Select("SELECT * FROM BOARD WHERE BID = #{bid}")
	public Board selectOneBoard(int bid);

	// 게시글 히트 7
	@Update("UPDATE BOARD SET BHIT= BHIT+1  WHERE BID=#{bid}")
	public int hitBoard(int bid);

	// 최종step 얻기 8
	@Select("SELECT max(bstep) FROM BOARD WHERE BGROUP=#{bgroup} ")
	public int maxStep(int bgroup);

	// 본 글 밑에 댓글이 몇 개 달렸는지 알아보기
	@Select("SELECT COUNT(*) FROM BOARD WHERE BGROUP=#{bgroup} and BSTEP>#{bstep}")
	public int countBgroup(@Param("bgroup") int bgroup, @Param("bstep") int bstep);
	// public int countBgroup(int bgroup,int bstep); // 이와 같이 해서 안되서 위 방법으로 매핑을 명시시킴

	@Select("SELECT BOARD_SEQ.NEXTVAL FROM DUAL")
	public int getNextVal();

	// 페이지에 맞는 게시글 목록 조회
	@Select("SELECT * FROM (SELECT ROWNUM AS RNUM, A.* FROM (SELECT * FROM BOARD ORDER BY BGROUP DESC, BSTEP ASC) A WHERE ROWNUM <= #{endRow}) WHERE RNUM >= #{startRow}")
	public List<Board> selectBoardListPerPage(@Param("startRow") int startRow, @Param("endRow") int endRow);

	// 게시글 총 개수 조회
	@Select("SELECT COUNT(*) FROM BOARD")
	public int selectBoardTotalCount();

}