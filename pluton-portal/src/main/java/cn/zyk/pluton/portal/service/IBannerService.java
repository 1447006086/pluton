package cn.zyk.pluton.portal.service;


import cn.zyk.pluton.portal.model.Banner;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;

public interface IBannerService extends IService<Banner> {
    List<Banner> findByBanners();
    void insertBanner(Banner banner);

    void updateBannerUrlById(Integer id,String url);

    void updateBannerFieldById(Integer id,String value,String field);

    void deleteBannerById(Integer id);

    Banner findBannerById(Integer id);
}
