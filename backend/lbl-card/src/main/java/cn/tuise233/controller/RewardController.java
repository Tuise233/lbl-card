package cn.tuise233.controller;

import cn.tuise233.enums.ResponseResult;
import cn.tuise233.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reward")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @GetMapping("/getRewardByOwnerId")
    private ResponseResult getRewardByOwnerId(@RequestParam String ownerId) {
        return rewardService.getRewardByOwnerId(ownerId);
    }

    @GetMapping("/buyReward")
    private ResponseResult buyReward(@RequestParam String userId, @RequestParam Integer rewardId) {
        return rewardService.buyReward(userId, rewardId);
    }
}
