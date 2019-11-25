#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include "text.h"

struct node_struct *txt2words(FILE* fp){

    struct node_struct *head = malloc(sizeof(struct node_struct));
    struct node_struct *next = head;
    char *string = calloc(256, sizeof(char));
    char *freeStr;
    char *sub;

    freeStr = string;

    while(fgets(string, 256, fp) != NULL) {

        if(string[0] != '\n' && strlen(string) > 1) {
            string[strlen(string)-1] = '\0'; /*Remove newline appended to end of word (if its not \n by itself)*/
        }

        while(string[0] != '\0') { /*Create list entries off of current line*/
            sub = get_word(&string);
            next->data = sub;
            next->next = calloc(1, sizeof(struct node_struct));
            next = next->next;
            next->next = NULL;
        }
    }

    next->next = NULL;

    fclose(fp);
    free(freeStr);

    return head;
}

char *get_word(char ** string) {
    char *word;
    int i = 0;

    word = calloc(2, sizeof(char));

    while((*string)[0] == ' '){ /*Ignore any spaces*/
        (*string)++;
    }

    /*Time to get the word*/
    if(isalnum((*string)[0]) || ((*string)[0] == '\'' && isalnum((*string)[1]))) { /*alphanumeric*/

        while(isalnum((*string)[i]) || (*string)[i] == '\n' || (*string)[i] == '-' || (*string)[i] == '\''){

            if((*string)[i] == '-' && (*string)[i+1] == '-') {
                word[++i] = '\0';
                break;
            }

            word[i] = (*string)[i];
            word[++i] = '\0';
            word = realloc(word, (strlen(word)+2)*sizeof(char));
        }

    }
    else if((*string)[0] == '\n') { /*New line character only*/
        word = realloc(word, 2*sizeof(char));
        word[0] = (*string)[0];
        word[1] = '\0';
    }
    else { /*Symbol*/
        while((*string)[i] == (*string)[i+1]) {

            word[i] = (*string)[i];
            word[++i] = '\0';
            word = realloc(word, (strlen(word)+2)*sizeof(char));
        }

        /*Loop ends one symbol too early*/
        word = realloc(word, (strlen(word)+2)*sizeof(char));
        word[i] = (*string)[i];
        word[++i] = '\0';
    }


    i = strlen(word);

    *string += i;

    return word;
}

struct node_struct *search(struct node_struct *list, char *target, int (*compar)(const void *, const void *)){

    return NULL;
}


struct node_struct *copy(struct node_struct *start, struct node_struct *end){

    struct node_struct *temp;
    struct node_struct *head;
    struct node_struct *copy = malloc(sizeof(struct node_struct));

    temp = start;
    head = copy;

    while(temp != end && temp->next != NULL) {

        copy->data = temp->data;
        copy->next = malloc(sizeof(struct node_struct));
        copy = copy->next;
        copy->next = NULL;
        temp = temp->next;

    }

    copy->next = NULL;

    return head;
}

void ftext(FILE* fp, struct node_struct *list){


    struct node_struct *temp = list;
    char *str;
    char *nextStr;
    int lineLen = 0;
    int futLineLen;

    while(temp->next != NULL) {

        str = ((char*)temp->data);

        if(temp->next->data) {
            nextStr = ((char*)temp->next->data);
        }

        printf("%s", str);
        lineLen += strlen(str);

        if(checkPunct(str[strlen(str)-1]) && noSpaceCond(nextStr)){

            futLineLen = lineLen + strlen(nextStr);
            if(futLineLen >= 80) {
                printf("\n");
                lineLen = 0;
            }
            else {
                printf(" ");
                lineLen++;
            }
        }

        if(isalnum(str[strlen(str)-1]) && isalnum(nextStr[0])) {

            futLineLen = lineLen + strlen(nextStr);
            if(futLineLen >= 80) {
                printf("\n");
                lineLen = 0;
            }
            else {
                printf(" ");
                lineLen++;
            }
        }

        temp = temp->next;

    }

    return;
}

int checkPunct(char c) {

    if(c == '"' || c == '!' || c == ';' || c == '.' || c ==',') {
        return 1;
    }

    return 0;
}

int noSpaceCond(char *ptr) {

    if(strcmp(ptr, "--") == 0 || strcmp(ptr, "\"") == 0) {
        return 0;
    }

    return 1;
}

struct node_struct *sort(struct node_struct *list, int (*compar)(const void *, const void *)){

    return NULL;
}

void remove_repeats(struct node_struct *list, int (*compar)(const void *, const void *)){

    return;
}

int length(struct node_struct *list){
    int len = 0;
    struct node_struct *temp;

    temp = list;

    while(temp->next) {
        temp = temp->next;
        len++;
    }

    return len;
}

void free_list(struct node_struct *list, int free_data){

    struct node_struct *temp;

    temp = list;

    if(free_data != 0) { /*Free data as well*/

        while(temp->next != NULL) {
            temp = temp->next;
            free(list->data);
            free(list);
            list = temp;
        }

        free(temp);

    }
    else { /*Free just struct pointers*/

        while(temp->next != NULL) {
            temp = temp->next;
            free(list);
            list = temp;
        }

        free(temp);

    }


    return;
}

void print_list(struct node_struct *head) {

    struct node_struct *temp;

    temp = head;

    while(temp->next != NULL) {
        printf("|%s|\n", (char*)temp->data);
        temp = temp->next;
    }

    return;
}
