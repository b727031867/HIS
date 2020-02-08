package com.gxf.his.task;

import com.gxf.his.mapper.TicketResourceMapper;
import com.gxf.his.po.Order;
import com.gxf.his.po.TicketResource;
import com.gxf.his.service.DoctorService;
import com.gxf.his.service.OrderService;
import com.gxf.his.vo.DoctorUserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author GXF
 * 定时任务类
 * 用于补充票务资源 与
 */
@Component
public class Task {
    private static final Logger log = LoggerFactory.getLogger(Task.class);

    @Resource
    private TicketResourceMapper ticketResourceMapper;

    @Resource
    private DoctorService doctorService;

    @Resource
    private OrderService orderService;

    private static List<Date> dateList = new ArrayList<>(8);

    /**
     * 获取当前日期是星期几
     *
     * @param dt 当前日期
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 获取过去第几天的日期(- 操作) 或者 未来 第几天的日期( + 操作)
     * @param past 过去则输入负数 未来则输入正数 0表示当天
     * @return 过去或未来的日期
     */
    public static Date getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        log.info(result);
        return today;
    }

    /**
     * 通过传入星期几 获取未来七天中满足星期几的日期
     *
     * @param day 星期几 比如 1：表示星期一  7：表示星期日
     * @return 星期几对应的日期
     */
    public static Date getDateByDay(String day) {
        for(Date date : dateList){
            //比如医生是周三值班 则返回未来中周三最接近的日期
            if(getWeekOfDate(date).equals(day)){
                return date;
            }
        }
        log.warn("没有任何日期匹配当前星期几！"+day);
        return null;
    }

    /**
     * 自动放号功能，定时器设置周日零点自动执行 ，插入下一周的票务资源
     */
    @Async
    @Scheduled(cron = "0 0 0 * * 0")
    public void generateTicketResource() {
        //获取未来六天的日期，包括当天,加入到列表中
        // 投放未来nextDay天的票务资源
        int nextDay = 7;
        for(int i = 0; i< nextDay; i++){
            dateList.add(getPastDate(i));
        }
        List<DoctorUserVo> doctorUserVos = doctorService.getAllDoctors();
        for(DoctorUserVo doctorUserVo:doctorUserVos){
            String[] workdays = doctorUserVo.getScheduling().getSchedulingTime().split(",");
            // 为某位医生每个出诊日都插入票务资源
            for(String day : workdays){
                TicketResource ticketResource = new TicketResource();
                ticketResource.setDoctorId(doctorUserVo.getDoctorId());
                ticketResource.setDay(day);
                ticketResource.setAvailableDate(getDateByDay(day));
                ticketResource.setTicketLastNumber(doctorUserVo.getTicketDayNum());
                ticketResourceMapper.insert(ticketResource);
            }
        }
        log.info("执行了放号也业务，当前时间为:"+ LocalDateTime.now());
    }

    /**
     * 每分钟查询订单表中超时的订单，退回库存
     */
    @Async
    @Scheduled(cron = "0 0/1 * * * ?")
    public void checkExpireOrder(){
        orderService.getAndRemoveExpireOrders();
    }
}
