package com.inmaytide.orbit.commons.service.core;

import java.util.List;
import java.util.Map;

/**
 * @author inmaytide
 * @since 2024/12/19
 */
public interface DictionaryService {

    Map<String, String> findNamesByCodes(List<String> codes);

}
