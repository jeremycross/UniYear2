/*********************
Jeremy Cross
jcross04@uoguelph.ca
1048495
**********************/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "common.h"

void revStr(char * revStr) {

    int j = 0;
    int i;
    char * str;
    int len = strlen(revStr);
    str = malloc(len + 1);
    strcpy(str, revStr);

    for(i = len - 1; i >= 0; i--) {
        revStr[i] = str[j++];
    }

    free(str);
    return;
}

int getSearch(char ** input) {

    int start = 0;
    int i = 0;
    char * str = malloc(1000);
    char * reset = str;
    char * startSearch;
    int opt;

    fgets(str, 1000, stdin);

    if(strstr(str, "name")) {
         opt = 1;
    }
    else if(strstr(str, "title")) {
         opt = 2;
    }
    else {
         return 0;
    }

    while(str[0] == ' ' || str[0] == '\t') {
         str++;
    }

    while(str[0] != ' ' && str[0] != '\t') {
         str++;
    }

    startSearch = str;
    while(str[0] != '\n') {
        if(start == 1) {
            i++;
        }
        else if(str[0] != ' ' && str[0] != '\t') {
            start = 1;
            i++;
        }
        str++;
    }

    str[0] = '\0';

    (*input) = calloc(i, 1);

    str = startSearch;

    while(str[0] == ' ' || str[0] == '\t') {
        str++;
    }

    strcpy(*input, str);
    free(reset);
    return opt;
}

void get_column(char * line, char colContents[200], int col) {

    int i = 0;

    while(i != col) {
        if(line[0] == '\t') {
            i++;
        }
        line++;
    }
    i = 0;
    while(line[0] != '\t' && line[0] != '\0') {
        colContents[i] = line[0];
        i++;
        line++;
    }

    colContents[i] = '\0';

    return;
}
