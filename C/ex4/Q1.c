#include<stdio.h>
#include<math.h>

/***************************************
    This is a C program for computing if a number is prime, and showing all the prime number until this number.
      ************************/


/************************************
    function is prime - using basic logic. ( modulo)
      params:
        - number - integer number
          return value: returns 1 if the number is prime, and 0 otherwise
            ***********************************/


int is_prime(int number) {


       int i;
           int sq = (int) sqrt(number);

               for (i = 2 ;i <= sq ; i++) {
                          if (number % i == 0 ) {
                                         return 0;
                                                 }
                              }

                   return 1;
}

/*** end of is_prime ***/


/*** main ***/

int main() {
       int num, res;
           printf("Please enter an integer greater that 1: ");
               res = scanf("%d", &num);
                   if (res < 1 || num < 2){
                              printf("Invalid input!\n");
                                      return 1;
                                          }

                       int i;
                           for (i = 2; i <= num; i++){
                                      if(is_prime(i) == 1){
                                                     printf("%d is a prime number\n",i);
                                                             }
                                          }
                               return 0;
}

/*** end of main ***/
