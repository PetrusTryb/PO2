package tk.ptrybisz.oop2.organisms.animals;

import com.almasb.fxgl.core.math.Vec2;
import tk.ptrybisz.oop2.organisms.Organism;

import static com.almasb.fxgl.core.math.FXGLMath.random;

public class Turtle extends Animal{
    public Turtle(Vec2 position) {
        super("🐢", 2, 1, position);
    }

    @Override
    public void action() {
        int move = random(0,1);
        if(move==0)
            return;
        super.action();
    }

    @Override
    public void collision(Organism other) {
        if(other.getStrength()<5 && this.getClass()!=other.getClass()){
            world.log("Fight has been avoided!");
            other.undoMove();
            return;
        }
        super.collision(other);
    }

}
