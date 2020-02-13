package com.gxf.his.service;

import com.gxf.his.po.generate.DoctorTicketResource;

import java.util.Date;
import java.util.List;

/**
 * @author GXF
 * 挂号资源
 */
public interface TicketResourceService {
    /**
     * 添加一批票务资源 用于在新的一天 分别对每位医生补充最新的票资源
     *
     * @param ticketResource 票务资源
     */
    void addTicketResource(DoctorTicketResource ticketResource);

    /**
     * 获取当前已放票的票务资源
     *
     * @param doctorId           医生ID
     * @param availableDateStart 投放票务资源的日期
     * @param availableDateEnd   投放票务资源的过期日期
     * @return 票务资源
     */
    List<DoctorTicketResource> getTicketResourceByDoctorIdAndAvailableDate(Long doctorId, Date availableDateStart, Date availableDateEnd);

    /**
     * 根据ID获取票务资源
     *
     * @param ticketResourceId 票务资源的ID
     * @return 票务资源
     */
    DoctorTicketResource getTicketResourceById(Long ticketResourceId);

    /**
     * 获取当前已放票的票务资源过期日期 比如1月1号 00：00 放票 则 1月8日 00：00 是票务资源的过期日期
     *
     * @return 票务资源过期日期
     */
    Date getTicketResourceMaxDate();

    /**
     * 获取当前放票日期
     *
     * @return 票务资源放票的日期
     */
    Date getTicketResourceMinDate();

    /**
     * 更新票务资源信息，一般为减少余票
     *
     * @param ticketResource 票务资源
     * @return 返回影响的行数
     */
    int updateTicketResource(DoctorTicketResource ticketResource);

    /**
     * 删除某天某医生一批票务资源
     *
     * @param availableDate 票务资源的日期
     * @param doctorId      医生的ID
     * @return 返回影响的行数
     */
    int deleteTicketResource(Date availableDate, Long doctorId);
}
