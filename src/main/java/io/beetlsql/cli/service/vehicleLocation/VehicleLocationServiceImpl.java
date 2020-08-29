package io.beetlsql.cli.service.vehicleLocation;

import io.beetlsql.cli.common.interceptor.BatchUpdateInterceptor;
import io.beetlsql.cli.common.utils.AlarmCode;
import io.beetlsql.cli.common.utils.StateCode;
import io.beetlsql.cli.dao.VehicleLocationDao;
import io.beetlsql.cli.entity.VehicleLocation;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * vehicle_location 数据更新 service 实现.
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "vehicleLocation")
public class VehicleLocationServiceImpl implements VehicleLocationService {
    private final SQLManager sqlManager;
    private final VehicleLocationDao vehicleLocationDao;

    @Value("${ds.max-page-size}")
    private long MAX_PAGE_SIZE;


    public VehicleLocationServiceImpl(SQLManager sqlManager,
                                      VehicleLocationDao vehicleLocationDao,
                                      @Value("${beetl-beetlsql.dev}") boolean BEETL_BEETLSQL_DEV) {
        this.sqlManager = sqlManager;
        this.vehicleLocationDao = vehicleLocationDao;

        // 是否开启批量参数拦截
        if (BEETL_BEETLSQL_DEV) this.sqlManager.setInters(new Interceptor[]{new BatchUpdateInterceptor()});
    }

    @Override
    public void updateAllInfo() {

        List<VehicleLocation> vls;

        // 获取需要打印的参数，供参考。
        // NOTE: 如果任务期间有新数据插入，该打印参数不会更新（节约运行时间）。但仍会更新新数据，日志会（可能）显示为任务组数大于预计总数
        long rowTotal = vehicleLocationDao.createLambdaQuery().andNotEq("state", 0).andIsNull("state_info").count();
        long pageTotal = rowTotal / MAX_PAGE_SIZE + 1;
        long batchNum = 1;
        for (; ; ) {
            log.info("[SELECT]: 查询数据库中...");
            vls = vehicleLocationDao.getBatchState(MAX_PAGE_SIZE);
            if (vls.size() == 0) break; // 返回为 0 说明全部更新完毕

            log.info("[UPDATE]: 文字转换完成，正在更新 state_info 字段...");
            int result = vehicleLocationDao.insertUpdateState(DecodeInfo(vls));

            if (result != 2 * vls.size()) // INSERT ... ON DUPLICATE KEY UPDATE 如果执行更新，受影响的行数是 2
                log.info("{}/{} 条数据更新失败（有其他线程在操作吗？）", 2 * vls.size() - result, vls.size());

            log.info("第 {}/{} 组任务执行完毕", batchNum, pageTotal);
            batchNum++;
        }
        log.info("state_info 更新完成");

        rowTotal = vehicleLocationDao.createLambdaQuery().andNotEq("alarm", 0).andIsNull("alarm_info").count();
        pageTotal = rowTotal / MAX_PAGE_SIZE + 1;
        batchNum = 1;
        for (; ; ) {
            log.info("[SELECT]: 正在查询数据库...");
            vls = vehicleLocationDao.getBatchAlarm(MAX_PAGE_SIZE);
            if (vls.size() == 0) break;

            log.info("[UPDATE]: 文字转换完成，正在更新 alarm_info 字段...");
            int result = vehicleLocationDao.insertUpdateAlarm(DecodeInfo(vls));

            if (result != 2 * vls.size())
                log.info("{}/{} 条数据更新失败（有其他线程在操作吗？）", 2 * vls.size() - result, vls.size());

            log.info("第 {}/{} 组任务执行完毕", batchNum, pageTotal);
            batchNum++;
        }
        log.info("alarm_info 更新完成");
    }

    @Async
    @Override
    public CompletableFuture<Integer> updateBatchState(long batchNum, long pageTotal) {
        log.info("第 {}/{} 组任务执行中", batchNum, pageTotal);
        log.info("[SELECT]: 正在查询 state...");
        List<VehicleLocation> vls = vehicleLocationDao.getBatchState(MAX_PAGE_SIZE);
        log.info("[UPDATE]: 文字转换完成，正在更新 state_info 字段...");
        int result = vehicleLocationDao.insertUpdateState(DecodeInfo(vls));
        return CompletableFuture.completedFuture(result);
    }

    @Async
    @Override
    public CompletableFuture<Integer> updateBatchAlarm(long batchNum, long pageTotal) {
        log.info("第 {}/{} 组任务执行中", batchNum, pageTotal);
        log.info("[SELECT]: 正在查询 alarm...");
        List<VehicleLocation> vls = vehicleLocationDao.getBatchAlarm(MAX_PAGE_SIZE);
        log.info("[UPDATE]: 文字转换完成，正在更新 alarm_info 字段...");
        int result = vehicleLocationDao.insertUpdateAlarm(DecodeInfo(vls));
        return CompletableFuture.completedFuture(result);
    }

    private List<VehicleLocation> DecodeInfo(List<VehicleLocation> vls) {
        for (VehicleLocation vl : vls) {
            String stateInfo = StateCode.Decode(vl.getState());
            String alarmInfo = AlarmCode.Decode(vl.getAlarm());
            vl.setStateInfo(stateInfo);
            vl.setAlarmInfo(alarmInfo);
        }
        return vls;
    }

}
