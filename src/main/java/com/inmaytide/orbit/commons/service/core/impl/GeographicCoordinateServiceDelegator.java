package com.inmaytide.orbit.commons.service.core.impl;

import com.inmaytide.orbit.commons.domain.GeographicCoordinate;
import com.inmaytide.orbit.commons.domain.dto.params.BatchUpdate;
import com.inmaytide.orbit.commons.domain.dto.result.AffectedResult;
import com.inmaytide.orbit.commons.service.core.GeographicCoordinateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author inmaytide
 * @since 2023/6/26
 */
@Service
public class GeographicCoordinateServiceDelegator implements GeographicCoordinateService {

    private final InsideGeographicCoordinateService service;

    public GeographicCoordinateServiceDelegator(InsideGeographicCoordinateService service) {
        this.service = service;
    }

    @Override
    public AffectedResult persist(BatchUpdate<GeographicCoordinate> body) {
        return service.persist(body);
    }

    @Override
    public AffectedResult deleteByBusinessDataId(Long businessDataId) {
        return null;
    }

    @Override
    public List<GeographicCoordinate> findByBusinessDataId(Long businessDataId) {
        return List.of();
    }

    @Override
    public Map<Long, List<GeographicCoordinate>> findByBusinessDataIds(List<Long> businessDataIds) {
        return Map.of();
    }
}
