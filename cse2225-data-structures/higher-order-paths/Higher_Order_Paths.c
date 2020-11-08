/* This algorithm is designed for finding higher order paths among different words from different categories and different texts.
    Implemented by Tuna Cinsoy, 04.11.2019
*/
# include <stdio.h>
# include <string.h>
# include <locale.h>
# include <math.h>

struct Node
{
	char word[50]; // Variable which holds the word itself
	int documentID[3]; //= {0,0,0}; // Declares which document am I in
    int howManyTextsInEcon; // variable of how many econ text includes this word
    int howManyTextsInMagazin; // variable of how many magazin text includes this word
    int howManyTextsInHealth; // variable of how many health text includes this word
    int textIDInEcon; // The ID of text which includes this word
    int textIDInHealth; // The ID of text which includes this word
    int textIDInMagazin; // The ID of text which includes this word
	int counterInEcon; // variable which shows how many times this word occurs in Econ
	int counterInHealth; // variable which shows how many times this word occurs in Health
	int counterInMagazin; // variable which shows how many times this word occurs in Magazin
	struct Node* nextMLL; // Pointer to next node
	int isWritable[3]; // Flag point to check whether the word is writable at Part B
};
struct Node* header = NULL;

void WriteInMasterLinkedList (char* word, int documentID, int textID);
void ReadWord (char* directory, int documentID);
void ConstructOrderPaths (struct Node* header);
void TfIdfValues (struct Node* header, int documentID);
void TfValues(struct Node* header, int documentID);

// Global variables for accessing purposes
int numberOfTextsInEcon;
int numberOfTextsInHealth;
int numberOfTextsInMagazin;

int main(void)
{
	setlocale(LC_ALL, "Turkish");

	ReadWord("econ/",0);
	ReadWord("health/",1);
	ReadWord("magazin/",2);


	ConstructOrderPaths(header);
	printf("\n\nBig O Notation: n^2");
	printf("\n\n");

	TfValues(header, 0); // Does include caller of TfIdfValues function
	TfValues(header, 1);
	TfValues(header, 2);

	return 0;
}
void WriteInMasterLinkedList (char* word, int documentID, int textID)
{
	struct Node* tempPointer = NULL;
	if (header == NULL)
	{
		header = (struct Node*)malloc(sizeof(struct Node));
		strcpy(header -> word, word);

		// Initializing document ID's in here
		header -> documentID[0] = 0;
		header -> documentID[1] = 0;
		header -> documentID[2] = 0;
		header -> documentID[documentID] = 1;
		header -> counterInEcon = 1;
		header -> isWritable[0] = 1;
		header -> isWritable[1] = 1;
        header -> isWritable[2] = 1;
        header -> howManyTextsInEcon = 1;
        header -> textIDInEcon = 1;

		// Declaring the pointer
		header -> nextMLL = NULL;
	}
	else
	{
		tempPointer = header;
		while (tempPointer -> nextMLL != NULL)
		{
			if (strcmp(tempPointer -> word, word) == 0) // Checking duplicate words
			{
			    if(documentID == 0) {
                    tempPointer -> counterInEcon++;
                    tempPointer -> documentID[documentID] = 1;
                    if(tempPointer ->textIDInEcon != textID){
                        tempPointer -> howManyTextsInEcon++;
                        tempPointer ->textIDInEcon = textID;

                    }
                    return;
			    }
			    else if(documentID == 1) {
                    tempPointer -> counterInHealth++;
                    tempPointer -> documentID[documentID] = 1;
                    if(tempPointer ->textIDInHealth != textID){
                        tempPointer -> howManyTextsInHealth++;
                        tempPointer ->textIDInHealth = textID;
                    }
                    return;

			    }
			    else if(documentID == 2) {
                    tempPointer -> counterInMagazin++;
                    tempPointer -> documentID[documentID] = 1;
                    if(tempPointer ->textIDInMagazin != textID){
                        tempPointer -> howManyTextsInMagazin++;
                        tempPointer ->textIDInMagazin = textID;
                    }
                    return;
			    }

			}
			tempPointer = tempPointer -> nextMLL;

		}
		tempPointer -> nextMLL = (struct Node*)malloc(sizeof(struct Node));
		tempPointer = tempPointer -> nextMLL;
		strcpy(tempPointer -> word, word);
		tempPointer -> documentID[0] = 0;
		tempPointer -> documentID[1] = 0;
		tempPointer -> documentID[2] = 0;
		tempPointer -> documentID[documentID] = 1;
		tempPointer -> isWritable[0] = 1;
		tempPointer -> isWritable[1] = 1;
        tempPointer -> isWritable[2] = 1;

		if(documentID == 0) {
                tempPointer -> counterInEcon = 1;
                tempPointer -> howManyTextsInEcon = 1;
                tempPointer ->textIDInEcon = textID;
		}
		else if(documentID == 1) {
                tempPointer -> counterInHealth = 1;
                tempPointer -> howManyTextsInHealth = 1;
                tempPointer ->textIDInHealth = textID;
		}
		else if(documentID ==2) {
                tempPointer -> counterInMagazin = 1;
                tempPointer -> howManyTextsInMagazin = 1;
                tempPointer ->textIDInMagazin = textID;
		}


	}



}
void ReadWord (char* directory, int documentID)
{
	// Iteration integer for texts
	int iter = 1;

	// While loop for ReadingWords, if there is no document to read, break the while loop
	while (1)
	{
	// Handling directory address, defining arrays to make appropriate directory
	char directoryInHere[50];
	strcpy(directoryInHere, directory);
	char iterArray[25];
	sprintf(iterArray,"%d", iter);
	strcat(iterArray,".txt");
	strcat(directoryInHere, iterArray);

	// Opening file pointer to read all written words
	FILE* file;
	file = fopen(directoryInHere,"r");

	// If there is no file to open, return to its caller
	if(!(file = fopen(directoryInHere,"r")))
	{
	    if (documentID == 0)
        {
            numberOfTextsInEcon = iter - 1;
	    }
	    else if (documentID == 1)
        {
            numberOfTextsInHealth = iter - 1;
        }
        else if (documentID == 2)
        {
            numberOfTextsInMagazin = iter - 1;
        }
		return;
	}

	//Passing all words to WriteInMasterLinkedList one by one
	while(!feof(file))
	{
		char inputWord[50];
		fscanf(file , "%s",inputWord);
		WriteInMasterLinkedList(inputWord, documentID, iter);
	}

	//Increasing iter by 1, text file number goes on
	iter++;


	//Closing the file for memory efficiency
	fclose(file);
	}
}
void ConstructOrderPaths (struct Node* header)
{
	struct Node* current = header;
	struct Node* current1;

    printf("\n\n\n------------------------- 1st Orders:----------------------------\n ");
	while(current != NULL) {
            current1 = current->nextMLL;

        if(current ->documentID[0] == 1) {
            while(current1 != NULL && current1 ->documentID[0] != 0) {
                printf("{%s, %s}", current -> word, current1 -> word);
                current1 = current1 ->nextMLL;
            }
            current = current -> nextMLL;
        }
        else if(current ->documentID[1] == 1) {
            while(current1 != NULL && current1 ->documentID[1] != 0) {
                printf("{%s, %s}", current -> word, current1 -> word);
                current1 = current1 ->nextMLL;
            }
            current = current -> nextMLL;
        }
        else {
            while(current1 != NULL && current1 ->documentID[2] != 0) {
                printf("{%s, %s}", current -> word, current1 -> word);
                current1 = current1 ->nextMLL;
            }
            current = current -> nextMLL;
        }

    }
    printf("\n\n\n------------------------- 2nd Orders:----------------------------\n ");
    current = header;
    while(current != NULL) {

            current1 = header;

            if(current -> documentID[0] == 1 && current -> documentID[1] == 0 && current -> documentID[2] == 0 ){

                while (current1 != NULL) {
                    if(current1 -> documentID[0] == 0 && current1 -> documentID[1] == 1 && current1 -> documentID[2] == 0 ) {
                        printf("{%s, %s}", current -> word, current1 -> word);

                    }
                    current1 = current1 ->nextMLL;
                }

            }


            else if(current -> documentID[0] == 0 && current -> documentID[1] == 1 && current -> documentID[2] == 0 ){

                while (current1 != NULL) {
                    if(current1 -> documentID[0] == 0 && current1 -> documentID[1] == 0 && current1 -> documentID[2] == 1 ) {
                        printf("{%s, %s}", current -> word, current1 -> word);

                    }
                    current1 = current1 -> nextMLL;
                }

            }

            current = current -> nextMLL;
    }

    printf("\n\n\n------------------------- 3rd Orders:----------------------------\n ");
    current = header;
    while(current != NULL) {
        current1 = header;

        if(current -> documentID[0] == 1 && current -> documentID[1] == 0 && current -> documentID[2] == 0) {

            while(current1 != NULL) {
                if(current1 -> documentID[0] == 0 && current1 -> documentID[1] == 0 && current1 -> documentID[2] == 1){
                    printf("{%s, %s}", current -> word, current1 -> word);
                }
                current1 = current1 -> nextMLL;
            }
        }
        current = current -> nextMLL;
    }

}
void TfValues (struct Node* header, int documentID)
{
    struct Node* temp;
    struct Node* maxTemp;
    if(documentID == 0) {

        printf("Most Common Words In Econ: ");
        printf("\t\t\tTF-IDF VALUES:\n");
        for(int i = 0; i < 5; i++)
        {
            temp = header;
            maxTemp = header;
            while(temp -> nextMLL != NULL)
            {
                if(temp -> counterInEcon >= maxTemp ->counterInEcon && temp -> isWritable[0] == 1 && temp ->documentID[0] == 1){
                   maxTemp = temp;
                }
                temp = temp ->nextMLL;
            }
            maxTemp -> isWritable[0] = 0;
            printf("%s, %d", maxTemp->word, maxTemp ->counterInEcon);
             TfIdfValues(maxTemp,0);
        }
        printf("\n");
    }
    else if(documentID == 1) {
        printf("Most Common Words In Health: ");
        printf("\t\t\tTF-IDF VALUES:\n");
        for(int i = 0; i < 5; i++)
        {
            temp = header;
            maxTemp ->counterInHealth = 0;
            while(temp -> nextMLL != NULL)
            {
                if(temp -> counterInHealth >= maxTemp -> counterInHealth && temp -> isWritable[1] == 1 && temp ->documentID[1] == 1){
                   maxTemp = temp;
                }
                temp = temp ->nextMLL;
            }
            maxTemp -> isWritable[1] = 0;
            printf("%s, %d", maxTemp->word, maxTemp ->counterInHealth);
             TfIdfValues(maxTemp,1);
        }
        printf("\n");

    }
    else if(documentID == 2) {
        printf("Most Common Words In Magazin: ");
        printf("\t\t\tTF-IDF VALUES:\n");
        for(int i = 0; i < 5; i++)
        {
            temp = header;
            maxTemp = header;
            while(temp -> nextMLL != NULL)
            {
                if(temp -> counterInMagazin >= maxTemp ->counterInMagazin && temp -> isWritable[2] == 1 && temp ->documentID[2] == 1){
                   maxTemp = temp;
                }
                temp = temp ->nextMLL;
            }
            maxTemp -> isWritable[2] = 0;
            printf("%s, %d", maxTemp->word, maxTemp ->counterInMagazin);
             TfIdfValues(maxTemp,2);
        }

    }

}
void TfIdfValues (struct Node* maxTemp, int documentID)
{
    if(documentID == 0){
    int numerator = numberOfTextsInEcon;
    int denominator = maxTemp ->howManyTextsInEcon;
    float result1 = log(numerator/denominator);
    int tf = maxTemp -> counterInEcon;
    float result2 = result1 * tf;
    printf("\t\t\t\t\t%s, %.2f\n",maxTemp ->word, result2);
    }
    else if(documentID == 1){
    int numerator = numberOfTextsInHealth;
    int denominator = maxTemp ->howManyTextsInHealth;
    float result1 = log(numerator/denominator);
    int tf = maxTemp -> counterInHealth;
    float result2 = result1 * tf;
    printf("\t\t\t\t\t\t%s, %.2f\n",maxTemp ->word, result2);



    }
    else if(documentID == 2){
    int numerator = numberOfTextsInMagazin;
    int denominator = maxTemp ->howManyTextsInMagazin;
    float result1 = log(numerator/denominator);
    int tf = maxTemp -> counterInMagazin;
    float result2 = result1 * tf;
    printf("\t\t\t\t\t\t%s, %.2f\n",maxTemp->word, result2);


    }

}































