#include<stdio.h>
#include<stdlib.h>

/*************
     This is a C program that calculate and print Pi using random numbers.
         ********/

/************
     function calc_pi
        This function receives an integer representing the number of
           sampled points n, and returns an approximation for π calculated using the Monte
              Carlo method.
                 params:
                    - n - integer
                     *************/

double calc_pi (int n) {   //the program randoms points in circle and than diterminate what is the value of pi

       if (n < 1) {
                  return -1;
                      }

           double pi;
               int i;
                   double x = (double)rand()/ (double)RAND_MAX;
                       double y = (double)rand()/ (double)RAND_MAX;
                           double count = 0.0 ;
                               double r = 1.0; // Given by the example radios=1;

                                   for (i = 0 ; i < n ; i++) {

                                              if ((x*x) + (y*y) < r*r ) {
                                                             count = count + 1.0 ;
                                                                     }

                                                      x = (double)rand()/ (double)RAND_MAX;
                                                              y = (double)rand()/ (double)RAND_MAX;
                                                                  }
                                       pi = ((count /(double)n) * 4.0) ;
                                           return pi;
}

/*** main ***/

int main() {
       printf("Please enter a positive number: ");
           int num, res;
               res = scanf("%d", &num);
                   if (num < 1 || res != 1) {
                              printf("Invalid input!\n");
                                      return 1;
                                          }
                       printf ("The approximated value of PI using %d points is: %f\n" ,num,calc_pi(num));
                           calc_pi(num);
                               return 0;
}

/*** end of main ***/
