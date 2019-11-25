CC=gcc
CFLAGS=-ansi -Wall -pedantic -g

a4: a4.o main.o ; $(CC) -o a4 a4.o main.o
main.o: main.c ; $(CC) $(CFLAGS) -c main.c
text.o: a4.c a4.h ; $(CC) $(CFLAGS) -c a4.c

clean: ; rm main.o a4.o a4
