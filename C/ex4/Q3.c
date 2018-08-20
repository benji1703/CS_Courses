#include<stdio.h>

/*************
   This is a C program that asks the user to enter 3 integers, and prints their values in ascending order
    ********/

/************
     function swap
        swaps the values of two integer variables that are
           passed to the function “by reference”.
              params:
                 - num1 - integer pointer
                    - num2 - integer pointer
                     *************/

void swap (int *num1, int *num2){
       int temp;
           temp = *num1;
               *num1 = *num2;
                   *num2 = temp;
                       return;
}

/*** end of swap ***/

/************
     function order_nums
        receives three pointers to integer variables (num1, num2, and num3) , and swaps their values such that they are ordered as follows:
           num1 holds the smallest value, num3 holds the largest value, and num2 holds the third remaining value.
              params:
                 - num1 - integer pointer
                    - num2 - integer pointer
                       - num3 - integer pointer
                        *************/

void order_nums(int *num1, int *num2, int *num3){

       if (*num3 < *num1){
                  swap(num3, num1);
                      }

           if (*num2 < *num1) {
                      swap(num1, num2);
                          }

               if (*num2 > *num3) {
                          swap(num2, num3);
                              }

                   return;
}

/*** end of order_nums ***/

/************
      function fill_nums
          receives three pointers to integer variables,
                  and writes into these variables numbers entered from the standard input
                      params:
                          - num1 - integer pointer
                              - num2 - integer pointer
                                  - num3 - integer pointer
                                  *************/

int fill_nums (int *num1, int *num2, int *num3){

       int res;

           res = scanf("%d%d%d", num1, num2, num3);

               if (res !=3){
                          printf("Invalid input!\n");
                                  return 0;
                                      }
                   return 1;

}

/*** end of fill_nums ***/

/*** main ***/

int main() {

       int num1, num2, num3;
           printf("Please enter 3 numbers: ");

               if ((fill_nums(&num1, &num2, &num3)) == 1) {

                          order_nums(&num1, &num2, &num3);
                                  printf("%d, %d, %d\n", num1, num2, num3);
                                          return 0;

                                              }

                   return 1;
}

/*** end of main ***/
