# Makefile

JFLAGS = -g
JC = javac
JAVA = java

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = Mur.java Boite.java Cible.java Joueur.java Matrice.java Graphic.java KeyHandler.java Direction.java Main.java

default: run

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

run: classes
	$(JAVA) Main
