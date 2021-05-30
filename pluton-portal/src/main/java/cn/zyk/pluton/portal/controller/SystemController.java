package cn.zyk.pluton.portal.controller;


import cn.zyk.pluton.portal.service.IUserService;
import cn.zyk.pluton.portal.vo.RegisterVo;
import com.zhenzi.sms.ZhenziSmsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class SystemController {
    @Autowired
    private IUserService userService;
    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("/register")
    public String register(@Validated RegisterVo registerVo, BindingResult result,HttpSession session) {
        log.debug("注册信息为:{}", registerVo);
        if (result.hasErrors()) {
            String error = result.getFieldError().getDefaultMessage();
            log.debug("注册失败:{}", error);
            return error;
        }
        if (!registerVo.getPassword().equals(registerVo.getConfirm())) {
            log.debug("密码不一致");
            return "两次密码输入不一致";
        }
        //验证码
        String code = (String) redisTemplate.opsForValue().get("register"+registerVo.getPhone()+"code");
        //手机号
        String phone = (String) redisTemplate.opsForValue().get("register"+registerVo.getPhone()+"phone");
        log.debug("code:{}",code);
        log.debug("phone:{}",phone);
        if (!registerVo.getPhone().equals(phone)){
            return "手机号码不一致";
        }
        if (!registerVo.getCode().equals(code)){
            return "验证码错误";
        }
        String msg = userService.registerUser(registerVo);
        return msg;
    }

    private String apiUrl = "https://sms_developer.zhenzikj.com";
    private String appId = "108973";
    private String appSecret = "197b5280-d37a-4030-964a-9b166e60670f";

    @GetMapping("/code")
    public boolean getCode(String phone, HttpSession httpSession) {
        try {
            log.debug("memPhone:{}",phone);
            //随机生成验证码
            String code = String.valueOf(new Random().nextInt(999999));
            //将验证码通过榛子云接口发送至手机
            ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("number", phone);
            params.put("templateId", "5118");
            String[] templateParams = new String[2];
            templateParams[0] = code;
            templateParams[1] = "5分钟";
            params.put("templateParams", templateParams);
            String result = client.send(params);

            log.debug("result:{}",result);
            System.out.println(result);
            if (!result.contains("0")){
                return false;
            }
            redisTemplate.opsForValue().set("register"+phone+"phone",phone);
            redisTemplate.opsForValue().set("register"+phone+"code",code,5, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


