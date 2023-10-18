package cn.tuise233.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
     PHONENUMBER_EXIST(502,"手机号已存在"), EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"),

    USER_NOT_EXIST(600, "用户不存在"),
    ERROR_TARGET(601, "错误的绑定对象"),
    MISSION_NOT_EXIST(602, "打卡不存在"),
    IS_NOT_MISSION_OWNER(603, "不是打卡的所有者"),
    IS_FINISH(604, "已经打过卡"),
    IS_NOT_MISSION_CREATOR(605, "不是打卡的创建者"),
    IS_NOT_CHECK_MISSION(606, "不是可审核的打卡"),
    IS_CHECKED(607, "这个打卡已经审核过了"),
    IS_NOT_CORRECT_MISSION(608, "打卡任务数据校验错误"),
    MISSION_OWNER_NOT_EXIST(609, "打卡任务的所有人不存在"),
    REWARD_NOT_EXIST(610, "打卡奖励不存在"),
    REWARD_NOT_ENOUGH(611, "打卡奖励没有足够的库存了"),
    REWARD_CAN_NOT_AFFORD(612, "没有足够的积分可以兑换奖励"),
    IS_NOT_REWARD_OWNER(613, "不是该奖励的所有者"),
    ITEM_NOT_EXIST(614, "奖励不存在"),
    ITEM_ALREADY_USED(615, "奖励已使用"),
    ;


    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}