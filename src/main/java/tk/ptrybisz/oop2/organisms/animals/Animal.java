package tk.ptrybisz.oop2.organisms.animals;

import com.almasb.fxgl.core.math.Vec2;
import tk.ptrybisz.oop2.organisms.Organism;

import static com.almasb.fxgl.core.math.FXGLMath.random;

public class Animal extends Organism {
    public Animal(String emoji, int strength, int initiative, Vec2 position) {
        super(emoji, strength, initiative, position);
    }

    public void action() {
        increaseAge();
        Vec2[] allowedMoves = world.getAllowedMoves();
        int move = random(0,allowedMoves.length-1);
        while(!setPosition(getPosition().add(allowedMoves[move]))){
            move++;
            move%=allowedMoves.length;
        }
    }

    public void collision(Organism other) {
        if(this.getClass().equals(other.getClass())){
            if(this.getAge()<2||other.getAge()<2)
                return;
            try {
                Vec2[] allowedMoves = world.getAllowedMoves();
                int move = 0;
                while(!world.isInBounds(getPosition().add(allowedMoves[move]))||world.getOrganism(getPosition().add(allowedMoves[move]))!=null){
                    move++;
                    if(move==allowedMoves.length) {
                        world.log("No place for new " + this.getClass().getSimpleName() + "!");
                        return;
                    }
                }
                undoMove();
                world.addOrganism(this.getClass().getCanonicalName(), getPosition().add(allowedMoves[move]));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            world.log(this.getClass().getSimpleName()+" has been born!");
            return;
        }
        if(this.hasEscaped()||other.hasEscaped()) {
            world.log("Fight has been avoided!");
            return;
        }
        if(this.getStrength()>=other.getStrength()){
            world.log(this.getClass().getSimpleName()+" has killed "+other.getClass().getSimpleName()+"!");
            other.death();
        } else {
            world.log(this.getClass().getSimpleName()+" has been killed by "+other.getClass().getSimpleName()+"!");
            this.death();
        }
    }
}
