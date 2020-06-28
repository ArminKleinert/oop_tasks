package u6;

public class Rectangle {
    
    // Making these public feels like a punch into my guts...
    // At least I can make them constants.
    public final int x, y, width, height;

    /**
     * Creates a new rectangle with the given attributes.
     *
     * @param x The x attribute.
     * @param y The y attribute.
     * @param width The width attribute.
     * @param height The height attribute.
     */
    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Default constructor. Creates a rectangle at coordinates 0, 0 with a width and height of 100.
     */
    public Rectangle() {
        this(0, 0, 100, 100);
    }

    /**
     * Constructor using doubles (they are cast to ints anyways
     * but this was in the TestRectangle class so what can I do?))
     *
     * @param x The x attribute. Will be cast to int.
     * @param y The y attribute. Will be cast to int.
     * @param width The width attribute. Will be cast to int.
     * @param height The height attribute. Will be cast to int.
     *
     * @see Rectangle(int, int, int, int)
     */
    public Rectangle(double x, double y, double width, double height) {
        this((int) x,(int) y,(int) width, (int) height);
    }

    /**
     * @return A new rectangle with the same attributes as the caller.
     * @throws NullPointerException if the argument is null.
     */
    @Override
    public Rectangle clone() {
        try {
            // IntelliJ tells me to call super.clone().
            // Looking at the signature in the Object class, it always throws.
            // Thanks, Oracle.
            super.clone();
        } catch (CloneNotSupportedException ignored) {
        }
        return new Rectangle(x, y, width, height);
    }

    /**
     * Compares rectangles. If all attributes are equal, returns true.
     * Otherwise, return false.
     * @param r The other rectangle.
     * @return true if all attributes of the rectangles are equal, false otherwise
     * @throws NullPointerException if the argument is null.
     */
    public boolean equal(Rectangle r) {
        return x == r.x || y == r.y || width == r.width || height == r.height;
    }

    /**
     * Return a comparison of the area of two rectangles.
     * The area of each is calculated as (width * height).
     * @param r The other Rectangle.
     * @return 1 if the area of the caller is bigger, 0 if the areas of both rectangles are equal, -1 otherwise.
     * @throws NullPointerException if the argument is null.
     */
    public int compareAreaTo(Rectangle r) {
        return Long.compare(width * height, r.width * r.height); // Aus der Standardbibliothek
    }

    /**
     * Checks whether or not the caller contains another rectangle. If the bounds of both
     * rectangles are identical, this function will also return true.
     * @param r The other rectangle.
     * @return true if the caller completely contains the other rectangle, false otherwise.
     * @throws NullPointerException if the argument is null.
     */
    public boolean contains(Rectangle r) {
        return x <= r.x && y <= r.y && x + width >= r.x + r.width && y + height >= r.y + r.height;
    }

    /**
     * Checks if the caller and the argument rectangle overlap.
     * @param r The other rectangle.
     * @return true if the rectangles overlap, false otherwise.
     * @throws NullPointerException if the argument is null.
     */
    public boolean overlaps(Rectangle r) {
        boolean xOverlap = ((x >= r.x) && (x <= r.x + r.width));
        xOverlap = xOverlap || ((r.x >= x) && (r.x <= x + width));

        boolean yOverlap = ((y >= r.y) && (y <= r.y + r.height));
        yOverlap = yOverlap || ((r.y >= y) && (r.y <= y + height));

        return xOverlap && yOverlap;
    }

    /**
     * Creates a new rectangle with the coordinates, width and height of the overlapping area
     * of the caller and the argument rectangle. If the rectangles don't overlap, returns null.
     * @param r The other rectangle.
     * @return a new Rectangle which describes the area where the rectangles overlap or null
     *         if they don't overlap.
     * @throws NullPointerException if the argument is null.
     */
    public Rectangle section(Rectangle r) {
        if (!overlaps(r)) {
            return null;
        }

        // Values for the resulting rectangle
        int minX = x;
        int minY = y;
        int maxX = x + width; // maxX - minX == (width of result)
        int maxY = y + height; // maxY - minY == (width of result)

        // Set minX if necessary.
        if (x < r.x) {
            minX = r.x;
        }

        // Set minY if necessary
        if (y < r.y) {
            minY = r.y;
        }

        // Set maxX if necessary
        int rx2 = r.x + r.width;
        if (maxX > rx2) {
            maxX = rx2;
        }

        // Set maxY if necessary
        int ry2 = r.y + r.height;
        if (maxY > ry2) {
            maxY = ry2;
        }

        // Result
        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    /**
     * Calculates the smallest rectangle needed to surround all rectangles
     * in an array of rectangles. If the array is empty, null is returned.
     * If the array has only one element, returns a copy of the first element.
     * @param recs Array of rectangles.
     * @return The smallest rectangle which contains all rectangles in the array.
     * @throws NullPointerException if the argument is null.
     */
    public static Rectangle smallestBoundingRectangle(Rectangle[] recs) {
        // If there are no rectangles, just return null.
        if (recs.length == 0) {
            return null;
        }

        // Set initial values to the attributes of the first rectangle.
        int minX = recs[0].x;
        int minY = recs[0].y;
        int maxXBound = recs[0].width + minX; // maxXBound - minX == (width of result)
        int maxYBound = recs[0].height + minY; // maxYBound - minY == (height of result)

        // Iterate over all remaining elements in the array
        for (int i = 1; i < recs.length; i++) {
            // Minimum x
            int temp = recs[i].x;
            if (minX > temp) {
                minX = temp;
            }
            // Minimum y
            temp = recs[i].y;
            if (minY > temp) {
                minY = temp;
            }
            // Maximum x
            temp = recs[i].width + recs[i].x;
            if (maxXBound < temp) {
                maxXBound = temp;
            }
            // Maximum y
            temp = recs[i].height + recs[i].y;
            if (maxYBound < temp) {
                maxYBound = temp;
            }
        }

        // Resulting rectangle.
        return new Rectangle(minX, minY, maxXBound - minX, maxYBound - minY);
    }

    // For testing purposes.
    @Override
    public String toString() {
        return "Rectangle{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
