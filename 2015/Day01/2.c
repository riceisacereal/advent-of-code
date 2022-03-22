#include <stdio.h>

int main(int argc, char *argv[]) {
    if (argc != 2) {
        printf("Incorrect number of arguments\n");
        return 0;
    }

    FILE *f = fopen(argv[1], "rw");
    int intValues[2] = {(int) '(', (int) ')'};
    int total = 0;
    int position = 1;

    int c = fgetc(f);
    while (c != EOF && c != (int) '\n') {
        if (c == intValues[0]) {
            total++;
        } else {
            total--;
        }

        if (total < 0) {
            break;
        }

        position++;
        c = fgetc(f);
    }

    fclose(f);
    printf("Santa entered the basement on position: %d", position);
    return 0;
}