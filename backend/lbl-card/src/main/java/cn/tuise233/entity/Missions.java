package cn.tuise233.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * (Missions)表实体类
 *
 * @author makejava
 * @since 2023-08-31 22:47:51
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("lbl_missions")
public class Missions {
    //任务id
    @TableId
    private Integer id;
    //拥有者id
    private String ownerId;
    //创建者id
    private String creatorId;
    //名称
    private String name;
    //简介
    private String description;
    //图片
    private String img;
    //积分
    private Integer score;
    //类型: 0(每日) 1(长期)
    private Integer type;
    //审批类型: 0(无需审批) 1(需要审批)
    private Integer checkType;
    //审批状态: 0(未审批) 1(已审批)
    private Integer checkState;
    //状态: 0(未完成) 1(已完成)
    private Integer state;
    //累计打卡天数
    private Integer combo;
    //创建时间
    private Date createTime;
    //完成时间
    private Date finishTime;
}
