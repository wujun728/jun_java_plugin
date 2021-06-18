package Immutable.Q5;

public class Line {
    private final Point startPoint;
    private final Point endPoint;
    public Line(int startx, int starty, int endx, int endy) {
        this.startPoint = new Point(startx, starty);
        this.endPoint = new Point(endx, endy);
    }
    public Line(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }
    public int getStartX() { return startPoint.getX(); }
    public int getStartY() { return startPoint.getY(); }
    public int getEndX() { return endPoint.getX(); }
    public int getEndY() { return endPoint.getY(); }
    public String toString() {
        return "[ Line: " + startPoint + "-" + endPoint + " ]";
    }
}
