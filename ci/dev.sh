#!/bin/bash
set -e
mvn clean package
case `(uname -s || echo unknown) 2>/dev/null` in
  Linux | linux | GNU | GNU/*) BASH=bash;;
  MINGW*) BASH=git-bash.exe;;
  *)      echo not yet supported platform; exit 1;;
esac
$BASH -c "mvn -q jetty:run -Dhsqlmem -pl server -am -Penv-dev" &
$BASH -c "mvn gwt:codeserver -pl client -am" &

