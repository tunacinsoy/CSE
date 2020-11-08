/*
    This program firstly gets "input.cnf" from user which contains a random boolean expression; in DIMACS CNF format,
    and tries to find whether the boolean equation is satisfiable or not.

    -----------Tuna Cinsoy
    -----Last Update: 07.12.19

*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

int main()
{
    char unsatisfiable[50] = "UNSAT";
    char satisfiable[50] = "SAT";
    char allInputs[100];
    int *parityArray;
    int lineNumber = 0;
    int numberOfClauses;
    int numberOfVariables;

    FILE *inputFile;
    inputFile = fopen("input.cnf", "r");


    while(fgets(allInputs, 100, inputFile))
    {
        char* inputLines = strtok(allInputs, "\n");
        while(inputLines != NULL)
        {
            char* inputVariables;

 // ----------------FOR THE FIRST LINE, LOOP PROCESS WILL ACT DIFFERENTLY TO CAPTURE VARIABLE AND CLAUSE COUNTS---------------------------------
            if(lineNumber == 0)
            {
                inputVariables = strtok(inputLines, " ");

                for(int i = 0; i < 3; i++)
                {
                    inputVariables = strtok(NULL, " ");

                    if(i == 1)
                    {
                        numberOfVariables = atoi(inputVariables);
                    }

                    else if(i == 2)
                    {
                        numberOfClauses = atoi(inputVariables);
                    }

                }
                    parityArray = (int)malloc(sizeof(int) * (int)pow(2, numberOfVariables));
            }


            // ------------------ INITIATING A TRUTH TABLE FOR SECOND TIME --------------------------------
            int truthTable[((int)pow(2, numberOfVariables))][numberOfVariables + 1];

                for(int i = 0; i < (int)pow(2, numberOfVariables); i++)
                {
                    int initialValue = i;
                    truthTable[i][0] = 1;

                    for(int j = numberOfVariables; j >= 1; j--)
                    {
                        truthTable[i][j] = initialValue % 2;
                        initialValue /= 2;
                    }
                }

// --------------- FOR EVERY SINGLE CLAUSE, THERE WILL BE AN ARRAY WHICH HAS NUMBEROFVARIABLES + 1 INDEXES, + 1 IS FOR PARITY BIT ------------------------
                int clauses[numberOfVariables + 1];
                inputVariables = strtok(inputLines, " ");
                int i = 0;
                while(inputVariables != NULL)
                {
                    clauses[i] = atoi(inputVariables);
                    inputVariables = strtok(NULL, " ");
                    i++;
                }
                for(int i = 0; i < (int)pow(2, numberOfVariables); i++)
                {
                    truthTable[i][0] = 0;
                    int j = 0;
                    while(clauses[j] != 0)
                    {
                        if (clauses[j] > 0)
                        {
                            truthTable[i][0] = truthTable[i][0] || truthTable[i][clauses[j]];
                        }
                        else
                        {
                            truthTable[i][0] = truthTable[i][0] || !truthTable[i][clauses[j] * -1];
                        }
                        j++;
                    }
                }

//-----------------------------------INITIALIZING PARITY ARRAY ACCORDING TO THE SECOND LINE, WHICH IS THE FIRST LINE OF CLAUSES---------------------------------------------------
                   if(lineNumber == 1)
                   {
                        for(i = 0; i < (int)pow(2, numberOfVariables); i++)
                        {
                            parityArray[i] = truthTable[i][0];
                        }
                   }


//----------------------------------CHECK OF SATISFIABILITY OF GIVEN CLAUSES------------------------------------------------------------------------------
                if(lineNumber > 1)
                {
                    for(i = 0; i < (int)pow(2, numberOfVariables); i++)
                    {
                        if(parityArray[i] == 1)
                        {
                          parityArray[i] = truthTable[i][0];
                        }
                    }
                }
                inputLines = strtok(NULL, "\n");

                lineNumber++;
            }
        }

    fclose(inputFile);


// ------------------ INITIATING A TRUTH TABLE FOR SECOND TIME --------------------------------
    int truthTable[((int)pow(2, numberOfVariables))][numberOfVariables + 1];
        for(int i = 0; i < (int)pow(2, numberOfVariables); i++) {
            int initialValue = i;
            truthTable[i][0] = 1;
            for(int j = numberOfVariables; j >= 1; j--) {
                truthTable[i][j] = initialValue % 2;
                initialValue /= 2;
            }
        }

    // ------------------------ CONSTRUCTING OUTPUT.CNF PART ----------------------------------------------
    FILE *outputFile;
    outputFile = fopen("output.cnf", "w");
    int countOfZeros = 0;
    for(int i = 0; i < (int)pow(2, numberOfVariables); i++)
    {
        if(parityArray[i] == 1)
        {
            fprintf(outputFile,"%s\n", satisfiable);
            for(int j = 0; j < numberOfVariables; j++)
            {
                if (truthTable[i][j] > 0)
                {
                    fprintf(outputFile,"%d ", j+1);
                }
                else
                {
                    fprintf(outputFile,"%d ", (j+1) * - 1);
                }
            }

            break;
        }
        if(parityArray[i] == 0)
        {
            countOfZeros++;
        }

    }

    if(countOfZeros == (int)pow(2, numberOfVariables))
    {
       fprintf(outputFile, "%s\n", unsatisfiable);
    }

    fclose(outputFile);
    return 0;
}
