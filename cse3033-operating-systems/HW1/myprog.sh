#!/bin/bash
# Script to create simple menus and take action according to that selected
# menu item
#
while :
do
   clear
   echo "-------------------------------------"
   echo "             Main Menu "
   echo "-------------------------------------"
   echo "   1. Create histogram"
   echo "   2. Encryption"
   echo "   3. Delete oldest"
   echo "   4. Convert numbers"
   echo "   5. Copy files"
   echo "   6. Exit\n"
   echo -n "Enter your menu choice [1-6]: "
   read -n 1 yourch
   clear
   case $yourch in
      1) echo "-------------------------------------"
         echo "        1. Create Histogram"
         echo "-------------------------------------"
         echo -n "Please enter the file name that includes number data: "
         read fileName
         echo -e "\n"
         ./myprog1.sh $fileName;;
 	   2) echo "-------------------------------------"
         echo "        2. Encryption"
         echo "-------------------------------------" 
         echo -n "Please enter string and encryption number: "
         read str
         ./myprog2.sh $str;;
      3) echo "-------------------------------------"
         echo "        3. Delete oldest"
         echo "-------------------------------------"
         echo -n "Please enter optional path name (if it exists): "
         read pathName
         ./myprog3.sh $pathName;;
      4) echo "-------------------------------------"
         echo "        4. Convert numbers"
         echo "-------------------------------------"
         echo -n "Please enter the file name to convert number to string: "
         read fileName
         ./myprog4.sh $fileName;;
      5) echo "-------------------------------------"
         echo "        5. Copy files"
         echo "-------------------------------------"
         echo -n "Please enter optional recursive flag -R and wildcard argument respectively: "
         read parameter
         ./myprog5.sh $parameter;;
      6) exit 0 ;;
      *) echo "Out of range!!";;
   esac
   echo -n "Press any key to go back to main menu... "
   read -n 1
done

exit 0
