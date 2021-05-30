package cn.zyk.pluton.portal.service.impl;


import cn.zyk.pluton.portal.mapper.UserMapper;
import cn.zyk.pluton.portal.mapper.UserRoleMapper;
import cn.zyk.pluton.portal.model.Permission;
import cn.zyk.pluton.portal.model.Role;
import cn.zyk.pluton.portal.model.User;
import cn.zyk.pluton.portal.model.UserRole;
import cn.zyk.pluton.portal.service.IUserService;
import cn.zyk.pluton.portal.service.ServiceException;
import cn.zyk.pluton.portal.vo.R;
import cn.zyk.pluton.portal.vo.RegisterVo;
import cn.zyk.pluton.portal.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserServiceimpl extends ServiceImpl<UserMapper,User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails getUserDetails(String username) {
        User user = userMapper.findeUserByName(username);
        if (user == null) {
            user=userMapper.findeUserByPhone(username);
            if (user==null){
                throw ServiceException.unprocesabelEntity("用户不存在");
            }
        }
        List<Permission> ps = userMapper.findUserPermissionById(user.getId());
        String[] auth = new String[ps.size()];
        int i = 0;
        for (Permission p : ps) {
            auth[i++] = p.getName();
        }
        List<Role> roles = userMapper.findUserRoleById(user.getId());
        auth= Arrays.copyOf(auth,auth.length+roles.size());
        for (Role r:roles){
            auth[i++]=r.getName();
        }
        UserDetails u= org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(auth)
                .disabled(user.getEnabled()==1)
                .build();
        log.debug("成功登录");
        return u;
    }

    @Override
    public User findByusername(String username) {
        User user=userMapper.findeUserByName(username);
        return user;
    }


    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public String registerUser(RegisterVo registerVo) {
        User reg= userMapper.findeUserByName(registerVo.getUsername());
        log.debug("reg:{}",reg);
        if (reg!=null){
            return "用户名已存在";
        }
        if (reg==null){
            reg=userMapper.findeUserByPhone(registerVo.getPhone());
            if (reg!=null){
                return "手机号已被注册";
            }
        }
        User user=new User();
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        user.setUsername(registerVo.getUsername());
        user.setPhone(registerVo.getPhone());
        user.setPassword("{bcrypt}"+encoder.encode(registerVo.getPassword()));
        userMapper.insert(user);
        UserRole userRole=new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(2);
        System.out.println(user);
        userRoleMapper.insert(userRole);
        log.debug("注册成功");
        return "注册成功";
    }

    @Override
    public void addUser(UserVo userVo, String username) {
        User user=userMapper.findeUserByName(username);
        user.setDate(userVo.getDate())
                .setUserNickname(userVo.getUserNickname())
                .setSex(userVo.getSex())
                .setUrl(userVo.getUrl());

        int insert = userMapper.updateById(user);
        if (insert!=1){
            throw ServiceException.busy();
        }
    }

    @Override
    public List<User> getUsers() {
        List<User> users = userMapper.getUsers();
        return users;
    }

    @Override
    public R updateUserById(Integer id, String value, String field) {
        if (id==null||value==null||field==null){
            return R.unauthorized("信息不全");
        }
        Integer num=null;
        if ("userNickname".equals(field)){
             num= userMapper.updateUserNickNameById(id, value);
        }else if ("phone".equals(field)){
            num=userMapper.updateUserPhoneById(id,value);
        }else if ("sex".equals(field)){
            num=userMapper.updateUserSexById(id,value);
        }
        if (num!=1){
            throw ServiceException.busy();
        }
        return R.ok("修改成功");
    }

    @Override
    public String updateUserImgById(Integer id, String url) {
        userMapper.updateUserImgById(id,url);
        return url;
    }

    @Override
    @Transactional
    public void addUserPermissionsById(Integer id) {
        if (id==null){
            throw ServiceException.busy();
        }
        Integer integer = userMapper.addPermissions(id);
        if (integer!=1){
            throw ServiceException.unprocesabelEntity("数据出错");
        }
    }

    @Override
    public List<User> getAdmins() {
        List<User> admins = userMapper.getAdmins();
        return admins;
    }

    @Override
    public void deletePermissionsById(Integer id) {
        if (id==null){
            throw ServiceException.notFound("用户不存在");
        }
        Integer integer = userMapper.deletePermissions(id);
        if (integer!=1){
            throw ServiceException.unprocesabelEntity("撤销失败");
        }
    }

    @Override
    public void updateUserEnabledById(Integer id) {
        if (id==null){
            throw ServiceException.notFound("用户为空");
        }
        Integer integer = userMapper.updateAdmin_userEnableById(id);
        if (integer!=1){
            throw ServiceException.busy();
        }
    }

    @Override
    public List<User> getEnabled() {
        List<User> enabled = userMapper.getEnabled();
        return enabled;
    }

    @Override
    public void updateAdmin_userBlacklistById(Integer id) {
        if (id==null){
            throw ServiceException.notFound("找不到该用户");
        }
        Integer integer = userMapper.updateAdmin_userBlacklistById(id);
        if (integer!=1){
            throw ServiceException.busy();
        }
    }


}
