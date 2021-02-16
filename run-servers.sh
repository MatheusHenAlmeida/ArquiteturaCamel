konsole --hold -e "mvn -f ./atendente-server/pom.xml spring-boot:run" &
konsole --hold -e "mvn -f ./cliente-server/pom.xml spring-boot:run" &
konsole --hold -e "mvn -f ./prestador-server/pom.xml spring-boot:run" &
wait