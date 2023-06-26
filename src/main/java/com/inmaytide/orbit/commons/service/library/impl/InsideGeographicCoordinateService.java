package com.inmaytide.orbit.commons.service.library.impl;

import com.inmaytide.orbit.commons.domain.GeographicCoordinate;
import com.inmaytide.orbit.commons.domain.dto.result.AffectedResult;
import com.inmaytide.orbit.commons.service.configuration.AuthorizedFeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author inmaytide
 * @since 2023/6/26
 */
@AuthorizedFeignClient(name = "library", contextId = "geographic-coordinates")
public interface InsideGeographicCoordinateService {

    @GetMapping("/api/backend/geographic-coordinates/{attribution}")
    List<GeographicCoordinate> findByAttribution(@PathVariable("attribution") Long attribution);

    @GetMapping("/api/backend/geographic-coordinates")
    Map<Long, List<GeographicCoordinate>> findByAttributions(@RequestParam("attributions") String attributions);

    @PostMapping("/api/backend/geographic-coordinates")
    AffectedResult persist(@RequestBody List<GeographicCoordinate> coordinates);

}
