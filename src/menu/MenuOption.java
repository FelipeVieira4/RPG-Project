package menu;

import game.GameComponent;

public class MenuOption implements OptionInterface {
    private String name;

    public MenuOption(String name){
        this.name=name;
    }


    @Override
    public boolean selectOption(GameComponent gameComponent) {return false;}
}