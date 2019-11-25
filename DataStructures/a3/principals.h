/*********************
Jeremy Cross
jcross04@uoguelph.ca
1048495
**********************/

typedef struct title_principals {

    char *tconst;
    char *nconst;
    char *characters;
} principals_struct;

principals_struct * getPrincipals(char *, long *);
void print_principals(principals_struct *, long);
void free_principals(principals_struct *, long);



