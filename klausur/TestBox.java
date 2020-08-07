package klausur;

public class TestBox {
    public static void main(String[] args) {
        Box<ItemL> box = new Box<>(5, 5, 5);

        CharSequence[] arr = new String[10];
        System.out.println(arr);

        System.out.println("Volume: " + box.getWidth() + "*" + box.getHeight() + "*" + box.getDeep() + " = " + box.volume());
        try {
            box.getItem();
        } catch (Exception e) {
            System.out.println("Exception when no item :)");
        }
        try {
            box.storeItem(new ItemL(10, 10, 10));
        } catch (Exception e) {
            System.out.println("Exception when item too big :)");
        }
        try {
            box.storeItem(new ItemL(1, 1, 1));
            System.out.println("Stored item :)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
