/** STUDENT INFO **
* Jeremy Cross
* jcross04@uoguelph.ca
* 1048495
**/

#include "ds_array.h"
#include "ds_memory.h"
#include <stdio.h>

long elements;

int ds_create_array() {

    elements = 0;
    if(ds_init("array.bin") != 0){
        return 1;
    }

    if(ds_malloc(sizeof(long)) == -1){ /*Allocated memory for long at beginning of file*/
        return 1;
    }


    if(ds_write(0, &elements, sizeof(long)) == -1){ /*Write 0 for elements in file*/
        return 1;
    }

    if(ds_malloc((sizeof(int) * MAX_ELEMENTS)) == -1){
        return 1;
    }

    if(!(ds_finish())){
        return 1;
    }

    return 0;
}

int ds_init_array() {


    if(ds_init("array.bin") != 0){
        return 1;
    }

    if(ds_read(&elements, 0, sizeof(long)) == NULL){
        return 1;
    }

    return 0;
}

int ds_replace(int value, long index) {

    if(index >= elements || index < 0){ /*If index is elements we are out of range of valid indexes, index cannot be negative, if either is true, error and do nothing*/
        return 1;
    }

    if(ds_write(8+(index*sizeof(int)), &value, sizeof(int)) == -1){ /*Write new value to index*/
        return 1;
    }

    return 0;
}


int ds_insert(int value, long index) {

    int oldValue;
    int newValue = value;
    int i;

    if(index > elements || index < 0){ /*If index is greater than elements we are out of range of valid indexes, index cannot be negative, if either is true, error and do nothing*/
        return 1;
    }

    if(index == elements){
        elements++;
        if(ds_write(8+(index*sizeof(int)), &newValue, sizeof(int)) == -1){ /*Write new value to current index*/
            elements--;
            return 1;
        }
    }
    else{
        elements++;
        for(i = index; i < elements; i++){
            if(ds_read(&oldValue, 8+(i*sizeof(int)), sizeof(int)) == NULL){ /*Grab value at our current index*/
                elements--;
                return 1;
            }
            if(ds_write(8+(i*sizeof(int)), &newValue, sizeof(int)) == -1){ /*Write new value to current index*/
                elements--;
                return 1;
            }
            newValue = oldValue; /*For next loop our new value will be the oldValue*/
        }
    }
    return 0;
}

int ds_delete(long index) {

    int a = 0;
    int i;
    int newValue;

    if(index >= elements || index < 0){ /*If index is elements we are out of range of valid indexes, index cannot be negative, if either is true, error and do nothing*/
        return 1;
    }

    if(index == elements-1){
        elements--; /*Reduce total number of elements in array*/
        if(ds_write(8+(index*sizeof(int)), &a, sizeof(int)) == -1){ /*Write 0 in deleted spot*/
            return 1;
        }
    }
    else{

        for(i = index; i < (elements-1); i++){
            if(ds_read(&newValue, 8+((i+1)*sizeof(int)), sizeof(int)) == NULL){ /*Take element after current element*/
                return 1;
            }
            if(ds_write((8+(i*sizeof(int))), &newValue, sizeof(int)) == -1){ /*Overwrite current element with next element*/
                return 1;
            }
        }
        elements--;
    }

    return 0;
}

int ds_swap(long index1, long index2) {

    int value1, value2;

    if(index1 == index2){ /*No need to swap*/
        return 1;
    }

    if(index1 >= elements || index1 < 0){ /*If index is elements we are out of range of valid indexes, index cannot be negative, if either is true, error and do nothing*/
        return 1;
    }

    if(index2 >= elements || index2 < 0){ /*If index is elements we are out of range of valid indexes, index cannot be negative, if either is true, error and do nothing*/
        return 1;
    }

    if(ds_read(&value1, 8+(index1*sizeof(int)), sizeof(int)) == NULL){ /*Take value at index1*/
        return 1;
    }

    if(ds_read(&value2, 8+(index2*sizeof(int)), sizeof(int)) == NULL){ /*Take value at index2*/
        return 1;
    }

    if(ds_write(8+(index2*sizeof(int)), &value1, sizeof(int)) == -1){ /*Overwrite value at index2 with value at index1*/
        return 1;
    }

    if(ds_write(8+(index1*sizeof(int)), &value2, sizeof(int)) == -1){ /*Overwrite value at index1 with value at index2*/
        return 1;
    }

    return 0;
}

long ds_find(int target) {

    long i;
    int currValue;

    for(i = 0; i < elements; i++){
        if(ds_read(&currValue, 8+(i*sizeof(int)), sizeof(int)) == NULL){ /*Check for reading error*/
            return -1;
        }

        if(currValue == target){ /*Target has been found, return current index*/
            return i;
        }
    }

    return -1; /*No match was found*/
}

int ds_read_elements(char * filename) {

    FILE* fp;
    int value;
    long currIndex = 0;

    fp = fopen(filename, "r");

    while(fscanf(fp, "%d\n", &value) != EOF){
        if(ds_insert(value, currIndex) != 0){
            return 1;
        }
        currIndex++;
    }


    fclose(fp);

    return 0;
}

int ds_finish_array() {

    if(ds_write(0, &elements, sizeof(long)) == -1){
        return 1;
    }

    if(ds_finish() == 0){
        return 1;
    }

    return 0;
}

void print_array(){

    int i;
    int value;

    /*print_block();*/
    printf("elements = %ld\n", elements);

    for(i = 0; i < elements; i++){
        ds_read(&value, sizeof(long)+(i*4), sizeof(int));
        printf("%d = %d\n", i, value);
    }

    return;
}
