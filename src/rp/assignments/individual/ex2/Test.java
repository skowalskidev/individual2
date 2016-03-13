package rp.assignments.individual.ex2;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import rp.robotics.testing.RobotTest;
import rp.robotics.testing.TestViewer;

public class Test {
	public static void main(String[] args){
		Test test = new Test();
		test.runTests();
	}
	
	public void runTests(){
		Result result = JUnitCore.runClasses(rp.assignments.individual.ex2.Ex2Tests.class);
		
		System.out.println(String.format("%d/%d tests successful",
			result.getRunCount() - result.getFailureCount(),
			result.getRunCount()));
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		
		  /*Ex2Tests tests = null;
			try {
				tests = new Ex2Tests();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        // RobotTest<?> test = tests.createSlowTest();
	        // RobotTest<?> test = tests.createMediumTest();
	        RobotTest<?> test = tests.createFastTest();
	        TestViewer demo = new TestViewer(test, test.getSimulation());
	        demo.run(); */
	}
	 
}
