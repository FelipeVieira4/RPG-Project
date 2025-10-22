package item;

import player.Player;

public class TuxCollect extends Item{
    private static final int numberFrames=0;
    private static final int sizeWidth=32;
    private static final int sizeHeight=32;

    public TuxCollect(int x, int y) {
        super(x,y,"rsc/tux_collect.png",numberFrames);
    }


    public boolean PlayerUse(Player p){
        if(this.hasCollision(p)) {
            p.addTuxCollected();
            return true;
        }
        return false;
    }
}
