public class Counter {
    // can be written as a nested class inside Board but it is easy to read this way
    public enum Shape {
        CIRCLE,
        CROSS
    }

    private final Shape shape;

    public Counter(Shape shape) {
        this.shape = shape;
    }

    public static Shape getOther(Shape shape) {
        if (shape == Shape.CIRCLE) {
            return Shape.CROSS;
        } else {
            return Shape.CIRCLE;
        }
    }

    public Shape getShape() {
        return shape;
    }
}
