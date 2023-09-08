package cn.tuise233.controller;

import cn.tuise233.enums.ResponseResult;
import cn.tuise233.service.WxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wx")
public class WxController {

    @GetMapping("/getOriginData")
    public ResponseResult getOriginData(@RequestParam String code){
        String openId = WxService.getOpenId(code);
        return ResponseResult.okResult(openId);
    }
}
