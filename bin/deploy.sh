#!/usr/bin/env bash

#目前只支持RSYNC方式
METHOD=RSYNC

SERVER=192.168.1.14
PORT=22
DEPLOY_USER="root"
DEPLOY_DIR="~"

PROJECT=$1
APP_HOME=$(cd "`dirname $0`/.." && pwd)

LOCAL="$APP_HOME/target/$PROJECT"
REMOTE="$DEPLOY_DIR/$PROJECT"

RSYNC_OPS="-avz --delete"


if [ "$2"x != ""x ]; then
    LOCAL="$LOCAL/$2"
    REMOTE="$REMOTE/$2"
fi

deployByRsync() {
    echo "$RSYNC_OPS"
    echo "Deploying $PROJECT ..."
    rsync -e "ssh -p $PORT" $RSYNC_OPS "$LOCAL/" "$DEPLOY_USER@$SERVER:$REMOTE"
    if [ $? -eq 0 ]; then
        echo "$PROJECT deployed"
    else
        echo "$PROJECT deploy failed"
    fi
}

case "$METHOD" in
"RSYNC")
  deployByRsync ;;
esac
