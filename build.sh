mvn package && mvn javafx:jlink && \
jpackage -t app-image --input target/artifacts --main-jar fifteenp.jar \
--main-class id.ac.itb.stei.informatika.fifteenp.Application \
--runtime-image target/image --dest bin --name fifteenp