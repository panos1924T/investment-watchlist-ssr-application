package pants.pro.analyst_registry.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pants.pro.analyst_registry.core.exceptions.EntityNotFoundException;
import pants.pro.analyst_registry.dto.AnalystEditDTO;
import pants.pro.analyst_registry.service.IAnalystService;

@Component
@Slf4j
@RequiredArgsConstructor
public class AnalystEditValidator implements Validator {

    private final IAnalystService analystService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AnalystEditDTO.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        AnalystEditDTO analystEditDTO = (AnalystEditDTO) target;

        try {
            AnalystEditDTO savedAnalyst = analystService.getAnalystByUuid(analystEditDTO.uuid());

            if (savedAnalyst != null && !savedAnalyst.email().equals(analystEditDTO.email())) {
                if (analystService.isAnalystExists(analystEditDTO.email())) {
                    log.warn("Update failed! Analyst with email={} already exists.", analystEditDTO.email());
                    errors.rejectValue("email", "email.analyst.exists");
                }
            }

        } catch (EntityNotFoundException e) {
            log.warn("Analyst with uuid={} not found!", analystEditDTO.uuid());
            errors.rejectValue("email", "email.analyst.exists");
        }
    }
}
