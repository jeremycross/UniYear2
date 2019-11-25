/*********************
Jeremy Cross
jcross04@uoguelph.ca
1048495
**********************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "name.h"
#include "common.h"

name_basics * getNames(char * argument, long * numNames) {
    char* fileName;
    name_basics * names;
    char bufferStr[1000];
    char colCont[200];
    FILE* fp;
    long i = 0;

    /*Access file by using argument*/
    fileName = malloc(sizeof(char) * (strlen(argument) + strlen("/name.basics.tsv") + 1));
    strcpy(fileName, argument);
    strcat(fileName, "/name.basics.tsv");


    fp = fopen(fileName, "r");

    if(fp == NULL) {
        printf("Could not find name.basics.tsv\n");
        return NULL;
    }


    /*Generate array*/
    while(fgets(bufferStr, 999, fp) != NULL) {

        get_column(bufferStr, colCont, 4);
        if(strstr(colCont, "actor") != NULL || strstr(colCont, "actress") != NULL) {
            i++;
        }
    }

    *numNames = i;

    fseek(fp, 0, SEEK_SET);

    names = malloc((i) * sizeof(name_basics));
    i = 0;

    while(fgets(bufferStr, 999, fp) != NULL) {

        get_column(bufferStr, colCont, 4);
        if(strstr(colCont, "actor") != NULL || strstr(colCont, "actress") != NULL) {
            get_column(bufferStr, colCont, 0);
            revStr(colCont);
            names[i].nconst = calloc(strlen(colCont) + 1, 1);
            strcpy(names[i].nconst, colCont);

            get_column(bufferStr, colCont, 1);
            names[i].primaryName = calloc(strlen(colCont) + 1, 1);
            strcpy(names[i].primaryName, colCont);
            i++;
        }

    }

    free(fileName);
    fclose(fp);

    return names;
}

void print_names(name_basics * names, long numNames) {

    long i;

    for(i = 0; i < numNames; i++) {
        printf("[%s] [%s]\n", names[i].nconst, names[i].primaryName);
    }

    return;
}

void free_names(name_basics * names, long numNames) {

    long i;

    /*Free all strings in each array element*/
    for(i = 0; i < numNames; i++) {
        free(names[i].nconst);
        free(names[i].primaryName);
    }

    free(names); /*Free whole array*/

    return;
}
