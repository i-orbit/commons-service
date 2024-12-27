package com.inmaytide.orbit.commons.service.core.impl;

import com.inmaytide.orbit.commons.service.core.DictionaryService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author inmaytide
 * @since 2024/12/19
 */
@Component
public class DefaultDictionaryServiceImpl implements DictionaryService {

    @Override
    public Map<String, String> findNamesByCodes(List<String> codes) {
        return Map.of();
    }

}
