package cn.tuise233.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("lbl_inventory")
public class Inventory {
    //id
    @TableId
    private Integer id;
    //所有者id
    private String ownerId;
    //奖励id
    private Integer rewardId;
    //奖励名字
    private String name;
    //所需积分
    private Integer score;
    //使用状态
    private Integer state;
    //图片
    private String img;
    //获得时间
    private Date getTime;
}
