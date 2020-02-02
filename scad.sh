## Shall script to run scad

export SCAD_USERS_PATH="src/main/resources/security/users.properties"
export SCAD_ROLES_PATH="src/main/resources/security/roles.properties"
export SCAD_MYSQL_ROOT_PASS="abcd1234"

HTTP_PORT=8080

## Set the debugger HTTP port 1000 higher, so as to remain unique.
let "DEBUG_PORT=HTTP_PORT+1000"

echo " "
echo "*****************************************************************************"
echo " "
echo "Starting 'SCAD Services' on HTTP_PORT=${HTTP_PORT}, DEBUG_PORT=${DEBUG_PORT} ..."
echo " "
echo "*****************************************************************************"
echo " "

JAVA_OPTS="${JAVA_OPTS} -Djava.net.preferIPv4Stack=true"
JAVA_OPTS="${JAVA_OPTS} -Dthorntail.http.port=${HTTP_PORT}" 
JAVA_OPTS="${JAVA_OPTS} -agentlib:jdwp=transport=dt_socket,address=${DEBUG_PORT},server=y,suspend=n"

mvn clean install

java ${JAVA_OPTS} -jar target/scadservices-thorntail.jar  -S local


