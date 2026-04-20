package pants.pro.investment_watchlist.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pants.pro.investment_watchlist.core.exceptions.EntityAlreadyExistsException;
import pants.pro.investment_watchlist.core.exceptions.EntityInvalidArgumentException;
import pants.pro.investment_watchlist.dto.UserInsertDTO;
import pants.pro.investment_watchlist.dto.UserReadOnlyDTO;
import pants.pro.investment_watchlist.mapper.Mapper;
import pants.pro.investment_watchlist.model.Role;
import pants.pro.investment_watchlist.model.User;
import pants.pro.investment_watchlist.repository.RoleRepository;
import pants.pro.investment_watchlist.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @Transactional(rollbackFor = {EntityAlreadyExistsException.class, EntityInvalidArgumentException.class})
    public UserReadOnlyDTO saveUser(UserInsertDTO userInsertDTO)
            throws EntityAlreadyExistsException, EntityInvalidArgumentException {

        try {

            if (userRepository.findByUsername(userInsertDTO.username()).isPresent()) {
                throw new EntityAlreadyExistsException("User with username=" + userInsertDTO.username() + " already exists!");
            }

            User user = mapper.toUserEntity(userInsertDTO);
            user.setPassword(passwordEncoder.encode(userInsertDTO.password()));
            Role role = roleRepository.findById(userInsertDTO.roleId())
                    .orElseThrow(() -> new EntityInvalidArgumentException("Role id=" + userInsertDTO.roleId() + " invalid!"));
            role.addUser(user);
            log.info("Save succeeded for user with username={}.", userInsertDTO.username());
            return mapper.toUserReadOnlyDTO(user);

        } catch (EntityAlreadyExistsException e) {
            log.error("Save failed! User with username={} already exists", userInsertDTO.username());
            throw e;
        } catch (EntityInvalidArgumentException e) {
            log.error("Save failed! Invalid arguments for user with username={}", userInsertDTO.username());
            throw e;
        }
    }
}
