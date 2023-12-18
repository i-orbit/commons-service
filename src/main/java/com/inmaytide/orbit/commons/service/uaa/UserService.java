package com.inmaytide.orbit.commons.service.uaa;

import com.inmaytide.orbit.commons.domain.SystemUser;

import java.util.Optional;

/**
 * @author inmaytide
 * @since 2023/5/15
 */
public interface UserService {

    Optional<SystemUser> getUserByUsername(String username);

}
