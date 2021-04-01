package a1;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.io.*;


import soot.*;
import soot.jimple.AssignStmt;
import soot.jimple.IdentityStmt;
import soot.jimple.InstanceInvokeExpr;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.Jimple;
import soot.jimple.StaticFieldRef;
import soot.jimple.Stmt;
import soot.jimple.StringConstant;
import soot.options.Options;

public class TestSootLogging extends BodyTransformer {
	private static final SootClass systemClass = Scene.v().loadClassAndSupport("java.lang.System");
	private static final SootClass printClass = Scene.v().loadClassAndSupport("java.io.PrintStream");
	private static final SootMethodRef printMethod = printClass.getMethod("void println(java.lang.String)").makeRef();
	SootFieldRef fr = Scene.v().makeFieldRef(systemClass,"out",RefType.v("java.io.PrintStream"),true);
	StaticFieldRef sfr = Jimple.v().newStaticFieldRef(fr);
	Local r = Jimple.v().newLocal("$r2",RefType.v("java.io.PrintStream"));
	AssignStmt newAssignStmt = Jimple.v().newAssignStmt(r, sfr);
	
	
	public static void main(String[] args)	{
		
		
		String mainclass = "a1.Example";
		
		//output Jimple
		Options.v().set_output_format(1);

//		//set classpath
	    String javapath = System.getProperty("java.class.path");
	    String jredir = System.getProperty("java.home")+"/lib/rt.jar";
	    String path = javapath+File.pathSeparator+jredir;
	    Scene.v().setSootClassPath(path);

            //add an intra-procedural analysis phase to Soot
	    TestSootLogging analysis = new TestSootLogging();
	    
	    
	    analysis.printFR();
	    
	    PackManager.v().getPack("jtp").add(new Transform("jtp.TestSoot", analysis));

            //load and set main class
	    Options.v().set_app(true);
	    SootClass appclass = Scene.v().loadClassAndSupport(mainclass);
	    Scene.v().setMainClass(appclass);
	    Scene.v().loadNecessaryClasses();

            //start working
	    PackManager.v().runPacks();

	    PackManager.v().writeOutput();
	}
	
	public void printFR() {
		
		System.out.println("fr:" + fr);
		System.out.println("sfr: " + sfr);
		System.out.println("newAss: " + newAssignStmt);
	}

	@Override
	protected void internalTransform(Body b, String phaseName,
		Map<String, String> options) {
		System.out.println("method: " + b.getMethod().getName()); 
		
		
		if(b.getMethod().getName().equals("main"))   //loops through all methods until it finds the main
		{
			
			System.out.println("79: 	" + b);
			
			System.out.println("locals: " + b.getLocals());
    		b.getLocals().add(r);

    		Unit toInsert = b.getUnits().getFirst();
    		
    		
    		System.out.println("toInsert: " + toInsert); 
    		
    		System.out.println("get units: " + b.getUnits().getClass());
    		System.out.println("get units first: " + b.getUnits().getFirst());
    		
//    		System.out.println("toInsert1: " + toInsert); 
//    			toInsert = b.getUnits().getSuccOf(toInsert);
//    			System.out.println("toInsert2: " + toInsert); 
//    			toInsert = b.getUnits().getSuccOf(toInsert);
//    			System.out.println("toInsert3: " + toInsert); 
//    			toInsert = b.getUnits().getSuccOf(toInsert);
    			
    			
    		while(toInsert instanceof IdentityStmt)   // @parameter0: java.lang.String[] is an identity stmt
    			toInsert = b.getUnits().getSuccOf(toInsert);
    			
    		
  // at this point toIsert = @parameter0: java.lang.String[]
    		
    		b.getUnits().insertBefore(newAssignStmt,toInsert); //newAssignStmt = AssignStmt imported from import soot.jimple.Stmt;

			Iterator<Unit> it = b.getUnits().snapshotIterator();
			
			
		    while(it.hasNext()){
		    	Stmt stmt = (Stmt)it.next();
		    	System.out.println("line 115: " + stmt);
		    	if(stmt.containsInvokeExpr())   //stmt.containsInvokeExpr()
		    	{
		    	
		    		System.out.println("stmt " + stmt);  
		    		String name = stmt.getInvokeExpr().getMethod().getName();
		    		InvokeExpr printExpr = Jimple.v().newVirtualInvokeExpr(r, printMethod, StringConstant.v("calling "+name));
		    		InvokeStmt invokeStmt = Jimple.v().newInvokeStmt(printExpr);
		    		b.getUnits().insertBefore(invokeStmt,stmt);

		    	}
		    }


		}
	}
}
