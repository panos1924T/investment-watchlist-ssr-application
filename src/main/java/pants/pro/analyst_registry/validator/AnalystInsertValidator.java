package pants.pro.analyst_registry.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pants.pro.analyst_registry.dto.AnalystInsertDTO;
import pants.pro.analyst_registry.service.IAnalystService;

@Component
@Slf4j
@RequiredArgsConstructor
/**
 * Spring Validator that prevents creating analysts with duplicate emails.
 */
public class AnalystInsertValidator implements Validator {

    private final IAnalystService analystService;

    /**
     * Checks whether this validator supports the given class.
     * @param clazz clazz value.
     * @return result value.
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return AnalystInsertDTO.class == clazz;
    }

    /**
     * Validates the target object and registers errors.
     * @param target target value.
     * @param errors errors value.
     */
    @Override
    public void validate(Object target, Errors errors) {
        AnalystInsertDTO analystInsertDTO = (AnalystInsertDTO) target;

        if (analystInsertDTO.email() != null && analystService.isAnalystExists(analystInsertDTO.email())) {
            log.warn("Save failed! Analyst with email={} already exists.", analystInsertDTO.email());
            errors.rejectValue("email", "email.analyst.exists");
        }
    }
}
