package cn.zyk.pluton.portal.service.impl;


import cn.zyk.pluton.portal.mapper.CommodityMapper;
import cn.zyk.pluton.portal.model.Commodity;
import cn.zyk.pluton.portal.service.ICommodityService;
import cn.zyk.pluton.portal.service.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements ICommodityService {
    @Autowired
    private CommodityMapper commodityMapper;
    @Override
    public List<Commodity> findByPopular() {
        List<Commodity> commodities=commodityMapper.getCommodityPopular();
        return commodities;
    }

    @Override
    public List<Commodity> findByCategoryId(Integer id) {
        List<Commodity>commodities=commodityMapper.findCommodityByClassifyId(id);
        return commodities;
    }

    @Override
    public List<Commodity> getCommoditys() {
        List<Commodity> commodities = commodityMapper.getCommoditys();
        return commodities;
    }

    @Override
    @Transactional
    public void updateCommodityUrlByid(Integer id,String url) {
       commodityMapper.updateCommodityUrlByid(id,url);
    }

    @Override
    @Transactional
    public void updateCommodity(Integer id, String value, String field) {
        if (field.equals("title")){
            commodityMapper.updateCommodityTitleById(id,value);
        }else if (field.equals("price")){
            commodityMapper.updateCommodityPriceById(id,value);
        }else if (field.equals("intro")){
            commodityMapper.updateCommodityIntroById(id,value);
        }
    }

    @Override
    @Transactional
    public int delteCommodityById(Integer id) {
        int num=commodityMapper.deleteById(id);
        return num;
    }

    @Override
    @Transactional
    public void addCommodity(Commodity commodity) {
        System.out.println(commodity);
       int i=commodityMapper.insert(commodity);
       if (i!=1){
           throw ServiceException.busy();
       }
    }

    @Override
    public PageInfo<Commodity> pageCommodity(Integer pageNum, Integer pagelimit) {
        PageHelper.startPage(pageNum,pagelimit);
        QueryWrapper queryWrapper=new QueryWrapper();
        List<Commodity>commodities=commodityMapper.selectList(queryWrapper);
        return new PageInfo<>(commodities);
    }

    @Override
    public PageInfo<Commodity> pageSeekCommodity(Integer id,Integer pageNum,Integer pagelimit) {
        PageHelper.startPage(pageNum,pageNum);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("category_id",id);
        List<Commodity>commodities=commodityMapper.selectList(queryWrapper);
        return new PageInfo<>(commodities);
    }

    @Override
    public List<Commodity> getCommoditySeek(Integer id) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("category_id",id);
        List<Commodity> commodity = commodityMapper.selectList(queryWrapper);;
        return commodity;
    }
    @Override
    public List<Commodity> findCommodityByValue(String value) {
        List<Commodity>commodities=commodityMapper.findCommodityByValue(value);
        return commodities;
    }

    @Override
    public Commodity findCommodityByid(Integer id) {
        if (id==null){
            throw ServiceException.busy();
        }
        Commodity commodity=commodityMapper.selectById(id);
        return commodity;
    }

}
