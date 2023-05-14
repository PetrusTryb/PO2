package tk.ptrybisz.oop2;

import com.almasb.fxgl.app.scene.GameScene;
import com.almasb.fxgl.core.math.Vec2;
import javafx.scene.text.Text;
import tk.ptrybisz.oop2.organisms.Organism;
import tk.ptrybisz.oop2.organisms.animals.Human;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGLForKtKt.random;

public abstract class World implements Serializable {
    int width;
    int height;
    int turn;
    transient Text[][] labels;
    transient Text logger;
    String logs;
    Human human;
    ArrayList<Organism> organisms;
    Vec2 initialPosition;
     static World currentInstance = null;
     static public World getInstance(String name, int sA, int sB) {
            currentInstance = switch (name) {
                case "Rectangle" -> new RectangleWorld(sA, sB);
                case "Hexagon" -> new HexagonWorld(sA, sB);
                default -> null;
            };
            return currentInstance;
     }

    static public World getInstance() {
        return currentInstance;
    }

    public void nextTurn(){
        logs = "";
        log("Turn " + ++turn + ":\n");
        organisms = new ArrayList<>(organisms.stream().filter(o -> o.getStrength()>=0).toList());
        organisms.sort(Organism::compareTo);
        int size = organisms.size();
        for (int i = 0; i < size; i++) {
            if(organisms.get(i).getStrength()>=0)
                organisms.get(i).action();
        }
        for(int i = 0; i<width; i++)
            for(int j = 0; j<height; j++)
                draw(new Vec2(i,j));
    }
    public abstract void draw(Vec2 position);
    public void log(String message){
        logs+=message+"\n";
        logger.textProperty().setValue(logs);
    }
    public Boolean isInBounds(Vec2 position){
        return position.x>=0 && position.x<width && position.y>=0 && position.y<height;
    }
    public void addOrganism(String name, Vec2 position){
        Organism o = Organism.spawn(this, name, position);
        organisms.add(o);
        log(o.getClass().getSimpleName() + " spawned at " + position.toString());
        draw(position);
    }
    public Organism getOrganism(Vec2 position) {
        return organisms.stream().filter(o -> o.getPosition().equals(position)).findFirst().orElse(null);
    }
    public abstract Vec2[] getAllowedMoves();
    void populateWorld(){
        addOrganism("animals.Human", new Vec2(random(0,width-1), random(0,height-1)));
        human = (Human) organisms.get(0);
        addOrganism("animals.Sheep", new Vec2(random(0,width-1), random(0,height-1)));
        addOrganism("animals.Sheep", new Vec2(random(0,width-1), random(0,height-1)));
        addOrganism("animals.Wolf", new Vec2(random(0,width-1), random(0,height-1)));
        addOrganism("animals.Wolf", new Vec2(random(0,width-1), random(0,height-1)));
        addOrganism("animals.Fox", new Vec2(random(0,width-1), random(0,height-1)));
        addOrganism("animals.Fox", new Vec2(random(0,width-1), random(0,height-1)));
        addOrganism("animals.Turtle", new Vec2(random(0,width-1), random(0,height-1)));
        addOrganism("animals.Turtle", new Vec2(random(0,width-1), random(0,height-1)));
        addOrganism("animals.Antelope", new Vec2(random(0,width-1), random(0,height-1)));
        addOrganism("animals.Antelope", new Vec2(random(0,width-1), random(0,height-1)));
        addOrganism("plants.Grass", new Vec2(random(0,width-1), random(0,height-1)));
        addOrganism("plants.Dandelion", new Vec2(random(0,width-1), random(0,height-1)));
        addOrganism("plants.Belladonna", new Vec2(random(0,width-1), random(0,height-1)));
        addOrganism("plants.Guarana", new Vec2(random(0,width-1), random(0,height-1)));
        addOrganism("plants.PineBorscht", new Vec2(random(0,width-1), random(0,height-1)));
    }
    public Human getHuman(){
        return human;
    }

}
