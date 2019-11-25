#include "ds_list.h"
#include "ds_memory.h"
#include <stdio.h>

int main(){

    ds_create("list.bin", 2048);
    ds_create_list();
    ds_init_list();
    ds_read_elements("numbers.txt");
    show_list();
    printf("Return of delete: %d\n", ds_delete(5));
    show_list();
    printf("Return of insert: %d\n", ds_insert(6, 8));
    printf("Return of insert: %d\n", ds_insert(0, 9));
    show_list();
    /*printf("Return of replace: %d\n", ds_replace(2, 8));
    printf("Return of swap: %d\n", ds_replace(0, 10));*/
    show_list();
    ds_finish_list();

    return 0;
}
