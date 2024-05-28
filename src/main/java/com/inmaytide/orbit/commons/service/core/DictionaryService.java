package com.inmaytide.orbit.commons.service.core;

import java.util.Collection;
import java.util.Map;

/**
 * @author inmaytide
 * @since 2024/5/28
 */
public interface DictionaryService {

    Map<String, String> findNamesByCodes(Collection<String> codes);

}
