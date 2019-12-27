JC = javac
JFLAGS = -g
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	bplustree.java \
	LeafNode.java\
    Node.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
	