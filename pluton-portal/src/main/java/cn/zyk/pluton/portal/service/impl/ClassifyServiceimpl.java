package cn.zyk.pluton.portal.service.impl;



import cn.zyk.pluton.portal.mapper.ClassifyMapper;
import cn.zyk.pluton.portal.model.Classify;
import cn.zyk.pluton.portal.service.IClassifyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassifyServiceimpl extends ServiceImpl<ClassifyMapper, Classify> implements IClassifyService {
    @Autowired
    private ClassifyMapper classifyMapper;

    public List<Classify> findByClassifys() {
        List<Classify>classifies=classifyMapper.findByClassifys();
        return classifies;
    }


}
