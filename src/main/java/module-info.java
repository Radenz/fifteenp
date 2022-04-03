module id.ac.itb.stei.informatika.fifteenp {
    requires javafx.controls;
    requires javafx.fxml;

    opens id.ac.itb.stei.informatika.fifteenp to javafx.fxml;
    exports id.ac.itb.stei.informatika.fifteenp;
}