module id.ac.itb.stei.informatika.fifteenp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens id.ac.itb.stei.informatika.fifteenp to javafx.fxml;
    exports id.ac.itb.stei.informatika.fifteenp;
}