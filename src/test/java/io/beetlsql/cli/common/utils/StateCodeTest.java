package io.beetlsql.cli.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class StateCodeTest {

    @Test
    public void StateDecodeTest() {
        Integer i = 262146;
        String info = StateCode.Decode(i);
        log.debug("[state_info]= {}", info);
    }
}
