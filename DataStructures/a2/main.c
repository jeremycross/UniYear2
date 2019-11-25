#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "text.h"

int main()
{

    FILE* fp = fopen("1342.txt", "r");
    FILE* fp1 = fopen("output.txt", "w");
    struct node_struct* head;
    struct node_struct* co;
    struct node_struct* ser;

    head = txt2words(fp);

    print_list(head);
    ftext(fp1, head);
    printf("Length %d\n", length(head));


    co = copy(head, NULL);
    printf("Copy length %d\n", length(co));

    print_list(co);
    remove_repeats(co, strcmpVoid);
    print_list(co);

    ser = search(head, "gutenberg",strcmpVoid);

    free_list(head, 1);
    free_list(co, 0);
    free_list(ser, 0);
    fclose(fp);
    fclose(fp1);

    return 0;
}
