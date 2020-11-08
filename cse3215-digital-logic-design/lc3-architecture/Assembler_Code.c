/*
    This program converts given Instruction Set Architecture to binary, then to hexadecimal.
    Hexadecimal outputs will be used in Logisim design as input parameters.
    
    Implemented by Tuna Cinsoy, Enes Gündüz, Muhammed Eroğlu
    18.11.19
*/
# include <stdio.h>
# include <string.h>
# include <locale.h>
# include <math.h>
#define _GNU_SOURCE
char* toBinary(int fifthIndex, int sixthIndex);
char* twosComplement(int decimal,int bit);
char* binaryToHex(char * string);
int main()
{
    FILE* file;
    FILE* file2;
    //input file has to be entered this point
    char directory[50] = "Input_Assembler.txt";
    char directory2[50] = "Output_Assembler.txt";
    file =fopen(directory,"r");
     file2 = fopen(directory2,"w");
    while(!feof(file)){
        char string[50];
        fgets(string,50,file);
        char* binary= malloc(19);
        if(string[0] == 'A' && string[1] == 'D' && string[2] == 'D' && string[3] == 'I')
        {

            char binaryOfInstruction[5]  = "0011";
            strcpy(binary, binaryOfInstruction);
            int slideToRight = 0;
            if((string[7]<58 && string[7] > 47))
            {
                strcat(binary,toBinary(string[6], string[7]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[6]<58 && string[6] > 47))
            {
                strcat(binary,toBinary(string[6], NULL));
                //printf("%s  ",binary );
            }

            if((string[10 + slideToRight]<58 && string[10 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[10 + slideToRight - 1], string[10 + slideToRight]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[9 + slideToRight]<58 && string[9 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[9 + slideToRight], NULL));
               // printf("%s  ",binary );
            }
            if((string[12 + slideToRight]<58 && string[12 + slideToRight] > 47))
            {
                char trivial[4] = "00";
                strcat(binary, trivial);
                strcat(binary, toBinary(string[12 + slideToRight - 1], string[12 + slideToRight]));
                printf("%s  ",binary );
            }

            else if((string[11 + slideToRight]<58 && string[11 + slideToRight] > 47))
            {
                char trivial[4] = "00";
                strcat(binary, trivial);
                strcat(binary,toBinary(string[11 + slideToRight], NULL));
                printf("%s  ",binary );
            }

        }

        else if(string[0] == 'A' && string[1] == 'D' && string[2] == 'D')
        {
            char binaryOfInstruction[5]  = "0010";
            strcpy(binary, binaryOfInstruction);
            int slideToRight = 0;
            if((string[6]<58 && string[6] > 47))
            {
                strcat(binary,toBinary(string[5], string[6]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[5]<58 && string[5] > 47))
            {
                strcat(binary,toBinary(string[5], NULL));
                //printf("%s  ",binary );
            }

            if((string[9 + slideToRight]<58 && string[9 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[9 + slideToRight - 1], string[9 + slideToRight]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[8 + slideToRight]<58 && string[8 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[8 + slideToRight], NULL));
               // printf("%s  ",binary );
            }
            if((string[12 + slideToRight]<58 && string[12 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[12 + slideToRight - 1], string[12 + slideToRight]));
                char trivial[3] = "00";
                strcat(binary,trivial);
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[11 + slideToRight]<58 && string[11 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[11 + slideToRight], NULL));

                char trivial[3] = "00";
                strcat(binary,trivial);
                printf("%s  ",binary );
            }






        }
        else if(string[0] == 'A' && string[1] == 'N' && string[2] == 'D' && string[3] == 'I')
        {
            char binaryOfInstruction[5]  = "0001";
            strcpy(binary, binaryOfInstruction);
            int slideToRight = 0;
            if((string[7]<58 && string[7] > 47))
            {
                strcat(binary,toBinary(string[6], string[7]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[6]<58 && string[6] > 47))
            {
                strcat(binary,toBinary(string[6], NULL));
                //printf("%s  ",binary );
            }

            if((string[10 + slideToRight]<58 && string[10 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[10 + slideToRight - 1], string[10 + slideToRight]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[9 + slideToRight]<58 && string[9 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[9 + slideToRight], NULL));
               // printf("%s  ",binary );
            }
            if((string[12 + slideToRight]<58 && string[12 + slideToRight] > 47))
            {
                char trivial[4] = "00";
                strcat(binary, trivial);
                strcat(binary, toBinary(string[12 + slideToRight - 1], string[12 + slideToRight]));
                printf("%s  ",binary );
            }

            else if((string[11 + slideToRight]<58 && string[11 + slideToRight] > 47))
            {
                char trivial[4] = "00";
                strcat(binary, trivial);
                strcat(binary,toBinary(string[11 + slideToRight], NULL));
                printf("%s  ",binary );
            }

        }
        else if(string[0] == 'A' && string[1] == 'N' && string[2] == 'D')
        {
            char binaryOfInstruction[5]  = "0000";
            strcpy(binary, binaryOfInstruction);
            int slideToRight = 0;
            if((string[6]<58 && string[6] > 47))
            {
                strcat(binary,toBinary(string[5], string[6]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[5]<58 && string[5] > 47))
            {
                strcat(binary,toBinary(string[5], NULL));
                //printf("%s  ",binary );
            }

            if((string[9 + slideToRight]<58 && string[9 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[9 + slideToRight - 1], string[9 + slideToRight]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[8 + slideToRight]<58 && string[8 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[8 + slideToRight], NULL));
               // printf("%s  ",binary );
            }
            if((string[12 + slideToRight]<58 && string[12 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[12 + slideToRight - 1], string[12 + slideToRight]));
                char trivial[3] = "00";
                strcat(binary,trivial);
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[11 + slideToRight]<58 && string[11 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[11 + slideToRight], NULL));

                char trivial[3] = "00";
                strcat(binary,trivial);
                printf("%s  ",binary );
            }



        }
        else if(string[0] == 'O' && string[1] == 'R' && string[2] == 'I')
        {
            char binaryOfInstruction[5]  = "0101";
            strcpy(binary, binaryOfInstruction);
            int slideToRight = 0;
            if((string[6]<58 && string[6] > 47))
            {
                strcat(binary,toBinary(string[5], string[6]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[5]<58 && string[5] > 47))
            {
                strcat(binary,toBinary(string[5], NULL));
                //printf("%s  ",binary );
            }

            if((string[9 + slideToRight]<58 && string[9 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[9 + slideToRight - 1], string[9 + slideToRight]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[8 + slideToRight]<58 && string[8 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[8 + slideToRight], NULL));
               // printf("%s  ",binary );
            }
            if((string[11 + slideToRight]<58 && string[11 + slideToRight] > 47))
            {
                char trivial[4] = "00";
                strcat(binary, trivial);
                strcat(binary, toBinary(string[11 + slideToRight - 1], string[11 + slideToRight]));
                printf("%s  ",binary );
            }

            else if((string[10 + slideToRight]<58 && string[10 + slideToRight] > 47))
            {
                char trivial[4] = "00";
                strcat(binary, trivial);
                strcat(binary,toBinary(string[10 + slideToRight], NULL));
                printf("%s  ",binary );
            }



        }
        else if(string[0] == 'O' && string[1] == 'R')
        {
            char binaryOfInstruction[5]  = "0100";
            strcpy(binary, binaryOfInstruction);
            int slideToRight = 0;
            if((string[5]<58 && string[5] > 47))
            {
                strcat(binary,toBinary(string[4], string[5]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[4]<58 && string[4] > 47))
            {
                strcat(binary,toBinary(string[4], NULL));
                //printf("%s  ",binary );
            }

            if((string[8 + slideToRight]<58 && string[8 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[8 + slideToRight - 1], string[8 + slideToRight]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[7 + slideToRight]<58 && string[7 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[7 + slideToRight], NULL));
               // printf("%s  ",binary );
            }
            if((string[11 + slideToRight]<58 && string[11 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[11 + slideToRight - 1], string[11 + slideToRight]));
                char trivial[3] = "00";
                strcat(binary,trivial);
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[10 + slideToRight]<58 && string[10 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[10 + slideToRight], NULL));

                char trivial[3] = "00";
                strcat(binary,trivial);
                printf("%s  ",binary );
            }

        }
        else if(string[0] == 'X' && string[1] == 'O' && string[2] == 'R' && string[3] == 'I')
        {

            char binaryOfInstruction[5]  = "0011";
            strcpy(binary, binaryOfInstruction);
            int slideToRight = 0;
            if((string[7]<58 && string[7] > 47))
            {
                strcat(binary,toBinary(string[6], string[7]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[6]<58 && string[6] > 47))
            {
                strcat(binary,toBinary(string[6], NULL));
                //printf("%s  ",binary );
            }

            if((string[10 + slideToRight]<58 && string[10 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[10 + slideToRight - 1], string[10 + slideToRight]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[9 + slideToRight]<58 && string[9 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[9 + slideToRight], NULL));
               // printf("%s  ",binary );
            }
            if((string[12 + slideToRight]<58 && string[12 + slideToRight] > 47))
            {
                char trivial[4] = "00";
                strcat(binary, trivial);
                strcat(binary, toBinary(string[12 + slideToRight - 1], string[12 + slideToRight]));
                printf("%s  ",binary );
            }

            else if((string[11 + slideToRight]<58 && string[11 + slideToRight] > 47))
            {
                char trivial[4] = "00";
                strcat(binary, trivial);
                strcat(binary,toBinary(string[11 + slideToRight], NULL));
                printf("%s  ",binary );
            }



        }
        else if(string[0] == 'X' && string[1] == 'O' && string[2] == 'R')
        {
            char binaryOfInstruction[5]  = "0110";
            strcpy(binary, binaryOfInstruction);
            int slideToRight = 0;
            if((string[6]<58 && string[6] > 47))
            {
                strcat(binary,toBinary(string[5], string[6]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[5]<58 && string[5] > 47))
            {
                strcat(binary,toBinary(string[5], NULL));
                //printf("%s  ",binary );
            }

            if((string[9 + slideToRight]<58 && string[9 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[9 + slideToRight - 1], string[9 + slideToRight]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[8 + slideToRight]<58 && string[8 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[8 + slideToRight], NULL));
               // printf("%s  ",binary );
            }
            if((string[12 + slideToRight]<58 && string[12 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[12 + slideToRight - 1], string[12 + slideToRight]));
                char trivial[3] = "00";
                strcat(binary,trivial);
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[11 + slideToRight]<58 && string[11 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[11 + slideToRight], NULL));

                char trivial[3] = "00";
                strcat(binary,trivial);
                printf("%s  ",binary );
            }

        }
        else if(string[0] == 'S' && string[1] == 'T')
        {
            char binaryOfInstruction[5]  = "1000";
            strcpy(binary, binaryOfInstruction);
            int slideToRight = 0;
            int containsMinus = 0;

            if((string[5]<58 && string[5] > 47))
            {
                strcat(binary,toBinary(string[4], string[5]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[4]<58 && string[4] > 47))
            {
                strcat(binary,toBinary(string[4], NULL));
                //printf("%s  ",binary );
            }

            if((string[6 + slideToRight] == '-'))
            {
                containsMinus = 1;
            }
            if((string[8 + slideToRight + containsMinus]<58 && string[8 + slideToRight + containsMinus] > 47))
            {
                int first = (int)string[8 + slideToRight + containsMinus] - 48;
                int second = (int)(string[8 + slideToRight + containsMinus - 1] - 48) * 10;
                int third = (int)(string[8 + slideToRight + containsMinus - 1 -1] - 48) * 100;

                int number = first + second + third;

                char* twoS = twosComplement(-number,10);
                /*char trivial[4] = "0000";
                strcat(binary, trivial);*/
                strcat(binary, twoS );
                printf("%s  ",binary );
            }

            else if((string[6 + slideToRight]<58 && string[6 + slideToRight] > 47))
            {
                /*char trivial[4] = "0000";
                strcat(binary, trivial);*/
                strcat(binary,toBinary(string[6 + slideToRight], NULL));
                printf("%s  ",binary );
            }


        }
        else if(string[0] == 'L' && string[1] == 'D')
        {
            char binaryOfInstruction[5]  = "1001";
            strcpy(binary, binaryOfInstruction);
            int slideToRight = 0;
            int containsMinus = 0;

            if((string[5]<58 && string[5] > 47))
            {
                strcat(binary,toBinary(string[4], string[5]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[4]<58 && string[4] > 47))
            {
                strcat(binary,toBinary(string[4], NULL));
                //printf("%s  ",binary );
            }

            if((string[6 + slideToRight] == '-'))
            {
                containsMinus = 1;
            }
            if((string[8 + slideToRight + containsMinus]<58 && string[8 + slideToRight + containsMinus] > 47))
            {
                int first = (int)string[8 + slideToRight + containsMinus] - 48;
                int second = (int)(string[8 + slideToRight + containsMinus - 1] - 48) * 10;
                int third = (int)(string[8 + slideToRight + containsMinus - 1 -1] - 48) * 100;
                int number = first + second + third;

                if (containsMinus == 1) {
                    char* twoS = twosComplement(-number,10);
                    strcat(binary,twoS);
                }

                else if(containsMinus != 1) {

                    char *twoS = twosComplement(number,10);
                    strcat(binary,twoS);
                }
                /*char trivial[4] = "0000";
                strcat(binary, trivial);*/
                printf("%s  ",binary );
            }

            else if((string[8 + slideToRight + containsMinus - 1]<58 && string[8 + slideToRight + containsMinus - 1] > 47))
            {
                printf("-----aaaa----");
                int first = (int)string[8 + slideToRight + containsMinus - 1] - 48;
                int second = (int)(string[8 + slideToRight + containsMinus - 1 -1] - 48) * 10;
                int number = first + second;
                if (containsMinus == 1) {

                    char* twoS = twosComplement(-number,10);
                    strcat(binary,twoS);
                }

                else if(containsMinus != 1) {

                    char *twoS = twosComplement(number,10);
                    strcat(binary,twoS);
                }

                /*char trivial[4] = "0000";
                strcat(binary, trivial);*/
                //strcat(binary,twoS);
                printf("%s  ",binary );
            }
            else
            {
                int number = (int)string[8 + slideToRight + containsMinus - 1 - 1] - 48;
                if (containsMinus == 1) {

                    char* twoS = twosComplement(-number,10);
                    strcat(binary,twoS);
                }

                else if(containsMinus != 1) {

                    char *twoS = twosComplement(number,10);
                    strcat(binary,twoS);
                }
                printf("%s  ",binary );
            }


        }
        else if(string[0] == 'J' && string[1] == 'U' && string[2] == 'M' && string[3] == 'P')
        {
            char binaryOfInstruction[5]  = "1010";
            strcpy(binary, binaryOfInstruction);
            int slideToRight = 0;
            int containsMinus = 0;


                //strcat(binary,"0000");
                //printf("%s  ",binary );




            if((string[5 + slideToRight] == '-'))
            {
                containsMinus = 1;
            }
            /////////////////////////////////////////////
              if((string[8 + slideToRight + containsMinus]<58 && string[8 + slideToRight + containsMinus] > 47))
            {
                int first = (int)string[8 + slideToRight + containsMinus] - 48;
                int second = (int)(string[8 + slideToRight + containsMinus - 1] - 48) * 10;
                int third = (int)(string[8 + slideToRight + containsMinus - 1 -1] - 48) * 100;
                int fourth = (int)(string[8 + slideToRight + containsMinus - 1 -1-1] - 48) * 1000;
                int number = first + second + third + fourth;

                if (containsMinus == 1) {
                    char* twoS = twosComplement(-number,14);
                    strcat(binary,twoS);
                }

                else if(containsMinus != 1) {

                    char *twoS = twosComplement(number,14);
                    strcat(binary,twoS);
                }
                /*char trivial[4] = "0000";
                strcat(binary, trivial);*/
                printf("%s  ",binary );
            }
            /////////////////////////////////////////////
           else if((string[7 + slideToRight + containsMinus]<58 && string[7 + slideToRight + containsMinus] > 47))
            {
                int first = (int)string[7 + slideToRight + containsMinus] - 48;
                int second = (int)(string[7 + slideToRight + containsMinus - 1] - 48) * 10;
                int third = (int)(string[7 + slideToRight + containsMinus - 1 -1] - 48) * 100;
                int number = first + second + third;

                if (containsMinus == 1) {
                    char* twoS = twosComplement(-number,14);
                    strcat(binary,twoS);
                }

                else if(containsMinus != 1) {

                    char *twoS = twosComplement(number,14);
                    strcat(binary,twoS);
                }
                /*char trivial[4] = "0000";
                strcat(binary, trivial);*/
                printf("%s  ",binary );
            }

            else if((string[7 + slideToRight + containsMinus - 1]<58 && string[7 + slideToRight + containsMinus - 1] > 47))
            {
                printf("-----aaaa----");
                int first = (int)string[7 + slideToRight + containsMinus - 1] - 48;
                int second = (int)(string[7 + slideToRight + containsMinus - 1 -1] - 48) * 10;
                int number = first + second;
                if (containsMinus == 1) {

                    char* twoS = twosComplement(-number,14);
                    strcat(binary,twoS);
                }

                else if(containsMinus != 1) {

                    char *twoS = twosComplement(number,14);
                    strcat(binary,twoS);
                }

                /*char trivial[4] = "0000";
                strcat(binary, trivial);*/
                //strcat(binary,twoS);
                printf("%s  ",binary );
            }
            else
            {
                int number = (int)string[7 + slideToRight + containsMinus - 1 - 1] - 48;
                if (containsMinus == 1) {

                    char* twoS = twosComplement(-number,14);
                    strcat(binary,twoS);
                }

                else if(containsMinus != 1) {

                    char *twoS = twosComplement(number,14);
                    strcat(binary,twoS);
                }
                printf("%s  ",binary );
            }

        }
        else if(string[0] == 'B' && string[1] == 'E' && string[2] == 'Q')
        {
            char binaryOfInstruction[5]  = "1011";
            strcpy(binary, binaryOfInstruction);
            int slideToRight = 0;
            if((string[6]<58 && string[6] > 47))
            {
                strcat(binary,toBinary(string[5], string[6]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[5]<58 && string[5] > 47))
            {
                strcat(binary,toBinary(string[5], NULL));
                //printf("%s  ",binary );
            }

            if((string[9 + slideToRight]<58 && string[9 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[9 + slideToRight - 1], string[9 + slideToRight]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[8 + slideToRight]<58 && string[8 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[8 + slideToRight], NULL));
               // printf("%s  ",binary );
            }
            if((string[11 + slideToRight]<58 && string[11 + slideToRight] > 47))
            {
                char trivial[4] = "00";
                strcat(binary, trivial);
                strcat(binary, toBinary(string[11 + slideToRight - 1], string[11 + slideToRight]));
                printf("%s  ",binary );
            }

            else if((string[10 + slideToRight]<58 && string[10 + slideToRight] > 47))
            {
                char trivial[4] = "00";
                strcat(binary, trivial);
                strcat(binary,toBinary(string[10 + slideToRight], NULL));
                printf("%s  ",binary );
            }



        }
        else if(string[0] == 'B' && string[1] == 'G' && string[2] == 'T')
        {
            char binaryOfInstruction[5]  = "1100";
            strcpy(binary, binaryOfInstruction);
            int slideToRight = 0;
            if((string[6]<58 && string[6] > 47))
            {
                strcat(binary,toBinary(string[5], string[6]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[5]<58 && string[5] > 47))
            {
                strcat(binary,toBinary(string[5], NULL));
                //printf("%s  ",binary );
            }

            if((string[9 + slideToRight]<58 && string[9 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[9 + slideToRight - 1], string[9 + slideToRight]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[8 + slideToRight]<58 && string[8 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[8 + slideToRight], NULL));
               // printf("%s  ",binary );
            }
            if((string[11 + slideToRight]<58 && string[11 + slideToRight] > 47))
            {
                char trivial[4] = "00";
                strcat(binary, trivial);
                strcat(binary, toBinary(string[11 + slideToRight - 1], string[11 + slideToRight]));
                printf("%s  ",binary );
            }

            else if((string[10 + slideToRight]<58 && string[10 + slideToRight] > 47))
            {
                char trivial[4] = "00";
                strcat(binary, trivial);
                strcat(binary,toBinary(string[10 + slideToRight], NULL));
                printf("%s  ",binary );
            }



        }
        else if(string[0] == 'B' && string[1] == 'L' && string[2] == 'T')
       {
            char binaryOfInstruction[5]  = "1101";
            strcpy(binary, binaryOfInstruction);
            int slideToRight = 0;
            if((string[6]<58 && string[6] > 47))
            {
                strcat(binary,toBinary(string[5], string[6]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[5]<58 && string[5] > 47))
            {
                strcat(binary,toBinary(string[5], NULL));
                //printf("%s  ",binary );
            }

            if((string[9 + slideToRight]<58 && string[9 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[9 + slideToRight - 1], string[9 + slideToRight]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[8 + slideToRight]<58 && string[8 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[8 + slideToRight], NULL));
               // printf("%s  ",binary );
            }
            if((string[11 + slideToRight]<58 && string[11 + slideToRight] > 47))
            {
                char trivial[4] = "00";
                strcat(binary, trivial);
                strcat(binary, toBinary(string[11 + slideToRight - 1], string[11 + slideToRight]));
                printf("%s  ",binary );
            }

            else if((string[10 + slideToRight]<58 && string[10 + slideToRight] > 47))
            {
                char trivial[4] = "00";
                strcat(binary, trivial);
                strcat(binary,toBinary(string[10 + slideToRight], NULL));
                printf("%s  ",binary );
            }



        }
        else if(string[0] == 'B' && string[1] == 'G' && string[2] == 'E')
        {
            char binaryOfInstruction[5]  = "1110";
            strcpy(binary, binaryOfInstruction);
            int slideToRight = 0;
            if((string[6]<58 && string[6] > 47))
            {
                strcat(binary,toBinary(string[5], string[6]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[5]<58 && string[5] > 47))
            {
                strcat(binary,toBinary(string[5], NULL));
                //printf("%s  ",binary );
            }

            if((string[9 + slideToRight]<58 && string[9 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[9 + slideToRight - 1], string[9 + slideToRight]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[8 + slideToRight]<58 && string[8 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[8 + slideToRight], NULL));
               // printf("%s  ",binary );
            }
            if((string[11 + slideToRight]<58 && string[11 + slideToRight] > 47))
            {
                char trivial[4] = "00";
                strcat(binary, trivial);
                strcat(binary, toBinary(string[11 + slideToRight - 1], string[11 + slideToRight]));
                printf("%s  ",binary );
            }

            else if((string[10 + slideToRight]<58 && string[10 + slideToRight] > 47))
            {
                char trivial[4] = "00";
                strcat(binary, trivial);
                strcat(binary,toBinary(string[10 + slideToRight], NULL));
                printf("%s  ",binary );
            }



        }
        else if(string[0] == 'B' && string[1] == 'L' && string[2] == 'E')
        {
            char binaryOfInstruction[5]  = "1111";
            strcpy(binary, binaryOfInstruction);
            int slideToRight = 0;
            if((string[6]<58 && string[6] > 47))
            {
                strcat(binary,toBinary(string[5], string[6]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[5]<58 && string[5] > 47))
            {
                strcat(binary,toBinary(string[5], NULL));
                //printf("%s  ",binary );
            }

            if((string[9 + slideToRight]<58 && string[9 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[9 + slideToRight - 1], string[9 + slideToRight]));
                //printf("%s  ",binary );
                slideToRight += 1;
            }

            else if((string[8 + slideToRight]<58 && string[8 + slideToRight] > 47))
            {
                strcat(binary,toBinary(string[8 + slideToRight], NULL));
               // printf("%s  ",binary );
            }
            if((string[11 + slideToRight]<58 && string[11 + slideToRight] > 47))
            {
                char trivial[4] = "00";
                strcat(binary, trivial);
                strcat(binary, toBinary(string[11 + slideToRight - 1], string[11 + slideToRight]));
                printf("%s  ",binary );
            }

            else if((string[10 + slideToRight]<58 && string[10 + slideToRight] > 47))
            {
                char trivial[4] = "00";
                strcat(binary, trivial);
                strcat(binary,toBinary(string[10 + slideToRight], NULL));
                printf("%s  ",binary );
            }



        }

    char* hexVersionOfBinary = binaryToHex(binary);
    printf("     hex version : %s",hexVersionOfBinary);
    fprintf(file2,"%s\n",hexVersionOfBinary);
    printf("\n");
    }
    fclose(file2);
    return 0;
}

char* toBinary(int fifthIndex, int sixthIndex)
{
  if(sixthIndex == NULL)
  {
    switch(fifthIndex)
    {
    case '0':
        return "0000";
        break;
    case '1':
        return "0001";
        break;
    case '2':
        return "0010";
        break;
    case '3':
        return "0011";
        break;
    case '4':
        return "0100";
        break;
    case '5':
        return "0101";
        break;
    case '6':
        return "0110";
        break;
    case '7':
        return "0111";
        break;
    case '8':
        return "1000";
        break;
    case '9':
        return "1001";
        break;
    }
  }
  else if(sixthIndex != NULL)
  {
     switch(sixthIndex)
    {
    case '0':
        return "1010";
        break;
    case '1':
        return "1011";
        break;
    case '2':
        return "1100";
        break;
    case '3':
        return "1101";
        break;
    case '4':
        return "1110";
        break;
    case '5':
        return "1111";
        break;
    }
  }

}

char * twosComplement(int decimal,int bit)
{
    char* complementString = malloc(bit);
    strcpy(complementString,"");
    if(decimal<0)
    {
    decimal*= (-1);
        strcpy(complementString,"1");

     for(int i = bit-2; i>=0 ; i--)
        {   //printf("bolum : %d\n",decimal/(int)pow(2,i));
        int truth = (int)(decimal/(int)pow(2,i) == 1);
            if(truth){strcat(complementString,"1");decimal -= pow(2,i);}
            else
            {
                strcat(complementString,"0");
            }


        }
        //printf("func : %s\n",complementString);
        for(int i = bit-1; i>0 ; i--)
        {
            if(complementString[i] == '0')
            {
                complementString[i] = '1';
            }
           else if(complementString[i] == '1')
            {
                complementString[i] = '0';
            }
        }
        for(int i = bit-1; i>0;i--)
        {
            if(complementString[i] == '0')
            {
                complementString[i] = '1';
                break;
            }
            else
            {
                complementString[i] = '0';
            }
        }
        //printf("asd :%s \n",complementString);
    return complementString;


    }
    else if(decimal >= 0)
    {strcpy(complementString,"0");
         for(int i = bit-2; i>=0 ; i--)
        {   //printf("bolum : %d\n",decimal/(int)pow(2,i));
        int truth = (int)(decimal/(int)pow(2,i) == 1);
            if(truth){strcat(complementString,"1");decimal -= pow(2,i);}
            else
            {
                strcat(complementString,"0");
            }


        }
        //printf("func : %s\n",complementString);
    return complementString;
    }


}
char* binaryToHex(char * string){
    char* hexnumber[5];
    int first,second,third,fourth,fifth = 0;
    int arr[5];
    arr[0]= (string[14]-48)*pow(2,3)+(string[15]-48)*pow(2,2)+(string[16]-48)*pow(2,1)+(string[17]-48);
    arr[1] = (string[10]-48)*pow(2,3)+(string[11]-48)*pow(2,2)+(string[12]-48)*pow(2,1)+(string[13]-48);
    arr[2] = (string[6]-48)*pow(2,3)+(string[7]-48)*pow(2,2)+(string[8]-48)*pow(2,1)+(string[9]-48);
    arr[3] = (string[2]-48)*pow(2,3)+(string[3]-48)*pow(2,2)+(string[4]-48)*pow(2,1)+(string[5]-48);
    arr[4] = (string[0]-48)*pow(2,1)+(string[1]-48);
    char* str =malloc(5) ;
    strcpy(str,"");
    for(int i = 4; i>=0 ; i--)
    {
        switch(arr[i]){
        case 0 :strcat(str,"0");break;
        case 1 :strcat(str,"1");break;
        case 2 :strcat(str,"2");break;
        case 3 :strcat(str,"3");break;
        case 4 :strcat(str,"4");break;
        case 5 :strcat(str,"5");break;
        case 6 :strcat(str,"6");break;
        case 7 :strcat(str,"7");break;
        case 8 :strcat(str,"8");break;
        case 9 :strcat(str,"9");break;
        case 10 :strcat(str,"A");break;
        case 11 :strcat(str,"B");break;
        case 12 :strcat(str,"C");break;
        case 13 :strcat(str,"D");break;
        case 14 :strcat(str,"E");break;
        case 15 :strcat(str,"F");break;
        }

    }

    return str;



}







