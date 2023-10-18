package cn.tuise233.service;

import cn.tuise233.entity.Inventory;
import cn.tuise233.enums.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

public interface InventoryService extends IService<Inventory> {
    ResponseResult getUserInventoryItem(String userId);

    ResponseResult useInventoryItem(String userId, Integer itemId);
}
