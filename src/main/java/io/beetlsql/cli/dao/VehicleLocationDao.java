package io.beetlsql.cli.dao;

import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import io.beetlsql.cli.entity.VehicleLocation;

import java.util.List;
import java.util.Map;

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
     * 获取一个分页的查询
     *
     * @param query 一个 PageQuery 对象（设置好 PageSize 等）
     */
    void pageInfo(PageQuery<VehicleLocation> query);

    /**
     * 批量更新 info
     *
     * @param vls 含有需要更新的 entity List
     * @return 更新结果：1：成功
     */
    int[] updateBatchInfo(List<VehicleLocation> vls);

    /**
     * 批量更新 info
     *
     * @param entities 参数是个数组 Map[]，每个 map 里是需要修改的内容
     * @return 更新结果：1：成功
     */
    int[] updateBatchInfoMap(Map<String, Object>[] entities);

    /**
     * Count remaining entities.
     *
     * @return 未更新信息的数量
     */
    Long countRemain();
}
