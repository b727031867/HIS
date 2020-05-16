package com.gxf.his.uitls;


import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author 龚秀峰
 * @date 2020-1-15
 */
public class DataGeneratorUtil {

    private static int i = 0;

    /**
     * 需要传入一个前缀：6、8、9中的一个。
     * 其中：6：类型1，8：类型2，9：类型3 【根据自己的业务定义】
     * 其他则会返回异常
     *
     * @param prefix 银行卡卡号前缀
     * @return 银行卡号
     */
    public synchronized static String getBrankNumber(String prefix) {
        if (StringUtils.isNotBlank(prefix)) {
            String pre = "689";
            if (pre.contains(prefix) && prefix.length() == 1) {
                String st = "666" + prefix + getUnixTime();
                return st + getBankCardCheckCode(st);
            } else {
                System.out.println("银行卡号前缀有误");
            }
        } else {
            System.out.println("银行卡号去前缀不能是空");
        }
        return "";
    }

    /***
     * 获取当前系统时间戳 并截取
     * @return 被截取后的当前系统时间
     */
    private synchronized static String getUnixTime() {
        try {
            //线程同步执行，休眠10毫秒 防止卡号重复
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        i++;
        i = i > 100 ? i % 10 : i;
        return ((System.currentTimeMillis() / 100) + "").substring(1) + (i % 10);
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId 银行卡号
     * @return 是否合法
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        char flag = 'N';
        if (bit == flag) {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId 校验码
     * @return 校验位
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 18位身份证号码各位的含义:
     * 1-2位省、自治区、直辖市代码；
     * 3-4位地级市、盟、自治州代码；
     * 5-6位县、县级市、区代码；
     * 7-14位出生年月日，比如19670401代表1967年4月1日；
     * 15-17位为顺序号，其中17位（倒数第二位）男为单数，女为双数；
     * 18位为校验码，0-9和X。
     * 作为尾号的校验码，是由把前十七位数字带入统一的公式计算出来的，
     * 计算的结果是0-10，如果某人的尾号是0－9，都不会出现X，但如果尾号是10，那么就得用X来代替，
     * 因为如果用10做尾号，那么此人的身份证就变成了19位。X是罗马数字的10，用X来代替10
     *
     * @return 身份证号码
     */
    public static String getRandomId() {
        String id;
        // 随机生成省、自治区、直辖市代码 1-2
        String[] provinces = {"11", "12", "13", "14", "15", "21", "22", "23",
                "31", "32", "33", "34", "35", "36", "37", "41", "42", "43",
                "44", "45", "46", "50", "51", "52", "53", "54", "61", "62",
                "63", "64", "65", "71", "81", "82"};
        String province = provinces[new Random().nextInt(provinces.length - 1)];
        // 随机生成地级市、盟、自治州代码 3-4
        String[] cities = {"01", "02", "03", "04", "05", "06", "07", "08",
                "09", "10", "21", "22", "23", "24", "25", "26", "27", "28"};
        String city = cities[new Random().nextInt(cities.length - 1)];
        // 随机生成县、县级市、区代码 5-6
        String[] countries = {"01", "02", "03", "04", "05", "06", "07", "08",
                "09", "10", "21", "22", "23", "24", "25", "26", "27", "28",
                "29", "30", "31", "32", "33", "34", "35", "36", "37", "38"};
        String county = countries[new Random().nextInt(countries.length - 1)];
        // 随机生成出生年月 7-14
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE,
                date.get(Calendar.DATE) - new Random().nextInt(365 * 100));
        String birth = dft.format(date.getTime());
        // 随机生成顺序号 15-17
        String no = new Random().nextInt(999) + "";
        // 随机生成校验码 18
        String[] checks = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "X"};
        String check = checks[new Random().nextInt(checks.length - 1)];
        // 拼接身份证号码
        id = province + city + county + birth + no + check;
        return id;
    }

}
