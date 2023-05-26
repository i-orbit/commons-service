package com.inmaytide.orbit.commons.service.uaa;

import com.inmaytide.orbit.commons.domain.GlobalUser;

import java.util.Optional;

/**
 * @author inmaytide
 * @since 2023/5/15
 */
public interface UserService {

    Optional<GlobalUser> getUserByUsername(String username);

}
