/*********************
Jeremy Cross
jcross04@uoguelph.ca
1048495
**********************/

typedef struct title_basics {
    char* tconst;
    char* primaryTitle;
}title_basics;

title_basics * getTitles(char *, long *);
void print_titles(title_basics *, long);
void free_titles(title_basics *, long);
