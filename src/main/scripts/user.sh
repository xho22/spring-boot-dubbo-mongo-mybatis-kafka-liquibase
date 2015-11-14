#!/bin/sh
JARFile="user.jar"
PIDFile="app.pid"
SPRING_OPTS="-DLOG_FILE=application.log"
function check_if_pid_file_exists {
if [ ! -f $PIDFile ]
then
echo "PID file not found: $PIDFile"
exit 1
fi
}
function check_if_process_is_running {
if ps -p $(print_process) > /dev/null
then
return 0
else
return 1
fi
}
function print_process {
echo $(<"$PIDFile")
}
case "$1" in
status)
check_if_pid_file_exists
if check_if_process_is_running
then
echo $(print_process)" is running"
else
echo "Process not running: $(print_process)"
fi
;;
stop)
check_if_pid_file_exists
if ! check_if_process_is_running
then
echo "Process $(print_process) already stopped"
exit 0
fi
kill -TERM $(print_process)
echo -ne "Waiting for process to stop"
NOT_KILLED=1
for i in {1..20}; do
if check_if_process_is_running
then
echo -ne "."
sleep 1
else
NOT_KILLED=0
fi
done
echo
if [ $NOT_KILLED = 1 ]
then
echo "Cannot kill process $(print_process)"
exit 1
fi
echo "Process stopped"
;;
start)
if [ -f $PIDFile ] && check_if_process_is_running
then
echo "Process $(print_process) already running"
exit 1
fi
nohup java $SPRING_OPTS -jar $JARFile &
echo "Process started"
;;
restart)
$0 stop
if [ $? = 1 ]
then
exit 1
fi
$0 start
;;
*)
echo "Usage: $0 {start|stop|restart|status}"
exit 1
esac
exit 0