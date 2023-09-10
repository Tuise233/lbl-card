package cn.tuise233.controller;

import cn.tuise233.entity.Missions;
import cn.tuise233.enums.MissionQueryType;
import cn.tuise233.enums.ResponseResult;
import cn.tuise233.service.MissionService;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/createMission")
    private ResponseResult createMission(@RequestBody Missions mission){
        return missionService.createMission(mission);
    }

    @PostMapping("/saveMission")
    private ResponseResult saveMissionPost(@RequestBody Missions mission){
        return missionService.saveMission(mission);
    }

    @GetMapping("/deleteMission")
    private ResponseResult deleteMission(@RequestParam String userId, @RequestParam Integer missionId){
        return missionService.deleteMission(userId, missionId);
    }
}
