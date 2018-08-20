#include<stdio.h>

/*************
     This is a C program that calculate the factorial of numbers and the sum of factorials.
         ********/

/************
     function factorial
        calculating the factorial of a given non-negative integer.
             params:
                - number - integer
                   return value: return the factorial
                    *************/

int factorial (int number){
       int i;
           int sum = 1;
               if(number<0){
                          return -1;
                              }
                   else for ( i=2 ; i <= number ; i++){
                              sum *= i;
                                  }
                       return sum;
}

/*** end of factorial ***/


/************
     function factorial_sum
         This function returns the following sum for a given n: S(n)=Σ(from i=1 to n) i!
               params:
                  - number - integer
                     return value: return the factorial sum
                      *************/

int factorial_sum (int number){

       if (number==0){
                  return 0;
                      }
           if(number==1){
                      return 1;
                          }
               else if(number>1){
                          return factorial(number)+factorial_sum(number-1);
                              }

                   return -1;
}

/*** end of factorial_sum ***/


/************
      function optimized_factorial
          This function has static local variables that hold the result of the previous execution of the function. It uses the static variables to reduce the number
              of calculation steps.
                    params:
                        - number - integer
                            return value: return the factorial
                             *************/

int optimized_factorial (int number){

       static int i = 6;
           static int sum = 720;
               int sum2 = 1;
                   int j;

                       if (number < 0) {
                                  return -1;
                                      }

                           if (number > i) { // the number is bigger than the static
                                      sum2 = sum ;
                                              while(i < number) {
                                                             i++;
                                                                         sum2 *= i;
                                                                                 }

                                                      i = number;
                                                              sum = sum2;
                                                                      return sum;
                                                                          }

                               else if (number == i){ // the number is equal
                                      return sum;
                                          }

                                   else if  (number < i) {
                                              if (i - number > number - 1){ // the distance is larger than 1 to number
                                                             for (j = 2; j <= number; j++) {
                                                                                sum2 *= j;
                                                                                            }
                                                                         i = number;
                                                                                     sum = sum2;
                                                                                                 return sum;
                                                                                                         }

                                                      else { // we compute backwards
                                                                     sum2 = sum ;
                                                                                 for ( j = i ; j > number ; j--){
                                                                                                    sum2 /= j;
                                                                                                                }

                                                                                         }
                                                              i = number;
                                                                      sum = sum2;
                                                                              return sum;
                                                                                  }

                                       return -1;
}

/*** end of optimized_factorial ***/


/************
     function optimized_factorial_sum
        This function is identical to the previous factorial_sum , but the calls to function factorial is replaced with calls to function
           optimized_factorial.
                params:
                   - number - integer
                      return value: return the factorial sum

                       *************/

int optimized_factorial_sum (int number){

       if (number == 0) {
                  return 0;
                      }
           else if(number==1){
                      return 1;
                          }
               else if(number>1){
                          return optimized_factorial(number)+optimized_factorial_sum(number-1);
                              }
                   return -1;
}

/*** end of optimized_factorial_sum ***/


/*** main ***/

int main(void) {
       int numbers[] = {5, 10, 4, 11, 7, 2, 0, -1, 3, 1};
           int numbers_length = sizeof(numbers) / sizeof(*numbers);

               int i = 0;

                   printf("### Testing non-optimized functions...\n\n");
                       for (i = 0 ; i < numbers_length ; ++i) {
                                  printf("Factorial for %d: %d\n", numbers[i], factorial(numbers[i]));
                                      }
                           printf("\n");
                               for (i = 0 ; i < numbers_length ; ++i) {
                                          printf("Sum of factorials for %d: %d\n", numbers[i], factorial_sum(numbers[i]));
                                              }

                                   printf("\n\n### Testing optimized functions...\n\n");
                                       for (i = 0 ; i < numbers_length ; ++i) {
                                                  printf("Factorial for %d: %d\n", numbers[i], optimized_factorial(numbers[i]));
                                                      }
                                           printf("\n");
                                               for (i = 0 ; i < numbers_length ; ++i) {
                                                          printf("Sum of factorials for %d: %d\n", numbers[i], optimized_factorial_sum(numbers[i]));
                                                              }

                                                   return 0;
}

/*** end of main ***/
