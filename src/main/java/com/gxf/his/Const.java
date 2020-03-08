package com.gxf.his;


/**
 * @author 龚秀峰
 * @date 2019-10-13
 */
public class Const {
   // ****************登录加密相关参数的开始****************
   /**
    * 加密方式选择：MD2、MD5、SHA-1、SHA-256、SHA-384、SHA-512
    */
   public final static String ENCRYPTION = "SHA-256";

   /**
    * 加密次数
    */
   public final static int ENCRYPTION_NUM = 3;

   /**
    * 盐和密文的编码方式 ： true【Hex编码】 or false【Base64编码】
    */
   public final static boolean DECIMAL = true;

   // ****************登录加密相关参数的结束****************

   /**
    * description: Shiro缓存前缀
    */
   public static final String REDIS_CONSTANT_SHIRO_CACHE_PREFIX = "shiro:cache:";

   /**
    * description: 用于认证的Token的前缀
    */
   public static final String REDIS_CONSTANT_ACCESS_TOKEN_PREFIX = "shiro:access_token:";

   /**
    * description: 用于刷新的Token的前缀
    */
   public static final String REDIS_CONSTANT_REFRESH_TOKEN_PREFIX = "shiro:refresh_token:";

   /**
    * 设置600秒后Shiro缓存过期
    */
   public static final String SHIROCACHEEXPIRETIME = "600";

   /**
    * 本项目的应用ID
    */
   public static final Long APP_ID = 1L;

   // ****************HIS系统相关参数的开始****************

   /**
    * 订单未付款的过期时间 单位：分钟
    */
   public static final long ORDER_EXPIRED_TIME = 10;

   /**
    * 处方订单未付款的过期时间 单位：分钟
    */
   public static final long PRESCRIPTION_ORDER_EXPIRED_TIME = 60*24;

   /**
    * 订单类型：挂号单
    */
   public static final Integer GH = 0;

   /**
    * 订单类型：处方单
    */
   public static final Integer CF = 1;

   /**
    * 订单类型：检查单
    */
   public static final Integer JC = 2;

   // ****************HIS系统相关参数的结束****************

}
