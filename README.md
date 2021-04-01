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
  
We are given sample code TestDominatorFinder.java, DominatorFinder.java and a test program GCD.java which computes the greatest common divisor of two integers. The task is to analyze the method doAnalysis() in the DominatorFinder.java file. 

A quick search in the soot source code on github I was able to find the class MHGDominatorFinder.java which also performs dominator analysis. After analyzing the code I verified that they are identical and therefore no change is required. 

## Part-2

Files Used:
  Example.java
  testSootCallGraph.java
  
Data Flow Analysis - Call GraphConstruction

In this section we use Soot to construct call graphs with the goal of developing a basic sense of inter-procedural data flow analysis.

We are then asked to compare the precision and speed between two call graph analysis algorithms: Points-to-analysis (PTA) and Class Hierarchy analysis (CHA). We are given the file Example.java (shown below) as well as TestSootCallGraph.java which can test both CHA and PTA. The TestSootCallGraph also prints out every call edge and the total number of call edges in Example.java.


In order to capture the speed (in nano seconds) of each algorithm, I made some changes to the method internalTransfor:

```
protected void internalTransform(String phaseName,
			Map options) {
		

		final long startTime = System.nanoTime();
		
		
		
		int numOfEdges =0;

		CallGraph callGraph = Scene.v().getCallGraph();
		
		
		for(SootClass sc : Scene.v().getApplicationClasses()){
			for(SootMethod m : sc.getMethods()){

		Iterator<MethodOrMethodContext> targets = new Targets(
				 callGraph.edgesOutOf(m));

				 while (targets.hasNext()) {

					 numOfEdges++;

				SootMethod tgt = (SootMethod) targets.next();
				 System.out.println(m + " may call " + tgt);
				 }
			}
		}
		
		final long elapsedTimeMillis = System.nanoTime() - startTime;
		System.out.println("Elapsed time: " + elapsedTimeMillis);
		
		
		 System.err.println("Total Edges:" + numOfEdges);

	}
}
```

Results:

CHA output:

Total Edges:12
<a1.Example: a1.Animal selectAnimal()> may call <a1.Cat: void <init>()>
<a1.Example: void main(java.lang.String[])> may call <a1.Example: a1.Animal selectAnimal()>
<a1.Example: void main(java.lang.String[])> may call <a1.Cat: void saySomething()>
<a1.Example: void main(java.lang.String[])> may call <a1.Fish: void saySomething()>
<a1.Animal: void <init>()> may call <java.lang.Object: void <init>()>	
<a1.Fish: void saySomething()> may call <java.lang.System: void <clinit>()>	
<a1.Fish: void saySomething()> may call <java.io.PrintStream: void println(java.lang.String)>
<a1.Fish: void saySomething()> may call <java.lang.Object: void <clinit>()>	
<a1.Cat: void <init>()> may call <a1.Animal: void <init>()>	
<a1.Cat: void saySomething()> may call <java.lang.System: void <clinit>()>	
<a1.Cat: void saySomething()> may call <java.io.PrintStream: void println(java.lang.String)>
<a1.Cat: void saySomething()> may call <java.lang.Object: void <clinit>()>
Elapsed time: 2355292


PTA output:

Total Edges:7
<a1.Example: a1.Animal selectAnimal()> may call <a1.Cat: void <init>()>
<a1.Example: void main(java.lang.String[])> may call <a1.Example: a1.Animal selectAnimal()>
<a1.Example: void main(java.lang.String[])> may call <a1.Cat: void saySomething()>
<a1.Animal: void <init>()> may call <java.lang.Object: void <init>()>
<a1.Cat: void <init>()> may call <a1.Animal: void <init>()>
<a1.Cat: void saySomething()> may call <java.lang.System: void <clinit>()>
<a1.Cat: void saySomething()> may call <java.lang.Object: void <clinit>()>
	Elapsed time: 725166
  
As shown in the output above, CHA found 12 edges in 2355292 nanoseconds and PTA found 7 edges in 725166 nanoseconds. 

## Part-3
