package cn.tuise233.service.impl;

import cn.tuise233.entity.Missions;
import cn.tuise233.entity.Users;
import cn.tuise233.enums.AppHttpCodeEnum;
import cn.tuise233.enums.MissionQueryType;
import cn.tuise233.enums.ResponseResult;
import cn.tuise233.mapper.MissionMapper;
import cn.tuise233.mapper.UserMapper;
import cn.tuise233.service.MissionService;
import cn.tuise233.service.UserService;
import cn.tuise233.util.BeanCopy;
import cn.tuise233.util.DateCompare;
import cn.tuise233.vo.MissionListVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MissionServiceImpl extends ServiceImpl<MissionMapper, Missions> implements MissionService {
    @Autowired
    private UserService userService;

    @Override
    public ResponseResult getListByOwnerId(String ownerId) {
        LambdaQueryWrapper<Users> userQuery = new LambdaQueryWrapper<>();
        userQuery.eq(Users::getUserId, ownerId);
        Users user = userService.getOne(userQuery);
        if(Objects.isNull(user)){
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_NOT_EXIST);
        }
        LambdaQueryWrapper<Missions> missionQuery = new LambdaQueryWrapper<>();
        missionQuery.eq(Missions::getOwnerId, ownerId);
        missionQuery.eq(Missions::getState, 0);
        List<Missions> missionList = list(missionQuery);
        List<MissionListVo> missionVos = BeanCopy.copyBeanList(missionList, MissionListVo.class);
        Collections.reverse(missionVos);
        return ResponseResult.okResult(missionVos);
    }

    @Override
    public ResponseResult getListByCreatorId(String creatorId, Integer type) {
        LambdaQueryWrapper<Missions> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Missions::getCreatorId, creatorId);
        MissionQueryType queryType = MissionQueryType.intToType(type);
        switch(queryType){
            case ONLY_UNFINISH:{
                queryWrapper.eq(Missions::getState, 0);
                break;
            }

            case ONLY_FINISHED: {
                queryWrapper.eq(Missions::getState, 1)
                        .or()
                        .eq(Missions::getType, 1);
                break;
            }

            case ONLY_FINISHED_DAY:{
                queryWrapper.eq(Missions::getState, 0);
                queryWrapper.eq(Missions::getType, 0);
                break;
            }

            case ONLY_FINISHED_MONTH:{
                queryWrapper.eq(Missions::getState, 0);
                queryWrapper.eq(Missions::getType, 1);
                break;
            }
        }
        List<Missions> missionList = list(queryWrapper);
        List<MissionListVo> missionVos = BeanCopy.copyBeanList(missionList, MissionListVo.class);
        Collections.reverse(missionVos);
        return ResponseResult.okResult(missionVos);
    }

    @Override
    public ResponseResult getMissionById(String missionId) {
        Missions mission = getById(missionId);
        if(Objects.isNull(mission)){
            return ResponseResult.errorResult(AppHttpCodeEnum.MISSION_NOT_EXIST);
        }
        return ResponseResult.okResult(mission);
    }

    @Override
    public ResponseResult finishMission(String userId, String missionId) {
        LambdaQueryWrapper<Users> userQuery = new LambdaQueryWrapper<>();
        userQuery.eq(Users::getUserId, userId);
        Users user = userService.getOne(userQuery);
        if(Objects.isNull(user)){
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_NOT_EXIST);
        }
        Missions mission = getById(missionId);
        if(!mission.getOwnerId().equals(userId)){
            return ResponseResult.errorResult(AppHttpCodeEnum.IS_NOT_MISSION_OWNER);
        }
        if(mission.getType() == 0){
            if(mission.getState() == 1){
                return ResponseResult.errorResult(AppHttpCodeEnum.IS_FINISH);
            }
            mission.setState(1);
            user.setScore(user.getScore() + mission.getScore());
            userService.updateById(user);
        } else {
            if(DateCompare.IsToday(mission.getFinishTime())){
                return ResponseResult.errorResult(AppHttpCodeEnum.IS_FINISH);
            }
            mission.setCombo(mission.getCombo() + 1);
        }
        mission.setFinishTime(new Date());
        updateById(mission);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateMissionCheck(String userId, String missionId, boolean isPass) {
        LambdaQueryWrapper<Missions> missionQuery = new LambdaQueryWrapper<>();
        missionQuery.eq(Missions::getId, missionId);
        Missions mission = getOne(missionQuery);
        if(Objects.isNull(mission)){
            return ResponseResult.errorResult(AppHttpCodeEnum.MISSION_NOT_EXIST);
        }
        if(!mission.getCreatorId().equals(userId)){
            return ResponseResult.errorResult(AppHttpCodeEnum.IS_NOT_MISSION_CREATOR);
        }
        if(mission.getCheckType() == 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.IS_NOT_CHECK_MISSION);
        }
        if(mission.getCheckState() == 1){
            return ResponseResult.errorResult(AppHttpCodeEnum.IS_CHECKED);
        }

        if(isPass) {
            mission.setCheckState(1);
            LambdaQueryWrapper<Users> userQuery = new LambdaQueryWrapper<>();
            userQuery.eq(Users::getUserId, mission.getOwnerId());
            Users user = userService.getOne(userQuery);
            if(Objects.isNull(user)) return ResponseResult.errorResult(AppHttpCodeEnum.MISSION_OWNER_NOT_EXIST);
            user.setScore(user.getScore() + mission.getScore());
            userService.updateById(user);
        } else {
            mission.setState(0);
        }
        updateById(mission);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult createMission(Missions mission){
        LambdaQueryWrapper<Users> userQuery = new LambdaQueryWrapper<>();
        userQuery.eq(Users::getUserId, mission.getCreatorId());
        Users user = userService.getOne(userQuery);
        if(Objects.isNull(user)) return ResponseResult.errorResult(AppHttpCodeEnum.USER_NOT_EXIST);
        mission.setOwnerId(user.getTargetId());
        mission.setCreateTime(new Date());
        save(mission);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult saveMission(Missions mission) {
        LambdaQueryWrapper<Missions> missionQuery = new LambdaQueryWrapper<>();
        missionQuery.eq(Missions::getId, mission.getId());
        Missions curMission = getOne(missionQuery);
        if(!curMission.getCreatorId().equals(mission.getCreatorId())) return ResponseResult.errorResult(AppHttpCodeEnum.IS_NOT_CORRECT_MISSION);
        curMission.setName(mission.getName());
        curMission.setDescription(mission.getDescription());
        curMission.setType(mission.getType());
        curMission.setCheckType(mission.getCheckType());
        curMission.setScore(mission.getScore());
        curMission.setImg(mission.getImg());
        updateById(mission);
        return ResponseResult.okResult(curMission.getId());
    }

    @Override
    public ResponseResult deleteMission(String userId, Integer missionId) {
        LambdaQueryWrapper<Users> userQuery = new LambdaQueryWrapper<>();
        userQuery.eq(Users::getUserId, userId);
        Users user = userService.getOne(userQuery);
        if(Objects.isNull(user)) return ResponseResult.errorResult(AppHttpCodeEnum.USER_NOT_EXIST);
        LambdaQueryWrapper<Missions> missionQuery = new LambdaQueryWrapper<>();
        missionQuery.eq(Missions::getId, missionId);
        Missions mission = getOne(missionQuery);
        if(Objects.isNull(mission)) return ResponseResult.errorResult(AppHttpCodeEnum.MISSION_NOT_EXIST);
        if(!mission.getCreatorId().equals(userId)) return ResponseResult.errorResult(AppHttpCodeEnum.IS_NOT_MISSION_CREATOR);
        remove(missionQuery);
        return ResponseResult.okResult();
    }
}
