package com.gxf.his.service;

import com.gxf.his.controller.UserController;
import com.gxf.his.po.Doctor;
import com.gxf.his.po.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    @Test
    public void testAddDoctor(){
        String[] professionalTitles = {"主治医师","副主任医师","主任医师","医师"};
        String[] departmentCodes = {"0001","0002","0003","0004","0005","0006","0007","0008","0009","0010","0011","0012","0013","0014"};
        String[] employeeIdPrefix = {"ZY-00","JZ-00","ZC-00","MZ-00"};
        for(int i=0;i<300;i++){
            Doctor doctor =new Doctor();
            String userName = "test"+i;
            String password = "test";
            String introduce = "这是一些测试医生介绍内容！这是一些测试医生介绍内容！这是一些测试医生介绍内容！";
            User user = UserController.doHashedCredentials(userName,password);
            Long userId = userService.addUser(user);
            doctor.setDepartmentCode(getRandomString(departmentCodes));
            doctor.setDoctorIntroduction(introduce);
            doctor.setDoctorName(getName());
            doctor.setDoctorProfessionalTitle(getRandomString(professionalTitles));
            doctor.setEmployeeId(getRandomString(employeeIdPrefix)+i);
            doctor.setUserId(userId);
            doctorService.addDoctor(doctor);
        }

    }


    private String getRandomString(String[] randomValue){
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
        if(i==2){
            int j = random.nextInt(girl.length()-2);
            if (j % 2 == 0) {
//                name = "女-" + name + girl.substring(j, j + 2);
                name =  name + girl.substring(j, j + 2);
            } else {
//                name = "女-" + name + girl.substring(j, j + 1);
                name = name + girl.substring(j, j + 1);
            }

        }
        else{
            int j = random.nextInt(girl.length()-2);
            if (j % 2 == 0) {
//                name = "男-" + name + boy.substring(j, j + 2);
                name =  name + boy.substring(j, j + 2);
            } else {
//                name = "男-" + name + boy.substring(j, j + 1);
                name =  name + boy.substring(j, j + 1);
            }

        }

        return name;
    }
}
