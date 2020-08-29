package io.beetlsql.cli.common.utils;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.StringJoiner;

@Slf4j
public class StateCode {
    // 编码总位数
    private static final Integer BITS_MAX_LENGTH = 32;

    private static final Map<Integer, String[]> stateMap = ImmutableMap.<Integer, String[]>builder()
            .put(2, new String[]{"北纬", "南纬"})
            .put(3, new String[]{"东经", "西经"})
            .put(6, new String[]{"保留位6", "保留位6",})
            .build();

    public static String Decode(Integer state) {
        if (state == null) return null;
        String code = Integer.toBinaryString(state);
        char[] bits = Strings.padStart(code, BITS_MAX_LENGTH, '0').toCharArray();

        StringJoiner sj = new StringJoiner(",");
        for (int idx = 0; idx < BITS_MAX_LENGTH; idx++) {
            if (stateMap.containsKey(idx)) {
                if (bits[BITS_MAX_LENGTH - idx - 1] == '1') {  // 编码的String从右开始数
                    String pos = stateMap.get(idx)[1];
                    sj.add(pos);
                } else {
                    String neg = stateMap.get(idx)[0];
                    sj.add(neg);
                }
            }
        }
        // 如果长度为 0 返回 null
        return sj.length() != 0 ? sj.toString() : null;
    }

    public static Map<Integer, String[]> getStateMap() {
        return stateMap;
    }

    public static Integer getBitsMaxLength() { return BITS_MAX_LENGTH; }
}
