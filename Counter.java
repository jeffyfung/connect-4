/**
 * Counter class. Represents a counter. Can be written as a nested class inside
 * Board but it is easy to read this way.
 */
public class Counter {

    public enum Shape {
        CIRCLE,
        CROSS
    }

    private final Shape shape;

    /**
     * Constructor method.
     * 
     * @param shape shape of the counter
     * @return a Counter object.
     */
    public Counter(Shape shape) {
        this.shape = shape;
    }

    /**
     * A binary switch; get the opposite shape of the given shape.
     * 
     * @param shape shape of the counter
     * @return the opposite counter shape
     */
    public static Shape getOther(Shape shape) {
        if (shape == Shape.CIRCLE) {
            return Shape.CROSS;
        } else {
            return Shape.CIRCLE;
        }
    }

    /**
     * Getter for shape
     * 
     * @return shape of the counter
     */
    public Shape getShape() {
        return shape;
    }
}
