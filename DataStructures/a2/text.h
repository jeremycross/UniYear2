#include <stdio.h>

struct node_struct{
    void * data;
    struct node_struct * next;
};

struct node_struct *txt2words(FILE*);
char *get_word(char **);
struct node_struct *search(struct node_struct *, char *, int (*compar)(const void *, const void *));
struct node_struct *copy(struct node_struct *, struct node_struct *);
void ftext(FILE*, struct node_struct *);
int checkPunct(char);
int noSpaceCond(char *);
struct node_struct *sort(struct node_struct *, int (*compar)(const void *, const void *));
void remove_repeats(struct node_struct *, int (*compar)(const void *, const void *));
int length(struct node_struct *);
void free_list(struct node_struct *, int);
void print_list(struct node_struct *);
