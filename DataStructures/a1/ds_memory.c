/** STUDENT INFO **
* Jeremy Cross
* jcross04@uoguelph.ca
* 1048495
**/

#include "ds_memory.h"
#include <stdio.h>

struct ds_file_struct ds_file;
struct ds_counts_struct ds_counts;

int ds_create(char * filename, long size) {

    int i;
    int check;
    unsigned char c = 0;

    ds_file.block[0].start = 0;
    ds_file.block[0].length = size;
    ds_file.block[0].alloced = 0;

    for(i = 1; i < MAX_BLOCKS; i++){
        ds_file.block[i].start = 0;
        ds_file.block[i].length = 0;
        ds_file.block[i].alloced = 0;
    }

    ds_file.fp = fopen(filename, "wb");

    if(ds_file.fp == NULL){ /*Check that file was opened properly*/
        return -1;
    }

    check = fwrite(ds_file.block, sizeof(struct ds_blocks_struct), MAX_BLOCKS, ds_file.fp);

    if(check != MAX_BLOCKS){ /*Check for writing error*/
        return -2;
    }

    for(i = 0; i < size; i++){
        check = fwrite(&c, sizeof(c), 1, ds_file.fp);
        if(check != 1){ /*Check for writing error, fwrite returns number of items written*/
            return -3;
        }
    }

    check = fclose(ds_file.fp);

    if(check != 0){ /*Check for closing file error*/
        return -4;
    }

    return 0;
}


int ds_init(char * filename) {

    int check;

    ds_file.fp = fopen(filename, "rb+");

    if(ds_file.fp == NULL){ /*Check for opening error*/
        return -1;
    }
    fseek(ds_file.fp, 0, SEEK_SET); /*Cursor in file should be at beginning but just in case set cursor to start of file*/

    /*Read in block array from binary file*/
    check = fread(ds_file.block, sizeof(struct ds_blocks_struct), MAX_BLOCKS, ds_file.fp);

    if(check != MAX_BLOCKS){ /*Check that reading was successful*/
        return -2;
    }

    /*Set reads and writes to 0*/
    ds_counts.reads = 0;
    ds_counts.writes = 0;

    return 0;

}


long ds_malloc(long amount) {

    int i;
    int j;
    long start = -1;
    long leftover;

    /*Search for adequately sized block that is unalloced*/
    for(i = 0; i < MAX_BLOCKS; i++){
        if(ds_file.block[i].length >= amount && ds_file.block[i].alloced == 0){ /*Block to store memory found*/

            start = ds_file.block[i].start;
            leftover = (ds_file.block[i].length - amount);
            ds_file.block[i].length = amount;
            ds_file.block[i].alloced = 1;

            /*Block to store memory found now check for a block to store the remainder of the memory that is unused*/
            for(j = 0; j < MAX_BLOCKS; j++){
                if(ds_file.block[j].length == 0){
                    ds_file.block[j].length = leftover;
                    ds_file.block[j].start = start + amount;
                    ds_file.block[j].alloced = 0;
                    return start; /*A place was found for the leftover memory no need to continue searching*/
                }
            }
            return start; /*No place was found for leftover but a place was found for requested memory (nothing to be done)*/
        }
    }

    return -1; /*This should only execute if a block large enough was not found*/
}


void ds_free(long start) {

    int i;

    for(i = 0; i < MAX_BLOCKS; i++){
        if(start == ds_file.block[i].start && ds_file.block[i].alloced == 1){ /*Found a block at start that is allocated, time to free it*/
            ds_file.block[i].alloced = 0;
            return;
        }
    }

    return;
}


void *ds_read(void * ptr, long start, long bytes) {

    int header_offset = (sizeof(struct ds_blocks_struct) * MAX_BLOCKS);
    long location = start + header_offset;
    int check;

    fseek(ds_file.fp, location, SEEK_SET); /*Set cursor in file to start position, with posn 0 right after the header of file*/
    check = fread(ptr, bytes, 1, ds_file.fp);

    if(check != 1){ /*Check if reading worked as intended*/
        return NULL;
    }

    ds_counts.reads++; /*Increment reads*/

    return ptr;
}


long ds_write(long start, void * ptr, long bytes) {

    int header_offset = (sizeof(struct ds_blocks_struct) * MAX_BLOCKS);
    long location = start + header_offset;
    int check;

    fseek(ds_file.fp, location, SEEK_SET); /*Set cursor in file to start position, with posn 0 right after the header of file*/

    check = fwrite(ptr, bytes, 1, ds_file.fp);

    if(check != 1){ /*Check if writing worked as intended*/
        return -1;
    }

    ds_counts.writes++;

    return start;
}


int ds_finish() {

    int check;

    fseek(ds_file.fp, 0, SEEK_SET); /*Set cursor to beginning*/

    check = fwrite(ds_file.block, sizeof(struct ds_blocks_struct), MAX_BLOCKS, ds_file.fp);
    if(check != MAX_BLOCKS){ /*Check that writing  was successful*/
        return 0;
    }


    printf("reads: %d\n", ds_counts.reads);
    printf("writes: %d\n", ds_counts.writes);

    fclose(ds_file.fp);

    return 1;

}

void print_block() { /*Used for testing functionality of memory management*/

    int i;
    printf("BLOCK#    START    LENGTH    ALLOCED\n");

    for(i = 0; i < MAX_BLOCKS; i++){
        printf(" %d       %ld         %ld         %hd\n", i, ds_file.block[i].start, ds_file.block[i].length, ds_file.block[i].alloced);
    }

    printf("reads =\t %d\n", ds_counts.reads);
    printf("writes=\t %d\n", ds_counts.writes);
    return;
}

int getReads(){
    return ds_counts.reads;
}

int getWrites(){
    return ds_counts.writes;
}
