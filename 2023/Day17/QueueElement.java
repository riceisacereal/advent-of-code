public class QueueElement {
    int[] location;
    int direction;
    int steps;
    int distance;

    int heuristicValue;

    public QueueElement(int[] location, int direction, int steps, int distance, int heuristicValue) {
        this.location = location;
        this.direction = direction;
        this.steps = steps;
        this.distance = distance;
        this.heuristicValue = heuristicValue;
    }
}
