package menu;

import game.GameComponent;

import java.util.ArrayList;

public class Menu {
    ArrayList<MenuOption> menuOptions = new ArrayList<>();

    public GameComponent gameComponent;

    public Menu(GameComponent gameComponent){
        this.gameComponent=gameComponent;
    }

    public void initOptions(){
        menuOptions.add(new MenuOption("Quit") {
            @Override
            public boolean selectOption(GameComponent gameComponent) {
                gameComponent.shouldClose();
                return true;
            }
        });
    }
}
