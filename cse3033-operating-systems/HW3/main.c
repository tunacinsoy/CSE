#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

/*
    This program aims to create a Publisher & Packager Mechanism with multithreaded programming approaches. 
    Our mechanism is just an another variant of well-known problem "Producer & Consumer".

    Authors: Tuna Cinsoy & Enver Aslan & Cem Güleç
*/

// Structure for holding information about publishers
struct publisher
{
    int publisherID;
    int publisherType;
    int totalPublishedBook;
    int alive;
    pthread_t publisherThread;

} typedef publisher;

//Packager structure, contains necessary attributes for further operations
struct packager
{

    int packagerID;
    int totalPackagedBook;
    pthread_t packagerThread;

} typedef packager;

// Simple structure to hold information about books
struct book
{

    int bookID;
    int bookType;

} typedef book;

// Buffer structure, holds info about different buffers
struct buffer
{
    sem_t empty;
    sem_t full;
    pthread_mutex_t mutex;
    int lastPublishedBookIndex;
    book *books;
    int bufferSize;

} typedef buffer;

// Struct to send publisher function as input argument
struct thread_arguments
{

    publisher *publisher;
    buffer *buffer;
    int totalBook;

} typedef thread_arguments;

// Struct to send packager function as input argument
struct thread_arguments_2
{

    packager *packager;
    buffer *buffer;
    int totalBook;
    int bufferTypeCount;
    book *package;

} typedef thread_arguments_2;

// Function Prototypes
void addBookToBuffer(buffer buffer[], int bufferIndex);
book retrieveBookFromBuffer(buffer buffer[], int bufferIndex);
void addBookToPackage(book package[], book addedBook, int packageSize);
void clearPackage(book package[], int packageSize);
void printPackage(book package[], int packageSize, int packagerID);
void *publisherBehaviour(void *arg);
int isPackageFull(book package[], int packageSize);
void *packagerBehaviour(void *arg);

int main(int argc, char *argv[])
{
    // Checking argument count as part of error checking
    if (argc != 10)
    {
        fprintf(stderr, "Input format: ./publisherPackager -n 2 3 4 -b 5 -s 6 7\n");
    }
    else
    {
        // Retrieving variables from input arguments
        int publisherTypeCount = atoi(argv[2]);
        int publisherThreadCount = atoi(argv[3]);
        int packagerThreadCount = atoi(argv[4]);

        int numberOfBooksThatShouldBePublished = atoi(argv[6]);
        int packageSize = atoi(argv[8]);
        int initialBufferSize = atoi(argv[9]);

        printf("<Thread-type and ID>\t\t<Output>\n");
        printf("\n");

        // Initializing data structures for further operations
        publisher allPublishers[publisherTypeCount][publisherThreadCount];
        for (int i = 0; i < publisherTypeCount; i++)
        {
            for (int j = 0; j < publisherThreadCount; j++)
            {
                allPublishers[i][j].publisherID = j + 1;
                allPublishers[i][j].totalPublishedBook = 0;
                allPublishers[i][j].publisherType = i + 1;
            }
        }

        packager allPackagers[packagerThreadCount];
        for (int i = 0; i < packagerThreadCount; i++)
        {
            allPackagers[i].packagerID = i + 1;
            allPackagers[i].totalPackagedBook = 0;
        }

        book allPackages[packagerThreadCount][packageSize];
        for (int i = 0; i < packagerThreadCount; i++)
        {
            clearPackage(allPackages[i], packageSize);
        }

        // All buffers have their own semaphores and mutexes, we initialize them in here
        buffer allBuffers[publisherTypeCount];
        for (int i = 0; i < publisherTypeCount; i++)
        {
            allBuffers[i].bufferSize = initialBufferSize;
            allBuffers[i].books = malloc(sizeof(book) * initialBufferSize);
            allBuffers[i].lastPublishedBookIndex = 0;
            sem_init(&(allBuffers[i].empty), 0, initialBufferSize);
            sem_init(&(allBuffers[i].full), 0, 0);
            pthread_mutex_init(&(allBuffers[i].mutex), NULL);
        }

        // Initializing thr_arg variable which will be sent to publisher's own method
        thread_arguments thr_arg;
        for (int i = 0; i < publisherTypeCount; i++)
        {
            for (int j = 0; j < publisherThreadCount; j++)
            {

                thr_arg.totalBook = numberOfBooksThatShouldBePublished;
                thr_arg.publisher = &(allPublishers[i][j]);
                thr_arg.buffer = allBuffers;
                pthread_create(&(allPublishers[i][j].publisherThread), NULL, (void *)publisherBehaviour, (void *)&thr_arg);
            }
        }

        // Initializing thr_arg variable which will be sent to packager's own method
        thread_arguments_2 thr_arg_2;
        for (int i = 0; i < packagerThreadCount; i++)
        {
            thr_arg_2.totalBook = packageSize;
            thr_arg_2.buffer = allBuffers;
            thr_arg_2.packager = &(allPackagers[i]);
            thr_arg_2.package = allPackages[i];
            thr_arg_2.bufferTypeCount = publisherTypeCount;
            pthread_create(&(allPackagers[i].packagerThread), NULL, (void *)packagerBehaviour, &thr_arg_2);
        }

        // Waiting all threads to end
        for (int i = 0; i < publisherTypeCount; i++)
        {
            for (int j = 0; j < publisherThreadCount; j++)
            {
                pthread_join(allPublishers[i][j].publisherThread, NULL);
            }
        }

        for (int i = 0; i < packagerThreadCount; i++)
        {
            pthread_join(allPackagers[i].packagerThread, NULL);
        }

        // Destroying mutexes and semaphores of buffers
        for (int i = 0; i < publisherTypeCount; i++)
        {
            pthread_mutex_destroy(&(allBuffers[i].mutex));
            sem_destroy(&(allBuffers[i].empty));
            sem_destroy(&(allBuffers[i].full));
        }

        // ------------------ TESTING PART -------------------------------
        // addBookToBuffer(allBuffers, 1);
        // addBookToBuffer(allBuffers, 1);
        // addBookToBuffer(allBuffers, 1);
        // addBookToBuffer(allBuffers, 1);
        // addBookToBuffer(allBuffers, 1);
        // addBookToBuffer(allBuffers, 1);
        // addBookToBuffer(allBuffers, 1);
        // addBookToBuffer(allBuffers, 1);
        // addBookToBuffer(allBuffers, 1);

        // addBookToBuffer(allBuffers, 2);
        // addBookToBuffer(allBuffers, 2);
        // addBookToBuffer(allBuffers, 2);
        // addBookToBuffer(allBuffers, 2);
        // addBookToBuffer(allBuffers, 2);
        // addBookToBuffer(allBuffers, 2);
        // addBookToBuffer(allBuffers, 2);
        // addBookToBuffer(allBuffers, 2);

        // printf("BEFORE\n");
        // for(int i = 0; i < allBuffers[0].bufferSize; i++) {
        //     printf("Book%d_%d\n", allBuffers[0].books[i].bookType,allBuffers[0].books[i].bookID);
        // }

        // for(int i = 0; i < allBuffers[1].bufferSize; i++) {
        //     printf("Book%d_%d\n", allBuffers[1].books[i].bookType,allBuffers[1].books[i].bookID);
        // }

        // book a = retrieveBookFromBuffer(allBuffers,0);

        // addBookToPackage(allPackages[0], a, packageSize);

        // // for (int i = 0; i < packageSize - 1; i++) {

        // //     a = retrieveBookFromBuffer(allBuffers,0);
        // //     addBookToPackage(allPackages[0], a, packageSize);
        // // }
        // printPackage(allPackages[0], packageSize, 1);
        // printf("\n");
        // printf("PackagedBook: Book%d_%d\n", allPackages[0][0].bookType, allPackages[0][0].bookID);

        // printf("Retrieved: Book%d_%d\n",a.bookType, a.bookID);

        //     printf("AFTER\n");
        //  for(int i = 0; i < allBuffers[0].bufferSize; i++) {
        //     printf("Book%d_%d\n", allBuffers[0].books[i].bookType,allBuffers[0].books[i].bookID);
        // }
    }
}

// Function that handles publishing a book into its own buffer
void addBookToBuffer(buffer buffer[], int bufferIndex)
{

    buffer[bufferIndex - 1].lastPublishedBookIndex++;

    int firstEmptyIndex;
    for (firstEmptyIndex = 0; firstEmptyIndex < buffer[bufferIndex - 1].bufferSize; firstEmptyIndex++)
    {
        if (buffer[bufferIndex - 1].books[firstEmptyIndex].bookID == 0)
        {
            break;
        }
    }

    if (firstEmptyIndex == buffer[bufferIndex - 1].bufferSize)
    {
        buffer[bufferIndex - 1].bufferSize = buffer[bufferIndex - 1].bufferSize * 2;
        buffer[bufferIndex - 1].books = realloc(buffer[bufferIndex - 1].books, sizeof(book) * buffer[bufferIndex - 1].bufferSize);
    }

    buffer[bufferIndex - 1].books[firstEmptyIndex].bookID = buffer[bufferIndex - 1].lastPublishedBookIndex;
    buffer[bufferIndex - 1].books[firstEmptyIndex].bookType = bufferIndex;
}

// Packagers call this function to retrieve a book to add it to their own package
book retrieveBookFromBuffer(buffer buffer[], int bufferIndex)
{

    book retrievedBook;

    retrievedBook = buffer[bufferIndex].books[0];

    for (int i = 1; i < buffer[bufferIndex].bufferSize; i++)
    {
        buffer[bufferIndex].books[i - 1] = buffer[bufferIndex].books[i];
    }

    buffer[bufferIndex].books[buffer[bufferIndex].bufferSize - 1].bookID = 0;
    buffer[bufferIndex].books[buffer[bufferIndex].bufferSize - 1].bookType = 0;

    return retrievedBook;
}

// After retrieving a book, packagers add that book to their packages by calling this function
void addBookToPackage(book package[], book addedBook, int packageSize)
{

    int i;
    for (i = 0; i < packageSize; i++)
    {
        if (package[i].bookID == 0)
        {
            break;
        }
    }

    package[i].bookID = addedBook.bookID;
    package[i].bookType = addedBook.bookType;
}

// After a package gets totally filled, packagers empty their packages by calling that function, so that,
// they can continue packaging more books
void clearPackage(book package[], int packageSize)
{

    for (int i = 0; i < packageSize; i++)
    {
        package[i].bookID = 0;
        package[i].bookType = 0;
    }
}

// Simple printing function print out the books that are located in given package
void printPackage(book package[], int packageSize, int packagerID)
{

    printf("Packager %d\t\tFinished preparing one package. The package contains:\n", packagerID);
    printf("\t\t\t");
    for (int i = 0; i < packageSize; i++)
    {
        printf("Book%d_%d ", package[i].bookType, package[i].bookID);
    }
}

// Publishers act based on this function
void *publisherBehaviour(void *arg)
{
    thread_arguments *thr_arg = (thread_arguments *)arg;

    for (int i = 0; i < thr_arg->totalBook; i++)
    {
        // Semaphore and mutex part, when we arrive here, publisher locks the corresponding buffer
        sem_wait(&(thr_arg->buffer[(thr_arg->publisher->publisherType) - 1].empty));
        pthread_mutex_lock(&(thr_arg->buffer[(thr_arg->publisher->publisherType) - 1].mutex));
        // CRITICAL SECTION BEGIN

        // 1. Getting beforeBufferSize to check if there is any change occurred at the size of buffer
        int beforeBufferSize = thr_arg->buffer[(thr_arg->publisher->publisherType) - 1].bufferSize;

        // 2. Adding book to corresponding buffer
        addBookToBuffer((*thr_arg).buffer, (thr_arg->publisher->publisherType));

        // 3. Incrementing the totalPublishedBook attribute of publishers,
        //so when it reaches to mustPublishBook amount, thread will leave the system
        thr_arg->publisher->totalPublishedBook++;

        // 4. Getting afterBufferSize
        int afterBufferSize = thr_arg->buffer[(thr_arg->publisher->publisherType) - 1].bufferSize;

        // 5. Comparing these two variables, if they are not equal, it means that buffer size is incremented
        if (beforeBufferSize != afterBufferSize)
        {
            printf("....................................\n");
            printf("Publisher %d of type %d\t\tBuffer is full. Resizing the buffer.\n", thr_arg->publisher->publisherID, thr_arg->publisher->publisherType);
            printf("....................................\n");
        }

        // 6. If totalPublishedBook amount is mustPublishBookAmount, thread will leave the system
        if (thr_arg->publisher->totalPublishedBook == thr_arg->totalBook)
        {

            printf("....................................\n");
            printf("Publisher %d of type %d\tFinished publishing %d books. Exiting the system.\n", thr_arg->publisher->publisherID, thr_arg->publisher->publisherType, thr_arg->totalBook);
            printf("....................................\n");
            pthread_mutex_unlock(&(thr_arg->buffer[(thr_arg->publisher->publisherType) - 1].mutex));
            sem_post(&(thr_arg->buffer[(thr_arg->publisher->publisherType) - 1].full));
            break;
        }

        // 7.If everything went standard, publisher simply publishes a book and adds it to buffer
        printf("Publisher %d of type %d\t", thr_arg->publisher->publisherID, thr_arg->publisher->publisherType);
        printf("Book%d_%d is published and put into the buffer %d\n", thr_arg->publisher->publisherType, thr_arg->buffer[(thr_arg->publisher->publisherType) - 1].lastPublishedBookIndex, thr_arg->publisher->publisherType);
        
        // CRITICAL SECTION END
        pthread_mutex_unlock(&(thr_arg->buffer[(thr_arg->publisher->publisherType) - 1].mutex));
        sem_post(&(thr_arg->buffer[(thr_arg->publisher->publisherType) - 1].full));
    }
}

// Packagers act based on this function
void *packagerBehaviour(void *arg)
{
    thread_arguments_2 *thr_arg_2 = (thread_arguments_2 *)arg;

    for (int i = 0; i < thr_arg_2->totalBook; i++)
    {
        // Before we start the process, packagers chooses a bufferIndex randomly
        int chosenIndex = rand() % (thr_arg_2->bufferTypeCount);

        // Semaphore and mutex part, when we arrive here, packager locks the corresponding buffer
        sem_wait(&(thr_arg_2->buffer[chosenIndex].full));
        pthread_mutex_lock(&(thr_arg_2->buffer[chosenIndex].mutex));
        // CRITICAL SECTION BEGIN

        // 1. Packager retrieves a book from chosenBuffer
        book retrievedBook = retrieveBookFromBuffer(thr_arg_2->buffer, chosenIndex);
        printf("Packager %d\t\tPut Book%d_%d into the package.\n", thr_arg_2->packager->packagerID, retrievedBook.bookType, retrievedBook.bookID);

        // 2. Packager adds retrievedBook to the package
        addBookToPackage(thr_arg_2->package, retrievedBook, thr_arg_2->totalBook);

        // 3. If package size hits its limit, we print out the package, and clear its content
        if (isPackageFull(thr_arg_2->package, thr_arg_2->totalBook))
        {
            printPackage(thr_arg_2->package, thr_arg_2->totalBook, thr_arg_2->packager->packagerID);
            printf("\n");
            clearPackage(thr_arg_2->package, thr_arg_2->totalBook);
        }

        // CRITICAL SECTION END
        pthread_mutex_unlock(&(thr_arg_2->buffer[chosenIndex].mutex));
        sem_post(&(thr_arg_2->buffer[chosenIndex].empty));
    }
}

// Simple function to check a package is full or not
int isPackageFull(book package[], int packageSize)
{

    if (package[packageSize - 1].bookID != 0)
    {
        return 1;
    }
    else
    {
        return 0;
    }
}