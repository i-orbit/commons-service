package com.inmaytide.orbit.commons.service.uaa;

import java.util.Optional;

/**
 * @author inmaytide
 * @since 2023/5/15
 */
public interface UserService {

    Optional<Long> getIdByUsername(String username);

}
