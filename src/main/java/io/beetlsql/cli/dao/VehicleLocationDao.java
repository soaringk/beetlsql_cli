package io.beetlsql.cli.dao;

import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import io.beetlsql.cli.entity.VehicleLocation;

import java.util.List;

/**
 *
 * gen by beetlsql mapper 2020-08-20
 */
@Repository
public interface VehicleLocationDao extends BaseMapper<VehicleLocation> {

    /**
     * 按照主键 id 查找对应的 entity
     *
     * @param id 主键
     * @return entity : vehicle location
     */
    VehicleLocation find(Long id);

    /**
     * 获取一个固定长度的查询，只查 state
     *
     * @param limit 一次获取的长度
     */
    List<VehicleLocation> getBatchState(long limit);

    /**
     * 获取一个固定长度的查询，只查 alarm
     *
     * @param limit 一次获取的长度
     */
    List<VehicleLocation> getBatchAlarm(long limit);

    /**
     * 批量更新 info
     *
     * @param vls 含有需要更新的 entity List
     * @return 更新结果：int[] 1：成功, 0：失败
     */
    int[] updateBatchInfo(List<VehicleLocation> vls);

    /**
     * 批量更新 state_info
     * 执行效率相比 updateBatchInfo 快很多
     * @param vls 需要更新 state_info 的 entity list
     * @return int：受影响的行数（插入和更新为两次操作，具体参考 sql 语句）
     */
    int insertUpdateState(List<VehicleLocation> vls);

    /**
     * 批量更新 alarm_info
     * 执行效率相比 updateBatchInfo 快很多
     * @param vls 需要更新 alarm_info 的 entity list
     * @return int：受影响的行数（插入和更新为两次操作，具体参考 sql 语句）
     */
    int insertUpdateAlarm(List<VehicleLocation> vls);

    /**
     * Count remaining entities.
     *
     * @return 未更新信息的数量
     */
    Long countRemain();
}