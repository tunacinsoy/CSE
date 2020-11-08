/*
    This program constructs a binary search tree with given inputs,
    and tries to find how many times is it possible to construct the same tree with the same sequence user has entered.
    Implemented by Tuna Cinsoy, 01.12.2019
*/
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include <limits.h>
//Defining structs at global
struct node
{
    int key;
    struct node* left;
    struct node* right;
};
// Creating root and root2 trees, root is first tree constructed with given inputs, root2 is constantly changing during execution with different permutations
struct node* root = NULL;
struct node* root2 = NULL;
int howMany = 1;
struct node* insert(struct node* node, int key)
{
    if (root == NULL)
    {
        root = (struct node*)malloc(sizeof(struct node));
        root -> key = key;
        root -> left = NULL;
        root -> right = NULL;
        //printf(" %d ", root -> key);
        return root;
    }
    else if (root != NULL)
    {
        if (node == NULL)
        {
            node = (struct node*)malloc(sizeof(struct node));
            node -> key = key;
            node -> left = NULL;
            node -> right = NULL;
            return node;
        }
        if (key < node -> key)
        {

        node -> left = insert(node -> left, key);

        }
        else if (key > node -> key)
        {

        node -> right = insert (node -> right, key);

        }
        else if (key == node -> key)
        {
            return;
        }
    }

}
void inorder(struct node* root)
{
    if (root != NULL)
    {
        inorder(root -> left);
        printf("%d \n", root -> key);
        inorder(root -> right);
    }
    return;
}
unsigned long long factorial(int number)
{
    unsigned long long factorial = 1;

    for(int i = 1; i <= number; ++i)
    {
        factorial *= i;
    }
    return factorial;
}
int sizeOfTree(struct node* node)
{
    if(node == NULL)
    {
        return 0;
    }
    else
        return(sizeOfTree(node -> left) + 1 + sizeOfTree(node -> right));
}
void printarray(int arr[], int size)
{
    int i;
    printf("%d. Sequence:\t ", howMany);
    for(i = 0; i < size; i++)
    {
        printf("%d ", arr[i]);
    }
    howMany++;
    printf("\n");
}
void swap(int* a, int *b)
{
    int temp;
    temp = *a;
    *a = *b;
    *b = temp;
}
void deleteTree(struct node* node)
{
    if (node == NULL)
    {
        return;
    }

    deleteTree(node -> left);
    deleteTree(node -> right);
    free(node);
}
void permutation(int* arr, int start, int end)
{
    if (start == end)
    {
        for(int i = 0; i < end + 1; i++)
        {
            root2 = insert(root2,arr[i]);
        }
        if (identicalTrees(root,root2))
        {
            printarray(arr, end + 1);
        }
        deleteTree(root2);
        root2 = NULL;
        return;
    }
    int i;
    for(i = start; i<= end; i++)
    {
        swap((arr+i),(arr+start));
        permutation(arr, start + 1, end);
        swap((arr+i), (arr+start));
    }
}
int identicalTrees(struct node* a, struct node* b) // Checking whether trees are same or not
{
    if (a == NULL && b == NULL)
    {
        return 1;
    }
    if (a != NULL && b != NULL)
    {
        return (a -> key == b -> key && identicalTrees(a->left, b->left) && identicalTrees(a->right, b->right));
    }

    return 0;
}
int main(void)
{
    int numberOfNodes;
    int keyOfNode;


    printf("--------WELCOME TO BST CREATOR!--------\n\n");
    printf("How many nodes will you enter?: ");
    scanf("%d",&numberOfNodes);
    printf("\n");
    int inputArray[numberOfNodes];
    for(int i = 0; i < numberOfNodes; i++)
    {
        printf("Please enter the value of node number %d: ", i+1);
        scanf("%d",&keyOfNode);
        inputArray[i] = keyOfNode; // Getting sequence into an array to find different permuted sequences
        root = insert(root,keyOfNode); // Initiating first array
    }


    printf("\nInorder Traversal of Given Binary Search Tree\n");
    printf("---------------------------------------------\n");
    inorder(root); // Inorder traversal of first tree
    printf("\n");

    printf("\nDifferent Sequences Yield The Same Topology with Given Inputs: \n");
    printf("----------------------------------------------------------------------\n");
    printf("\n");
    permutation(inputArray, 0, numberOfNodes - 1); // Calculates the permutation of given sequence from 0th index to numberOfNodes - 1

    printf("\n\nCount of Different Sequences Yield The Same Topology with Given Inputs: \n");
    printf("----------------------------------------------------------------------\n");
    printf("%d Different Sequences\n", howMany - 1);


    return 0;
}















































