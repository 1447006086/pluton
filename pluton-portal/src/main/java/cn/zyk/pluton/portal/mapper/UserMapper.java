package cn.zyk.pluton.portal.mapper;

import cn.zyk.pluton.portal.model.Permission;
import cn.zyk.pluton.portal.model.Role;
import cn.zyk.pluton.portal.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {
    @Select("select id,username,password,user_nickname,url,sex,date,enabled from admin_user where username=#{username}")
    User findeUserByName(String username);

    @Select("select id,username,password,user_nickname,url,sex,date,enabled from admin_user where phone=#{phone}")
    User findeUserByPhone(String phone);

    @Select("select p.id,p.name" +
            " from admin_user u" +
            " left join user_role ur on ur.user_id=u.id" +
            " left join role r on r.id=ur.role_id" +
            " left join role_permission rp on rp.role_id=r.id" +
            " left join permission p on p.id=rp.permission_id" +
            " where u.id=#{id}")
    List<Permission> findUserPermissionById(Integer id);

    @Select("select r.id,r.name" +
            " from admin_user u" +
            " left join user_role ur on ur.user_id=u.id" +
            " left join role r on r.id=ur.role_id" +
            " where u.id=#{id}")
    List<Role> findUserRoleById(Integer id);

    @Select("select u.id,u.username,u.password,u.user_nickname,url,sex,date,phone from admin_user u where u.id not in(select u.id from admin_user u left join user_role ur on u.id=ur.user_id where role_id=1) and enabled!=1")
    List<User> getUsers();

    @Update("update admin_user set user_nickname=#{value} where id=#{id}")
    Integer updateUserNickNameById(Integer id,String value);

    @Update("update admin_user set phone=#{value} where id=#{id}")
    Integer updateUserPhoneById(Integer id,String value);

    @Update("update admin_user set sex=#{value} where id=#{id}")
    Integer updateUserSexById(Integer id,String value);

    @Update("update admin_user set url=#{url} where id=#{id}")
    Integer updateUserImgById(Integer id,String url);
    
    @Insert("insert into user_role values(null,#{id},1)")
    Integer addPermissions(Integer id);

    @Select("select u.id,u.username,u.user_nickname,u.url,u.sex,date,phone from admin_user u left join user_role ur on u.id=ur.user_id where role_id=1 and user_id!=1 and enabled!=1")
    List<User> getAdmins();

    @Delete("delete from user_role where user_role.user_id=#{id} and user_role.role_id=1")
    Integer deletePermissions(Integer id);

    @Update("update admin_user set enabled=1 where id=#{id}")
    Integer updateAdmin_userEnableById(Integer id);

    @Select("select id,username,user_nickname,url,sex,date,phone from admin_user where enabled=1")
    List<User> getEnabled();

    @Update("update admin_user set enabled=0 where id=#{id}")
    Integer updateAdmin_userBlacklistById(Integer id);
}
