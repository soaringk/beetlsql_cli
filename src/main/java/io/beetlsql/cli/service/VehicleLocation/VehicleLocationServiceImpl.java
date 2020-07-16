package io.beetlsql.cli.service.VehicleLocation;

import io.beetlsql.cli.common.utils.AlarmCode;
import io.beetlsql.cli.common.utils.StateCode;
import io.beetlsql.cli.dao.VehicleLocationDao;
import io.beetlsql.cli.entity.VehicleLocation;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * vehicle_location 数据更新 service 实现.
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "vehicleLocation")
public class VehicleLocationServiceImpl implements VehicleLocationService {
    @Autowired
    private VehicleLocationDao vehicleLocationDao;

    @Value("${ds.max-page-size}")
    private long MAX_PAGE_SIZE;

    @Override
    public int updateInfoById(Long id) {
        VehicleLocation vl = vehicleLocationDao.find(id);
        String stateInfo = StateCode.Decode(vl.getState());
        String alarmInfo = AlarmCode.Decode(vl.getAlarm());
        vl.setStateInfo(stateInfo);
        vl.setAlarmInfo(alarmInfo);
        return vehicleLocationDao.updateTemplateById(vl);
    }

    @Override
    public void updateAllInfo() {
        PageQuery<VehicleLocation> query = new PageQuery<>();
        query.setPageSize(MAX_PAGE_SIZE);
        vehicleLocationDao.pageInfo(query);

        for (long pageNum = 1; !query.isLastPage(); pageNum++) {
            query.setPageNumber(pageNum);
            vehicleLocationDao.pageInfo(query); // 设定 pageNumber 后需要执行才能获得下一个 page
            log.info("Updating {}/{} batch of size {}", query.getPageNumber(), query.getTotalPage(), MAX_PAGE_SIZE);
            int[] results = updateBatch(query);
        }
    }

    /**
     * 批量更新一个 pageSize 数量的 entity 的 info
     *
     * @param query 一个 PageQuery 对象（设置好 PageSize 等）
     * @return int[] 1：成功
     */
    private int[] updateBatch(PageQuery<VehicleLocation> query) {
        List<VehicleLocation> vls = query.getList();
        for (VehicleLocation vl : vls) {
            String stateInfo = StateCode.Decode(vl.getState());
            String alarmInfo = AlarmCode.Decode(vl.getAlarm());
            vl.setStateInfo(stateInfo);
            vl.setAlarmInfo(alarmInfo);
        }
        //return vehicleLocationDao.getSQLManager().updateBatchTemplateById(VehicleLocation.class, vls);
        return vehicleLocationDao.updateBatchInfo(vls);
    }

    /**
     * 批量更新一个 pageSize 数量的的 info
     * 更新参数使用 map
     * 不建议使用，仅供参考，建议操作 entity
     *
     * @param query 一个 PageQuery 对象（设置好 PageSize 等）
     * @return int[] 1：成功
     */
    private int[] updateBatchMap(PageQuery<VehicleLocation> query) {
        List<Map<String, Object>> maps = new ArrayList<>();
        for (VehicleLocation vl : query.getList()) {
            String stateInfo = StateCode.Decode(vl.getState());
            String alarmInfo = AlarmCode.Decode(vl.getAlarm());

            Map<String, Object> map = new HashMap<>();
            map.put("id", vl.getId());
            map.put("stateInfo", stateInfo);
            map.put("alarmInfo", alarmInfo);
            maps.add(map);
        }
        Map[] entities = maps.toArray(new Map[0]);
        return vehicleLocationDao.updateBatchInfoMap(entities);
    }
}
