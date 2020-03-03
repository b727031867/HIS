package com.gxf.his.po.vo;

import com.gxf.his.po.generate.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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

    /**
     * 挂号信息一对一关联的医生
     */
    private DoctorVo doctorVo;

    /**
     * 挂号信息一对一关联的病人
     */
    private Patient patient;

    /**
     * 挂号信息一对一关联的处方单
     */
    private Prescription prescription;

    /**
     * 挂号信息一对一关联的电子病历的记录
     */
    private PatientMedicalRecord patientMedicalRecord;

    /**
     * 挂号信息一对多关联的检查项目
     */
    private List<CheckItemInfo> checkItemInfos;

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
