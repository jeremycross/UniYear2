#include "ds_array.h"
#include "ds_memory.h"
#include <stdio.h>
#include <stdlib.h>

void get(char *filename, int reads[], int writes[], int num);

int main(){

    FILE *write;

    int reads[10], writes[10], i;

    write = fopen("arraydelete.csv", "w");

    fprintf(write, "delete\n");
    fprintf(write, "iteration,reads,,,,,,,,,,,writes,\n");
    fprintf(write, ",10,20,30,40,50,60,70,80,90,100,10,20,30,40,50,60,70,80,90,100,\n");
    for(i = 0; i < 30; i++){

        get("10s.txt", reads, writes, 10);
        get("20s.txt", reads, writes, 20);
        get("30s.txt", reads, writes, 30);
        get("40s.txt", reads, writes, 40);
        get("50s.txt", reads, writes, 50);
        get("60s.txt", reads, writes, 60);
        get("70s.txt", reads, writes, 70);
        get("80s.txt", reads, writes, 80);
        get("90s.txt", reads, writes, 90);
        get("100s.txt", reads, writes, 100);

        fprintf(write, "%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d\n", i + 1,
                reads[0], reads[1], reads[2], reads[3], reads[4], 
                reads[5], reads[6], reads[7], reads[8], reads[9],
                    writes[0], writes[1], writes[2], writes[3], writes[4],
                writes[5], writes[6], writes[7], writes[8], writes[9]);
    }
    fclose(write);
    return 0;
}

void get(char *filename, int reads[], int writes[], int num){
    ds_create("array.bin", 2048);
    ds_create_array();
    ds_init_array();
    ds_read_elements(filename);
    ds_finish_array();
    ds_init_array();
    ds_delete(rand() % num);
    reads[num/10 - 1] = getReads();
    writes[num/10 -1] = getWrites();
    ds_finish_array();
}

