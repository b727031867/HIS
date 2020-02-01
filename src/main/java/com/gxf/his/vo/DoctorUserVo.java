package com.gxf.his.vo;

import com.gxf.his.po.Department;
import com.gxf.his.po.Scheduling;
import com.gxf.his.po.User;
import lombok.Data;

import java.util.List;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
@Data
public class DoctorUserVo {
    private Long doctorId;

    private String employeeId;

    private String doctorName;

    private String doctorProfessionalTitle;

    private String doctorIntroduction;

    private Department department;

    private Scheduling scheduling;

    private Integer ticketDayNum;

    private User user;

    private List<Department> departments;
}
