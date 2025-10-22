package ia;

import entidy.Entidy;
import world.ChunkCollision;
import world.LevelWorld;
import game.GameComponent;

import java.util.*;

public class PathFinder {
    private LevelWorld world;
    private ChunkCollision collision;

    public PathFinder(LevelWorld world) {
        this.world = world;
        this.collision = new ChunkCollision();
    }

    public List<Entidy> findPath(Entidy start, Entidy goal) {
        int startX = start.getX() / GameComponent.tileSize;
        int startY = start.getY() / GameComponent.tileSize;
        int goalX = goal.getX() / GameComponent.tileSize;
        int goalY = goal.getY() / GameComponent.tileSize;

        PriorityQueue<PathNode> openList = new PriorityQueue<>();
        HashSet<PathNode> closedList = new HashSet<>();

        PathNode startNode = new PathNode(startX, startY, null, 0, heuristic(startX, startY, goalX, goalY));
        openList.add(startNode);

        while (!openList.isEmpty()) {
            PathNode current = openList.poll();
            if (current.x == goalX && current.y == goalY)
                return buildPath(current);

            closedList.add(current);

            for (int[] neighbor : getNeighbors(current.x, current.y)) {
                int nx = neighbor[0];
                int ny = neighbor[1];

                // ignora posições inválidas ou bloqueadas
                if (isBlocked(nx, ny) || contains(closedList, nx, ny)) continue;

                int gCost = current.gCost + 1;
                PathNode neighborNode = new PathNode(nx, ny, current, gCost, heuristic(nx, ny, goalX, goalY));

                // evita duplicar nós já presentes com custo menor
                Optional<PathNode> existing = openList.stream()
                        .filter(n -> n.equals(neighborNode) && n.fCost() <= neighborNode.fCost())
                        .findFirst();

                if (existing.isEmpty()) openList.add(neighborNode);
            }
        }

        return Collections.emptyList(); // nenhum caminho
    }

    private boolean isBlocked(int tileX, int tileY) {
        return (world.getLevelImage().getRGB(tileX, tileY) & 0xff) != LevelWorld.colorTileFree;
    }

    private int heuristic(int x1, int y1, int x2, int y2) {
        // distância Manhattan (boa pra movimento em grid 4-direcional)
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private boolean contains(Set<PathNode> set, int x, int y) {
        return set.stream().anyMatch(n -> n.x == x && n.y == y);
    }

    private List<Entidy> buildPath(PathNode goalNode) {
        LinkedList<Entidy> path = new LinkedList<>();
        PathNode current = goalNode;

        while (current != null) {
            path.addFirst(new Entidy(current.x, current.y));
            current = current.parent;
        }

        return path;
    }

    private List<int[]> getNeighbors(int x, int y) {
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{x + 1, y});
        list.add(new int[]{x - 1, y});
        list.add(new int[]{x, y + 1});
        list.add(new int[]{x, y - 1});
        return list;
    }
}
