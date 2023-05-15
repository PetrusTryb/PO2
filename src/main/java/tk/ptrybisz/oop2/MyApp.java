package tk.ptrybisz.oop2;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class MyApp extends GameApplication {
    World world;
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(1500);
        gameSettings.setHeight(900);
        gameSettings.setTitle("Gra w Życie");
        gameSettings.setCredits(Collections.singletonList("Piotr Trybisz (s193557)"));
        gameSettings.setVersion("Piotr Trybisz 193557");
    }

    @Override
    protected void initGame(){
        world = World.getInstance("Rectangle",35, 25);
        world.populateWorld();
    }

    @Override
    protected void initInput(){
        super.initInput();
        getInput().addAction(new UserAction("Move up") {
            @Override
            protected void onAction() {
                super.onAction();
                world.getHuman().setDirection(0,-1);
            }
        }, KeyCode.UP);
        getInput().addAction(new UserAction("Move down") {
            @Override
            protected void onAction() {
                super.onAction();
                world.getHuman().setDirection(0,1);
            }
        }, KeyCode.DOWN);
        getInput().addAction(new UserAction("Move left") {
            @Override
            protected void onAction() {
                super.onAction();
                world.getHuman().setDirection(-1,0);
            }
        }, KeyCode.LEFT);
        getInput().addAction(new UserAction("Move right") {
            @Override
            protected void onAction() {
                super.onAction();
                world.getHuman().setDirection(1,0);
            }
        }, KeyCode.RIGHT);
        getInput().addAction(new UserAction("Next turn") {
            @Override
            protected void onActionEnd() {
                super.onAction();
                world.nextTurn();
            }
        }, KeyCode.ENTER);
        getInput().addAction(new UserAction("Special ability") {
            @Override
            protected void onAction() {
                super.onAction();
                world.getHuman().activateSpecialAbility();
            }
        }, KeyCode.SPACE);
        getInput().addAction(new UserAction("Save") {
            @Override
            protected void onAction() {
                super.onAction();
                FileOutputStream fileOutputStream;
                try {
                    fileOutputStream = new FileOutputStream("save.bin");
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    objectOutputStream.writeObject(world);
                    objectOutputStream.close();
                    fileOutputStream.close();
                    world.log("World saved");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, KeyCode.S);

        getInput().addAction(new UserAction("Load") {
            @Override
            protected void onAction() {
                super.onAction();
                FileInputStream fileInputStream;
                try {
                    fileInputStream = new FileInputStream("save.bin");
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    world = (World) objectInputStream.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, KeyCode.L);

        getInput().addAction(new UserAction("Switch world type") {
            @Override
            protected void onAction(){
                super.onAction();
                world = World.toggleType();
                world.populateWorld();
            }
        }, KeyCode.F1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}