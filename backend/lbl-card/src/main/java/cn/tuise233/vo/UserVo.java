package cn.tuise233.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    private Integer id;
    private String userId;
    private String name;
    private String targetId;
    private Integer score;
    private Date registerTime;
}
