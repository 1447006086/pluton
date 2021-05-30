package cn.zyk.pluton.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("cn.zyk.pluton.portal.mapper")
@EnableCaching
@EnableScheduling
public class PlutonPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlutonPortalApplication.class, args);
    }

}
