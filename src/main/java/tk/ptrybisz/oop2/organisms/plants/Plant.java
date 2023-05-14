package tk.ptrybisz.oop2.organisms.plants;

import com.almasb.fxgl.core.math.Vec2;
import tk.ptrybisz.oop2.organisms.Organism;

import static com.almasb.fxgl.core.math.FXGLMath.random;

public class Plant extends Organism {
    public Plant(String emoji, int strength, Vec2 position) {
        super(emoji, strength, 0, position);
    }

    @Override
    public void action() {
        increaseAge();
        if(random(0,100)<95)
            return;
        Vec2[] allowedMoves = world.getAllowedMoves();
        for(int move = random(0,allowedMoves.length-1); move<=8; move++){
            if(!world.isInBounds(getPosition().add(allowedMoves[move%world.getAllowedMoves().length])))
                continue;
            if(world.getOrganism(getPosition().add(allowedMoves[move%world.getAllowedMoves().length]))==null){
                try {

                    world.addOrganism(this.getClass().getCanonicalName(), getPosition().add(allowedMoves[move % world.getAllowedMoves().length]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }

    @Override
    public void collision(Organism other) {
        if(this.getClass().equals(other.getClass()))
            return;
        if(getStrength()>=other.getStrength()){
            world.log(this.getClass().getSimpleName()+" has killed "+other.getClass().getSimpleName()+"!");
            other.death();
        } else {
            world.log(this.getClass().getSimpleName()+" has been killed by "+other.getClass().getSimpleName()+"!");
            this.death();
        }
    }
}
