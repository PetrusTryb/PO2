package tk.ptrybisz.oop2.organisms;

import com.almasb.fxgl.core.math.Vec2;
import tk.ptrybisz.oop2.World;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public abstract class Organism implements Serializable {
    protected World world;
    private int strength;
    private final int initiative;
    private int age;
    private final String emoji;
    private Vec2 position;
    private Vec2 previousPosition;

    static public String[] userAddableClasses = {
            "animals.Wolf",
            "animals.Sheep",
            "animals.Fox",
            "animals.Turtle",
            "animals.Antelope",
            "plants.Grass",
            "plants.Guarana",
            "plants.Belladonna",
            "plants.Dandelion",
            "plants.PineBorscht",
    };

    protected Organism(String emoji, int strength, int initiative, Vec2 position) {
        world = World.getInstance();
        this.strength = strength;
        this.initiative = initiative;
        this.age = 0;
        this.emoji = emoji;
        this.position = position;
    }

    public static Organism spawn(World world, String name, Vec2 position) {
        try{
            if(!name.contains("tk.ptrybisz.oop2.organisms"))
                name = "tk.ptrybisz.oop2.organisms." + name;
            return (Organism) Class.forName(name).getConstructor(Vec2.class).newInstance(position);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract void action();

    public abstract void collision(Organism other);

    public void death() {
        Vec2 previousPosition = new Vec2(position);
        position = new Vec2(-1,-1);
        strength = -1;
        world.draw(previousPosition);
    }

    public int getStrength() {
        return strength;
    }

    public void increaseStrength(int strength) {
        this.strength += strength;
    }

    public int getInitiative() {
        return initiative;
    }

    public int getAge() {
        return age;
    }

    public void increaseAge() {
        age++;
    }

    public Vec2 getPosition() {
        return position;
    }

    public boolean hasEscaped(){
        return false;
    }

    public boolean setPosition(Vec2 position) {
        if (!world.isInBounds(position))
            return false;
        previousPosition = this.position;
        world.log(getClass().getSimpleName() + " moved from " + previousPosition + " to " + position);
        Organism other = world.getOrganism(position);
        if (other!=null&&other!=this) {
            other.collision(this);
        }
        this.position = position;
        return true;
    }

    public void undoMove() {
        setPosition(previousPosition);
    }

    public String getEmoji() {
        return emoji;
    }

    public int compareTo(Organism organism) {
        if (this.initiative < organism.initiative)
            return 1;
        else if (this.initiative > organism.initiative)
            return -1;
        else return Integer.compare(this.age, organism.age);
    }
}
