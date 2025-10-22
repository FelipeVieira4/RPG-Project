package ia;

import entity.Entity;
import entity.GameObject;
import game.GameComponent;
import world.LevelWorld;

import java.awt.*;
import java.util.List;

public class Enemy extends GameObject {

    private PathFinder pathfinder;
    private List<PathFinder.TileNode> path;
    private int currentStep = 0;

    private int speed=2;

    private Entity target;
    private int lastTargetTileX = -1;
    private int lastTargetTileY = -1;

    private LevelWorld world;

    public Enemy(int x, int y, LevelWorld world) {
        super(x, y);
        this.world = world;
        this.pathfinder = new PathFinder(world);

    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    private boolean collisionWithTile(int px, int py) {
        int tileX = px / GameComponent.tileSize;
        int tileY = py / GameComponent.tileSize;

        if(tileX < 0 || tileY < 0 || tileX >= world.getLevelImage().getWidth() || tileY >= world.getLevelImage().getHeight())
            return true;

        return (world.getLevelImage().getRGB(tileX, tileY) & 0xff) != LevelWorld.colorTileFree;
    }

    public void update() {
        if(target == null) return;

        int playerTileX = target.getX() / GameComponent.tileSize;
        int playerTileY = target.getY() / GameComponent.tileSize;

        // Recalcula o caminho se jogador mudou de tile ou não há caminho
        if(path == null || path.isEmpty() || playerTileX != lastTargetTileX || playerTileY != lastTargetTileY) {
            List<PathFinder.TileNode> newPath = pathfinder.findPath(this, target);

            // tenta manter o NPC no ponto mais próximo do caminho antigo
            if(path != null && !path.isEmpty() && currentStep < path.size()) {
                PathFinder.TileNode currentTile = path.get(currentStep);
                // procura o tile mais próximo no novo caminho
                int closestIndex = 0;
                int minDist = Integer.MAX_VALUE;
                for(int i=0; i<newPath.size(); i++) {
                    PathFinder.TileNode t = newPath.get(i);
                    int dist = Math.abs(t.x - currentTile.x) + Math.abs(t.y - currentTile.y);
                    if(dist < minDist) {
                        minDist = dist;
                        closestIndex = i;
                    }
                }
                currentStep = closestIndex;
            } else {
                currentStep = 0;
            }

            path = newPath;
            lastTargetTileX = playerTileX;
            lastTargetTileY = playerTileY;
        }

        if(path == null || path.isEmpty() || currentStep >= path.size()) return;

        PathFinder.TileNode nextTile = path.get(currentStep);
        int targetX = nextTile.x * GameComponent.tileSize;
        int targetY = nextTile.y * GameComponent.tileSize;

        // Movimento separado: X primeiro
        if(getX() < targetX && !collisionWithTile(getX() + speed, getY())) setX(getX() + speed);
        else if(getX() > targetX && !collisionWithTile(getX() - speed, getY())) setX(getX() - speed);

        // Depois Y
        if(getY() < targetY && !collisionWithTile(getX(), getY() + speed)) setY(getY() + speed);
        else if(getY() > targetY && !collisionWithTile(getX(), getY() - speed)) setY(getY() - speed);

        // Se chegou no tile, passa para o próximo
        if(Math.abs(getX() - targetX) <= 1 && Math.abs(getY() - targetY) <= 1) {
            currentStep++;
        }
    }

    @Override
    public void draw(Graphics graphic) {

    }
}
