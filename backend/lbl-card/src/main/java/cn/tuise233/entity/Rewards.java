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
@TableName("lbl_rewards")
public class Rewards {
    //奖励id
    @TableId
    private Integer id;
    //所有者id
    private String ownerId;
    //创建者id
    private String creatorId;
    //奖励名称
    private String name;
    //所需分数
    private Integer score;
    //库存数量
    private Integer amount;
    //图片
    private String img;
    //创建时间
    private Date createTime;
}
