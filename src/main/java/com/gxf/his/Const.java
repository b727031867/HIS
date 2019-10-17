package com.gxf.his;

import sun.security.provider.SHA;

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
}
