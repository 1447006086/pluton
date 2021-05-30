package cn.zyk.pluton.portal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Commodity implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String title;
    private String price;
    private String intro;
    private String url;
    @TableField("category_id")
    private Integer categoryId;
    @TableField(exist = false)
    private String classify;
}
