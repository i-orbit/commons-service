package com.inmaytide.orbit.commons.service.core;

import com.inmaytide.orbit.commons.domain.GeographicCoordinate;
import com.inmaytide.orbit.commons.domain.dto.params.BatchUpdate;
import com.inmaytide.orbit.commons.domain.dto.result.AffectedResult;

/**
 * @author inmaytide
 * @since 2024/12/19
 */
public interface GeographicCoordinateService {

    void persist(BatchUpdate<GeographicCoordinate> body);

    AffectedResult deleteByBusinessDataId(String businessDataId);

}
