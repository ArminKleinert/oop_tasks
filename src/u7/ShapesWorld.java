package u7;

import java.awt.Color;

/**
 * @author M. Esponda
 * @version 1.01
 */

/**
 * This interface contains the methods that a Shape object can perform through its ShapesWorld-reference.
 */
public interface ShapesWorld
{
        /**
         * returns the smallest visible x-coordinate of the ShapesWorld-object
         */
        public int getMin_X();

        /**
         * returns the smallest visible y-coordinate of the ShapesWorld-object
         */
        public int getMin_Y();

        /**
         * returns the biggest visible x-coordinate of the ShapesWorld-object
         */
        public int getMax_X();

        /**
         * returns the biggest visible y-coordinate of the ShapesWorld-object
         */
        public int getMax_Y();
        
        /**
         * returns the background color of the ShapesWorld-object
         */
        public Color getBackgroundColor();

        /**
         * returns the reference of the closest object to myShape. If only the myShape object
         * exists in the ShapesWorld-object it returns the null constant.
         */
        public Shape getClosestShape( Shape myShape );

        /**
         * a new shape object is added to the ShapesWorld-object
         */
        public void addShape( Shape aNewShape );

         /**
          * removes a shape from the ShapesWorld-object
          */
        public void removeShape( Shape toBeRemoved );
        
} // end of the ShapesWorld interface