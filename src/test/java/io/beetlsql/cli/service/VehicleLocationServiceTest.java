package io.beetlsql.cli.service;

import io.beetlsql.cli.ConsoleApplicationTests;
import io.beetlsql.cli.dao.VehicleLocationDao;
import io.beetlsql.cli.service.vehicleLocation.VehicleLocationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Slf4j
public class VehicleLocationServiceTest extends ConsoleApplicationTests {
    @Autowired
    private VehicleLocationDao vehicleLocationDao;

    @Autowired
    private VehicleLocationService vehicleLocationService;

    @Test
    public void updateAllInfoTest() {
        log.debug("[Test]: Updating all info...");
        vehicleLocationService.updateAllInfo();
        assertEquals(0, vehicleLocationDao.countRemain(), "All rows should be updated!!");
        log.debug("[Test]: All info updated successfully!");
    }

}

