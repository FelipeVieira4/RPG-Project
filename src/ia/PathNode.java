package ia;

public class PathNode implements Comparable<PathNode> {
    public int x, y;
    public int gCost; // custo do início até este nó
    public int hCost; // custo heurístico (distância até o alvo)
    public PathNode parent;

    public PathNode(int x, int y, PathNode parent, int gCost, int hCost) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.gCost = gCost;
        this.hCost = hCost;
    }

    public int fCost() {
        return gCost + hCost;
    }

    @Override
    public int compareTo(PathNode other) {
        return Integer.compare(this.fCost(), other.fCost());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PathNode)) return false;
        PathNode other = (PathNode) obj;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return x * 31 + y;
    }
}
