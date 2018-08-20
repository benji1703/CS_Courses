#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "stringList.h"


/*************************************************
Implements a program similar to the sort command in Linux.
  
The program should be executed as follows:
  
./sortFile file key1 [key2 ...]

The first argument is a path to a readable file, and it is followed by a 
list of positive integers (>0) representing sorting keys. The program 
should print the lines of the input file to the standard output, sorted 
in ascending lexicographic order of the words indexed according to the 
keys. For instance, executing ./sortFile myFile 4 2 will print the lines 
of myFile sorted primarily according to the lexicographic order of the 
4th word in each line, and secondarily according to the lexicographic 
order of the 2nd word in each line. Lines that are identical in their 
4th and 2nd words should be printed according to their order of 
appearance in the file. [ similar to what sort myFile -k4,4 -k2,2 does ]
  
* Use dynamic allocation to create the integer array that holds the sort 
keys.
* Your main function should use a single array variable to read lines 
using the fgets function (from library stdio). You may assume that lines 
in the input file have no more than 200 characters (not including the 
newline).
* You may also assume that the file contains no more than 1,000 lines.
* Each line read from the file should be converted to a string list 
using function createStrList from your solution to 1.5.
* Use the functions you implemented in 2.3 – 2.5.  
* Make sure to free all dynamically allocated memory.
* Instead of a file name, the user may specify “-“ to indicate 
that lines should be read from the standard input. For instance, 
sortFile - 4 2.
* Error messages should be printed to standard error.
*************************************************/

#define MAXIMUM_LINES 1000
#define MAXIMUM_LINE_LENGTH 200

/*********************************************************
*  malloc wrapper                                        *
*  ~~~~~~~~~~~~                                          *
*  used it instead of "malloc"                           *
*********************************************************/

void * malloc_wrapper (unsigned int n);

int main(int argc, char **argv){

    int i;
    int number;
    char* stringList[MAXIMUM_LINES];
    char temp[MAXIMUM_LINE_LENGTH];
    int counter, gotFile;
    FILE * file;

    /* Not enough arguments */

    if (argc < 3) {
        fprintf(stderr, "Usage: ./sortFile file key1 [key2 ... ]\n");
        return 1;
    }

    /* Alocate space for keys */

    int *keys = (int*)malloc_wrapper(sizeof(int) * (argc - 2));


    /* Use and open file */

    if (strcmp(argv[1], "-") == 0) {
        file = stdin;
    }

    else {
        file = fopen(argv[1], "r");
        gotFile = 1;
    }

    if (file == NULL) {
        fprintf(stderr, "Could not open input file %s for reading\n", 
argv[1]);
        return 1;
    }

    /* Check keys */

    for (i = 2; i < argc; i++) {
        number = string2posInt(argv[i]);
        if (number == 0){
            fprintf(stderr, "key %s is not a positive integer\n", 
argv[i]);
            return 1;
        }

        keys[i-2] = number;

    }

    /* Read the file */

    while (fgets(temp, MAXIMUM_LINE_LENGTH, file)){
        /* The C library function char *fgets reads a line from the 
specified stream and stores it into the string pointed to by str.
         * It stops when either (n-1) characters are read, the newline 
character is read, or the end-of-file
         * is reached, whichever comes first. */
        stringList[counter] = createStrList(temp);
        counter++;
    }

    strListSort(stringList, counter, keys, argc - 2);
    printStrLists(stringList, counter);

    /* Close the opened file! */

    if (gotFile) {
        fclose(file);
    }

    /* Free */

    free(keys);

    return 0;

}
