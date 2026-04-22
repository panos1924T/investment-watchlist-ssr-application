package pants.pro.analyst_registry.core.exceptions;

/**
 * Checked exception indicating a duplicate entity conflict during write operations.
 */
public class EntityAlreadyExistsException extends Exception {

    /**
     * Executes EntityAlreadyExistsException.
     * @param message message value.
     */
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
