package cn.zyk.pluton.portal.controller;

import cn.zyk.pluton.portal.model.Address;
import cn.zyk.pluton.portal.service.AddressService;
import cn.zyk.pluton.portal.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/address")
@Slf4j
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("")
    public R getaddresses(@AuthenticationPrincipal UserDetails user) {
        List<Address> address = addressService.getAddress(user.getUsername());
        return R.ok(address);
    }

    @PostMapping("/add")
    public R add(Address address,@AuthenticationPrincipal UserDetails user){
        if (address==null){
            return R.notFound("请填写地址");
        }
        addressService.addAddress(address,user.getUsername());
        log.debug("address:{}",address);
        return R.ok("地址已添加");
    }
}
