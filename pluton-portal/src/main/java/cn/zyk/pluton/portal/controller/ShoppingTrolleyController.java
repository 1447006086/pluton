package cn.zyk.pluton.portal.controller;

import cn.zyk.pluton.portal.model.ShoppingTrolley;
import cn.zyk.pluton.portal.service.AliPayService;
import cn.zyk.pluton.portal.service.IShoppingTrolleyService;
import cn.zyk.pluton.portal.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/trolley")
@Slf4j
public class ShoppingTrolleyController {
    @Autowired
    private IShoppingTrolleyService shoppingTrolleyService;

    @GetMapping("")
    public R<List<ShoppingTrolley>> ShoppingTrolleys(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails==null){
            return R.notFound("");
        }
        List<ShoppingTrolley> shoppingTrolleys = shoppingTrolleyService.getShoppingTrolleys(userDetails.getUsername());
        log.debug("购物车:{}", shoppingTrolleys.size());
        return R.ok(shoppingTrolleys);
    }

    @GetMapping("/delete/{id}")
    public R delete(@PathVariable Integer id) {
        log.debug("删除:{}", id);
        shoppingTrolleyService.deleteShoppingTrolleyById(id);
        return R.gone("删除");
    }

    @GetMapping("/add")
    public R add(ShoppingTrolley shoppingTrolley, @AuthenticationPrincipal UserDetails user) {
        if (user == null||shoppingTrolley==null) {
            return R.unauthorized("错误");
        }
        shoppingTrolleyService.addShoppingTrolley(shoppingTrolley, user.getUsername());
        log.debug("shoppingTrolley:{}", shoppingTrolley);
        return R.ok("成功");
    }

    @GetMapping("/shoppingTrolleys")
    public R shoppingTrolley(@AuthenticationPrincipal UserDetails userDetails) {
        List<ShoppingTrolley> shoppingTrolleyByUserId = shoppingTrolleyService.findShoppingTrolleyByUserId(userDetails.getUsername());
        return R.ok(shoppingTrolleyByUserId);
    }

    @GetMapping("/orderForm")
    public R select(String shopping, HttpServletRequest request) throws Exception {
        if (shopping == null) {
            R.notFound("请选择商品");
        }
        log.debug("传入:{}", shopping);
        List<ShoppingTrolley> shoppingTrolleys = shoppingTrolleyService.shoppingToSession(shopping, request);
        log.debug("传出:{}", shoppingTrolleys);
        return R.ok("ok");
    }

    @GetMapping("/orderForm/session")
    public R orderForm(HttpServletRequest request) {
        List<ShoppingTrolley> sessionShoppingTrolley = shoppingTrolleyService.getSessionShoppingTrolley(request);
        return R.ok(sessionShoppingTrolley);
    }

    @GetMapping("/countUpdate")
    public R countUpdate(Integer value,Integer id){
        if (value==null||id==null){
            return R.notFound("请选择商品");
        }
        log.debug("value:{}",+value);
        log.debug("id:{}",+id);
        shoppingTrolleyService.UpdateShoppingTrolleyCountById(value,id);
        return R.ok("已变更");
    }

    @GetMapping("/pay")
    public String pay(String amount, String shopping, HttpServletResponse response,HttpServletRequest request) throws Exception {
//        List<ShoppingTrolley> shoppingTrolleys = shoppingTrolleyService.getJsonToList(shopping);
//        StringBuilder builder=new StringBuilder();
//        shoppingTrolleys.forEach(name->builder.append(name.getSpName()+","));
//        String subject = builder.substring(0,builder.length()-1).trim();
        AliPayService aliPayService=new AliPayService();
        String form = aliPayService.alipayTradePagePay(amount,"PLUTON商城", "商品购买",request);
        log.debug("商品:{}",amount);
        log.debug("金额:{}",amount);
        return form;
    }
}
