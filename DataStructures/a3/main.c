/*********************
Jeremy Cross
jcross04@uoguelph.ca
1048495
**********************/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "binary.h"
#include "name.h"
#include "principals.h"
#include "title.h"
#include "common.h"


int main(int argc, char *argv[]) {

    principals_struct * princis;
    title_basics * titles;
    name_basics * names;
    long numNames = 0;
    tree * rootName[2];
    tree * rootTitle[2];
    tree * rootPrin[2];

    tree * nameSearch[3];
    tree * titleSearch[3];
    long numTitles = 0;
    long numPrinc = 0;

    int i;
    int opt;
    char * searchString;
    rootName[0] = NULL;
    rootName[1] = NULL;
    rootTitle[0] = NULL;
    rootTitle[1] = NULL;
    rootPrin[0] = NULL;
    rootPrin[1] = NULL;

    if(argc != 2) {
        printf("Incorrect number of arguments entered, program requires a single argument that corresponds to the location of the .tsv files\n");
        return -1;
    }


    /*Get initial input*/
    printf("> ");

    opt = getSearch(&searchString);

    if(opt == 0) { /*No proper option entered*/
        return -2;
    }

    /*Generate Arrays*/
    names = getNames(argv[1], &numNames);
    titles = getTitles(argv[1], &numTitles);
    princis = getPrincipals(argv[1], &numPrinc);

    /*Generate trees*/
    for(i = 0; i < numNames; i++) {
        add_node( &(rootName[0]), names[i].nconst, &(names[i]) );
        add_node( &(rootName[1]), names[i].primaryName, &(names[i]) );
    }

    for(i = 0; i < numTitles; i++) {
        add_node( &(rootTitle[0]), titles[i].tconst, &(titles[i]) );
        add_node( &(rootTitle[1]), titles[i].primaryTitle, &(titles[i]) );
    }

    for(i = 0; i < numPrinc; i++) {
        add_node( &(rootPrin[0]), princis[i].tconst, &(princis[i]) );
        add_node( &(rootPrin[1]), princis[i].nconst, &(princis[i]) );
    }

    /*Set up searching tree arrays*/
    nameSearch[0] = rootName[1];
    nameSearch[1] = rootPrin[1];
    nameSearch[2] = rootTitle[0];

    titleSearch[0] = rootTitle[1];
    titleSearch[1] = rootPrin[0];
    titleSearch[2] = rootName[0];

    /*Loop searching for titles and names (leave by not writing name or title properly)*/
    do {
        if(opt == 1) {
            findAllName(nameSearch, searchString);
        }
        else if(opt == 2) {
            findAllTitle(titleSearch, searchString);
        }
        free(searchString);
        printf("> ");
        opt = getSearch(&searchString);

    } while( opt != 0);


    /*Free all malloc variables*/
    free_names(names, numNames);
    free_tree(rootName[0]);
    free_tree(rootName[1]);
    free_titles(titles, numTitles);
    free_tree(rootTitle[0]);
    free_tree(rootTitle[1]);
    free_principals(princis, numPrinc);
    free_tree(rootPrin[0]);
    free_tree(rootPrin[1]);

    return 0;
}
