/*********************
Jeremy Cross
jcross04@uoguelph.ca
1048495
**********************/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "title.h"
#include "common.h"


title_basics * getTitles(char * argument, long * numTitle) {

    char* fileName;
    title_basics * titles;
    char bufferStr[1000];
    char colCont[200];
    char colCont2[20];
    FILE* fp;
    long i = 0;

    /*Access file by using argument*/
    fileName = malloc(sizeof(char) * (strlen(argument) + strlen("/title.basics.tsv") + 1));
    strcpy(fileName, argument);
    strcat(fileName, "/title.basics.tsv");



    fp = fopen(fileName, "r");

    if(fp == NULL) {
        printf("Could not find name.basics.tsv\n");
        return NULL;
    }


    /*Generate array*/
    while(fgets(bufferStr, 999, fp) != NULL) {

        get_column(bufferStr, colCont, 1);
        get_column(bufferStr, colCont2, 4);
        if(strstr(colCont, "movie") != NULL && strstr(colCont2, "0") != NULL) {
            i++;
        }
    }

    *numTitle = i;

    fseek(fp, 0, SEEK_SET);

    i = 0;

    titles = malloc((*numTitle) * sizeof(title_basics));

    while(fgets(bufferStr, 999, fp) != NULL) {

        get_column(bufferStr, colCont, 1);
        get_column(bufferStr, colCont2, 4);
        if(strstr(colCont, "movie") != NULL && strstr(colCont2, "0") != NULL) {
            get_column(bufferStr, colCont, 0);
            revStr(colCont);
            titles[i].tconst = calloc(strlen(colCont) + 1, 1);
            strcpy(titles[i].tconst, colCont);


            get_column(bufferStr, colCont, 2);
            titles[i].primaryTitle = calloc(strlen(colCont) + 1, 1);
            strcpy(titles[i].primaryTitle, colCont);
            i++;
        }

    }


    free(fileName);
    fclose(fp);

    return titles;
}


void print_titles(title_basics * titles, long numTitles) {

    long i;

    for(i = 0; i < numTitles; i++) {
        printf("[%s] [%s]\n", titles[i].tconst, titles[i].primaryTitle);
    }

    return;
}

void free_titles(title_basics * titles, long numTitles) {

    long i;

    /*Free all strings in each array element*/
    for(i = 0; i < numTitles; i++) {
        free(titles[i].tconst);
        free(titles[i].primaryTitle);
    }

    free(titles); /*Free whole array*/

    return;
}


