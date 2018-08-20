#include <stdio.h>

#define ERR_1 "Expecting two integers and an operator"
#define ERR_2 "Expecting two positive integers"
#define ERR_3 "Invalid operator %c"

/**************************************
  *  This is a buggy program
  *  please fix me !!
  ******************************/

int main() {
    int n1, n2, res;
    char op;
    printf("Enter an arithmetic operation for two positive integer numbers.\n");
    printf("For example: 13+45  or 3 - 12 or 87x 123\n");

    res=scanf("%d %c %d", &n1, &op, &n2);

    /***  testing input  ***/

    if (res<3) {
        printf(ERR_1 "\n");
        return 1;
    }

    if (n1 <= 0 || n2 <= 0) {
        printf(ERR_2 "\n");
        return 2;
    }

    if (op != '+' && op != '-' && op != 'x' && op != ':' && op != '/') {
        printf(ERR_3 "\n", op);
        return 3;
    }

    /*******************************************************
      if code reaches here then:
      - the operation is valid (+, -, x, /, or :)
      - the two input numbers are positive integers
    *******************************************************/


    /***  parsing operation  ***/

    int result;
    double result2;
    switch (op) {
        case '+':
            result = n1 + n2;
            break;

        case '-':
            result = n1 - n2;
            break;

        case 'x':
            result = n1 * n2;
            break;

        case '/':
            result = (n1 / n2);
            break;

        case ':':
            result2 = (double) n1 / n2;
            printf("Your operation is: %d %c %d = %.3f\n",n1 ,op, n2, result2);

            return 0;
    }

    printf("Your operation is: %d %c %d = %d\n",n1 ,op, n2, result);

    return 0;
}

