package cn.zyk.pluton.portal.service.impl;


import cn.zyk.pluton.portal.mapper.BannerMapper;
import cn.zyk.pluton.portal.model.Banner;
import cn.zyk.pluton.portal.service.IBannerService;
import cn.zyk.pluton.portal.service.ServiceException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceimpl extends ServiceImpl<BannerMapper,Banner> implements IBannerService {
    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<Banner> findByBanners() {
        List<Banner>banners=bannerMapper.getBybanner();
        return banners;
    }

    @Override
    public void insertBanner(Banner banner) {
        int i = bannerMapper.insert(banner);
        if (i!=1){
            throw ServiceException.busy();
        }
    }

    @Override
    public void updateBannerUrlById(Integer id, String url) {
        bannerMapper.UpdateUrlByid(id,url);
    }

    @Override
    public void updateBannerFieldById(Integer id, String value, String field) {
        if (field.equals("title")){
            bannerMapper.updateTitleById(id,value,field);
        }else if (field.equals("price")){
            bannerMapper.updatePriceById(id,value,field);
        }else if (field.equals("autro")){
            bannerMapper.updateAutroById(id,value,field);
        }else if (field.equals("infor")){
            bannerMapper.updateinforById(id,value,field);
        }
    }

    @Override
    public void deleteBannerById(Integer id) {
        int i = bannerMapper.deleteById(id);
        if (i!=1){
            throw ServiceException.busy();
        }
    }

    @Override
    public Banner findBannerById(Integer id) {
        if (id==null){
            throw ServiceException.notFound("id为空");
        }
        Banner banner = bannerMapper.selectById(id);
        return banner;
    }
}
