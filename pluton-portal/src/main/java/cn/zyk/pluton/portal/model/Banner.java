package cn.zyk.pluton.portal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class Banner implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String title;
    private String autro;
    private String price;
    @TableField("category_id")
    private String categoryId;
    private String infor;
    private String url;
    @TableField(exist = false)
    private String classify;
}
