package cn.zyk.pluton.portal.service;


import cn.zyk.pluton.portal.model.User;
import cn.zyk.pluton.portal.vo.R;
import cn.zyk.pluton.portal.vo.RegisterVo;
import cn.zyk.pluton.portal.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface IUserService extends IService<User> {

    UserDetails getUserDetails(String username);

    User findByusername(String username);

    String registerUser(RegisterVo registerVo);

    void addUser(UserVo userVo,String username);

    List<User> getUsers();

    R updateUserById(Integer id, String value, String field);

    String  updateUserImgById(Integer id,String url);

    void addUserPermissionsById(Integer id);

    List<User>getAdmins();

    void deletePermissionsById(Integer id);

    void updateUserEnabledById(Integer id);

    List<User>getEnabled();

    void updateAdmin_userBlacklistById(Integer id);
}
