#include "ds_array.h"
#include "ds_memory.h"
#include <stdio.h>

int main()

{

    ds_create("array.bin", 2048);
    ds_init_array();
    ds_read_elements("numbers.txt");
    print_array();
    printf("Return of insert: %d\n", ds_insert(9, 0));
    printf("Return of insert: %d\n", ds_insert(80, 5));
    printf("Return of insert: %d\n", ds_insert(11, 12));
    print_array();
    printf("Return of delete: %d\n", ds_delete( 8));
    printf("Return of delete: %d\n", ds_delete(11));
    print_array();
    ds_finish_array();
    return 0;

}
