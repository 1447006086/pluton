package cn.zyk.pluton.portal.controller;


import cn.zyk.pluton.portal.model.Classify;
import cn.zyk.pluton.portal.model.Commodity;
import cn.zyk.pluton.portal.service.IClassifyService;
import cn.zyk.pluton.portal.service.ICommodityService;
import cn.zyk.pluton.portal.vo.R;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
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
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/commodity")
@Slf4j
public class CommodityController {
    @Autowired
    private ICommodityService commodityService;
    @Autowired
    private IClassifyService classifyService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/popular")
    public List<Commodity> popular(){
        List<Commodity>commodities;
        if (redisTemplate.hasKey("popular")){
            commodities= (List<Commodity>) redisTemplate.opsForValue().get("popular");
            log.debug("redis:{}",commodities.size());
            return commodities;
        }
        commodities= commodityService.findByPopular();
        redisTemplate.opsForValue().set("popular",commodities,1, TimeUnit.MINUTES);
        log.debug("热门商品:{}",commodities.size());
        return commodities;
    }

    @GetMapping("/classifys")
    @ResponseBody
    public List<Classify> classifies(){
        log.debug("查询分类");
        List<Classify>classif;
        if (redisTemplate.hasKey("classify")) {
            classif= (List<Classify>) redisTemplate.opsForValue().get("classify");
            log.debug("redis:{}",classif);
            return classif;
        }
        classif=classifyService.findByClassifys();
        redisTemplate.opsForValue().set("classify",classif);
        log.debug("db:{}",classif);
        return classif;
    }

    @GetMapping("/classify/{id}")
    public List<Commodity>commoditie(@PathVariable Integer id){
        log.debug("id:{}",id);
        if (id==0){
            List<Commodity>commodities= commodityService.findByPopular();
            return commodities;
        }
        List<Commodity>commodities=commodityService.findByCategoryId(id);
        return commodities;
    }

    @GetMapping("/commoditys")
    public Map commodities(){
        List<Commodity> commodities=commodityService.getCommoditys();
        log.debug("商品管理:{}",commodities.size());
        Map map=new HashMap();
        map.put("code",0);
        map.put("data",commodities);
        map.put("count",commodities.size());
        map.put("msg","ok");
        return map;
    }
    @GetMapping("/delete/{id}")
    public R delete(@PathVariable Integer id){
        log.debug("删除商品:{}",id);
        int num = commodityService.delteCommodityById(id);
        if (num!=1){
            return R.invalidRequest("删除失败");
        }

        return R.gone("删除成功");
    }
    @GetMapping("/update")
    public R update(Integer id,String value,String field){
        if (id!=null&&value!=null&&field!=null){
              commodityService.updateCommodity(id,value,field);
             return R.ok("商品信息已修改");
        }
        return R.invalidRequest("修改失败");
    }

    @PostMapping("/addcommodity")
    public void addcommodity(Commodity commodity){
      log.debug("新增商品:{}",commodity);
      commodityService.addCommodity(commodity);
    }

    @Value("${pluton.resource.path}")
    private String resourcePath;
    @Value("${pluton.resource.host}")
    private String resourceHost;
    @PostMapping("/upload/img")
    public R uploadImg(MultipartFile file) throws IOException {
        String path= DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDate.now());
        File folder= new File(resourcePath,path);
        folder.mkdirs();
        log.debug("上传的目标文件夹:{}",folder.getAbsolutePath());
        String fileName=file.getOriginalFilename();
        String ext=fileName.substring(fileName.lastIndexOf("."));
        String name= UUID.randomUUID().toString()+ext;
        File upload=new File(folder.getAbsolutePath(),name);
        file.transferTo(upload);
        log.debug("文件完整路径:{}",upload.getAbsolutePath());
        String url=resourceHost+"/"+path+"/"+name;
        log.debug("Url:{}",url);
        return R.ok(url);
    }
    @PostMapping("/updateImg/{id}")
    public R updateimg(MultipartFile file,@PathVariable Integer id) throws IOException {
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
        commodityService.updateCommodityUrlByid(id,url);
        log.debug("图片路径{}",file.getOriginalFilename());
        return R.ok("上传成功");
    }

    @GetMapping("/page")
    public PageInfo<Commodity> page(Integer pageNum,Integer pagelimit){
        log.debug("页数:{}",pageNum);
        log.debug("limit:{}",pagelimit);
        PageInfo<Commodity> page=commodityService.pageCommodity(pageNum,pagelimit);
        return page;
    }

    @GetMapping("/seek/{id}")
    public R<List<Commodity>> seek(@PathVariable Integer id){
        if (id==0){
            List<Commodity> commodities=commodityService.getCommoditys();
            return R.ok(commodities);
        }
        List<Commodity> commodities=commodityService.getCommoditySeek(id);
        return R.ok(commodities);
    }

    @GetMapping("/search")
    public R<List<Commodity>> search(String val){
        List<Commodity>commodities=commodityService.findCommodityByValue(val);
        return R.ok(commodities);
    }

    @GetMapping("/datails/{id}")
    public R details(@PathVariable Integer id){
        log.debug("商品详情:{}",id);
        Commodity commodity=commodityService.findCommodityByid(id);
        return R.ok(commodity);
    }


}
