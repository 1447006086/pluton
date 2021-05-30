package cn.zyk.pluton.portal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Comment implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("sp_id")
    private Integer spId;

    @TableField("user_id")
    private Integer userId;

    @TableField("user_nickname")
    private String userNickName;

    @TableField("rate")
    private double rete;

    @TableField("content")
    private String content;

    @TableField("url")
    private String url;

    @TableField("createTime")
    private LocalDateTime dateTime;

    @TableField("user_like")
    private Integer userLike;

    @TableField("user_step")
    private Integer step;

}
