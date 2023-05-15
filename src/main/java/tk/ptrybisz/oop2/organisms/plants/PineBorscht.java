package tk.ptrybisz.oop2.organisms.plants;

import com.almasb.fxgl.core.math.Vec2;
import tk.ptrybisz.oop2.organisms.Organism;
import tk.ptrybisz.oop2.organisms.animals.Animal;

public class PineBorscht extends Plant{
    public PineBorscht(Vec2 position){
        super("🏵", 10, position);
    }

    @Override
    public void action(){
        Vec2[] neighbours = world.getAllowedMoves();
        for(Vec2 neighbour : neighbours){
            Vec2 position = getPosition().add(neighbour);
            if(world.isInBounds(position)) {
                if (world.getOrganism(position) != null) {
                    if (world.getOrganism(position) instanceof Animal)
                        world.getOrganism(position).death();
                }
            }
        }
        super.action();
    }

    @Override
    public void collision(Organism other){
        other.death();
        this.death();
    }
}
