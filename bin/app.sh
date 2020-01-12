#!/usr/bin/env bash

APP_MAINCLASS=com.youngc.pipeline.Application

JAVA_HOME=~/jdk1.8.0_144


JAVA_OPTS="-server -Xms512m -Xmx2048m -Xss512k"


APP_HOME=$(cd "`dirname $0`/.." && pwd)


CLASSPATH=$APP_HOME/config:$APP_HOME/classes
for i in "$APP_HOME"/lib/*.jar; do
    CLASSPATH="$CLASSPATH":"$i"
done

#初始化pid
pid=0

#判断程序是否已启动
checkPid() {
    JPS_RESULT=`"$JAVA_HOME"/bin/jps -l | grep "$APP_MAINCLASS"`

    if [ -n "$JPS_RESULT" ]; then
        pid=`echo "$JPS_RESULT" | awk '{print $1}'`
    else
        pid=0
    fi
}

#启动程序
start() {
    checkPid

    if [ "$pid" -ne 0 ]; then
        echo "warning: $APP_MAINCLASS already started! (pid=$pid)"
        return 0
    else
        echo -n "Starting $APP_MAINCLASS ..."
        nohup $JAVA_HOME/bin/java $JAVA_OPTS -classpath $CLASSPATH $APP_MAINCLASS >$APP_HOME/run.log 2>&1 &
        checkPid
        if [ "$pid" -ne 0 ]; then
            echo "(pid=$pid) [OK]"
            return 0
        else
            echo "[Failed]"
            return 1
        fi
    fi
}

#停止程序
stop() {

    checkPid

    if [ "$pid" -ne 0 ]; then
        echo -n "Stopping $APP_MAINCLASS ...(pid=$pid) "
        kill -9 "$pid"
        if [ $? -eq 0 ]; then
            for i in 1 2 3;
            do
                sleep 1
                checkPid
                if [ "$pid" -eq 0 ]; then
                    break
                fi
            done;

            if [ "$pid" -eq 0 ]; then
                echo "[OK]"
                return 0
            else
                echo "[Failed]"
                return 1
            fi

        else
            echo "[Failed]"
            return 1
        fi
    else
        echo "warning: $APP_MAINCLASS is not running"
        return 0
    fi
}

#检查程序运行状态
status() {
   checkPid

   if [ "$pid" -ne 0 ];  then
      echo "$APP_MAINCLASS is running! (pid=$pid)"
   else
      echo "$APP_MAINCLASS is not running"
   fi
}


case "$1" in
    'start')
        start
        ;;
    'stop')
        stop
        ;;
    'restart')
        stop && start
        ;;
    'status')
        status
        ;;
    *)
        echo "Usage: $0 {start|stop|restart|status}"
        exit 1
esac