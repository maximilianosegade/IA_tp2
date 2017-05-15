LIBS_PATH=../target/lib
MAIN_PATH=../target
#CP="$LIBS_PATH/commons-cli-1.0.jar:$LIBS_PATH/commons-codec-1.3.jar:$LIBS_PATH/commons-lang-2.3.jar:$LIBS_PATH/commons-logging-1.0.jar:$LIBS_PATH/commons-math-2.2.jar:$LIBS_PATH/jcgrid-0.05.jar:$LIBS_PATH/jgap-3.6.2.jar:$LIBS_PATH/junit-3.8.1.jar:$LIBS_PATH/junit-addons-1.4.jar:$LIBS_PATH/log4j-1.2.9.jar:$LIBS_PATH/logback-classic-1.2.3.jar:$LIBS_PATH/logback-core-1.2.3.jar:$LIBS_PATH/slf4j-api-1.7.25.jar:$LIBS_PATH/TableLayout-20050920.jar:$LIBS_PATH/trove4j-2.0.2.jar:$LIBS_PATH/xercesImpl-2.6.2.jar:$LIBS_PATH/xmlParserAPIs-2.6.2.jar:$LIBS_PATH/xpp3_min-1.1.3.4.O.jar:$LIBS_PATH/xpp3-1.1.3.4.O.jar:$LIBS_PATH/xstream-1.2.2.jar:$MAIN_PATH/tp2-0.0.1-SNAPSHOT.jar"
CP="$MAIN_PATH/tp2-0.0.1-SNAPSHOT.jar"
java -cp "$CP" tp2.Main $1 $2 $3 $4