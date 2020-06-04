public class Node implements Comparable {

    private Point point;
    private int h;
    private int g;
    private int f;
    private Node parent;

    public Node(Point point, int h, int g, int f, Node parent)
    {
        this.point = point;
        this.h = h;
        this.g = g;
        this.f = f;
        this.parent = parent;
    }

    public int getF() {
        return f;
    }

    public int getG() {
        return g;
    }

    public Node getParent() {
        return parent;
    }

    public Point getPoint() {
        return point;
    }

    public int compareTo(Object n2)
    {
        return this.getF() - ((Node)n2).getF();
    }

}
