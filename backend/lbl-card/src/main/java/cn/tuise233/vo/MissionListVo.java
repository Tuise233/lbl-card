package cn.tuise233.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MissionListVo {
    //任务id
    private Integer id;
    //创建者id
    private String ownerId;
    //所有者id
    private String targetId;
    //名称
    private String name;
    //图片
    private String img;
    //积分
    private Integer score;
    //类型: 0(每日) 1(长期)
    private Integer type;
    //状态: 0(未完成) 1(已完成)
    private Integer state;
    //累计打卡天数
    private Integer combo;
    //完成时间
    private Date finishTime;

    private Integer checkType;
    private Integer checkState;
}
