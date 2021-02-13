#!/bin/bash

#
# This program finds all the files whose name obeys the wildcard and copy them into a directory named
# copied. Program can take 2 arguments: a wildcard and a recursive argument "-R". 
# Wildcard is necessary argument but recursive argument can be optional.
#

wildcard=""


# Check whether satisfying amount of arguments entered
# If it does not satisfy the condition, exit program with error code 1.
if [ $# -lt 1 ] || [ $# -gt 2 ]
then
	echo "Number of arguments not satisfied."
	exit 1
fi

# Check whether an option given or not.
# If the option argument is entered, copy the files recursively.
if [ "$1" == "-R" ]
then
    current_directory=$PWD  # Store the current directory path into a variable
							# to go back

    # Delete " sign at the beginning and ending of wildcard
    wildcard=${2#\"}
    wildcard=${wildcard%\"}

    paths=`find -type d -not -path '*/\.*'`   # assign all folders name to a variable without
                                              # hidden folders.

    for path in $paths
    do
        if [[ "$path" !=  *"copied"* ]]       # check whether path string consisting of copied string
        then
            cd $path
            files=`ls $wildcard 2>/dev/null` # Store the all paths that obeys the whildcard without error
            
            file_count=`ls $wildcard 2>/dev/null | wc -l`

            # if there is no file that obeys the wildcard, exit program with error code 1
            if [ $file_count -eq 0 ]; then
                echo "There is no file that obeys the wildcard under $PWD"
            fi

            for file in $files
            do
                # if copied folder exist, copy the file(s) into copied folder
                # otherwise create copied folder, then copy the file(s) in it
                if [ -d copied ]
                then
                    cp $file copied
                    echo "$file is copied under to $PWD/copied"
                else
                    mkdir copied
                    cp $file copied
                    echo "$file is copied under to $PWD/copied"
                fi                
            done
        fi

        cd $current_directory
        
   done
# If the opiton argument is not entered, only work on current working directory.
elif [ "$1" != "-R" ]
then
    # Delete " sign at the beginning and ending of wildcard
    wildcard=${1#\"}
    wildcard=${wildcard%\"} 

    files=`ls $wildcard 2>/dev/null`    # Store the all paths that obeys the whildcard without error

    file_count=`ls $wildcard 2>/dev/null | wc -l`

    # if there is no file that obeys the wildcard, exit program with error code 1
    if [ $file_count -eq 0 ]; then
        echo "There is no file that obeys the wildcard under $PWD"
    fi

    for file in $files
    do
        # if copied folder exist, copy the file(s) into copied folder
        # otherwise create copied folder, then copy the file(s) in it
        if [ -d copied ]
        then
            cp $file copied
            echo "$file is copied under to $PWD/copied"
        else
            mkdir copied
            cp $file copied
            echo "$file is copied under to $PWD/copied"
        fi
    done

fi

exit 0