package pants.pro.analyst_registry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pants.pro.analyst_registry.core.exceptions.EntityAlreadyExistsException;
import pants.pro.analyst_registry.core.exceptions.EntityInvalidArgumentException;
import pants.pro.analyst_registry.dto.UserInsertDTO;
import pants.pro.analyst_registry.dto.UserReadOnlyDTO;
import pants.pro.analyst_registry.mapper.Mapper;
import pants.pro.analyst_registry.model.Role;
import pants.pro.analyst_registry.model.User;
import pants.pro.analyst_registry.repository.RoleRepository;
import pants.pro.analyst_registry.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
/**
 * Service implementation for registering users with encoded passwords and roles.
 */
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    /**
     * Creates and persists a new user.
     * @param userInsertDTO user registration data.
     * @return saved user view data.
     * @throws EntityAlreadyExistsException if username already exists.
     * @throws EntityInvalidArgumentException if role id is invalid.
     */
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
            userRepository.save(user);
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
