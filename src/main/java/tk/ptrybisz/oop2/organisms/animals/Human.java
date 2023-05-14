package tk.ptrybisz.oop2.organisms.animals;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;

public class Human extends Animal{
    Vec2 nextMove;
    int specialAbility = -5;
    public Human(Vec2 position) {
        super("🚹",5, 4, position);
        nextMove = new Vec2(0,0);
    }
    public void setDirection(int x, int y) {
        if(!world.isInBounds(this.getPosition()))
            return;
        nextMove = new Vec2(x,y);
    }

    public void action(){
        increaseAge();
        if(nextMove.x == 0 && nextMove.y == 0)
            return;
        int speed = 1;
        if(specialAbility > 2)
            speed = 2;
        else if (specialAbility > 0)
            speed = FXGL.random(1,2);
        if(specialAbility> -5)
            specialAbility--;
        setPosition(getPosition().add(nextMove.mul(speed)));
        setDirection(0,0);
    }

    public void activateSpecialAbility() {
        if(specialAbility != -5)
            return;
        specialAbility = 5;
    }
}
