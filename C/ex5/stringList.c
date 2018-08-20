/*********************************************************
*  stringList.c                                          *
*  ~~~~~~~~~~~~                                          *
*  file implementing data structure and functions for    *
*  string list                                           *
*********************************************************/

/*** use only these C libraries ***/
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>    // for isspace()

#include "stringList.h"

/*********************************************************
*  malloc wrapper                                        *
*  ~~~~~~~~~~~~                                          *
*  used it instead of "malloc"                           *
*********************************************************/

void * malloc_wrapper (unsigned int n) {
    fprintf(stderr, "\nmalloc - %d bytes\n", n);
    fflush(stderr);
    return malloc(n);
}

/*********************************************************************************/
/**********    YOUR  SOLUTUON TO PROBLEM 1.1 - 1.6 UNDER HERE         
************/
/*********************************************************************************/

/*********************************************************
   numStrsInList                             (Problem 1.1)
   params:
   - strList

   The function receives pointer to the beginning of a string list and 
returns the
   number of strings in the list (until the terminating ‘\0\0’ 
characters).
   Should return -1 on NULL input.
*********************************************************/

int numStrsInList(const char* strList) {
    int len=0;
    int count=0;
    if(strList == NULL) return -1;
    while(strList[len] || strList[len+1]){
        if(!strList[len+1]) {
            count++;
        }
        len++;
    }
    return count;
}

/*********************************************************
   strListLen                                (Problem 1.2)
   params:
   - strList

   The function receives pointer to the beginning of a string list and 
returns the
   total number of characters in the list, including separating 
‘\0’ chars, but
   excluding the two terminating ‘\0’ chars.
   Should return -1 on NULL input.
*********************************************************/

int strListLen(const char* strList) {
    int len=0;
    int count=0;
    if(strList == NULL) return 0;
    while (strList[len] || strList[len+1]) {
        count++;
        len++;
    }
    return count;
}

/*********************************************************
   emptyStrList                              (Problem 1.3)
   writes an empty string list in a given array (buffer).
   buffer size should be at least 2
   if target is NULL return -1, otherwise return 0.
*********************************************************/

int emptyStrList(char* target) {
    if(target == NULL) return -1;
    if(target[0] && target[1]){
        target[0]='\0';
        target[1]='\0';
    }
    return 0;
}

/*********************************************************
   strListFromWords                          (Problem 1.4)
   writes a new string list into a given array (buffer)
   using words in a given source string.
   returns number of srings in list
   - if source is NULL returns -1 (and does nothing)
   - if buffSize<2, returns -1 (and does nothing)
   - if buffSize is too small to hold the entire list, writes
     an empty list and returns 0.
*********************************************************/

int strListFromWords(const char* source, char* target, int buffSize) {
    int srclen = 0, targetlen=0 ;
    int boolLast =0;

    if ((source == NULL) || (target == NULL)) return 0;

    if (buffSize < 2) return -1;


    while (isspace(source[srclen])){
        srclen++;
    }

    while ((targetlen < buffSize) && (source[srclen])) {
        while (isspace(source[srclen])) {
            srclen++;
            boolLast =1;
        }

        if (source[srclen] == '\0')
            break;

        if (boolLast==1) {
            target[targetlen] = '\0';
            targetlen++;
            boolLast = 0;
        }
        else
        {
            target[targetlen] = source[srclen];
            targetlen++;
            srclen++;
        }
    }

    if(targetlen <= (buffSize - 2)) {
        target[targetlen++] = '\0';
        target[targetlen] = '\0';
        return numStrsInList(target);
    }
    else {
        emptyStrList(target);
        return 0;
    }

}

/*********************************************************
   createStrList                             (Problem 1.5)
   creates a new string list using words in a given string.
   allocates required space (exactly)
   returns pointer to new string list
   (or NULL if allocation failed)
   returns NULL if str is NULL.
*********************************************************/

char* createStrList(const char* str) {

    if (str == NULL) return NULL;
    int buffSize = 2;
    int moreSpace = -1;
    const char* counter = str;
    while (*counter) {
        if (!isspace(*counter)) {
            buffSize++;
            moreSpace = 1;
        }
        else {
            if (moreSpace == 1) {
                buffSize++;
                moreSpace = 0;
            }
        }
        counter++;
    }

    if (moreSpace == 0)
        //Ending with a space!
        buffSize--;

    char *target = malloc_wrapper(buffSize);
    if (target==NULL) return NULL;
    strListFromWords(str, target, buffSize);
    return target;
}

/*********************************************************
   nextStrInList                             (Problem 1.6)
   returns pointer to next string in list.
   - if previous call to function was with different list,
     returns pointer to first string in list.
   - if previous call to function was with same list,
     returns pointer to next string in list
     (following the string returned by the previous call).
     If that string was the last one, then returns NULL.
   - if function is called with NULL pointer, then it re-sets
     the identity of the previous list.
*********************************************************/

char* nextStrInList(char* stringList) {

    static char* lastString = 0;
    static char* lastWord = 0;
    static int previousCalls = 0;
    if (stringList == NULL) {
        lastString = 0;
        lastWord = 0;
        return 0;
    }
    if (lastString == stringList) {
        if (!*lastWord) { // For consequential checks
            if (previousCalls > 0) {
                return NULL;
            }
            else {
                previousCalls++;
                lastWord = stringList;
                return lastWord;
            }
        }

        while (*lastWord++) { }
        previousCalls++;
        if (!*lastWord) { // Checks whether we reached the end
            return NULL;
        }

        return lastWord;
    }
    previousCalls = 1;
    lastString = stringList;
    lastWord = stringList;
    return lastWord;

}

/*********************************************************************************/
/**********    YOUR  SOLUTUON TO PROBLEM 2.1 - 2.5 UNDER HERE         
************/
/*********************************************************************************/

/*********************************************************
   strcmpInList                              (Problem 2.1)
   compares two string list according to k indicating
   which string to use as key.
*********************************************************/

int strcmpInList(char* strList1, char* strList2, int k) {

    int counter = 0;
    char* tempStr1;
    char* tempStr2;

    if ((strList1 == NULL) || (strList2 == NULL)) {
        return -10;
    }

    if (strList1 == strList2) {
        return 0;
    }

    if (numStrsInList(strList1) < k && numStrsInList(strList2) < k) {
        return 0;
    }

    else if (numStrsInList(strList1) >= k && numStrsInList(strList2) < 
k) {
        return 1;
    }

    else if (numStrsInList(strList1) < k && numStrsInList(strList2) >= 
k) {
        return -1;
    }

    nextStrInList(NULL);
    for (counter = 0; counter < k; counter++) {
        tempStr1 = nextStrInList(strList1);
    }

    nextStrInList(NULL);
    for (counter = 0; counter < k; counter++) {
        tempStr2 = nextStrInList(strList2);
    }

    if ((tempStr1 == NULL) && (tempStr2 == NULL)) {
        return 0;
    }
    else if (tempStr1 == NULL) {
        return 1;
    }
    else if (tempStr2 == NULL) {
        return -1;
    }

    if (strcmp(tempStr1, tempStr2) == 0) {
        return 0;
    }
    else if (strcmp(tempStr1, tempStr2) < 0) {
        return -1;
    }
    else if (strcmp(tempStr1, tempStr2) > 0) {
        return 1;
    }

    return 0;
}

/*********************************************************
   strListCmp                                (Problem 2.2)
   compares two string list according to a prioritized
   list of keys
*********************************************************/

int strListCmp(char* strList1, char* strList2, const int keys[], int 
numKeys) {

    int counter, result;
    if (strList1 == NULL || strList2 == NULL || keys == NULL ) {
        return -10;
    }

    for (counter=0; counter < numKeys; counter++) {
        result = strcmpInList(strList1, strList2, keys[counter]);

        if (result != 0){
            return result;
        }
    }

    return 0;
}

/*********************************************************
   strListSort                               (Problem 2.3)
   sorts an array of string lists according to a
   prioritized list of keys in ascending lex. order.
   return -1 if strListArr is NULL, otherwise return 0
*********************************************************/

int strListSort(char** strListArr, int numLists, const int keys[], int 
numKeys) {

    int c, d;
    char* swap;

    if (strListArr == NULL || keys == NULL ) {
        return -1;
    }


    for (c = 0 ; c < (numLists - 1); c++) {
        for (d = 0 ; d < (numLists - c - 1); d++) {
            if (strListCmp(strListArr[d], strListArr[d+1], keys, 
numKeys) == 1) { // To compare!
                swap = strListArr[d];
                strListArr[d] = strListArr[d+1];
                strListArr[d+1] = swap;
            }
        }
    }
    return 0;
}

/*********************************************************
   printStrLists                             (Problem 2.4)
   prints a list of string lists in sequence.
   prints each list in a separate line and uses tab character
   as delimiter between strings in list
   return -1 if strListArr is NULL, otherwise return 0
*********************************************************/

int printStrLists(char** strListArr, int numLists) {

    int line, row;

    if (strListArr == NULL){
        return -1;
    }

    for (line = 0; line < numLists; line++){
        for (row = 1; row < numStrsInList(strListArr[line]); row++){
            printf("%s\t", nextStrInList(strListArr[line]));
        }
        printf("%s\n", nextStrInList(strListArr[line]));
    }
    return 0;
}

/*********************************************************
   string2posInt                             (Problem 2.5)
   returns positive integer number that is written in
   string. If string does not represent a positive number
   then the function returns 0.
*********************************************************/

int string2posInt(const char* str) {

    int strlen = 0, result = 0;

    if (str == NULL){
        return -1;
    }

    while (str[strlen]){
        if (!isdigit(str[strlen])){
            return 0;
        }
        result = (10*result) + (str[strlen] - '0');
        strlen++;

    }
    return result;
}

/*** end of stringList.c ***/
