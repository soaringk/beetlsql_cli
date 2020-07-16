package io.beetlsql.cli.dao;

import com.google.common.collect.Sets;
import io.beetlsql.cli.ConsoleApplicationTests;
import io.beetlsql.cli.entity.VehicleLocation;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class VehicleLocationDaoTest extends ConsoleApplicationTests {
    @Autowired
    private VehicleLocationDao vehicleLocationDao;

    @Test
    public void findByIdTest() {
        log.debug("[Test]: Find a single row of info...");
        long id = 1;
        log.debug("[Test]: {}", vehicleLocationDao.find(id));
    }

    @Test
    void pageInfoTest() {
        log.debug("[Test]: Get a pageQuery of size 1000...");
        PageQuery<VehicleLocation> query = new PageQuery<>();
        query.setPageSize(1000);
        vehicleLocationDao.pageInfo(query);
        assertEquals(1000, query.getPageSize());
    }

    @Test
    public void updateBatchInfoTest() {
        log.debug("[Test]: Update a batch of rows in one time...");
        List<VehicleLocation> vls = vehicleLocationDao.all(1, 100);
        int[] results = vehicleLocationDao.updateBatchInfo(vls);
        Set resultSet = Sets.newHashSet(results);
        log.debug("[Test]: All rows updated successfully!");
        assertFalse(resultSet.contains(0));
    }

    @Test
    public void countRemainTest() {
        log.debug("[Test]: Counting rows that were not updated yet...");
        Long count = vehicleLocationDao.countRemain();
        assertNotNull(count);
        Long test = vehicleLocationDao.createLambdaQuery()
                .andNotEq("alarm", 0)
                .andIsNull("alarm_info")
                .orNotEq("state", 0)
                .andIsNull("state_info")
                .count();
        assertEquals(test, count);
        log.debug("[Test]: Remaining rows: {} = {}", count, test);
    }
}
