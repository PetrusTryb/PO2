module tk.ptrybisz.oop2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens tk.ptrybisz.oop2 to javafx.fxml;
    exports tk.ptrybisz.oop2;
    exports tk.ptrybisz.oop2.organisms;
    exports tk.ptrybisz.oop2.organisms.animals;
    exports tk.ptrybisz.oop2.organisms.plants;
}