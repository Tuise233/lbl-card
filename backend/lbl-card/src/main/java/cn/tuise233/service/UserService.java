package cn.tuise233.service;

import cn.tuise233.entity.Users;
import cn.tuise233.enums.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<Users> {
    ResponseResult checkExist(String open_id);

    ResponseResult getUserInfo(String open_id);

    ResponseResult createUser(String open_id, String name);
}
