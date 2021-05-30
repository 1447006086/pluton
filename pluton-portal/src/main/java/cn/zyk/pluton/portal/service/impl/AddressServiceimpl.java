package cn.zyk.pluton.portal.service.impl;

import cn.zyk.pluton.portal.mapper.AddressMapper;
import cn.zyk.pluton.portal.mapper.UserMapper;
import cn.zyk.pluton.portal.model.Address;
import cn.zyk.pluton.portal.model.User;
import cn.zyk.pluton.portal.service.AddressService;
import cn.zyk.pluton.portal.service.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceimpl extends ServiceImpl<AddressMapper, Address> implements AddressService {
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Address> getAddress(String username) {
        User user = userMapper.findeUserByName(username);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", user.getId());
        List<Address> addresses = addressMapper.selectList(queryWrapper);
        return addresses;
    }

    @Override
    public void addAddress(Address address, String username) {
        User user= userMapper.findeUserByName(username);
        address.setUserId(user.getId());
        int insert = addressMapper.insert(address);
        if (insert!=1){
            throw ServiceException.busy();
        }
    }
}
