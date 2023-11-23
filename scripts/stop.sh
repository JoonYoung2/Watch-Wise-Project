#!/usr/bin/env bash

PROJECT_ROOT="/home/ubuntu/app"
WAR_FILE="$PROJECT_ROOT/spring-webapp.war"
WAR_FILE2="$PROJECT_ROOT/spring-webapp2.war"

DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

# 현재 구동 중인 애플리케이션 pid 확인
CURRENT_PID=$(pgrep -f $WAR_FILE)
CURRENT_PID2=$(pgrep -f $WAR_FILE2)

# 프로세스가 켜져 있으면 종료
if [ -z $CURRENT_PID ]; then
  echo "$TIME_NOW > 현재 실행중인 애플리케이션이 없습니다" >> $DEPLOY_LOG
else
  echo "$TIME_NOW > 실행중인 $CURRENT_PID 애플리케이션 종료 " >> $DEPLOY_LOG
  echo "$TIME_NOW > 실행중인 $CURRENT_PID2 애플리케이션 종료 " >> $DEPLOY_LOG
  kill -15 $CURRENT_PID
fi