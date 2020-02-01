package com.gxf.his.uitls;

import java.util.ArrayList;

/**
 * 组合工具类，用于生成字符串的组合
 * @author GXF
 */
public class CombinationUtil {

    public static ArrayList<String> combine(ArrayList<String> is, String s, int m) {
        ArrayList<String> res = new ArrayList<String>();
        if (m == 0) {
            res.add(s);
            return res;
        }
        for (int i = 0; i < is.size(); i++) {
            String str = "";
            if (s.equals("")) {
                str = is.get(i);
            } else {
                str = s + "," + is.get(i);
            }
            ArrayList<String> innerIs = new ArrayList<String>();
            for (int j = i + 1; j < is.size(); j++) {
                innerIs.add(is.get(j));
            }
            ArrayList<String> innerRes = combine(innerIs, str, m - 1);
            res.addAll(innerRes);
        }
        return res;
    }
}
