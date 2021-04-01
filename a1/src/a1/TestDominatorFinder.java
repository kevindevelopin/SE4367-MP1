package a1;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import soot.*;
import soot.jimple.Stmt;
import soot.options.Options;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;


public class TestDominatorFinder extends BodyTransformer{
	
		int count = 0; 
	
	public static void main(String[] args)	{
		
		

		String mainclass = "a1.GCD";

//		//set classpath
	    String javapath = System.getProperty("java.class.path");
	    String jredir = System.getProperty("java.home")+"/lib/rt.jar";
	    String path = javapath+File.pathSeparator+jredir;
	    Scene.v().setSootClassPath(path);

	    TestDominatorFinder analysis = new TestDominatorFinder();
		PackManager.v().getPack("jtp").add(new Transform("jtp.A1TestDataFlowAnalysis", analysis));

		Options.v().set_app(true);

		SootClass appclass = Scene.v().loadClassAndSupport(mainclass);
		
		
	    Scene.v().setMainClass(appclass);

	    Scene.v().loadNecessaryClasses();
	    PackManager.v().runPacks();
	}
	@Override
	protected void internalTransform(Body b, String phaseName,
		Map<String, String> options) {
		
	    UnitGraph g = new ExceptionalUnitGraph(b);
	    DominatorFinder analysis = new DominatorFinder(g);   //implement dominatorFinder, does graph analysis for each method at a time
	    
//	    
//	    System.out.println("___________________________________________________________");
//	    
//	    count = count + 1; 
//	    System.out.println(count);
//	    System.out.println("");
//	    System.out.println("b units (statements) size: " + b.getUnits().size());
	    
	    
	
        Iterator it = b.getUnits().iterator();
        
        
        
        while (it.hasNext()){
        	
        	
            Stmt s = (Stmt)it.next();  
            
            System.out.println("s: " + s.getClass());
            
            System.out.println(s.toString());
            
            List dominators = analysis.getDominators(s);   //send dominatorFidner a statement at a time that, dominatorFinder will then analyze its dominators
            Iterator dIt = dominators.iterator();
            while (dIt.hasNext()){
            	
            
                Stmt ds = (Stmt)dIt.next();
                String info = s+" is dominated by "+ds;
                System.out.println(info);

            }
        }
	}
}
