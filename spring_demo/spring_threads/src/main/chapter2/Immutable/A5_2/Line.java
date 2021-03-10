package Immutable.A5_2;

public class Line {
    private final Point startPoint;
    private final Point endPoint;
    public Line(int startx, int starty, int endx, int endy) {
        this.startPoint = new Point(startx, starty);
        this.endPoint = new Point(endx, endy);
    }
    public Line(Point startPoint, Point endPoint) {
        this.startPoint = new Point(startPoint.x, startPoint.y);
        this.endPoint = new Point(endPoint.x, endPoint.y);
    }
    public int getStartX() { return startPoint.x; }
    public int getStartY() { return startPoint.y; }
    public int getEndX() { return endPoint.x; }
    public int getEndY() { return endPoint.y; }
    public String toString() {
        return "[ Line: " + startPoint + "-" + endPoint + " ]";
    }
}
