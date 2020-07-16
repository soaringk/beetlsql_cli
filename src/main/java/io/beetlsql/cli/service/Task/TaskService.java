package io.beetlsql.cli.service.Task;

import io.beetlsql.cli.service.VehicleLocation.VehicleLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TaskService {
    private VehicleLocationService vehicleLocationService;

    public TaskService(VehicleLocationService vehicleLocationService) {
        this.vehicleLocationService = vehicleLocationService;
    }
    public void execute() {
        log.info("[Task]: Updating `vehicle_location`.");

        log.info("Updating info...");
        vehicleLocationService.updateAllInfo();
        log.info("All info updated!");
    }
}
