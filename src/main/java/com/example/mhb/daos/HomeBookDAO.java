package com.example.mhb.daos;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.example.mhb.dto.HomeBook;


@Mapper
public interface HomeBookDAO {
    
    @Insert("INSERT INTO HOMEBOOK (SERIALNO, DAY, SECTION, ACCOUNTTITLE, REMARK, REVENUE, EXPENSE, MID) " +
            "VALUES (HOMEBOOK_SEQ.NEXTVAL,sysdate, #{section}, #{accounttitle}, #{remark}, #{revenue}, #{expense}, #{mid})")
    void addHomeBook(HomeBook homebook);
    
    @Select("SELECT * FROM homebook")
    List<HomeBook> getAllHomeBooks();
    
    @Select("SELECT * FROM homebook WHERE SERIALNO = #{serialno}")
    HomeBook  getHomeBookbySN(long sn);
    
    @Select("SELECT * FROM homebook WHERE MID = #{mid}")
    List<HomeBook> getAllHomeBooksById(String id);
    
    @Update("UPDATE homebook SET day = #{day}, section = #{section}, accounttitle = #{accounttitle}, " +
            "remark = #{remark}, revenue = #{revenue}, expense = #{expense}, mid = #{mid} WHERE serialno = #{serialno}")
    int updateHomeBook(HomeBook homeBook);
    
    @Delete("DELETE FROM homebook WHERE serialno = #{serialno}")
    int deleteHomeBook(long sn);
   
}