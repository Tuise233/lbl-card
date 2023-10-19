package cn.tuise233.controller;

import cn.tuise233.entity.Rewards;
import cn.tuise233.enums.ResponseResult;
import cn.tuise233.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/createReward")
    private ResponseResult createReward(@RequestBody Rewards reward) {
        return rewardService.createReward(reward);
    }

    @PostMapping("/saveReward")
    private ResponseResult saveReward(@RequestBody Rewards reward) {
        return rewardService.saveReward(reward);
    }

    @GetMapping("/deleteReward")
    private ResponseResult deleteReward(@RequestParam String creatorId, @RequestParam Integer rewardId) {
        return rewardService.deleteReward(creatorId, rewardId);
    }

    @GetMapping("/getRewardById")
    private ResponseResult getRewardById(@RequestParam Integer rewardId) {
        return rewardService.getRewardById(rewardId);
    }
}
