package cn.zyk.pluton.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserVo implements Serializable {
    @NotBlank(message = "昵称不能为空")
    private String userNickname;
    @NotBlank(message = "生日不能为空")
    private String date;
    @NotBlank(message = "性别不能为空")
    private String sex;
    private String url;
}
