/*********************
Jeremy Cross
jcross04@uoguelph.ca
1048495
**********************/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "common.h"
#include "principals.h"

principals_struct * getPrincipals(char * argument, long * numPrinci) {
    char* fileName;
    principals_struct * princis;
    char bufferStr[1000];
    char colCont[200];
    FILE* fp;
    long i = 0;

    /*Access file by using argument*/
    fileName = malloc(sizeof(char) * (strlen(argument) + strlen("/title.principals.tsv") + 1));
    strcpy(fileName, argument);
    strcat(fileName, "/title.principals.tsv");

    fp = fopen(fileName, "r");

    if(fp == NULL) {
        printf("Could not find name.basics.tsv\n");
        return NULL;
    }


    /*Generate array*/
    while(fgets(bufferStr, 999, fp) != NULL) {

        get_column(bufferStr, colCont, 3);
        if(strstr(colCont, "actor") != NULL || strstr(colCont, "actress") != NULL) {
            i++;
        }
    }

    *numPrinci = i;

    fseek(fp, 0, SEEK_SET);

    i = 0;

    princis = malloc((*numPrinci) * sizeof(principals_struct));

    while(fgets(bufferStr, 999, fp)) {

        get_column(bufferStr, colCont, 3);
        if(strstr(colCont, "actor") != NULL || strstr(colCont, "actress") != NULL) {
            get_column(bufferStr, colCont, 0);
            revStr(colCont);
            princis[i].tconst = calloc(strlen(colCont) + 1, 1);
            strcpy(princis[i].tconst, colCont);

            get_column(bufferStr, colCont, 2);
            revStr(colCont);
            princis[i].nconst = calloc(strlen(colCont) + 1, 1);
            strcpy(princis[i].nconst, colCont);

            get_column(bufferStr, colCont, 5);
            princis[i].characters = calloc(strlen(colCont) + 1, 1);
            strcpy(princis[i].characters, colCont);
            i++;
        }
    }


    free(fileName);
    fclose(fp);

    return princis;
}

void print_principals(principals_struct * princis, long numPrinc) {

    long i;

    for(i = 0; i < numPrinc; i++) {
        printf("[%s] [%s] %s\n", princis[i].nconst, princis[i].tconst, princis[i].characters);
    }

    return;
}

void free_principals(principals_struct * princis, long numPrinc) {

    long i;

    /*Free all strings in each array element*/
    for(i = 0; i < numPrinc; i++) {
        free(princis[i].nconst);
        free(princis[i].tconst);
        free(princis[i].characters);
    }

    free(princis); /*Free whole array*/
    return;
}


