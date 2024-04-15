package com.inmaytide.orbit.commons.service.uaa;

import com.inmaytide.orbit.commons.domain.SystemUser;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author inmaytide
 * @since 2023/5/15
 */
public interface UserService {

    Optional<SystemUser> getUserByUsername(String username);

    Map<Long, String> findEmailsWithIds(List<Long> ids);

    Map<Long, String> findTelephoneNumbersWithIds(List<Long> ids);

}
