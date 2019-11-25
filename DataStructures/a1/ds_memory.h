/** STUDENT INFO **
* Jeremy Cross
* jcross04@uoguelph.ca
* 1048495
**/

#include <stdio.h>
#define MAX_BLOCKS 4096

struct ds_counts_struct {
    int reads;
    int writes;
};

struct ds_blocks_struct {
    long start;
    long length;
    char alloced;
};

struct ds_file_struct {
    FILE* fp;
    struct ds_blocks_struct block[MAX_BLOCKS];
};

void print_block();
int getReads();
int getWrites();
int ds_create(char *, long size);
int ds_init(char *);
long ds_malloc(long);
void ds_free(long);
void * ds_read(void *, long, long);
long ds_write(long, void *, long);
int ds_finish();
