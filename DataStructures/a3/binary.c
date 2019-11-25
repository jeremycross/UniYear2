/*********************
Jeremy Cross
jcross04@uoguelph.ca
1048495
**********************/


#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "binary.h"
#include "name.h"
#include "title.h"
#include "principals.h"

int treeCmp(char * addKey, char* treeKey) {
    return strcmp(addKey, treeKey);
    
}

void add_node(tree ** root, char* addKey, void * data) {
    /*Note this function was heavily inspired by "fixed_tree.c"
    found on courselink for cis2520*/

    if(*root) { /*Node here so we will continue down tree*/
        if(treeCmp(addKey, (*root)->key) < 0) {
            add_node( &((*root)->children[0]), addKey, data);
        }
        else {
            add_node( &((*root)->children[1]), addKey, data);
        }
    }
    else {
        /*No node here yet, allocate and set values to add node to tree*/

        (*root) = malloc(sizeof(tree));
        (*root)->key = addKey;
        (*root)->data = data;
        (*root)->children[0] = NULL;
        (*root)->children[1] = NULL;
    }
    return;
}

void free_tree(tree * root) {

    /*Note this function was heavily inspired by "fixed_tree.c"
    found on courselink for cis2520*/

    if(root) {

        free_tree(root->children[0]);
        free_tree(root->children[1]);
        free(root);
    }
    return;
}

void print_tree(tree * root) {

    /*Note this function was heavily inspired by "fixed_tree.c"
    found on courselink for cis2520*/

    if(root) {

        printf("key: %s, data: %s\n", root->key, ((name_basics *)root->data)->nconst);
        print_tree(root->children[0]);
        print_tree(root->children[1]);

    } else {
        printf("NULL\n");
    }
}

void * find_node(tree * root, char* key) {

    /*Note this function was heavily inspired by "fixed_tree.c"
    found on courselink for cis2520*/

    if(root) {
        if(strcmp(root->key, key) == 0) {
            return root->data;
        }
        else {
            if(treeCmp(key, root->key) < 0) {
                return find_node(root->children[0], key);
            }
            else {
                return find_node(root->children[1], key);
            }
        }
    }
    return NULL;

}

tree * findTree(tree * root, char * key) {
    if(root) {
        if(strcmp(root->key, key) == 0) {
            return root;
        }
        else {
            if(treeCmp(key, root->key) < 0) {
                return findTree(root->children[0], key);
            }
            else {
                return findTree(root->children[1], key);
            }
        }
    }
    return NULL;

}

void findAllName(tree * root[3], char * search) {

    void * nameStr;
    void * titleStr;
    void * prinStr;
    char * nameFound;
    tree * nextRoot;

    nameStr = find_node(root[0], search);

    if(!nameStr) { /*Name not here*/
        return;
    }

    search = ((name_basics*)nameStr)->nconst;
    nameFound = search;

    nextRoot = findTree(root[1], nameFound);

    /*Find every movie/character set*/
    while(nextRoot) {
        
        prinStr = find_node(nextRoot, nameFound);

        if(prinStr) {
            search = ((principals_struct*)prinStr)->tconst;

            titleStr = find_node(root[2], search);

            if(titleStr) {
                printf("%s : %s", ((title_basics*)titleStr)->primaryTitle, ((principals_struct*)prinStr)->characters);
            }
            
        }
        
	nextRoot = findTree(nextRoot->children[1], nameFound);
    }
}

void findAllTitle(tree * root[3], char * search) {

    void * nameStr;
    void * titleStr;
    void * prinStr;
    char * titleFound;
    tree * nextRoot;

    titleStr = find_node(root[0], search);

    if(!titleStr) { /*Title not here*/
        return;
    }
    
    search = ((title_basics*)titleStr)->tconst;
    titleFound = search;

    nextRoot = findTree(root[1], titleFound);

    /*Find every Actor/Character set*/
    while(nextRoot) {

        prinStr = find_node(nextRoot, titleFound);

        if(prinStr) {
            search = ((principals_struct*)prinStr)->nconst;

            nameStr = find_node(root[2], search);

            if(nameStr) {
                printf("%s : %s", ((name_basics*)nameStr)->primaryName, ((principals_struct*)prinStr)->characters);
            }
        }

         nextRoot = findTree(nextRoot->children[1], titleFound);
    } 

}

