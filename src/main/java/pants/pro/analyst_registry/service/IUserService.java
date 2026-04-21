package pants.pro.analyst_registry.service;

import pants.pro.analyst_registry.core.exceptions.EntityAlreadyExistsException;
import pants.pro.analyst_registry.core.exceptions.EntityInvalidArgumentException;
import pants.pro.analyst_registry.dto.UserInsertDTO;
import pants.pro.analyst_registry.dto.UserReadOnlyDTO;

public interface IUserService {

    UserReadOnlyDTO saveUser(UserInsertDTO userInsertDTO)
            throws EntityAlreadyExistsException, EntityInvalidArgumentException;
}
