package tk.ptrybisz.oop2.organisms.plants;

import com.almasb.fxgl.core.math.Vec2;
import tk.ptrybisz.oop2.organisms.Organism;

public class Guarana extends Plant{
    public Guarana(Vec2 position){
        super("☘", 0, position);
    }

    @Override
    public void collision(Organism other){
        other.increaseStrength(3);
        this.death();
    }
}
