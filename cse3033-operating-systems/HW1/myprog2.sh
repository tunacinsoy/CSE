#!/bin/bash

#
# The program takes a two command line argument, first one being a string and
# the second one being a number. The number has to have a length of either 1 or same as the
# string. It converts the input string into a ciphered one using the given number. For each
# letter in the string, it has to find another letter in English alphabet advacing over the
# alphabet corresponding digit times, For example
#
# $ ./myprog2.sh apple 12345
# brspj
#


# If the number of parameters is less than or greater than 2, exit program with error code 1
if [ $# -lt 2 ] || [ $# -gt 2 ] 
then
	echo "The number of parameters should be two."
	exit 1
else 
	# If the first parameter includes the numeric character, exit program with the error code 1
	# otherwise assing the first parameter value to word variable
	if echo $1 | egrep -q '^[A-Za-z]+$'
	then
		word=$1
	else
		echo "First argument should not include numeric character"
		exit 1
	fi

	# If the second parameter includes the numeric character, assign the value of it to
	# encyription number variable,
	# otherwise exit program with the error code 1
	if echo $2 | egrep -q '^[0-9]+$'
	then
		encNum=$2
	else
		echo "Second argument should be a number"
		exit 1
	fi
fi

lLetters=({a..z})
uLetters=({A..Z})
result=""

# Check whether lenght of word is euqal to lenght of encryption number
if [ ${#word} -eq ${#encNum} ] 
then
	for (( i=0; i<${#word}; i++)); do
		asc=`echo "${word:$i:1}" | tr -d "\n" | od -An -t uC` # Conver the character to  the ascii code

		# Convert the characters of second argument to interger
		eNumAsc=`echo "${encNum:$i:1}" | tr -d "\n" | od -An -t uC` # Conver the character to  the ascii code
		eNumAsc=`expr $eNumAsc - 48`
		
		# Find the encrypted character
		if [ $asc -ge 65 ] && [ $asc -le 90 ] 
		then
			asc=`expr $asc + $eNumAsc`
			if [ $asc -gt 90 ] 
			then
				asc=`expr $asc % 90`
				asc=`expr $asc + 64`
			fi
			result+=`echo "${uLetters[$asc-65]}"`
		elif [ $asc -ge 97 ] && [ $asc -le 122 ]
		then
			asc=`expr $asc + $eNumAsc`
			if [ $asc -gt 122 ] 
			then
				asc=`expr $asc % 122`
				asc=`expr $asc + 96`
			fi
			result+=`echo "${lLetters[$asc-97]}"`
		fi		
	done
elif [ ${#encNum} -eq 1 ]
then
	# Convert the characters of second argument to interger
	eNumAsc=`echo "${encNum}" | tr -d "\n" | od -An -t uC` # Conver the character to  the ascii code
	eNumAsc=`expr $eNumAsc - 48`

	for (( i=0; i<${#word}; i++)); do
		asc=`echo "${word:$i:1}" | tr -d "\n" | od -An -t uC` # Conver the character to  the ascii code
		
		# Find the ascii code new character
		if [ $asc -ge 65 ] && [ $asc -le 90 ]
		then
			asc=`expr $asc + $eNumAsc`
			if [ $asc -gt 90 ] 
			then
				asc=`expr $asc % 90`
				asc=`expr $asc + 64`
			fi
			result+=`echo "${uLetters[$asc-65]}"`
		elif [ $asc -ge 97 ] && [ $asc -le 122 ]
		then
			asc=`expr $asc + $eNumAsc`
			if [ $asc -gt 122 ] 
			then
				asc=`expr $asc % 122`
				asc=`expr $asc + 96`
			fi
			result+=`echo "${lLetters[$asc-97]}"`
		fi
	done
else
	echo "Input format is incorrect"
	exit 1
fi

echo "Encrypted string: $result"

exit 0
