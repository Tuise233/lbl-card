package cn.tuise233.service.impl;

import cn.tuise233.entity.Inventory;
import cn.tuise233.entity.Rewards;
import cn.tuise233.entity.Users;
import cn.tuise233.enums.AppHttpCodeEnum;
import cn.tuise233.enums.ResponseResult;
import cn.tuise233.mapper.RewardMapper;
import cn.tuise233.mapper.UserMapper;
import cn.tuise233.service.InventoryService;
import cn.tuise233.service.RewardService;
import cn.tuise233.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import kotlin.jvm.internal.Lambda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class RewardServiceImpl extends ServiceImpl<RewardMapper, Rewards> implements RewardService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RewardMapper rewardMapper;
    @Autowired
    private InventoryService inventoryService;

    @Override
    public ResponseResult getRewardByOwnerId(String ownerId) {
        LambdaQueryWrapper<Users> userQuery = new LambdaQueryWrapper<>();
        userQuery.eq(Users::getUserId, ownerId);
        Users user = userService.getOne(userQuery);
        if(Objects.isNull(user)) return ResponseResult.errorResult(AppHttpCodeEnum.USER_NOT_EXIST);
        LambdaQueryWrapper<Rewards> rewardQuery = new LambdaQueryWrapper<>();
        rewardQuery.eq(Rewards::getOwnerId, ownerId);
        List<Rewards> rewards = list(rewardQuery);
        Collections.reverse(rewards);
        return ResponseResult.okResult(rewards);
    }

    @Override
    public ResponseResult buyReward(String userId, Integer rewardId) {
        LambdaQueryWrapper<Users> userQuery = new LambdaQueryWrapper<>();
        userQuery.eq(Users::getUserId, userId);
        Users user = userService.getOne(userQuery);
        if(Objects.isNull(user)) return ResponseResult.errorResult(AppHttpCodeEnum.USER_NOT_EXIST);
        LambdaQueryWrapper<Rewards> rewardQuery = new LambdaQueryWrapper<>();
        rewardQuery.eq(Rewards::getId, rewardId);
        Rewards reward = getOne(rewardQuery);
        if(Objects.isNull(reward)) return ResponseResult.errorResult(AppHttpCodeEnum.REWARD_NOT_EXIST);
        if(!reward.getOwnerId().equals(userId)) return ResponseResult.errorResult(AppHttpCodeEnum.IS_NOT_REWARD_OWNER);
        if(reward.getScore() > user.getScore()) return ResponseResult.errorResult(AppHttpCodeEnum.REWARD_CAN_NOT_AFFORD);
        if(reward.getAmount() <= 0) return ResponseResult.errorResult(AppHttpCodeEnum.REWARD_NOT_ENOUGH);
        user.setScore(user.getScore() - reward.getScore());
        reward.setAmount(reward.getAmount() - 1);
        userMapper.update(user, userQuery);
        rewardMapper.update(reward, rewardQuery);
        Inventory inventory = new Inventory();
        inventory.setOwnerId(userId);
        inventory.setRewardId(rewardId);
        inventory.setName(reward.getName());
        inventory.setScore(reward.getScore());
        inventory.setState(0);
        inventory.setImg(reward.getImg());
        inventory.setGetTime(new Date());
        inventoryService.save(inventory);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult createReward(Rewards reward) {
        LambdaQueryWrapper<Users> userQuery = new LambdaQueryWrapper<>();
        userQuery.eq(Users::getUserId, reward.getCreatorId());
        Users user = userService.getOne(userQuery);
        if(Objects.isNull(user)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_NOT_EXIST);
        }
        reward.setOwnerId(user.getTargetId());
        reward.setCreateTime(new Date());
        save(reward);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult saveReward(Rewards reward) {
        LambdaQueryWrapper<Rewards> rewardQuery = new LambdaQueryWrapper<>();
        rewardQuery.eq(Rewards::getId, reward.getId());
        Rewards curReward = getOne(rewardQuery);
        if(Objects.isNull(curReward)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.REWARD_NOT_EXIST);
        }
        curReward.setName(reward.getName());
        curReward.setScore(reward.getScore());
        curReward.setAmount(reward.getAmount());
        curReward.setImg(reward.getImg());
        updateById(reward);
        return ResponseResult.okResult(curReward.getId());
    }

    @Override
    public ResponseResult deleteReward(String creatorId, Integer rewardId) {
        LambdaQueryWrapper<Users> userQuery = new LambdaQueryWrapper<>();
        userQuery.eq(Users::getUserId, creatorId);
        Users user = userService.getOne(userQuery);
        if(Objects.isNull(user)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_NOT_EXIST);
        }
        LambdaQueryWrapper<Rewards> rewardQuery = new LambdaQueryWrapper<>();
        rewardQuery.eq(Rewards::getId, rewardId);
        Rewards curReward = getOne(rewardQuery);
        if(Objects.isNull(curReward)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.REWARD_NOT_EXIST);
        }
        if(!curReward.getCreatorId().equals(user.getId())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.IS_NOT_REWARD_OWNER);
        }
        remove(rewardQuery);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRewardById(Integer rewardId) {
        Rewards reward = getById(rewardId);
        if(Objects.isNull(reward)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.REWARD_NOT_EXIST);
        }
        return ResponseResult.okResult(reward);
    }
}
