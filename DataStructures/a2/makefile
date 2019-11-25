CC=gcc
CFLAGS=-ansi -Wall -pedantic -g

text: text.o main.o ; $(CC) -o text text.o main.o
main.o: main.c ; $(CC) $(CFLAGS) -c main.c
text.o: text.c text.h ; $(CC) $(CFLAGS) -c text.c

clean: ; rm main.o text.o text
