package cn.zyk.pluton.portal.service;


import cn.zyk.pluton.portal.model.Commodity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICommodityService extends IService<Commodity> {
    List<Commodity> findByPopular();

    List<Commodity> findByCategoryId(Integer id);

    List<Commodity> getCommoditys();

    void updateCommodityUrlByid(Integer id,String url);
    void updateCommodity(Integer id,String value,String field);

    int delteCommodityById(Integer id);

    void addCommodity(Commodity commodity);

    PageInfo<Commodity> pageCommodity(Integer pageNum,Integer pagelimit);

    PageInfo<Commodity> pageSeekCommodity(Integer id,Integer PageNum ,Integer pagelimit);

    List<Commodity> getCommoditySeek(Integer id);

    List<Commodity> findCommodityByValue(String value);

    Commodity findCommodityByid(Integer id);
}
