package pants.pro.investment_watchlist.service;

import pants.pro.investment_watchlist.core.exceptions.EntityAlreadyExistsException;
import pants.pro.investment_watchlist.core.exceptions.EntityInvalidArgumentException;
import pants.pro.investment_watchlist.dto.UserReadOnlyDTO;

public interface IUserService {

    UserReadOnlyDTO saveUser(UserInsertDTO userInsertDTO)
            throws EntityAlreadyExistsException, EntityInvalidArgumentException;
}
