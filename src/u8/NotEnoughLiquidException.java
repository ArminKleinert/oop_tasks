package u8;

public class NotEnoughLiquidException extends Exception {

    public NotEnoughLiquidException() {
        super("There is not enough liquid in mug.");
    }
}
