#!/bin/bash

#
# The shell script finds the oldest file under the current working directory,
# when it runs without argument.
# If it takes the path name as an argument, it finds the oldest file under given path.
#

# If the number of arguments is equal to zero, works under current directory
if [ $# -eq 0 ]; then
	paths=$(ls -tr) # Store the paths and directories path name into a variable called
					# by ascending order date and time

	first_paths_count=$(ls -l | wc -l) 	# Store the count of paths into a variable
										# before the deletion process
	for path in $paths; do
		if [ -f $path ]; then 	# Remove the file and quit loop, if it is a file
			rm -i $path         # Remove file
			break
		fi
	done

	last_paths_count=$(ls -l | wc -l)	# Store the count of paths int a variable
										# after the deletion process

	# Print messages
	if [ $last_paths_count -lt $first_paths_count ]; then
		echo "1 file is deleted"
	else
		echo "Deletion process is cancelled"
	fi

elif

	[ $# -eq 1 ] # If the number of arguments is equal to 1, works under given path directory
then
	current_directory=$PWD	# Stode the current directory path into a variable
							# to go back

	if [ -d $1 ]; then	# If the first agument is a directory, go to the directroy path	
		cd $1
		paths=$(ls -tr)	# Store the paths and directories path name into a variable called
						# by ascending order date and time

		first_paths_count=$(ls -l | wc -l)	# Store the count of paths into a variable
											# before the deletion process

		for file in $paths; do
			if [ -f $file ]; then	# Remove the file and quit loop, if it is a file
				rm -i $file			# Remove file
				break
			fi
		done

		last_paths_count=$(ls -l | wc -l) 	# Store the count of paths int a variable
											# after the deletion process
		# Print messages
		if [ $last_paths_count -lt $first_paths_count ]; then
			echo "1 file is deleted"
		else
			echo "Deletion process is cancelled"
		fi
	else
		echo "Please enter the directory path that exists"
	fi
	cd $current_directory
else # If the number of argumens is greater than 1, exit program with the error code 1
	echo "There should be at most one argument"
	exit 1
fi

exit 0
