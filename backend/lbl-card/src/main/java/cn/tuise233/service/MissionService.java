package cn.tuise233.service;

import cn.tuise233.entity.Missions;
import cn.tuise233.enums.MissionQueryType;
import cn.tuise233.enums.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

public interface MissionService extends IService<Missions>  {
    ResponseResult getListByOwnerId(String ownerId);

    ResponseResult getListByCreatorId(String creatorId, Integer type);

    ResponseResult getMissionById(String missionId);

    ResponseResult finishMission(String userId, String missionId);

    ResponseResult updateMissionCheck(String userId, String missionId, boolean isPass);

    ResponseResult createMission(Missions mission);

    ResponseResult saveMission(Missions mission);

    ResponseResult deleteMission(String userId, Integer missionId);


}
