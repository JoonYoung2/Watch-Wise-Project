#!/usr/bin/env bash

PROJECT_ROOT="/home/ubuntu/app"
WAR_FILE="$PROJECT_ROOT/spring-webapp.war"
WAR_FILE2="$PROJECT_ROOT/spring-webapp2.war"

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

# build 파일 복사
echo "$TIME_NOW > $WAR_FILE 파일 복사" >> $DEPLOY_LOG
echo "$TIME_NOW > $WAR_FILE2 파일 복사" >> $DEPLOY_LOG
cp $PROJECT_ROOT/project/build/libs/*.war $WAR_FILE
cp $PROJECT_ROOT/restApi/api/build/libs/*.war $WAR_FILE2

# jar 파일 실행
echo "$TIME_NOW > $WAR_FILE 파일 실행" >> $DEPLOY_LOG
echo "$TIME_NOW > $WAR_FILE2 파일 실행" >> $DEPLOY_LOG
nohup java -jar $WAR_FILE > $APP_LOG 2> $ERROR_LOG &
nohup java -jar $WAR_FILE2 > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $WAR_FILE)
CURRENT_PID2=$(pgrep -f $WAR_FILE2)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID2 입니다." >> $DEPLOY_LOG