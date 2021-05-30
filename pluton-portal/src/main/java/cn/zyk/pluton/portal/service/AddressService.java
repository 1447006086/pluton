package cn.zyk.pluton.portal.service;

import cn.zyk.pluton.portal.model.Address;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface AddressService extends IService<Address> {
    List<Address> getAddress(String usernaem);
    void addAddress(Address address,String username);
}
