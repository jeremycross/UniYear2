/*********************
Jeremy Cross
jcross04@uoguelph.ca
1048495
**********************/

typedef struct tree {
  char* key;
  void* data;
  struct tree *children[2];
} tree;

int treeCmp(char *, char*);
void add_node(tree **, char *, void *);
void free_tree(tree *);
tree * findTree(tree *, char *);
void print_tree(tree *);
void * find_node(tree *, char*);
void findAllName(tree*[], char*);
void findAllTitle(tree*[], char*);
