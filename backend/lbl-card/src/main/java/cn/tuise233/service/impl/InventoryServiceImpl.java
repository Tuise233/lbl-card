package cn.tuise233.service.impl;

import cn.tuise233.entity.Inventory;
import cn.tuise233.entity.Users;
import cn.tuise233.enums.AppHttpCodeEnum;
import cn.tuise233.enums.ResponseResult;
import cn.tuise233.mapper.InventoryMapper;
import cn.tuise233.mapper.UserMapper;
import cn.tuise233.service.InventoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import kotlin.jvm.internal.Lambda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    InventoryMapper inventoryMapper;

    @Override
    public ResponseResult getUserInventoryItem(String userId) {
        LambdaQueryWrapper<Users> userQuery = new LambdaQueryWrapper<>();
        userQuery.eq(Users::getUserId, userId);
        Users user = userMapper.selectOne(userQuery);
        if(Objects.isNull(user)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_NOT_EXIST);
        }
        LambdaQueryWrapper<Inventory> inventoryQuery = new LambdaQueryWrapper<>();
        inventoryQuery.eq(Inventory::getOwnerId, userId);
        List<Inventory> inventoryList = list(inventoryQuery);
        Collections.reverse(inventoryList);
        return ResponseResult.okResult(inventoryList);
    }

    @Override
    public ResponseResult useInventoryItem(String userId, Integer itemId) {
        LambdaQueryWrapper<Users> userQuery = new LambdaQueryWrapper<>();
        userQuery.eq(Users::getUserId, userId);
        Users user = userMapper.selectOne(userQuery);
        if(Objects.isNull(user)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_NOT_EXIST);
        }
        LambdaQueryWrapper<Inventory> inventoryQuery = new LambdaQueryWrapper<>();
        inventoryQuery.eq(Inventory::getId, itemId);
        Inventory item = getOne(inventoryQuery);
        if(Objects.isNull(item)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ITEM_NOT_EXIST);
        }
        if(item.getState().equals(1)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ITEM_ALREADY_USED);
        }
        item.setState(1);
        inventoryMapper.update(item, inventoryQuery);
        return ResponseResult.okResult();
    }
}
