package io.beetlsql.cli.service.vehicleLocation;

import java.util.concurrent.CompletableFuture;

/**
 * The interface Vehicle location service.
 */
public interface VehicleLocationService {

    /**
     * 更新所有 entity 的 info
     */
    void updateAllInfo();

    CompletableFuture<Integer> updateBatchState(long batchNum, long pageTotal);

    CompletableFuture<Integer> updateBatchAlarm(long batchNum, long pageTotal);
}

