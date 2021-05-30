package cn.zyk.pluton.portal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("shopping_trolley")
public class ShoppingTrolley implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("sp_id")
    private Integer spId;

    @TableField("sp_name")
    private String spName;

    @TableField("sp_price")
    private String spPrice;

    @TableField("sp_count")
    private Integer count;

    @TableField("sp_url")
    private String url;

    @TableField("status")
    private Integer status;

    @TableField(exist = false)
    private Integer sum;

    @TableField(exist = false)
    private String classify;
}
