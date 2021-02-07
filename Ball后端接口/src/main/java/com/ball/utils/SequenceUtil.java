package com.ball.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class SequenceUtil {

    static int sequence = 0;
    private static int length = 6;

    public static synchronized String getLocalTrmSeq() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String datetime = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        return datetime + uuid;
    }

    /**
     * 左填0
     * @param s 原字符串
     * @param length 字符串总长度
     * @return 左填0后的字符串
     */
    public static String addLeftZero(String s, int length) {
        // StringBuilder sb=new StringBuilder();
        int old = s.length();
        if (length > old) {
            char[] c = new char[length];
            char[] x = s.toCharArray();
            if (x.length > length) {
                throw new IllegalArgumentException(
                        "Numeric value is larger than intended length: " + s
                                + " LEN " + length);
            }
            int lim = c.length - x.length;
            for (int i = 0; i < lim; i++) {
                c[i] = '0';
            }
            System.arraycopy(x, 0, c, lim, x.length);
            return new String(c);
        }
        return s.substring(0, length);
    }

    public static void main(String[] args) {
        System.out.println(getLocalTrmSeq());
    }

}
