package cn.tuise233.service.impl;

import cn.tuise233.entity.Inventory;
import cn.tuise233.mapper.InventoryMapper;
import cn.tuise233.service.InventoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {
}
