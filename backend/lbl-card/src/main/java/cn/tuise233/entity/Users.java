package cn.tuise233.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * (Users)表实体类
 *
 * @author makejava
 * @since 2023-09-02 01:36:54
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("lbl_users")
public class Users {
    //id
    @TableId
    private Integer id;
    //用户id
    private String userId;
    //openId
    private String openId;
    //用户名
    private String name;
    //绑定对方用户id
    private String targetId;
    //注册时间
    private Date registerTime;
}
