package com.inmaytide.orbit.commons.service.core.impl;

import com.inmaytide.orbit.commons.domain.GeographicCoordinate;
import com.inmaytide.orbit.commons.domain.dto.params.BatchUpdate;
import com.inmaytide.orbit.commons.domain.dto.result.AffectedResult;
import com.inmaytide.orbit.commons.service.core.GeographicCoordinateService;
import org.springframework.stereotype.Component;

/**
 * @author inmaytide
 * @since 2024/12/19
 */
@Component
public class DefaultGeographicCoordinateServiceImpl implements GeographicCoordinateService {

    @Override
    public void persist(BatchUpdate<GeographicCoordinate> body) {

    }

    @Override
    public AffectedResult deleteByBusinessDataId(Long businessDataId) {
        return null;
    }

}
