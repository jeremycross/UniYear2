#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include "text.h"

struct node_struct *txt2words(FILE* fp){

    struct node_struct *head;
    struct node_struct *next;
    struct node_struct *curr;
    char *string = calloc(256, sizeof(char));
    char *freeStr;
    int firstLoop = 0;
    char *sub;

    freeStr = string;

    while(fgets(string, 256, fp) != NULL) {

        if(string[0] != '\n' && strlen(string) > 1) {
            string[strlen(string)-1] = '\0'; /*Remove newline appended to end of word (if its not \n by itself)*/
        }

        while(string[0] != '\0') { /*Create list entries off of current line*/


            if(firstLoop == 0) {
                next = calloc(1, sizeof(struct node_struct));
                head = next;
                firstLoop = 1;
            }
            sub = get_word(&string);
            /*set current nodes data*/
            next->data = sub;
            curr = next;

            next->next = calloc(1, sizeof(struct node_struct)); /*Allocate for next node*/
            next = next->next; /*Set next to next node*/
        }
        string = freeStr;
    }

    curr->next = NULL; /*Last node's next should be NULL*/
    free(next); /*Free unneeded malloc*/

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

    struct node_struct *temp;
    struct node_struct *head;
    struct node_struct *searchNode;
    struct node_struct *prev;

    head = calloc(1, sizeof(struct node_struct));

    searchNode = head;
    temp = list;

    while(temp) {

        /*Found matching node*/
        if(compar(temp->data, (void *)target) == 0) {
            searchNode->data = temp;
            prev = searchNode;
            searchNode->next = calloc(1, sizeof(struct node_struct));
            searchNode = searchNode->next;
        }

        temp = temp->next;
    }

    prev->next = NULL;
    free(searchNode);

    return head;
}

int strcmpVoid(const void *a, const void *b) {

    char *str_a;
    char *str_b;

    str_a = (char*)a;
    str_b = (char*)b;

    return strcmp(str_a, str_b);
}

struct node_struct *copy(struct node_struct *start, struct node_struct *end){

    struct node_struct *temp;
    struct node_struct *head;
    struct node_struct *prev;
    struct node_struct *cop = malloc(sizeof(struct node_struct));

    temp = start;
    head = cop;

    while(temp != end && temp != NULL) {

        cop->data = temp->data;
        prev = cop;

        cop->next = malloc(sizeof(struct node_struct));
        cop = cop->next;
        temp = temp->next;

    }

    prev->next = NULL;
    free(cop);

    return head;
}

void ftext(FILE* fp, struct node_struct *list){


    struct node_struct *temp = list;
    char *str;
    char *nextStr;
    int lineLen = 0;
    int futLineLen;

    while(temp != NULL) {

        str = ((char*)temp->data);

        if(temp->next) {
            nextStr = ((char*)temp->next->data);
        } else {
            nextStr = NULL;
        }

        fprintf(fp, "%s", str);
        lineLen += strlen(str);

        if(nextStr == NULL) {

        }
        else {

            if(checkPunct(str[strlen(str)-1]) && noSpaceCond(nextStr)){

                futLineLen = lineLen + strlen(nextStr);
                if(futLineLen >= 80) {
                    fprintf(fp, "\n");
                    lineLen = 0;
                }
                else {
                    fprintf(fp, " ");
                    lineLen++;
                }
            }

            if(isalnum(str[strlen(str)-1]) && isalnum(nextStr[0])) {

                futLineLen = lineLen + strlen(nextStr);
                if(futLineLen >= 80) {
                    fprintf(fp, "\n");
                    lineLen = 0;
                }
                else {
                    fprintf(fp, " ");
                    lineLen++;
                }
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

    struct node_struct *temp;
    struct node_struct *prev;
    void *toRemove;


    prev = list;
    toRemove = prev->data;
    temp = list->next;


    while(temp) {

        /*Found matching node must remove*/
        if(compar(temp->data, toRemove) == 0) {
            prev->next = temp->next;
            free(temp);
            temp = prev->next;
        } else {
            prev = temp;
            toRemove = prev->data;
            temp = temp->next;
        }

    }


    return;
}

int length(struct node_struct *list){
    int len = 0;
    struct node_struct *temp;

    temp = list;

    while(temp) {
        temp = temp->next;
        len++;
    }

    return len;
}

void free_list(struct node_struct *list, int free_data){

    struct node_struct *temp;

    temp = list;

    if(free_data != 0) { /*Free data as well*/

        while(temp) {
            temp = temp->next;
            free(list->data);
            free(list);
            list = temp;
        }

    }
    else { /*Free just struct pointers*/

        while(temp != NULL) {
            temp = temp->next;
            free(list);
            list = temp;
        }

    }


    return;
}

void print_list(struct node_struct *head) {

    struct node_struct *temp;

    temp = head;

    while(temp) {
        printf("|%s| %p\n", (char*)temp->data, (void*)temp->next);
        temp = temp->next;
    }

    return;
}

void print_search(struct node_struct *head) {

    struct node_struct *temp;

    temp = head;

    while(temp) {
        printf("|%p| %p\n", temp->data, (void*)temp->next);
        temp = temp->next;
    }


    return;
}
