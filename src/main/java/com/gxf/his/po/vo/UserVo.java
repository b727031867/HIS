package com.gxf.his.po.vo;

import com.gxf.his.po.generate.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/17 02:31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class UserVo extends User {
    private String oldPassword;
    private String newPassword;
    private String renewPassword;
}
