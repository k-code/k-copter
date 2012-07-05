@rem @SET OLDPATH=%PATH%
@rem SET PATH=%PATH%;lib
javac -classpath . com\centralnexus\test\WindowTest.java
@rem java -classpath . com.centralnexus.test.WindowTest
@rem java -classpath .;Joystick.jar com.centralnexus.test.WindowTest -d:0.25 -d2:0.1 -i:60
@rem java -Xprof -classpath .;Joystick.jar com.centralnexus.test.WindowTest -d:0.25 -d2:0.1 -i:0
@rem java -classpath .;Joystick.jar com.centralnexus.test.WindowTest -d:0.25 -d2:0.1 -i:60
java -Djava.library.path=lib -classpath .;Joystick.jar com.centralnexus.test.WindowTest -d:0.25 -d2:0.1 -i:60
@rem @SET PATH=%OLDPATH%