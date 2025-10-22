package ia;

import entity.Entity;
import game.GameComponent;
import world.LevelWorld;

import java.util.*;

public class PathFinder {

    private LevelWorld world;

    public PathFinder(LevelWorld world) {
        this.world = world;
    }

    public static class TileNode {
        public final int x, y;
        public TileNode(int x, int y) { this.x = x; this.y = y; }
    }

    private static class PathNode implements Comparable<PathNode> {
        int x, y;
        PathNode parent;
        int gCost, hCost;

        public PathNode(int x, int y, PathNode parent, int gCost, int hCost) {
            this.x = x; this.y = y;
            this.parent = parent;
            this.gCost = gCost;
            this.hCost = hCost;
        }

        public int fCost() { return gCost + hCost; }

        @Override
        public int compareTo(PathNode o) { return Integer.compare(this.fCost(), o.fCost()); }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof PathNode)) return false;
            PathNode o = (PathNode)obj;
            return x == o.x && y == o.y;
        }

        @Override
        public int hashCode() { return Objects.hash(x, y); }
    }

    public List<TileNode> findPath(Entity start, Entity goal) {
        int startX = start.getX() / GameComponent.tileSize;
        int startY = start.getY() / GameComponent.tileSize;
        int goalX = goal.getX() / GameComponent.tileSize;
        int goalY = goal.getY() / GameComponent.tileSize;

        PriorityQueue<PathNode> openList = new PriorityQueue<>();
        Set<PathNode> closedList = new HashSet<>();

        openList.add(new PathNode(startX, startY, null, 0, heuristic(startX, startY, goalX, goalY)));

        while(!openList.isEmpty()) {
            PathNode current = openList.poll();

            if(current.x == goalX && current.y == goalY) return buildPath(current);

            closedList.add(current);

            for(int[] neighbor : getNeighbors(current.x, current.y)) {
                int nx = neighbor[0], ny = neighbor[1];
                if(isBlocked(nx, ny) || closedList.contains(new PathNode(nx, ny, null,0,0))) continue;

                int g = current.gCost + 1;
                PathNode node = new PathNode(nx, ny, current, g, heuristic(nx, ny, goalX, goalY));

                boolean skip = false;
                for(PathNode n : openList)
                    if(n.equals(node) && n.fCost() <= node.fCost()) { skip = true; break; }

                if(!skip) openList.add(node);
            }
        }

        return Collections.emptyList();
    }

    private boolean isBlocked(int x, int y) {
        if(x < 0 || y < 0 || x >= world.getLevelImage().getWidth() || y >= world.getLevelImage().getHeight()) return true;
        return (world.getLevelImage().getRGB(x, y) & 0xff) != LevelWorld.colorTileFree;
    }

    private int heuristic(int x1, int y1, int x2, int y2) {
        int dx = x1 - x2;
        int dy = y1 - y2;
        return (int)Math.sqrt(dx*dx + dy*dy);
    }

    private List<int[]> getNeighbors(int x, int y) {
        return Arrays.asList(
                new int[]{x+1,y},
                new int[]{x-1,y},
                new int[]{x,y+1},
                new int[]{x,y-1}
        );
    }

    private List<TileNode> buildPath(PathNode goal) {
        LinkedList<TileNode> path = new LinkedList<>();
        PathNode current = goal;
        while(current != null) {
            path.addFirst(new TileNode(current.x, current.y));
            current = current.parent;
        }
        return path;
    }
}
