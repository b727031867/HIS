package com.gxf.his.po.vo;

import com.gxf.his.po.generate.Doctor;
import com.gxf.his.po.generate.DoctorTicket;
import com.gxf.his.po.generate.Patient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 龚秀峰
 * @version 1.0
 * @date 2020/2/10 17:31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class TicketVo extends DoctorTicket {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private DoctorVo doctorVo;

    private Patient patient;

    private Long uid;

    /**
     * 本次挂号排队名次
     */
    private Integer rank;



    /**
     * 模板类型
     */
    private Integer type;
}
