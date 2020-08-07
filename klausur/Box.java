package klausur;

public class Box<T extends ItemL> implements Container<T> {

    private int width;
    private int height;
    private int depth;
    private T item;

    public Box(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        item = null;
    }

    public Box() {
        this(16, 16, 16);
    }

    @Override
    public void storeItem(T item) throws CanNotBeStoreException {
        if (!fitIn(item)) throw new CanNotBeStoreException();
        this.item = item;
    }

    @Override
    public T getItem() throws EmptyBoxException {
        if (item == null) throw new EmptyBoxException();
        T temp = item;
        item = null;
        return temp;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getDeep() {
        return depth;
    }

    @Override
    public int volume() {
        return width * height * depth;
    }

    private boolean fitIn(T item) {
        return width >= item.width && height >= item.height && depth >= item.depth;
    }
}
