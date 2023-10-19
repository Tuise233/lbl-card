package cn.tuise233.service;

import cn.tuise233.entity.Rewards;
import cn.tuise233.enums.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

public interface RewardService extends IService<Rewards> {
    ResponseResult getRewardByOwnerId(String ownerId);

    ResponseResult buyReward(String userId, Integer rewardId);

    ResponseResult createReward(Rewards reward);

    ResponseResult saveReward(Rewards reward);

    ResponseResult deleteReward(String creatorId, Integer rewardId);

    ResponseResult getRewardById(Integer rewardId);
}
