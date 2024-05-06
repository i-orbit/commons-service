package com.inmaytide.orbit.commons.service.uaa;

import java.util.List;
import java.util.Map;

/**
 * @author inmaytide
 * @since 2023/5/15
 */
public interface UserService {

    Map<Long, String> findNamesByIds(List<Long> ids);

    Map<Long, String> findEmailsByIds(List<Long> ids);

    Map<Long, String> findTelephoneNumbersByIds(List<Long> ids);

}
