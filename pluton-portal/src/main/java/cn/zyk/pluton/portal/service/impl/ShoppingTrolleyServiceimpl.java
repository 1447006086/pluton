package cn.zyk.pluton.portal.service.impl;

import cn.zyk.pluton.portal.mapper.ClassifyMapper;
import cn.zyk.pluton.portal.mapper.ShoppingTrolleyMapper;
import cn.zyk.pluton.portal.mapper.UserMapper;
import cn.zyk.pluton.portal.model.Classify;
import cn.zyk.pluton.portal.model.ShoppingTrolley;
import cn.zyk.pluton.portal.model.User;
import cn.zyk.pluton.portal.service.IShoppingTrolleyService;
import cn.zyk.pluton.portal.service.ServiceException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingTrolleyServiceimpl extends ServiceImpl<ShoppingTrolleyMapper, ShoppingTrolley> implements IShoppingTrolleyService {
    @Autowired
    private ShoppingTrolleyMapper shoppingTrolleyMapper;

    @Autowired
    private ClassifyMapper classifyMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<ShoppingTrolley> getShoppingTrolleys(String username) {
        User user=userMapper.findeUserByName(username);
        List<ShoppingTrolley> shoppingTrolleys = shoppingTrolleyMapper.getShoppingTrolleys(user.getId());
        return shoppingTrolleys;
    }


    @Override
    @Transactional
    public void deleteShoppingTrolleyById(Integer id) {
        int i = shoppingTrolleyMapper.deleteById(id);
        if (i != 1) {
            throw new ServiceException().busy();
        }
    }

    @Override
    public ShoppingTrolley findShoppingTrolleyBySpId(Integer id) {
        ShoppingTrolley shoppingTrolley=shoppingTrolleyMapper.findShoppingTrolleyBySpId(id);
        return shoppingTrolley;
    }

    @Override
    @Transactional
    public void addShoppingTrolley(ShoppingTrolley shoppingTrolley,String username) {
        User user=userMapper.findeUserByName(username);
        Integer num=shoppingTrolleyMapper.findByShoppingTrolleysSpId(shoppingTrolley.getSpId(),user.getId());
        if (num!=null){
            Integer count = shoppingTrolley.getCount();
            shoppingTrolley.setCount(count+num);
            shoppingTrolleyMapper.updateShoppingTrolleysBySpId(shoppingTrolley.getCount(),shoppingTrolley.getSpId(),user.getId());
            return;
        }
        Classify classify=classifyMapper.findClassfiyById(shoppingTrolley.getSpId());
        shoppingTrolley.setStatus(0);
        shoppingTrolley.setUserId(user.getId());
        shoppingTrolley.setClassify(classify.getName());
        int insert = shoppingTrolleyMapper.insert(shoppingTrolley);
        if (insert!=1){
            throw ServiceException.busy();
        }
    }

    @Override
    public List<ShoppingTrolley> findShoppingTrolleyByUserId(String username) {
        User user=userMapper.findeUserByName(username);
        List<ShoppingTrolley> shoppingTrolleys = shoppingTrolleyMapper.getShoppingTrolleys(user.getId());
        return shoppingTrolleys;
    }

    @Override
    public List<ShoppingTrolley> shoppingToSession(String shopping,HttpServletRequest request) {
        if (shopping==null){
            throw ServiceException.busy();
        }
        JSONArray jsonArray = JSONArray.fromObject(shopping);
        List<ShoppingTrolley>list=new ArrayList<>();
        if (shopping!=null){
            for (int i = 0; i < jsonArray.size(); i++) {
                Integer id = (int) jsonArray.getJSONObject(i).get("id");
                Integer spId = (int) jsonArray.getJSONObject(i).get("spId");
                Integer userId=(int) jsonArray.getJSONObject(i).get("userId");
                String spName = (String) jsonArray.getJSONObject(i).get("spName");
                String spPrice = (String) jsonArray.getJSONObject(i).get("spPrice");
                Integer count = (int) jsonArray.getJSONObject(i).get("count");
                String url = (String) jsonArray.getJSONObject(i).get("url");
                Integer sum = (int) jsonArray.getJSONObject(i).get("sum");
                Integer status = (int) jsonArray.getJSONObject(i).get("status");
                String classify = (String) jsonArray.getJSONObject(i).get("classify");
                list.add(new ShoppingTrolley(id,userId,spId,spName,spPrice,count,url,status,sum,classify));
            }
            HttpSession session= request.getSession();
            session.setAttribute("list",list);
        }
        return list;
    }

    @Override
    public List<ShoppingTrolley> getSessionShoppingTrolley(HttpServletRequest request) {
        HttpSession session=request.getSession();
        List<ShoppingTrolley>list= (List<ShoppingTrolley>) session.getAttribute("list");
        return list;
    }

    @Override
    public List<ShoppingTrolley> getJsonToList(String shopping) {
        if (shopping==null){
            throw ServiceException.busy();
        }
        JSONArray jsonArray = JSONArray.fromObject(shopping);
        List<ShoppingTrolley>list=new ArrayList<>();
        if (shopping!=null){
            for (int i = 0; i < jsonArray.size(); i++) {
                Integer id = (int) jsonArray.getJSONObject(i).get("id");
                Integer spId = (int) jsonArray.getJSONObject(i).get("spId");
                Integer userId=(int) jsonArray.getJSONObject(i).get("userId");
                String spName = (String) jsonArray.getJSONObject(i).get("spName");
                String  spPrice = (String) jsonArray.getJSONObject(i).get("spPrice");
                Integer count = (int) jsonArray.getJSONObject(i).get("count");
                String url = (String) jsonArray.getJSONObject(i).get("url");
                Integer sum = (int) jsonArray.getJSONObject(i).get("sum");
                Integer status = (int) jsonArray.getJSONObject(i).get("status");
                String classify = (String) jsonArray.getJSONObject(i).get("classify");
                list.add(new ShoppingTrolley(id,userId,spId,spName,spPrice,count,url,status,sum,classify));
            }
        }
        return list;
    }

    @Override
    @Transactional
    public void UpdateShoppingTrolleyCountById(Integer value, Integer id) {
        if (value==null||id==null){
            throw ServiceException.notFound("数据错误");
        }
        Integer integer = shoppingTrolleyMapper.updateShoppingTrolleyCountById(value, id);
        if (integer!=1){
            throw ServiceException.busy();
        }
    }
}
