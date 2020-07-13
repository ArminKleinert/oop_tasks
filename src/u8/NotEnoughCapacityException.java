package u8;

public class NotEnoughCapacityException extends Exception {

    public NotEnoughCapacityException() {
        super("There is not enough capacity in mug.");
    }
}
