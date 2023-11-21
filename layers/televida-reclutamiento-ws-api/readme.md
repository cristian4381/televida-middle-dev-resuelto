# Compilar y construir los wars para produccion y tomcat en docker local
mvn clean install tomcat7:redeploy -Dmaven.test.skip=true
