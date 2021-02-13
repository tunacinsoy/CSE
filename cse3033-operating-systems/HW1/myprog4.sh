#!/bin/bash

#
# The shell script changes the numbers to text for each number in between 0-9, inclusive.
# It must should take one argument.
#

if [ $# -eq 1 ]
then
	echo `sed 's/0/zero/g' $1` > $1
	echo `sed 's/1/one/g' $1` > $1
	echo `sed 's/2/two/g' $1` > $1
	echo `sed 's/3/three/g' $1` > $1
	echo `sed 's/4/four/g' $1` > $1
	echo `sed 's/5/five/g' $1` > $1
	echo `sed 's/6/six/g' $1` > $1
	echo `sed 's/7/seven/g' $1` > $1
	echo `sed 's/8/eight/g' $1` > $1
	echo `sed 's/9/nine/g' $1` > $1
else # If there is no argument, exit program with the error code 1
	echo "Please enter the file name as an argument"
	exit 1
fi



exit 0
