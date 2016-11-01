#!/bin/sh

ppath=$(pwd)
antlr=$ppath'/lib/antlr-3.5.2-complete.jar'
echo $antlr

# Tests if the user has given an argument
if [ $# -lt 1 ]
then
  echo "syntax: ./run.sh <test_file.vsl>"
  exit 0
fi

java -cp $antlr:./src VslComp $1

