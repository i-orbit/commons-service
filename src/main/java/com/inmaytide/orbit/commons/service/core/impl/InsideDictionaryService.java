package com.inmaytide.orbit.commons.service.core.impl;

import com.inmaytide.orbit.commons.service.configuration.AuthorizedFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author inmaytide
 * @since 2024/5/28
 */
@AuthorizedFeignClient(name = "core", contextId = "dictionary")
interface InsideDictionaryService {

    @GetMapping("/api/dictionaries/names")
    Map<String, String> findNamesByCodes(@RequestParam("codes") String codes);

}
