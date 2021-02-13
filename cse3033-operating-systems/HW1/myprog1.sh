#!/bin/bash

#
# This program creates a histogram that is representation of the distribution of 
# numerical data. Numeric data is read from a file given as an argument.
#

# Check whether there is an argument.
# Exit the program, if there is no argument
if [ $# -eq 0 ]
then
	echo "The file name is required as an argument."
	exit 1 
else 
    # If there is no file, exit with the error code 1
	# otherwise assign argument 1 to file variable
	if [ -e $1 ] 
	then
		file=$1
	else
		echo "File does not exist"
		exit 1
	fi 
fi


# Print the histodgram
for (( i=0; i < 10; i++ ))
do
# Initialize the repetition count and star string 
repetitionCount=0 
star=""
    # Read number from file
	while read number
	do
        # If the number is greater than or equal to 0, increase the repetition count for it
		if [ $number -ge 0 ]
		then
            # If the number is greater than 9,  exit program with the error code 1
            if [ $number -gt 9 ]
            then
                echo "The input file includes invalid values."
			    exit 1
			elif [ $number -eq $i ]
			then
				repetitionCount=`expr $repetitionCount + 1`
			fi
        # If the number is less than 0,  exit program with the error code 1
		elif [ $number -lt 0 ]
		then
			echo "The input file includes invalid values."
			exit 1			
		fi
	done < $file

    # Print the histodgram
	for (( j=0; j<repetitionCount; j++))
	do
		star+="*"
	done
    echo "$i $star"
done

exit 0