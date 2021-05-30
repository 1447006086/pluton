package cn.zyk.pluton.portal.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class Classify implements Serializable {
    private Integer id;
    private String name;
}
