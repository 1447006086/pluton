package cn.zyk.pluton.portal.mapper;

import cn.zyk.pluton.portal.model.ShoppingTrolley;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingTrolleyMapper extends BaseMapper<ShoppingTrolley> {
    @Select("select id,user_id,sp_id,sp_name,sp_price,sp_count,sp_url,status from shopping_trolley where sp_id=#{id}")
    ShoppingTrolley findShoppingTrolleyBySpId(Integer spId);

    @Select("select shopping_trolley.id,user_id,sp_id,sp_name,sp_price,sp_count count,sp_url url,status,sum(sp_price*sp_count) sum,classify.name classify from shopping_trolley join commodity on commodity.id=sp_id join classify ON commodity.category_id=classify.id where user_id=#{userId} group by sp_id")
    List<ShoppingTrolley> getShoppingTrolleys(Integer userId);

    @Select("select sp_count count from shopping_trolley where sp_id=#{spId} and user_id=#{userId}")
    Integer findByShoppingTrolleysSpId(Integer spId,Integer userId);

    @Update("update shopping_trolley set sp_count=#{count} where sp_id=#{spId} and user_id=#{user_id}")
    Integer updateShoppingTrolleysBySpId(Integer count,Integer spId,Integer user_id);

    @Update("update shopping_trolley set sp_count=#{value} where id=#{id}")
    Integer updateShoppingTrolleyCountById(Integer value,Integer id);
}
