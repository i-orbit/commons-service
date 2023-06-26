package com.inmaytide.orbit.commons.service.library.impl;

import com.inmaytide.orbit.commons.domain.GeographicCoordinate;
import com.inmaytide.orbit.commons.domain.dto.result.AffectedResult;
import com.inmaytide.orbit.commons.service.library.GeographicCoordinateService;
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
    public List<GeographicCoordinate> findByAttribution(Long attribution) {
        if (attribution == null) {
            return Collections.emptyList();
        }
        return service.findByAttribution(attribution);
    }

    @Override
    public Map<Long, List<GeographicCoordinate>> findByAttributions(List<Long> attributions) {
        if (CollectionUtils.isEmpty(attributions)) {
            return Collections.emptyMap();
        }
        String parameter = attributions.stream().distinct()
                .filter(Objects::nonNull)
                .map(Objects::toString)
                .collect(Collectors.joining(","));
        if (StringUtils.isBlank(parameter)) {
            return Collections.emptyMap();
        }
        return service.findByAttributions(parameter);
    }

    @Override
    public AffectedResult persist(List<GeographicCoordinate> coordinates) {
        if (CollectionUtils.isEmpty(coordinates)) {
            return AffectedResult.notAffected();
        }
        return service.persist(coordinates);
    }
}
