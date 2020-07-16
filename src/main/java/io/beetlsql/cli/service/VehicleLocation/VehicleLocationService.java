package io.beetlsql.cli.service.VehicleLocation;

/**
 * The interface Vehicle location service.
 */
public interface VehicleLocationService {

    /**
     * 按照主键 id 更新对应的 entity 的 info
     *
     * @param id 主键
     * @return 1 ：成功
     */
    int updateInfoById(Long id);

    /**
     * 更新所有 entity 的 info
     */
    void updateAllInfo();

}
