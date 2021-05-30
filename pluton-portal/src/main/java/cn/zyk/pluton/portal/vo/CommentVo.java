package cn.zyk.pluton.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CommentVo implements Serializable {
    @NotBlank(message = "内容不能为空")
    private String content;
    @NotNull(message = "请选择星级")
    private double rate;
    @NotNull(message = "评论商品不能为空")
    private Integer spId;
}
