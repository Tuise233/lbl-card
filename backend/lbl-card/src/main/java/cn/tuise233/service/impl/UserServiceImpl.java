package cn.tuise233.service.impl;

import cn.tuise233.entity.Users;
import cn.tuise233.enums.AppHttpCodeEnum;
import cn.tuise233.enums.ResponseResult;
import cn.tuise233.mapper.UserMapper;
import cn.tuise233.service.UserService;
import cn.tuise233.util.BeanCopy;
import cn.tuise233.util.Randoms;
import cn.tuise233.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import kotlin.jvm.internal.Lambda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, Users> implements UserService {

    @Override
    public ResponseResult checkExist(String open_id) {
        try{
            System.out.println(open_id);
            LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Users::getOpenId, open_id);
            List<Users> resultUsers = list(queryWrapper);
            Map result = new HashMap();
            result.put("exist", !resultUsers.isEmpty());
            return ResponseResult.okResult(result);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseResult getUserInfo(String open_id) {
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getOpenId, open_id);
        Users user = getOne(queryWrapper);
        UserVo userVo = BeanCopy.copyBean(user, UserVo.class);
        return ResponseResult.okResult(userVo);
    }

    @Override
    public ResponseResult createUser(String open_id, String name) {
        Users user = new Users();
        String userId = Randoms.generalRandomString(10);
        user.setUserId(userId);
        user.setOpenId(open_id);
        user.setName(name);
        user.setRegisterTime(new Date());
        save(user);
        UserVo userVo = BeanCopy.copyBean(user, UserVo.class);
        return ResponseResult.okResult(userVo);
    }

    @Override
    public ResponseResult getuserInfoById(String userId) {
        LambdaQueryWrapper<Users> userQuery = new LambdaQueryWrapper<>();
        userQuery.eq(Users::getUserId, userId);
        Users user = getOne(userQuery);
        if(Objects.isNull(user)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.USER_NOT_EXIST);
        }
        UserVo userVo = BeanCopy.copyBean(user, UserVo.class);
        return ResponseResult.okResult(userVo);
    }
}
