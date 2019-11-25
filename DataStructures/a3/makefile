CC=gcc
CFLAGS=-ansi -Wall -pedantic -g

a3: main.o common.o binary.o name.o title.o principals.o ; $(CC) -o a3 main.o name.o common.o title.o principals.o binary.o
main.o: main.c common.h binary.h title.h principals.h ; $(CC) $(CFLAGS) -c main.c
common.o: common.c common.h ; $(CC) $(CFLAGS) -c common.c
binary.o: binary.c binary.h common.h ; $(CC) $(CFLAGS) -c binary.c
name.o: name.c name.h binary.h common.h ; $(CC) $(CFLAGS) -c name.c
title.o: title.c title.h binary.h common.h ; $(CC) $(CFLAGS) -c title.c
principals.o: principals.c principals.h binary.h common.h ; $(CC) $(CFLAGS) -c principals.c

clean: ; rm *.o a3
