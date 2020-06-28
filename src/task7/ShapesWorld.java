package task7;

public interface ShapesWorld {
    public double getMin_X();

    public double getMin_Y();

    public double getMax_X();

    public double getMax_Y();

    public Shape getClosestShape(Shape currentShape);

    public void addShape(Shape aNewShape);

    public void removeShape(Shape shapeToBeRemoved);
}
