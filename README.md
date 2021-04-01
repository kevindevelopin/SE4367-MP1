## Table of content

- [Introduction](#introduction)
- [Part 1](#part-1)
- [Part 2](#part-2)
- [Part 3](#part-3)

## Introduction



## Part-1

Control Flow Analysis - FindingDominators

Files Used:

TestDominatorFinder.java
DominatorFinder.java
GCD.java
  
 We are given sample code TestDominatorFinder.java, DominatorFinder.java and a test program GCD.java which computes the greatest common divisor of two integers. The task is to analyze the method doAnalysis() in the DominatorFinder.java. 

A quick search in the soot source code on github I was able to find the class MHGDominatorFinder.java which also performs dominator analysis. After analyzing the code I verified that they are identical and therefore no change is required. 

## Part-2

Files Used:
  Example.java
  
Data Flow Analysis - Call GraphConstruction

In this section we use Soot to construct call graphs with the goal of developing a basic sense of inter-procedural data flow analysis.

We are provided with the Example.java file shown below: 

```
abstract class Animal {
    public abstract void saySomething();
}

class Cat extends Animal {
    public void saySomething() {
        System.out.println("purr");
    }
}

class Dog extends Animal {
    public void saySomething() {
        System.out.println("woof");
    }
}

class Fish extends Animal {
    public void saySomething() {
        System.out.println("...");
    }
}

class Car {  // not an Animal
    public void saySomething() {
        System.out.println("honk!");
    }
}

public class Example {
    static Animal neverCalled() {
        return new Fish();
    }

    static Animal selectAnimal() {
        return new Cat();
    }

    public static void main(String[] args) {
        Animal animal = selectAnimal();
        animal.saySomething();
    }
}
```


## Part-3
