package pants.pro.analyst_registry.service;

import pants.pro.analyst_registry.core.exceptions.EntityAlreadyExistsException;
import pants.pro.analyst_registry.core.exceptions.EntityInvalidArgumentException;
import pants.pro.analyst_registry.dto.UserInsertDTO;
import pants.pro.analyst_registry.dto.UserReadOnlyDTO;

/**
 * Service contract for user creation and validation-related outcomes.
 */
public interface IUserService {

    /**
     * Creates and persists a new user.
     * @param userInsertDTO user registration data.
     * @return saved user view data.
     * @throws EntityAlreadyExistsException if username already exists.
     * @throws EntityInvalidArgumentException if role id is invalid.
     */
    UserReadOnlyDTO saveUser(UserInsertDTO userInsertDTO)
            throws EntityAlreadyExistsException, EntityInvalidArgumentException;
}
