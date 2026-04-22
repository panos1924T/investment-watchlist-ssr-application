package pants.pro.analyst_registry.core.exceptions;

/**
 * Checked exception indicating invalid domain arguments for an operation.
 */
public class EntityInvalidArgumentException extends Exception{

    /**
     * Executes EntityInvalidArgumentException.
     * @param message message value.
     */
    public EntityInvalidArgumentException(String message) {
        super(message);
    }
}
