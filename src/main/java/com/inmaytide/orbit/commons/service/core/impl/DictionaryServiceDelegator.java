package com.inmaytide.orbit.commons.service.core.impl;

import com.inmaytide.orbit.commons.service.core.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 * @author inmaytide
 * @since 2024/5/28
 */
@Service
public class DictionaryServiceDelegator implements DictionaryService {

    private final InsideDictionaryService service;

    public DictionaryServiceDelegator(InsideDictionaryService service) {
        this.service = service;
    }

    @Override
    public Map<String, String> findNamesByCodes(Collection<String> codes) {
        if (codes == null || codes.isEmpty()) {
            return Map.of();
        }
        return service.findNamesByCodes(StringUtils.join(codes, ","));
    }

}
