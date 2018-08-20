/*******************************************
*  stringList.c                            *
*  ~~~~~~~~~~~~                            *
*  Implementation of string list data      *
*  structure  using a generic linked list  *
*******************************************/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "stringList.h"
#include "genericLinkedList.h"



/*********************************************************
*  malloc wrapper                                        *
*  ~~~~~~~~~~~~                                          *
*  used it instead of "malloc"                           *
*********************************************************/

void * malloc_wrapper (unsigned int n)
{
    printf("\nmalloc - %d bytes\n", n);
    fflush(stdout);
    return malloc(n);
}

/************************************
*    TYPE DEFINITIONS - Problem 1.1 *
************************************/

/*
 * Each string list holds the following information:
 * The number of strings in the list
 * The head of a generic list, in which each node holds a reference 
(pointer) to a character array
 * holding a string
 * The tail of the string list (pointer to last node in chain)
 */

struct StringList_st {
    int numStrs;
    ListNode listHead;
    ListNode listTail;
};


/************************************
*    FUNCTION DEFINITIONS           *
************************************/


/***************************************************************************************************************
*    IMPORTANT NOTE: ALL FUNCTIONS BELOW ASSUME THAT THE STRING LIST 
OBJECT (strList) IS NOT NULL !!!          *
***************************************************************************************************************/


/***********************************************************
  numStrsInList                                  Problem 1.2
  returns the number of strings in list
  param
  - strList - string list object (assumes that object != NULL)
  return
  - the number of strings in list
***********************************************************/

/* Count the number of strings until end of list */

int numStrsInList (StringList strList) {

    if (!strList) return (int) NULL;
    
       return strList->numStrs;
}


/***********************************************************
  firstStrInList                                 Problem 1.3
  returns a pointer to the first string in the list
  param
  - strList - string list object (assumes that object != NULL)
  return
  - pointer to the first string in the list.
    NULL if list is empty
***********************************************************/

char* firstStrInList(StringList strList) {

    if (!strList->listHead) return NULL;

    ListNode temp = strList->listHead;

    return (char*) listNodeData(temp);

}


/***********************************************************
  lastStrInList                                  Problem 1.4
  returns a pointer to the last string in the list
  param
  - strList - string list object (assumes that object != NULL)
  return
  - pointer to the last string in the list.
    NULL if list is empty
***********************************************************/

char* lastStrInList(StringList strList) {

    if (!strList->listTail) return NULL;

    ListNode temp = strList->listTail;

    return (char*) listNodeData(temp);

}

/***********************************************************
  newStrList                                     Problem 1.5
  creates a new string list object with an empty list
  param
  - none
  return
  - pointer to a newly allocated string list object.
    NULL if allocation failed
***********************************************************/

StringList newStrList(void) {

    /*** allocate array ***/

    StringList newElement = malloc_wrapper (sizeof(struct 
StringList_st));
    if (!newElement) return NULL;

    /*** set structure members ***/

    if (newElement) {
        newElement->listHead = NULL;
        newElement->listTail = NULL;
        newElement->numStrs = 0;
    }

    return newElement;
}

/***********************************************************
  addStrToList                                   Problem 1.6
  adds string to end of list.
  allocates memory for list node and string
  param
  - strList - string list object (assumes that object != NULL)
  - str     - pointer to string to copy into list
  return
  - 1 if addition is successful, and 0 if not (allocation failed)
***********************************************************/

int addStrToList(StringList strList, char* str) {

    char *newStr = malloc_wrapper(sizeof(char) * (strlen(str)+1));
    if(!newStr) return (int) NULL;

    ListNode new;
    strcpy(newStr,str);
    ListNode temp = strList->listTail;
    new = newListNode(str);

    if (new == NULL){
        return 0;
    }

    if (strList->numStrs == 0){
        strList->listHead = new;
        strList->listTail = new;
        strList->numStrs++;
        return 1;
    }

    listNodeInsert(temp, new);
    strList->listTail = new;
    strList->numStrs++;
    return 1;
}


/***********************************************************
  addStrToSortedList                             Problem 1.7
  adds string to list in a sorted way.
  allocates memory for list node and string.
  the function assumes that the strings in the list are sorted
  in ascending lexicographical order (according to function
  strcmp), and it puts the new string right before the first
  string in the list that is “larger” than it.
  if all strings in the list are "smaller" than it, then it
  puts the string in the end.
  param
  - strList - string list object (assumes that object != NULL)
  - str     - pointer to string to copy into list
  return
  - 1 if addition is successful, and 0 if not (allocation failed)
***********************************************************/

int addStrToSortedList(StringList strList, char* str) {

    /* Empty list - no matter where to add */

    if (strList->numStrs == 0){
        addStrToList(strList, str);
        return 1;
    }

    /* Edge case - last to add */

    if (strcmp(str, listNodeData(strList->listTail)) > 0) {
        addStrToList(strList, str);
        return 1;
    }


    char *newStr = malloc_wrapper(sizeof(char) * (strlen(str)+1));
    strcpy(newStr,str);
    ListNode temp = strList->listHead;
    ListNode temp2;
    ListNode strNode = newListNode(newStr);


    /* Edge case - first to add */

    if (strcmp(str, listNodeData(temp)) <= 0){

        int size = strList->numStrs + 1;
        char **arr = (char**)listToArray(strList->listHead);
        freeList(strList->listHead);
        strList->listHead = strNode;
        strList->numStrs = 1;
        int i;

        ListNode current = strNode;
        for(i = 1; i < size; i++){
            ListNode tmp = newListNode(arr[i-1]);
            listNodeInsert(current,tmp);
            strList->numStrs++;
            current = tmp;
        }

        strList->listTail = current;
        return 1;

    }


    /* Normal case - find where to add */

    temp2 = listNodeNext(strList->listHead);

    while (temp2) {
        if ((strcmp(newStr, listNodeData(temp)) > 0) && strcmp(newStr, 
listNodeData(temp2)) <= 0) {
            /* Using insert */
            listNodeInsert(temp, strNode);
            strList->numStrs++;
            return 1;
        }
        else {
            temp = listNodeNext(temp);
            temp2 = listNodeNext(temp);
        }

    }


    return 0;

}


/***********************************************************
  printStrList                                   Problem 1.8
  prints string list according to format argument.
  format 0: each string in separate line
  format 1: stretches of identical strings are collapsed
  format 2: stretches of identical strings are collapsed with
            number of strings printed in a field of width 4.
  param
  - strList - string list object (assumes that object != NULL)
  - int format == 0, 1, or 2 (see above)
  return
  - none
***********************************************************/
void printStrList(StringList strList, int format) {

    if (!strList->listHead) {
        printf("Empty list\n");
        return;
    }

    ListNode tempPrint = strList->listHead;

    if (format == 0) {
        while (tempPrint){
            char* toPrint = (char*) 
listNodeData(tempPrint);
            printf("%s\n", toPrint);
            tempPrint = listNodeNext(tempPrint);
        }
        return;
    }

    else if (format == 1) {

        char* toPrintCheck;

        while (tempPrint){
            char* toPrint = (char*) 
listNodeData(tempPrint);
            if (strcmp(toPrint, toPrintCheck) != 0) {
                printf("%s\n", toPrint);
            }
            toPrintCheck = toPrint;
            tempPrint = listNodeNext(tempPrint);
        }
        return;
    }

    else if (format == 2) {

        char* toPrint;
        char* toPrintCheck;
        int counter = 1;

        if (numStrsInList(strList) == 1){
            toPrint = (char*) listNodeData(tempPrint);
            printf("%4d %s\n", counter, toPrint);
            return;
        }

        while (listNodeNext(tempPrint)) {
            toPrint = (char*) listNodeData(tempPrint);
            toPrintCheck = (char*) 
listNodeData(listNodeNext(tempPrint));
            if (strcmp(toPrint, toPrintCheck) == 0){
                counter ++;
            }
            else {
                printf("%4d %s\n", counter, toPrint);
                counter = 1;
            }
            tempPrint = listNodeNext(tempPrint);
        }

        printf("%4d %s\n", counter, toPrintCheck);

        return;
    }

    else return;

}

/***********************************************************
  freeStrList                                    Problem 1.9
  frees all memory allocated by string list including:
  - all strings
  - all list nodes
  - the string list structure
  param
  - strList - string list object (assumes that object != NULL)
  return
  - none
***********************************************************/
void freeStrList(StringList strList) {

    freeList(strList->listHead);
    free(strList);
    return;

}

