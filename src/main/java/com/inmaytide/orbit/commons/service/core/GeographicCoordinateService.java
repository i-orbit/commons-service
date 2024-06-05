package com.inmaytide.orbit.commons.service.core;

import com.inmaytide.orbit.commons.domain.GeographicCoordinate;
import com.inmaytide.orbit.commons.domain.dto.params.BatchUpdate;
import com.inmaytide.orbit.commons.domain.dto.result.AffectedResult;

import java.util.List;
import java.util.Map;

/**
 * @author inmaytide
 * @since 2023/6/26
 */
public interface GeographicCoordinateService {

    /**
     * 覆盖保存(先删除后插入)指定业务对象的地理位置信息
     *
     * @return 影响的数据数量
     */
    AffectedResult persist(BatchUpdate<GeographicCoordinate> body);

    /**
     * 清除指定业务对象的地理位置信息
     *
     * @param businessDataId 业务对象唯一标识
     * @return 影响的数据数量
     */
    AffectedResult deleteByBusinessDataId(Long businessDataId);

    /**
     * 查询指定业务对象的地理位置信息
     *
     * @param businessDataId 业务对象唯一标识
     * @return 地理位置信息列表
     */
    List<GeographicCoordinate> findByBusinessDataId(Long businessDataId);

    /**
     * 批量查询一组业务数据对象的地理位置信息
     *
     * @param businessDataIds 要查询的业务对象唯一标识列表
     * @return 地理位置信息 Map 集合
     * <ul>
     *  <li>key:   业务对象唯一标识</li>
     *  <li>value: 对应的地理位置信息列表</li>
     * </ul>
     */
    Map<Long, List<GeographicCoordinate>> findByBusinessDataIds(List<Long> businessDataIds);

}
