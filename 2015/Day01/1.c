#include <stdio.h>

int main(int argc, char *argv[]) {
    if (argc != 2) {
        printf("Incorrect number of arguments\n");
        return 0;
    }

    FILE *f = fopen(argv[1], "rw");
    int total = 0;
    int c = fgetc(f);
    int intValues[2] = {(int) '(', (int) ')'};

    while (c != EOF && c != (int) '\n') {
        if (c == intValues[0]) {
            total++;
        } else {
            total--;
        }

        c = fgetc(f);
    }

    fclose(f);
    printf("Santa is on floor:%d", total);
    return 0;
}