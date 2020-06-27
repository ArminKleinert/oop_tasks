package task6;

import u6.StdDraw;

public class Task3 {

    /**
     * Recursively draw a "mickey-mouse" pattern by drawing a cricle and then 4 cirlces
     * around it and 4 circles around each of those. With each such iteration,
     * the depth-parameter is decremented by 1. Once it reaches 0, no more circles will be drawn
     * @param startX Starting x-coordinate. Double value between 0 and 1.
     * @param startY Starting y-coordinate. Double value between 0 and 1.
     * @param radius Radius for the circles. Reduced for recursive calls. Double value between 0 and 1.
     * @param depth Number of recursive calls. If this value is 0, no more recursion will take place.
     *             It is recommended to choose a value < 8.
     */
    private static void drawCircle(final double startX, final double startY, final double radius, int depth) {
        // Draw a circle with the requested parameters.
        StdDraw.filledCircle(startX, startY, radius);

        // If depth is greater than 0, do recursive calls. Otherwise, quit.
        if (depth > 0) {
            // Set new parameters for recursive calls.
            final double nextRadius = radius / 2;
            depth--;
            drawCircle(startX + radius, startY - radius, nextRadius, depth);
            drawCircle(startX - radius, startY - radius, nextRadius, depth);
            drawCircle(startX + radius, startY + radius, nextRadius, depth);
            drawCircle(startX - radius, startY + radius, nextRadius, depth);
        }
    }

    public static void main(String[] args) {
        drawCircle(0.5, 0.5, 0.25, 5);
    }
}
