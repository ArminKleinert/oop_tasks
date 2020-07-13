package u8;

/**
 * @author Maria Stange
 */
public class NotEnoughCapacityException extends Exception {

    public NotEnoughCapacityException() {
        super("There is not enough capacity in mug.");
    }
}
