package cn.zyk.pluton.portal.mapper;


import cn.zyk.pluton.portal.model.Commodity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityMapper extends BaseMapper<Commodity> {
    @Select("select id,title,price,intro,url,category_id from commodity limit 12")
    List<Commodity> getCommodityPopular();

    @Select("select id,title,price,intro,url,category_id from commodity where category_id=#{id}")
    List<Commodity> findCommodityByClassifyId(Integer id);

    @Select("select a.id,title,price,intro,url,category_id,c.name classify from commodity a left join classify c on category_id=c.id ")
    List<Commodity> getCommoditys();

    @Select("update commodity set title=#{value} where id=#{id}")
    void updateCommodityTitleById(Integer id,String value);

    @Select("update commodity set price=#{value} where id=#{id}")
    void updateCommodityPriceById(Integer id,String value);

    @Select("update commodity set intro=#{value} where id=#{id}")
    void updateCommodityIntroById(Integer id,String value);

    @Update("update commodity set url=#{url} where id=#{id}")
    void updateCommodityUrlByid(Integer id,String url);

    @Select("select id,title,price,intro,url,category_id from commodity where price like concat('%',#{val},'%') or title like concat('%',#{val},'%')")
    List<Commodity> findCommodityByValue(String val);
}
