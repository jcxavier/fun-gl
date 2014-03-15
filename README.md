fun-gl
======

Small Java-based Android OpenGL ES 2.0 engine made for fun.

Requires Java 7 and Maven 3.1.1.

The sample project requires the engine to be built first. One should run the command `mvn clean install` in the /engine folder before actually attempting to compile the sample.

To compile the sample project, run `mvn clean install`. If you want to deploy the resulting apk on a device or emulator and run it, afterwards run `mvn android:deploy android:run`.

Concatenating both commands and running `mvn clean install android:deploy android:run` would also work.
