package cn.zyk.pluton.portal.service;

import cn.zyk.pluton.portal.model.ShoppingTrolley;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IShoppingTrolleyService extends IService<ShoppingTrolley> {
    List<ShoppingTrolley> getShoppingTrolleys(String username);

    void deleteShoppingTrolleyById(Integer id);

    ShoppingTrolley findShoppingTrolleyBySpId(Integer id);

    void addShoppingTrolley(ShoppingTrolley shoppingTrolley,String username);

    List<ShoppingTrolley> findShoppingTrolleyByUserId(String username);

    List<ShoppingTrolley> shoppingToSession(String shopping, HttpServletRequest request);

    List<ShoppingTrolley> getSessionShoppingTrolley(HttpServletRequest request);

    List<ShoppingTrolley> getJsonToList(String shopping);

    void UpdateShoppingTrolleyCountById(Integer value,Integer id);
}
