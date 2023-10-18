package cn.tuise233.controller;

import cn.tuise233.enums.ResponseResult;
import cn.tuise233.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/getUserInventoryItem")
    private ResponseResult getUserInventoryItem(@RequestParam String userId) {
        return inventoryService.getUserInventoryItem(userId);
    }

    @GetMapping("/useInventoryItem")
    private ResponseResult useInventoryItem(@RequestParam String userId, @RequestParam Integer itemId) {
        return inventoryService.useInventoryItem(userId, itemId);
    }
}
