package cn.zyk.pluton.portal.controller;

import cn.zyk.pluton.portal.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {
    public static final GrantedAuthority ADMIN=new SimpleGrantedAuthority("ROLE_ADMIN");
    public static final GrantedAuthority USER=new SimpleGrantedAuthority("ROLE_USER");

    @GetMapping("/management")
    public R management(@AuthenticationPrincipal UserDetails user){
        log.debug("user:{}",user.getAuthorities());
        if (user.getAuthorities().contains(ADMIN)){
            return R.ok("ok");
        }else if (user.getAuthorities().contains(USER)){
            return R.unauthorized("权限不足");
        }

        return null;
    }
}
