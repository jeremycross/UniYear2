/** STUDENT INFO **
* Jeremy Cross
* jcross04@uoguelph.ca
* 1048495
**/


#include <stdio.h>
#include "ds_list.h"
#include "ds_memory.h"

int ds_create_list() {

    long head = -1;

    if(ds_init("list.bin") != 0){
        return 1;
    }

    if(ds_malloc(sizeof(long)) == -1){ /*Allocated memory for long at beginning of file*/
        return 1;
    }

    if(ds_write(0, &head, sizeof(long)) == -1){ /*Write -1 for head in file*/
        return 1;
    }

    if(!(ds_finish())){
        return 1;
    }

    return 0;
}

int ds_init_list() {

    if(ds_init("list.bin") != 0){
        return 1;
    }

    return 0;
}

int ds_replace(int value, long index) {

    long next;
    long head;
    long prevLoc;
    int i = 0;
    struct ds_list_item_struct node1;

    if(ds_read(&head, 0, sizeof(long)) == NULL){ /*Read in head pointer value*/
        return 1;
    }

    next = head;

    if(index < 0){
        return 1;
    }

    if(head == -1){ /*List is empty cannot replace*/
        return 1;
    }


    if(head != -1 && index == 0){ /*Replacing first item (no need to traverse)*/


        if(ds_read(&node1, head, sizeof(struct ds_list_item_struct)) == NULL){ /*Read first node*/
            return 1;
        }

        node1.item = value;

        if(ds_write(head, &node1, sizeof(struct ds_list_item_struct)) == -1){ /*Write new value to node*/
            return 1;
        }

        return 0;
    }
    else{

        do{
            if(ds_read(&node1, next, sizeof(struct ds_list_item_struct)) == NULL){ /*Read next node*/
                return 1;
            }

            if(i != 0){
                prevLoc = next;
            }
            next = node1.next;

            i++;
        }while(i <= index && node1.next !=-1);


        if(i <= index && node1.next == -1){ /*Inputted index is out of bounds*/
            return 1;
        }

        node1.item = value;

        if(ds_write(prevLoc, &node1, sizeof(struct ds_list_item_struct)) == -1){ /*Write new next pointer for previous list node*/
            return 1;
        }
    }

    return 0;
}


int ds_insert(int value, long index) {

    long head;
    long next;
    int i = 0;
    long prevNodeLoc;
    struct ds_list_item_struct insertNode;
    struct ds_list_item_struct node1;

    insertNode.item = value;

    if(ds_read(&head, 0, sizeof(long)) == NULL){ /*Read in head pointer value*/
        return 1;
    }

    if(index < 0){
        return 1;
    }

    if(head == -1 && index != 0){ /*List is empty and index given is invalid*/
        return 1;
    }

    if(head == -1 && index == 0){ /*List is empty and an element is to be added at index 0*/
        insertNode.next = -1;
        if((head = ds_malloc(sizeof(struct ds_list_item_struct))) == -1){
            return 1;
        }

        if(ds_write(0, &head, sizeof(long)) == -1){ /*Write new value for head in file*/
            return 1;
        }

        if(ds_write(head, &insertNode, sizeof(struct ds_list_item_struct)) == -1){ /*Write new node to disk*/
            return 1;
        }
        return 0;
    }
    else if(head != -1 && index == 0){ /*List is not empty and we want to insert at beginning*/
        insertNode.next = head;

        if((head = ds_malloc(sizeof(struct ds_list_item_struct))) == -1){
            return 1;
        }

        if(ds_write(0, &head, sizeof(long)) == -1){ /*Write new value for head in file*/
            return 1;
        }

        if(ds_write(head, &insertNode, sizeof(struct ds_list_item_struct)) == -1){ /*Write new node to disk*/
            return 1;
        }
        return 0;
    }
    else{ /*Traverse the list and decide what to do*/
        next = head;
        prevNodeLoc = head;

        do{
            if(ds_read(&node1, next, sizeof(struct ds_list_item_struct)) == NULL){ /*Read next node*/
                return 1;
            }

            if(i != 0){
                prevNodeLoc = next; /*Keep track of pointer to previous node*/
            }

            next = node1.next;

            i++;
        }while(i < index && node1.next !=-1);


        if(i == index && node1.next == -1){ /*Insert at end of list*/
            if((node1.next = ds_malloc(sizeof(struct ds_list_item_struct))) == -1){ /*Allocate memory for new node*/
                return 1;
            }
            insertNode.next = -1;

            if(ds_write(prevNodeLoc, &node1, sizeof(struct ds_list_item_struct)) == -1){ /*Write new next pointer for previous list node*/
                return 1;
            }

            if(ds_write(node1.next, &insertNode, sizeof(struct ds_list_item_struct)) == -1){ /*Write new node to disk*/
                return 1;
            }
            return 0;
        }
        else if(i < index && node1.next == -1){ /*Inputted index is out of bounds*/
            return 1;
        }
        else{ /*Insert to middle of list*/
            insertNode.next = node1.next; /*Set prev nodes old next value to our new nodes next value*/

            if((node1.next = ds_malloc(sizeof(struct ds_list_item_struct))) == -1){ /*Allocate memory for new node*/
                return 1;
            }

            if(ds_write(prevNodeLoc, &node1, sizeof(struct ds_list_item_struct)) == -1){ /*Write new next pointer for previous list node*/
                return 1;
            }

            if(ds_write(node1.next, &insertNode, sizeof(struct ds_list_item_struct)) == -1){ /*Write new node to disk*/
                return 1;
            }
            return 0;
        }
    }

    return 0;
}

int ds_delete(long index) {

    long head;
    long next;
    int i = 0;
    long prevNodeLoc1;
    long prevNodeLoc2;
    struct ds_list_item_struct node;
    struct ds_list_item_struct prevNode;

    if(ds_read(&head, 0, sizeof(long)) == NULL){ /*Read in head pointer value*/
        return 1;
    }

    next = head;
    prevNodeLoc1 = head;
    prevNodeLoc2 = head;

    if(index < 0){
        return 1;
    }

    if(head == -1){ /*List is empty cannot delete*/
        return 1;
    }


    if(head != -1 && index == 0){ /*Deleting first item (no need to traverse)*/

        ds_free(head);

        if(ds_read(&node, head, sizeof(struct ds_list_item_struct)) == NULL){ /*Read in head pointer value*/
            return 1;
        }

        head = node.next;

        if(ds_write(0, &head, sizeof(long)) == -1){ /*Write new value to node*/
            return 1;
        }

        return 0;
    }
    else{

        /*Find current node*/

        do{
            if(ds_read(&node, next, sizeof(struct ds_list_item_struct)) == NULL){ /*Read next node*/
                return 1;
            }

            if(i != 0){
                prevNodeLoc1 = next; /*Keep track of pointer to node*/
            }

            if(i < index){
                next = node.next;
            }
            i++;
        }while(i <= index && node.next !=-1);

        if(i <= index && next == -1){ /*Out of bounds index*/
            return 1;
        }

        next = head;
        i = 0;
        /*Find prev node*/
        do{
            if(ds_read(&prevNode, next, sizeof(struct ds_list_item_struct)) == NULL){ /*Read next node*/
                return 1;
            }

            if(i != 0){
                prevNodeLoc2 = next; /*Keep track of pointer to node*/
            }

            if(i < (index-1)){
                next = prevNode.next;
            }
            i++;
        }while(i < index && prevNode.next !=-1);


        prevNode.next = node.next;
        ds_free(prevNodeLoc1);

        if(ds_write(prevNodeLoc2, &prevNode, sizeof(struct ds_list_item_struct)) == -1){ /*Write new value to node*/
            return 1;
        }
    }


    return 0;
}

int ds_swap(long index1, long index2) {

    long head;
    long next;
    int i = 0;
    int tempItem;
    long nodeLoc1;
    long nodeLoc2;
    struct ds_list_item_struct node1;
    struct ds_list_item_struct node2;

    if(ds_read(&head, 0, sizeof(long)) == NULL){ /*Read in head pointer value*/
        return 1;
    }

    next = head;
    nodeLoc1 = head;
    nodeLoc2 = head;

    if(index1 == index2){ /*No need to replace*/
        return 1;
    }

    if(index1 < 0 || index2 < 0){ /*Either index is negative*/
        return 1;
    }

    if(head == -1){
        return 1;
    }

    do{
        if(ds_read(&node1, next, sizeof(struct ds_list_item_struct)) == NULL){ /*Read next node*/
            return 1;
        }

        if(i != 0){
            nodeLoc1 = next; /*Keep track of pointer to node, first iteration of loop is to be ignored since nodeLoc1 is head to start*/
        }

        next = node1.next;
        i++;
     }while(i <= index1 && node1.next !=-1);

    if(i <= index1 && node1.next == -1){ /*Index1 is invalid*/
        return 1;
    }

    next = head;
    i = 0;

    do{
        if(ds_read(&node2, next, sizeof(struct ds_list_item_struct)) == NULL){ /*Read next node*/
            return 1;
        }

        if(i != 0){
            nodeLoc2 = next; /*Keep track of pointer to node*/
        }

        next = node2.next;
        i++;
     }while(i <= index2 && node2.next !=-1);


    if(i <= index2 && node2.next == -1){ /*Index2 is invalid*/
        return 1;
    }

    /*Swap item values*/
    tempItem = node1.item;
    node1.item = node2.item;
    node2.item = tempItem;

    if(ds_write(nodeLoc1, &node1, sizeof(struct ds_list_item_struct)) == -1){ /*Write new value to node*/
        return 1;
    }

    if(ds_write(nodeLoc2, &node2, sizeof(struct ds_list_item_struct)) == -1){ /*Write new value to node*/
        return 1;
    }

    return 0;
}

long ds_find(int target) {

    long index = 0;
    struct ds_list_item_struct node;
    long next;

    if(ds_read(&next, 0, sizeof(long)) == NULL){ /*Read in head pointer value*/
        return -1;
    }

    if(next == -1){ /*Empty list do not search*/
        return -1;
    }

    do{
        if(ds_read(&node, next, sizeof(struct ds_list_item_struct)) == NULL){ /*Read next node*/
            return -1;
        }

        if(node.item == target){/*Found target*/
            return index;
        }

        next = node.next;
        index++;
    }while(node.next !=-1);

    return -1; /*Whole list traversed at no target was found*/
}

int ds_read_elements(char * filename) {


    FILE* fp;
    int value;
    long currIndex = 0;

    fp = fopen(filename, "r");

    while(fscanf(fp, "%d\n", &value) != EOF){
        if(ds_insert(value, currIndex) != 0){
            return 1;
        }
        currIndex++;
    }

    fclose(fp);

    return 0;
}

int ds_finish_list() {

    if(!(ds_finish())){
        return 1;
    }
    return 0;
}

void show_list(){

    struct ds_list_item_struct node;

    int index = 0;
    long next;

    /*print_block();*/

    ds_read(&next, 0, sizeof(long));

    printf("****Reading list****\n");

    while(next != -1){
        ds_read(&node, next, sizeof(struct ds_list_item_struct));
        printf("Index: %d, Value: %d, Next: %ld\n", index, node.item, node.next);
        next = node.next;
        index++;
    }

    return;
}
