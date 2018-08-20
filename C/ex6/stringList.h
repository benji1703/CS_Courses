#ifndef _STRING_LIST_H_
#define _STRING_LIST_H_
/*******************************************
*  stringList.h                            *
*  ~~~~~~~~~~~~                            *
*  Header file for string list data        *
*  structure                               *
*******************************************/


/************************************
*    TYPE DEFINITIONS - Problem 1.1 *
************************************/ 

typedef struct StringList_st* StringList;

/************************************
*    FUNCTION DECLARATIONS          *
************************************/ 


/***************************************************************************************************************
*    IMPORTANT NOTE: ALL FUNCTIONS BELOW ASSUME THAT THE STRING LIST OBJECT (strList) IS NOT NULL !!!          *
***************************************************************************************************************/ 


/***********************************************************
  numStrsInList                                  Problem 1.2
  returns the number of strings in list
  param
  - strList - string list object (assumes that object != NULL)
  return
  - the number of strings in list
***********************************************************/
int numStrsInList (StringList strList);

/***********************************************************
  firstStrInList                                 Problem 1.3
  returns a pointer to the first string in the list
  param
  - strList - string list object (assumes that object != NULL)
  return
  - pointer to the first string in the list.
    NULL if list is empty
***********************************************************/
char* firstStrInList(StringList strList);


/***********************************************************
  lastStrInList                                  Problem 1.4
  returns a pointer to the last string in the list
  param
  - strList - string list object (assumes that object != NULL)
  return
  - pointer to the last string in the list.
    NULL if list is empty
***********************************************************/
char* lastStrInList(StringList strList);

/***********************************************************
  newStrList                                     Problem 1.5
  creates a new string list object with an empty list
  param
  - none
  return
  - pointer to a newly allocated string list object.
    NULL if allocation failed
***********************************************************/
StringList newStrList(void);

/***********************************************************
  addStrToList                                   Problem 1.6
  adds string to end of list.
  allocates memory for string
  param
  - strList - string list object (assumes that object != NULL)
  - str     - pointer to string to copy into list
  return
  - 1 if addition is successful, and 0 if not (allocation failed)
***********************************************************/
int addStrToList(StringList strList, char* str);

/***********************************************************
  addStrToSortedList                             Problem 1.7
  adds string to list in a sorted way.
  allocates memory for string.
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
int addStrToSortedList(StringList strList, char* str);

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
void printStrList(StringList strList, int format);

/***********************************************************
  freeStrList                                    Problem 1.9
  frees all memory allocated by string list including
  (strings, string list object and other auxilliary objects)
  param
  - strList - string list object (assumes that object != NULL)
  return
  - none
***********************************************************/
void freeStrList(StringList strList);

#endif
