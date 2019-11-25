CC=gcc
CFLAGS=-ansi -Wall -pedantic



arrayReplace: ds_memory.o ds_array.o arrayTest.o ; $(CC) -o arrayReplace ds_memory.o ds_array.o arrayTest.o

arrayTest.o: arrayTest.c ; $(CC) $(CFLAGS) -c arrayTest.c

listInsert:ds_memory.o ds_array.o listTest.o ; $(CC) -o listInsert ds_memory.o ds_list.o listTest.o

listTest.o: listTest.c ; $(CC) $(CFLAGS) -c listTest.c

list: ds_memory.o ds_list.o testList.o ; $(CC) -o list ds_memory.o testList.o ds_list.o
create_list: ds_memory.o ds_list.o create_list.o ; $(CC) -o create_list ds_memory.o create_list.o ds_list.o

create_list.o: create_list.c ; $(CC) $(CFLAGS) -c create_list.c
testList.o: testList.c ; $(CC) $(CFLAGS) -c testList.c
ds_list.o: ds_list.c ds_list.h ; $(CC) $(CFLAGS) -c ds_list.c

memory: ds_memory.o main.o ds_array.o ; $(CC) -o memory ds_memory.o main.o ds_array.o
main.o: main.c ; $(CC) $(CFLAGS) -c main.c
ds_memory.o: ds_memory.c ds_memory.h ; $(CC) $(CFLAGS) -c ds_memory.c
ds_array.o: ds_array.c ds_array.h ; $(CC) $(CFLAGS) -c ds_array.c
clean: ; rm memory ds_memory.o main.o

tests: main1 main2 main3
main1: ds_memory.o main1.o ; $(CC) -o main1 ds_memory.o main1.o
main1.o: main1.c ; $(CC) $(CFLAGS) -c main1.c

main2: ds_memory.o main2.o ; $(CC) -o main2 ds_memory.o main2.o
main2.o:  main2.c ; $(CC) $(CFLAGS) -c main2.c

main3: ds_memory.o main3.o ; $(CC) -o main3 ds_memory.o main3.o
main3.o:  main3.c ; $(CC) $(CFLAGS) -c main3.c

clean: ; rm *.o ./memory
