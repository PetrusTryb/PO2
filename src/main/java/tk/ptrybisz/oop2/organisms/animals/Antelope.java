package tk.ptrybisz.oop2.organisms.animals;

import com.almasb.fxgl.core.math.Vec2;

import static com.almasb.fxgl.core.math.FXGLMath.random;

public class Antelope extends Animal{
    public Antelope(Vec2 position) {
        super("🦌", 4, 4, position);
    }

    @Override
    public void action() {
        super.action();
        super.action();
    }

    @Override
    public boolean hasEscaped(){
        if(random(0,1)==0)
            return false;
        Vec2[] allowedMoves = world.getAllowedMoves();
        int move = 0;
        while(!world.isInBounds(getPosition().add(allowedMoves[move]))||world.getOrganism(getPosition().add(allowedMoves[move]))!=null){
            move++;
            if(move==allowedMoves.length) {
                world.log(this.getClass().getSimpleName() + "Failed to escape!");
                return false;
            }
        }
        world.log(this.getClass().getSimpleName()+" has escaped!");
        return true;
    }
}
