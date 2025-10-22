package ia;

import entidy.Entidy;
import world.LevelWorld;
import java.util.List;

public class Npc extends Entidy {
    private PathFinder pathfinder;
    private List<Entidy> path;
    private int currentStep = 0;

    private Entidy target;
    private int lastTargetX = -1;
    private int lastTargetY = -1;

    public Npc(int x, int y, LevelWorld world) {
        super(x, y);
        this.pathfinder = new PathFinder(world);
    }

    public void setTarget(Entidy target) {
        this.target = target;
    }

    public void update() {
        // Se não tem alvo, não faz nada
        if (target == null) return;

        // Atualiza o caminho se o alvo mudou de posição
        if (target.getX() != lastTargetX || target.getY() != lastTargetY) {
            path = pathfinder.findPath(this, target);
            currentStep = 0;
            lastTargetX = target.getX();
            lastTargetY = target.getY();
        }

        // Se não há caminho, não faz nada
        if (path == null || path.isEmpty() || currentStep >= path.size()) return;

        Entidy next = path.get(currentStep);
        int targetX = next.getX();
        int targetY = next.getY();

        // Movimento básico até o próximo ponto
        if (getX() < targetX) setX(getX() + 4);
        else if (getX() > targetX) setX(getX() - 4);

        if (getY() < targetY) setY(getY() + 4);
        else if (getY() > targetY) setY(getY() - 4);

        // Quando chega no tile alvo, vai pro próximo
        if (Math.abs(getX() - targetX) < 2 && Math.abs(getY() - targetY) < 2) {
            currentStep++;
        }
    }
}
