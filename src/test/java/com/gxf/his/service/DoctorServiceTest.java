package com.gxf.his.service;

import com.gxf.his.controller.UserController;
import com.gxf.his.enmu.UserTypeEnum;
import com.gxf.his.po.generate.Department;
import com.gxf.his.po.generate.Doctor;
import com.gxf.his.po.generate.DoctorScheduling;
import com.gxf.his.po.generate.User;
import com.gxf.his.uitls.CombinationUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DoctorServiceTest {
    @Autowired
    DoctorService doctorService;
    @Autowired
    UserService userService;
    @Autowired
    SchedulingService schedulingService;
    @Autowired
    DepartmentService departmentService;

    String[] professionalTitles = {"主治医师", "副主任医师", "主任医师", "医师"};
    String[] employeeIdPrefix = {"ZY-00", "JZ-00", "ZC-00", "MZ-00"};
    // 出诊时间段 0：上午8~12 1：下午14:30~18:30 2: 晚上19:00~2 3:白天全天 4:急诊24小时
    static String[] workTimes = {"0", "1", "2", "3", "4"};
    static String[] workDates = {"1", "2", "3", "4", "5", "6", "7"};

    /**
     * 希望生成多少位医生
     */
    static int generateDoctorNumber = 200;

    /**
     * 每个医生一天默认的挂号数
     */
    static int ticketNumber = 60;

    /**
     * 每层有多少房间
     */
    static int roomNumber = 15;
    static String[] floors = {"1", "2", "3", "4", "5", "6"};
    static String[] buildings = {"A", "B", "C"};
    static String[] ticketPrice = {"17", "25", "30", "200"};
    /**
     * 排班可选列表
     */
    static ArrayList<DoctorScheduling> schedulingList = new ArrayList<>(generateDoctorNumber);

    /**
     * 可能出现的工作日列表
     */
    static ArrayList<ArrayList<String>> dates = new ArrayList<>(8);

    /**
     * 出诊房间
     */
    static ArrayList<String> rooms = new ArrayList<>(160);

    // 初始化工作日情况列表
    private static void init() {
        //上班周一到周日为1~7
        ArrayList<String> is = new ArrayList<>(Arrays.asList(workDates));
        //一周上班两天的所有可能情况
        ArrayList<String> twoWorkDates = CombinationUtil.combine(is, "", 2);
        ArrayList<String> threeWorkDates = CombinationUtil.combine(is, "", 3);
        ArrayList<String> fourWorkDates = CombinationUtil.combine(is, "", 4);
        ArrayList<String> fiveWorkDates = CombinationUtil.combine(is, "", 5);
        ArrayList<String> sixWorkDates = CombinationUtil.combine(is, "", 6);
        //随机获取一种排班，加入排班列表中，如果排班列表
        dates.add(twoWorkDates);
        dates.add(threeWorkDates);
        dates.add(fourWorkDates);
        dates.add(fiveWorkDates);
        dates.add(sixWorkDates);
        //设置所有房间，分楼层设置
        for (String building : buildings) {
            for (String floor : floors) {
                for (int i = 1; i <= roomNumber; i++) {
                    //小于10则补0,比如 09 08
                    String room;
                    if (i % 10 == i) {
                        room = building + "-" + floor + "0" + i;
                    } else {
                        room = building + "-" + floor + i;
                    }
                    rooms.add(room);
                }
            }
        }
        //初始化排班列表
        setSchedulingList();
    }


    @Test
    public void testAddDoctor() {
        init();
        Random random = new Random();
        List<Department> departments = departmentService.getAllChildrenDepartments();
        for (int i = 0; i < generateDoctorNumber; i++) {
            Department department = departments.get(random.nextInt(departments.size() - 1));
            Doctor doctor = new Doctor();
            String userName = "test" + i;
            String password = "test";
            User user = UserController.doHashedCredentials(userName, password, UserTypeEnum.DOCTOR);
            userService.addUser(user);
            doctor.setDepartmentCode(department.getDepartmentCode());
            doctor.setDoctorIntroduction(department.getDepartmentIntroduction());
            doctor.setDoctorName(getName());
            doctor.setDoctorProfessionalTitle(getRandomString(professionalTitles));
            doctor.setEmployeeId(getRandomString(employeeIdPrefix) + i);
            doctor.setTicketDayNum(ticketNumber);
            doctor.setTicketPrice(new BigDecimal(getRandomString(ticketPrice)));
            doctor.setTicketCurrentNum(random.nextInt(61));
            doctor.setUserId(user.getUserId());
            DoctorScheduling scheduling = getScheduling();
            schedulingService.addScheduling(scheduling);
            doctor.setSchedulingId(scheduling.getSchedulingId());
            doctorService.addDoctor(doctor);
        }

    }

    /**
     * 从排班列表中获取不冲突排班列表
     *
     * @return 返回一个排班
     */
    private static DoctorScheduling getScheduling() {
        Random random = new Random();
        if (schedulingList.size() - 1 == 0) {
            return schedulingList.get(0);
        }
        int index = random.nextInt(schedulingList.size() - 1);
        DoctorScheduling scheduling = schedulingList.get(index);
        //每个医生都有不同的排班，不可能出现同时在相同地方上班
        schedulingList.remove(index);
        return scheduling;
    }

    /**
     * 初始化不重复的排班
     */
    private static void setSchedulingList() {
        for (; schedulingList.size() < generateDoctorNumber; ) {
            DoctorScheduling scheduling = new DoctorScheduling();
            String workDates = getWorkDays();
            String workRoom = getWorkRoom();
            //是否重复
            boolean flag = false;
            //检查已经保存的Scheduling，防止出现相同的排班情况，即上班周期重叠并且房间号完全相同
            for (DoctorScheduling schedulingItem : schedulingList) {
                if (schedulingItem.getSchedulingRoom().equals(workRoom) && schedulingItem.getSchedulingTime().contains(workDates)) {
                    flag = true;
                    break;
                }
            }
            scheduling.setSchedulingType(getRandomString(workTimes));
            scheduling.setSchedulingTime(workDates);
            scheduling.setSchedulingRoom(workRoom);
            if (!flag) {
                schedulingList.add(scheduling);
            }
        }
    }

    /**
     * 随机获取一个房间
     *
     * @return room 房间号 A-101 A栋一楼一号房间 A-211 A栋二楼十一号房间
     */
    private static String getWorkRoom() {
        Random random = new Random();
        return rooms.get(random.nextInt(rooms.size() - 1));
    }

    /**
     * 获取一周随机上几天班的某一种情况，比如 1，3，5 说明总共上三天班，并且周一、周三、周五上班。
     *
     * @return 上班情况 比如1，3，5
     */
    private static String getWorkDays() {
        Random random = new Random();
        //获取一周随机上几天班
        ArrayList<String> strings = dates.get(random.nextInt(dates.size() - 1));
        //返回上几天班的某种情况
        return strings.get(random.nextInt(strings.size() - 1));
    }

    private static String getRandomString(String[] randomValue) {
        Random random = new Random();
        int index = random.nextInt(randomValue.length);
        return randomValue[index];
    }

    private String getName() {
        Random random = new Random();
        String[] Surname = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "褚", "卫", "蒋", "沈", "韩", "杨", "朱", "秦", "尤", "许",
                "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜", "戚", "谢", "邹", "喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎",
                "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳", "酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷",
                "罗", "毕", "郝", "邬", "安", "常", "乐", "于", "时", "傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟", "平", "黄", "和",
                "穆", "萧", "尹", "姚", "邵", "湛", "汪", "祁", "毛", "禹", "狄", "米", "贝", "明", "臧", "计", "伏", "成", "戴", "谈", "宋", "茅", "庞", "熊", "纪", "舒",
                "屈", "项", "祝", "董", "梁", "杜", "阮", "蓝", "闵", "席", "季"};
        String girl = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽 ";
        String boy = "伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";
        int index = random.nextInt(Surname.length - 1);
        String name = Surname[index]; //获得一个随机的姓氏
        int i = random.nextInt(3);//可以根据这个数设置产生的男女比例
        int j = random.nextInt(girl.length() - 2);
        if (i == 2) {
            if (j % 2 == 0) {
//                name = "女-" + name + girl.substring(j, j + 2);
                name = name + girl.substring(j, j + 2);
            } else {
//                name = "女-" + name + girl.substring(j, j + 1);
                name = name + girl.substring(j, j + 1);
            }

        } else {
            if (j % 2 == 0) {
//                name = "男-" + name + boy.substring(j, j + 2);
                name = name + boy.substring(j, j + 2);
            } else {
//                name = "男-" + name + boy.substring(j, j + 1);
                name = name + boy.substring(j, j + 1);
            }

        }

        return name;
    }
}
