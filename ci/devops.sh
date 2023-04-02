#!/bin/bash
set -e

CI_PATH=$(dirname $(realpath ${BASH_SOURCE[0]}))
DOCKER_PATH=${CI_PATH%/docker*}/docker

if [ ! -d "$DOCKER_PATH" ] ; then
 echo Failed to location the source of Docker
 exit 1
fi

if [ -z "$1" ]; then
  echo "No remote docker host provide"
  exit 1
fi

source $DOCKER_PATH/devops.env

if [ -z "$WORKSPACE" ]; then
  echo "Remote workspace not provide"
  exit 1
fi

echo -----------------------------------------------------------------
echo ======= DevOps ==================================================
echo ======= Build [ $PROJECT_NAME ] to remote host: [ $1 ]
echo ======= Workspace: [ $WORKSPACE ]
echo -----------------------------------------------------------------

cd $CI_PATH/../
REMOTE_HOST=$1

## Transfer to remote
ssh $REMOTE_HOST "mkdir -p $BUILD_DIR/docker"
scp $DOCKER_PATH/Dockerfile \
    $DOCKER_PATH/docker-entrypoint.sh \
    $DOCKER_PATH/devops.env \
    $REMOTE_HOST:$BUILD_DIR/docker

## create workspace for docker volume
## chown for docker access with accout 'jetty', uid: 999.
echo -e "\033[32mCreate workspace on remote host.\033[0m"
echo -e "\033[33m If prompted, you'll need to enter your sudo password\033[0m"

## store docker-compose.yml in WORKSPACE for management.
## Devops special
scp $DOCKER_PATH/devops.env $REMOTE_HOST:$WORKSPACE/.env
scp $DOCKER_PATH/docker-compose.yml \
    $DOCKER_PATH/*.properties \
    $REMOTE_HOST:$WORKSPACE

## Build project
mvn clean package
scp $CI_PATH/../server/target/risetek.gwt.gwtp-server-1.0-SNAPSHOT.war $REMOTE_HOST:$BUILD_DIR/docker/ROOT.war

## Build new Docker
echo -e "\033[32mBuild docker image on remote host.\033[0m"
echo -e "\033[33m If prompted, you'll need to enter your sudo password\033[0m"
ssh -t $REMOTE_HOST docker build $BUILD_DIR/docker/ -f $BUILD_DIR/docker/$DOCKER_FILE -t $DOCKER_IMAGE_PREFIX/$PROJECT_NAME
## clean up
ssh $REMOTE_HOST "rm -rf $BUILD_DIR"
## This will clean all datas
## ssh $REMOTE_HOST rm -rf $WORKSPACE/

## Down old Docker and Up new Docker
## change dir to WORKSPACE for .env file.
echo -e "\033[32mShutdown old docker and bring new one on remote host.\033[0m"
echo -e "\033[33m If prompted, you'll need to enter your sudo password\033[0m"
ssh -t $REMOTE_HOST "sudo mkdir -p $WORKSPACE/.database && sudo mkdir -p $WORKSPACE/config && sudo chown -R 999 $WORKSPACE/.database && sudo chown -R 999 $WORKSPACE/config" \
                    "&& cd $WORKSPACE && sudo docker-compose -f $WORKSPACE/docker-compose.yml down || true && sudo docker-compose -f $WORKSPACE/docker-compose.yml up -d"
