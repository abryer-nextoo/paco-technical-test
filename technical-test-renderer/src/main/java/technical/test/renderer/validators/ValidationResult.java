package technical.test.renderer.validators;

import java.util.Map;

public record ValidationResult(
        Map<String, String> fieldErrors
) {
    public boolean isValid() {
        return fieldErrors.isEmpty();
    }
}
