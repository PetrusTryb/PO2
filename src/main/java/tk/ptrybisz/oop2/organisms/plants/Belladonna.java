package tk.ptrybisz.oop2.organisms.plants;

import com.almasb.fxgl.core.math.Vec2;
import tk.ptrybisz.oop2.organisms.Organism;

public class Belladonna extends Plant{
    public Belladonna(Vec2 position){
        super("🍒", 99, position);
    }

    @Override
    public void collision(Organism other){
        other.death();
        this.death();
    }
}
