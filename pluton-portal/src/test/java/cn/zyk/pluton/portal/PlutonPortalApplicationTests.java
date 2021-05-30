package cn.zyk.pluton.portal;

import cn.zyk.pluton.portal.mapper.ClassifyMapper;
import cn.zyk.pluton.portal.model.Classify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class PlutonPortalApplicationTests {
    @Autowired
    ClassifyMapper mapper;
    @Resource
    RedisTemplate redisTemplate;
    @Test
    void contextLoads() {
        List<Classify> findgogo = redisTemplate.opsForList().range("classify",0,-1);
        System.out.println(findgogo);
    }
    @Test
    void classify(){
        List<Classify> byClassifys = mapper.findByClassifys();
        redisTemplate.opsForValue().set("classify",byClassifys);
        List<Classify>classifies= (List<Classify>) redisTemplate.opsForValue().get("classify");
        System.out.println(classifies);
        classifies.forEach(s-> System.out.println(s));
    }


}
