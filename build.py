import os
package_cmd = [
    "mvn",
    "package"
]

runtime_cmd = [
    "mvn",
    "javafx:jlink"
]

install_cmd = [
    "jpackage",
    "-t app-image",
    "--input target/artifacts",
    "--main-jar fifteenp.jar",
    "--main-class id.ac.itb.stei.informatika.fifteenp.Application",
    "--runtime-image target/image",
    "--dest bin",
    "--name fifteenp"
]

os.system(" ".join(package_cmd))
os.system(" ".join(runtime_cmd))
os.system(" ".join(install_cmd))
