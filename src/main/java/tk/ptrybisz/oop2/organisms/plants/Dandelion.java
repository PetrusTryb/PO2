package tk.ptrybisz.oop2.organisms.plants;

import com.almasb.fxgl.core.math.Vec2;

public class Dandelion extends Plant{
    public Dandelion(Vec2 position){
        super("🌼", 0, position);
    }

    @Override
    public void action(){
        super.action();
        super.action();
        super.action();
    }
}
