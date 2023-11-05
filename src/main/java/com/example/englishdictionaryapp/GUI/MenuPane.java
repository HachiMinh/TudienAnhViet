package com.example.englishdictionaryapp.GUI;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
//import com.example.englishdictionaryapp.Database.*;
public class MenuPane {

    double sizeX = MainScene.sizeX;
    double sizeY = MainScene.sizeY;
    MenuBar menuBar = new MenuBar();
    public MenuBar make() {
        menuBar.setLayoutX(0);
        menuBar.setLayoutY(0);
        menuBar.setPrefSize(sizeX,sizeY/21);
        final Menu item1 = new Menu("File");
        final Menu item2 = new Menu("Help");
        final Menu item3 = new Menu("Exit");
        menuBar.getMenus().addAll(item1, item2, item3);
        return menuBar;
    }
}
