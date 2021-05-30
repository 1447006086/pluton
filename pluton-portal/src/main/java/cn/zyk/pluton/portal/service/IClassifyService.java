package cn.zyk.pluton.portal.service;


import cn.zyk.pluton.portal.model.Classify;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IClassifyService extends IService<Classify> {
    List<Classify> findByClassifys();

}
