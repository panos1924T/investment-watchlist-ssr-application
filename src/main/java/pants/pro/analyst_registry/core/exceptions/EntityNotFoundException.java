package pants.pro.analyst_registry.core.exceptions;

/**
 * Checked exception indicating an expected entity was not found.
 */
public class EntityNotFoundException extends Exception {

    /**
     * Executes EntityNotFoundException.
     * @param message message value.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
