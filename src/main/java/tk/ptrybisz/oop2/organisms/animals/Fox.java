package tk.ptrybisz.oop2.organisms.animals;

import com.almasb.fxgl.core.math.Vec2;

import static com.almasb.fxgl.core.math.FXGLMath.random;

public class Fox extends Animal{
    public Fox(Vec2 position) {
        super("🦊", 3, 7, position);
    }

    @Override
    public void action() {
        increaseAge();
        Vec2[] allowedMoves = world.getAllowedMoves();
        for(int move = random(0,allowedMoves.length-1); move<=8; move++){
            if(!world.isInBounds(getPosition().add(allowedMoves[move%world.getAllowedMoves().length])))
                continue;
            if(world.getOrganism(getPosition().add(allowedMoves[move%world.getAllowedMoves().length]))==null){
                setPosition(getPosition().add(allowedMoves[move%world.getAllowedMoves().length]));
                return;
            }
            if(world.getOrganism(getPosition().add(allowedMoves[move%world.getAllowedMoves().length])).getStrength()<=getStrength()){
                setPosition(getPosition().add(allowedMoves[move%world.getAllowedMoves().length]));
                return;
            }
        }
    }
}
