package io.beetlsql.cli.component;

import io.beetlsql.cli.dao.VehicleLocationDao;
import io.beetlsql.cli.service.vehicleLocation.VehicleLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Profile("!test")
@Slf4j
@Component
public class CommandLineTaskExecutor implements CommandLineRunner {
    private VehicleLocationService vehicleLocationService;
    private VehicleLocationDao vehicleLocationDao;

    @Value("${ds.max-page-size}")
    private long MAX_PAGE_SIZE;

    public CommandLineTaskExecutor(VehicleLocationService vehicleLocationService, VehicleLocationDao vehicleLocationDao) {
        this.vehicleLocationService = vehicleLocationService;
        this.vehicleLocationDao = vehicleLocationDao;
    }

    @Override
    public void run(String... args) {
        // Start the clock
        long start = System.currentTimeMillis();

        List<CompletableFuture<Integer>> results = new ArrayList<>();

        long rowTotal = vehicleLocationDao.createLambdaQuery()
                .andNotEq("state", 0)
                .andIsNull("state_info")
                .count();
        long pageTotal = rowTotal / MAX_PAGE_SIZE + 1;
        long batchNum = 1;
        while (batchNum <= pageTotal) {
            results.add(vehicleLocationService.updateBatchState(batchNum, pageTotal));
            batchNum++;
        }
        CompletableFuture.allOf(results.toArray(new CompletableFuture[0])).join();
        log.info("state_info 更新完成，花费时间 {}", System.currentTimeMillis() - start);

        results.clear();

        rowTotal = vehicleLocationDao.createLambdaQuery()
                .andNotEq("alarm", 0)
                .andIsNull("alarm_info")
                .count();
        pageTotal = rowTotal / MAX_PAGE_SIZE + 1;
        batchNum = 1;
        while (batchNum <= pageTotal) {
            results.add(vehicleLocationService.updateBatchAlarm(batchNum, pageTotal));
            batchNum++;
        }
        CompletableFuture.allOf(results.toArray(new CompletableFuture[0])).join();
        log.info("alarm_info 更新完成，花费时间 {}", System.currentTimeMillis() - start);
    }
}
