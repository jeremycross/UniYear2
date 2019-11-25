/** STUDENT INFO **
* Jeremy Cross
* jcross04@uoguelph.ca
* 1048495
**/

#include "a4.h"
#include <stdio.h>
#include <string.h>
#include <ctype.h>

int hash1(char * string, int hash_size) {

    /*******************
    This function can be found on zybooks 5.7 in the multiplicative string hash section
    The link to the website where this can be found is:
    https://learn.zybooks.com/zybook/UOGUELPHCIS2520KremerFall2019/chapter/5/section/7
    ********************/

    unsigned long key = 57;
    char* c;

    for (c = string; (*c); c++) {
        key = (key*61) + *c;
    }
   
    return key % hash_size;
}

int hash2(char * string, int hash_size) {

    int j;
    int key;
    int primes[15] = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47};

    if(strlen(string) > 0) {
        key = 0;

        j = 0;
        /*Add different variations of prime multiplication*/
        for(; string[0] != '\0'; string++) {
            
            key = key + ((intInStr(string[0], j+1)*primes[j]) * (intInStr(string[0], j+1)*primes[j>0?j-1:14]));
            (j<14?j++:(j=0));
            key ^= key >> (j<=7?j:j-7);
        }
        key = key%hash_size;

        if(key < 0) {
            key = key * (-1);
        }
    } else {
        key = hash1(string, hash_size); 
    }

    return key;
}

int intInStr(char c, int pos) {

    if(isdigit(c)) {
        return c * pos;
    } else if(isalpha(c)) {
        if(isupper(c)) {
            return c * (c+1);
        } else {
            return c * (c+1);
        }
    }

    return c * c;
}

int hash3(char * string, int hash_size) {

    unsigned int key;
    int day;
    int month;
    int year;

    if(sscanf(string, "%d/%d/%d", &month, &day, &year) == 3) {

        if(year >= 2000) { /*Allow for differentiation between 1900s and 2000s after modulus*/
            day = day + 31;
        }

        /*Convert issue_date to mmddyy format in int for hash*/
        year = year%100;   
        month = month*10000; 
        day = day*100;


        key = (day + month + year)%hash_size;
         
    } else {
        key = hash1(string, hash_size);
    }

    return key;
}

