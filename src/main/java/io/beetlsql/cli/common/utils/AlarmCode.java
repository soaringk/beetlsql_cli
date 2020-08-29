package io.beetlsql.cli.common.utils;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.StringJoiner;

@Slf4j
public class AlarmCode {
    // 编码总位数
    private static final Integer BITS_MAX_LENGTH = 32;

    private static final Map<Integer, String> alarmMap = ImmutableMap.<Integer, String>builder()
            .put(2, "疲劳驾驶")
            .put(3, "预警")
            .put(30, "保留位30")
            .put(31, "保留位31")
            .build();


    public static String Decode(Integer alarm) {
        if (alarm == null) return null;
        String code = Integer.toBinaryString(alarm);
        char[] bits = Strings.padStart(code, BITS_MAX_LENGTH, '0').toCharArray();

        StringJoiner sj = new StringJoiner(",");
        for (int idx = 0; idx < BITS_MAX_LENGTH; idx++) {
            if (alarmMap.containsKey(idx)) {
                if (bits[BITS_MAX_LENGTH - idx - 1] == '1') {  // 编码的String从右开始数
                    String pos = alarmMap.get(idx);
                    sj.add(pos);
                }
            }
        }
        // 如果长度为 0 返回 null
        return sj.length() != 0 ? sj.toString() : null;
    }

    public static Map<Integer, String> getAlarmMap() {
        return alarmMap;
    }

    public static Integer getBitsMaxLength() { return BITS_MAX_LENGTH; }
}
