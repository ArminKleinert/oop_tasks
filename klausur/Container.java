package klausur;

public interface Container<T> {
    public void storeItem(T item) throws CanNotBeStoreException;
    public T getItem() throws EmptyBoxException;
    public int getWidth();
    public int getHeight();
    public int getDeep();
    public int volume();
}
