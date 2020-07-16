package io.beetlsql.cli.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class AlarmCodeTest {

    @Test
    public void AlarmDecodeTest() {
        Integer i = 99999;
        String info = AlarmCode.Decode(i);
        log.debug("[alarm_info]= {}", info);
    }

}
