package com.atguigu.yygh.msm.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author lzq
 */
public class RandomUtil {

    private static final Random RANDOM = new Random();

    private static final DecimalFormat FOURDF = new DecimalFormat("0000");

    private static final DecimalFormat SIXDF = new DecimalFormat("000000");

    public static String getFourBitRandom() {
        return FOURDF.format(RANDOM.nextInt(10000));
    }

    public static String getSixBitRandom() {
        return SIXDF.format(RANDOM.nextInt(1000000));
    }

    /**
     * 给定数组，抽取n个数据
     *
     * @param list
     * @param n
     * @return
     */
    public static ArrayList getRandom(List list, int n) {
        Random random = new Random();
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        for (int i = 0; i < list.size(); i++) {
            int number = random.nextInt(100) + 1;
            hashMap.put(number, i);
        }
        Object[] robjs = hashMap.values().toArray();
        ArrayList r = new ArrayList();
        for (int i = 0; i < n; i++) {
            r.add(list.get((int) robjs[i]));
        }
        return r;
    }
}
