package cn.zyk.pluton.portal.controller;

import cn.zyk.pluton.portal.model.User;
import cn.zyk.pluton.portal.service.IUserService;
import cn.zyk.pluton.portal.vo.R;
import cn.zyk.pluton.portal.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/my")
    public R my(@AuthenticationPrincipal UserDetails user){
        if(user!=null){
            User my= userService.findByusername(user.getUsername());
            if (my.getUserNickname()==null){
                return R.unauthorized("请填写信息");
            }
            return R.ok(my);
        }
       return R.notFound("用户不存在");
    }

    @PostMapping("/add")
    public R add(UserVo userVo, @AuthenticationPrincipal UserDetails userDetails, BindingResult result){
        if (result.hasErrors()){
            String msg=result.getFieldError().getDefaultMessage();
            return R.invalidRequest(msg);
        }
        userService.addUser(userVo,userDetails.getUsername());
        log.debug("userVo:{}",userVo);
        return R.ok("成功");
    }

    @GetMapping("/users")
    public Map users(){
        List<User> users = userService.getUsers();
        Map map=new HashMap();
        map.put("code",0);
        map.put("data",users);
        map.put("count",users.size());
        map.put("msg","ok");
        return map;
    }

    @GetMapping("/updateField")
    public R updateField(Integer id,String value,String field){
        log.debug("id:{}",id);
        log.debug("value:{}",value);
        log.debug("field:{}",field);
        R r = userService.updateUserById(id, value, field);
        return r;
    }

    @Value("${pluton.resource.path}")
    private String resourcePath;
    @Value("${pluton.resource.host}")
    private String resourceHost;

    @PostMapping("/updateImg/{id}")
    public R uploadImg(MultipartFile file, @PathVariable Integer id) throws IOException {
        String path= DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDate.now());
        File folder= new File(resourcePath,path);
        folder.mkdirs();
        log.debug("上传的目标文件夹:{}",folder.getAbsolutePath());
        String fileName=file.getOriginalFilename();
        String ext=fileName.substring(fileName.lastIndexOf("."));
        String name= UUID.randomUUID().toString()+ext;
        log.debug("上传文件名为:{}",name);
        File upload=new File(folder.getAbsolutePath(),name);
        file.transferTo(upload);
        log.debug("文件完整路径:{}",upload.getAbsolutePath());
        String url=resourceHost+"/"+path+"/"+name;
        userService.updateUserImgById(id,url);
        log.debug("Url:{}",url);
        log.debug("图片路径{}",file.getOriginalFilename());
        log.debug("id:{}",id);
        return R.ok(url);
    }
    private static final GrantedAuthority ADMIN=new SimpleGrantedAuthority("ROLE_ADMIN");
    private static final GrantedAuthority USER=new SimpleGrantedAuthority("ROLE_USER");
    private static final GrantedAuthority ROOT=new SimpleGrantedAuthority("ROLE_ROOT");

    @GetMapping("/permissions/{id}")
    public R permissions(@PathVariable Integer id,@AuthenticationPrincipal UserDetails user){
        if (id==null){
            return R.notFound("id为空");
        }
        if (user.getAuthorities().contains(ROOT)){
            userService.addUserPermissionsById(id);
            return R.ok("已将该用户设置为管理员");
        }else {
            return R.unauthorized("权限不足");
        }
    }
    @GetMapping("/deletePermissions/{id}")
    public R deletePermissions(@PathVariable Integer id,@AuthenticationPrincipal UserDetails user){
        if (id==null){
            return R.notFound("id为空");
        }
        if (user.getAuthorities().contains(ROOT)){
            userService.deletePermissionsById(id);
            return R.ok("已撤销该用户的管理员权限");
        }else {
            return R.unauthorized("权限不足");
        }

    }

    @GetMapping("/admins")
    public Map admins(){
        List<User> admins = userService.getAdmins();
        Map map=new HashMap();
        map.put("code",0);
        map.put("data",admins);
        map.put("count",admins.size());
        map.put("msg","ok");
        return map;
    }

    @GetMapping("/management/permissions")
    public R management(@AuthenticationPrincipal UserDetails user){
        if (user.getAuthorities().contains(ROOT)){
            return R.ok("允许");
        }else{
            return R.unauthorized("权限不足");
        }
    }

    @GetMapping("/enabled/{id}")
    public R enabled(@PathVariable Integer id){
        log.debug("禁用ID:{}",id);
        if (id==null){
            return R.notFound("请选择用户");
        }
        userService.updateUserEnabledById(id);
        return R.ok("已禁止此用户登录");
    }

    @GetMapping("/enabled")
    public Map getEnabled(){
        List<User>enabled= userService.getEnabled();
        Map map=new HashMap();
        map.put("code",0);
        map.put("data",enabled);
        map.put("count",enabled.size());
        map.put("msg","ok");
        return map;
    }

    @GetMapping("/management/blacklist")
    public R managementBlacklist(@AuthenticationPrincipal UserDetails user){
        if (user.getAuthorities().contains(ROOT)){
            return R.ok("允许");
        }else{
            return R.unauthorized("权限不足");
        }
    }

    @GetMapping("/blacklist/{id}")
    public R blacklist(@PathVariable Integer id){
        if (id==null){
            R.notFound("请选择用户");
        }
        userService.updateAdmin_userBlacklistById(id);
        return R.ok("已将该用户移出黑名单");
    }
}
