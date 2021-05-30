package cn.zyk.pluton.portal.vo;


import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RegisterVo implements Serializable {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "确认密码不能为空")
    private String confirm;
    @NotBlank(message = "号码不能为空")
    private String phone;
    @NotBlank(message = "验证码不能为空")
    private String code;
}
