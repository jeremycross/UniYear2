/*********************
Jeremy Cross
jcross04@uoguelph.ca
1048495
**********************/

typedef struct name_basics {

    char* nconst;
    char* primaryName;

}name_basics;

name_basics * getNames(char*, long*);
void print_names(name_basics *, long);
void free_names(name_basics *, long);

