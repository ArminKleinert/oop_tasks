package u8;

/**
 * @author Maria Stange
 */
public class NotEnoughLiquidException extends Exception {

    public NotEnoughLiquidException() {
        super("There is not enough liquid in mug.");
    }
}
