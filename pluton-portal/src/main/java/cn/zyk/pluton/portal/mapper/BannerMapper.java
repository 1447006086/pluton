package cn.zyk.pluton.portal.mapper;


import cn.zyk.pluton.portal.model.Banner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerMapper extends BaseMapper<Banner> {
    @Select("select banner.id,title,autro,price,category_id,infor,url,c.name classify from banner left join classify c on c.id=banner.category_id")
    List<Banner> getBybanner();
    @Update("update banner set url=#{url} where id=#{id}")
    void UpdateUrlByid(Integer id,String url);

    @Update("update banner set title=#{value} where id=#{id}")
    void updateTitleById(Integer id,String value,String field);

    @Update("update banner set price=#{value} where id=#{id}")
    void updatePriceById(Integer id,String value,String field);

    @Update("update banner set autro=#{value} where id=#{id}")
    void updateAutroById(Integer id,String value,String field);

    @Update("update banner set infor=#{value} where id=#{id}")
    void updateinforById(Integer id,String value,String field);

}
