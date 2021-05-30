package cn.zyk.pluton.portal.controller;

import cn.zyk.pluton.portal.model.Banner;
import cn.zyk.pluton.portal.service.IBannerService;
import cn.zyk.pluton.portal.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private IBannerService bannerService;
    @GetMapping("")
    public List<Banner> banner(){
        List<Banner> banners=bannerService.findByBanners();
        log.debug("轮播:{}",banners.size());
        return banners;
    }
    @GetMapping("/banners")
    public Map banners(){
        List<Banner>banners=bannerService.findByBanners();
        Map map=new HashMap();
        map.put("code",0);
        map.put("data",banners);
        map.put("count",banners.size());
        map.put("msg","ok");
        return map;
    }
    @PostMapping("/addbanner")
    public void addbanner(Banner banner){
        bannerService.insertBanner(banner);
        log.debug("轮播图添加:{}",banner);
    }

    @Value("${pluton.resource.path}")
    private String resourcePath;
    @Value("${pluton.resource.host}")
    private String resourceHost;

    @PostMapping("/uploadImg")
    public R uploadimg(MultipartFile file) throws IOException {
        String path= DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDate.now());
        File folder= new File(resourcePath,path);
        folder.mkdirs();
        String fileName=file.getOriginalFilename();
        String ext=fileName.substring(fileName.lastIndexOf("."));
        String name= UUID.randomUUID().toString()+ext;
        File upload=new File(folder.getAbsolutePath(),name);
        file.transferTo(upload);
        String url=resourceHost+"/"+path+"/"+name;
        return R.ok(url);
    }
    @PostMapping("/updateImg/{id}")
    public R uploadImg(MultipartFile file,@PathVariable Integer id) throws IOException {
        String path= DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDate.now());
        File folder= new File(resourcePath,path);
        folder.mkdirs();
        log.debug("上传的目标文件夹:{}",folder.getAbsolutePath());
        String fileName=file.getOriginalFilename();
        String ext=fileName.substring(fileName.lastIndexOf("."));
        String name= UUID.randomUUID().toString()+ext;
        log.debug("上传文件名为:{}",name);
        File upload=new File(folder.getAbsolutePath(),name);
        file.transferTo(upload);
        log.debug("文件完整路径:{}",upload.getAbsolutePath());
        String url=resourceHost+"/"+path+"/"+name;
        bannerService.updateBannerUrlById(id,url);
        log.debug("Url:{}",url);
        log.debug("图片路径{}",file.getOriginalFilename());
        log.debug("id:{}",id);
        return R.ok(url);
    }
    @GetMapping("/updateField")
    public R updateField(Integer id,String value,String field){
        log.debug("id:{}",id);
        log.debug("value:{}",value);
        log.debug("field:{}",field);
        bannerService.updateBannerFieldById(id, value, field);
        return R.ok("ok");
    }

    @GetMapping("/delete/{id}")
    public R delete(@PathVariable Integer id){
        log.debug("Id:{}",id);
        bannerService.deleteBannerById(id);
        return R.ok("成功删除");
    }

    @GetMapping("/details/{id}")
    public R details(@PathVariable Integer id){
        if (id==null){
            return R.notFound("请选择商品");
        }
        Banner banner = bannerService.findBannerById(id);
        log.debug("banner:{}",banner);
        return R.ok(banner);
    }
}
