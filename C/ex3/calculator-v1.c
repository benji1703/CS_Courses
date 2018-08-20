#include <stdio.h>

#define ERR_1 "Expecting two integers and an operator"
#define ERR_2 "Expecting two positive integers"
#define ERR_3 "Invalid operator %c"

/**************************************
  *  This is a buggy program
  *  please fix me !!
  ******************************/

int main() {
   int n1, n2;
   char op;
   printf("Enter an arithmetic operation for two positive integer numbers.\n");
   printf("For example: 13+45  or 3 - 12 or 87x 123\n");

   scanf("%d %c %d", &n1, &op, &n2);

   /***  testing input  ***/
   /***  ADD CODE HERE  ***/
   
   /*******************************************************
     if code reaches here then:
     - the operation is valid (+, -, x, /, or :)
     - the two input numbers are positive integers
   *******************************************************/

   printf("Your operation is: %d %c %d = ",n1,op,n2);
   
   /***  parsing operation  ***/
   /***  ADD CODE HERE  ***/

   printf("<! No result yet !>\n");
   return 0;
}
