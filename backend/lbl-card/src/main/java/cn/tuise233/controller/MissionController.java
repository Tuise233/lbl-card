package cn.tuise233.controller;

import cn.tuise233.enums.MissionQueryType;
import cn.tuise233.enums.ResponseResult;
import cn.tuise233.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mission")
public class MissionController {
    @Autowired
    private MissionService missionService;

    @GetMapping("/getListByOwnerId")
    private ResponseResult getListByOwnerId(@RequestParam String ownerId){
        return missionService.getListByOwnerId(ownerId);
    }

    @GetMapping("/getListByCreatorId")
    private ResponseResult getListByCreatorId(@RequestParam String creatorId, @RequestParam Integer type){
        return missionService.getListByCreatorId(creatorId, type);
    }

    @GetMapping("/getMissionById")
    private ResponseResult getMissionById(@RequestParam String missionId){
        return missionService.getMissionById(missionId);
    }

    @GetMapping("/finishMission")
    private ResponseResult finishMission(@RequestParam String userId, @RequestParam String missionId){
        return missionService.finishMission(userId, missionId);
    }

    @GetMapping("/updateMissionCheck")
    private ResponseResult updateMissionCheck(@RequestParam String userId, @RequestParam String missionId, @RequestParam boolean isPass){
        return missionService.updateMissionCheck(userId, missionId, isPass);
    }
}
