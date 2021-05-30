package cn.zyk.pluton.portal.mapper;


import cn.zyk.pluton.portal.model.Classify;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassifyMapper extends BaseMapper<Classify> {
    @Select("select id,name from classify")
    List<Classify> findByClassifys();

    @Select("select classify.id,name from classify left join commodity c on c.category_id=classify.id where c.id=#{spId}")
    Classify findClassfiyById(Integer spId);


}
