#Makefile
CLIENT = ./src/client/*.java
SERVER = ./src/server/*.java
JAVAC = javac

.PHONY: all
all: comp
comp:
	mkdir -p ./bin
	${JAVAC} -d ./bin ${CLIENT}
	${JAVAC} -d ./bin ${SERVER}

.PHONY: clean
   clean:
	rm bin/*.class
