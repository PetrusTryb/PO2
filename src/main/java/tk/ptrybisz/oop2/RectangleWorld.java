package tk.ptrybisz.oop2;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import tk.ptrybisz.oop2.organisms.Organism;
import tk.ptrybisz.oop2.organisms.animals.Animal;
import tk.ptrybisz.oop2.organisms.animals.Human;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGLForKtKt.random;

public class RectangleWorld extends World implements Serializable {
    transient Rectangle[][] board;
    final Vec2[] directions = {new Vec2(0, 1), new Vec2(1, 0), new Vec2(0, -1), new Vec2(-1, 0)};

    public RectangleWorld(int width, int height) {
        this.width = width;
        this.height = height;
        turn = 0;
        organisms = new ArrayList<>();
        logs = "Rectangle World\n";
        initGUI();
    }

    private void initGUI(){
        FXGL.getGameScene().clearGameViews();
        FXGL.getGameScene().clearUINodes();
        board = new Rectangle[width][height];
        labels = new Text[width][height];
        initialPosition = new Vec2(FXGL.getGameScene().getAppWidth() / 2.0 - width * 20 / 2.0, FXGL.getGameScene().getAppHeight() / 2.0 - height * 20 / 2.0);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height ; j++) {
                board[i][j] = new Rectangle(20, 20);
                board[i][j].setTranslateX(initialPosition.x + i * 20);
                board[i][j].setTranslateY(initialPosition.y + j * 20);
                board[i][j].setStroke(Color.BLACK);
                board[i][j].setUserData(new Vec2(i, j));
                board[i][j].setOnMouseClicked(e -> {
                    Vec2 position = (Vec2) ((Rectangle) e.getSource()).getUserData();
                    if(getOrganism(position)==null){
                        ContextMenu contextMenu = new ContextMenu();
                        for (String name : Organism.userAddableClasses) {
                            contextMenu.getItems().add(new MenuItem(name) {
                                {
                                    setOnAction(e -> {
                                        addOrganism(name, position);
                                        draw(position);
                                    });
                                }
                            });
                        }
                        contextMenu.show(FXGL.getGameScene().getRoot(), e.getScreenX(), e.getScreenY());
                        contextMenu.setAutoHide(true);
                    }
                });
                FXGL.getGameScene().addUINode(board[i][j]);
                labels[i][j] = new Text();
                labels[i][j].setTranslateX(initialPosition.x + i * 20);
                labels[i][j].setTranslateY(initialPosition.y + j * 20 + 17);
                labels[i][j].setFill(Color.WHITE);
                labels[i][j].setFont(Font.font("DejaVu Sans Mono",20));
                FXGL.getGameScene().addUINode(labels[i][j]);
            }
        }
        logger = new Text();
        logger.setTranslateY(10);
        FXGL.getGameScene().addUINode(logger);
        for(int i = 0; i<width; i++)
            for(int j = 0; j<height; j++)
                draw(new Vec2(i,j));
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();
        initGUI();
    }

    @Override
    public void draw(Vec2 position) {
        Organism o = getOrganism(position);
        if(o == null) {
            board[(int) position.x][(int) position.y].setFill(Color.DARKGREY);
            labels[(int) position.x][(int) position.y].textProperty().setValue(" ");
        }
        else if(o.getStrength()<0){
            board[(int) position.x][(int) position.y].setFill(Color.DARKGREY);
            labels[(int) position.x][(int) position.y].textProperty().setValue(" ");
        }
        else{
            board[(int) position.x][(int) position.y].setFill(o instanceof Animal ? Color.BROWN : Color.DARKGREEN);
            labels[(int) position.x][(int) position.y].textProperty().setValue(o.getEmoji());
        }
    }

    @Override
    public Vec2[] getAllowedMoves() {
        return directions;
    }
}
