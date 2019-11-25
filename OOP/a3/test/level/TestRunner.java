package level;

import java.util.List;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	public static void main(String [] args) {
		System.out.println("===============DoorTest===================");
		Result result1 = JUnitCore.runClasses(DoorTest.class);
		System.out.println("Failed Tests:");
		//Print out failure tests
		List <Failure> failedList1 = result1.getFailures();
		failedList1.forEach(System.out::println);
		System.out.println("Number of Failed Tests = " + failedList1.size());

		System.out.println("\n===============ChamberTest===================");
		Result result2 = JUnitCore.runClasses(ChamberTest.class);
		System.out.println("Failed Tests:");
		//Print out failure tests
		List <Failure> failedList2 = result2.getFailures();
		failedList2.forEach(System.out::println);
		System.out.println("Number of Failed Tests = " + failedList2.size());

		System.out.println("\n===============PassageTest===================");
		Result result3 = JUnitCore.runClasses(PassageTest.class);
		System.out.println("Failed Tests:");
		//Print out failure tests
		List <Failure> failedList3 = result3.getFailures();
		failedList3.forEach(System.out::println);
		System.out.println("Number of Failed Tests = " + failedList3.size());

		System.out.println("\n===============PassageSectionTest===================");
		Result result4 = JUnitCore.runClasses(PassageSectionTest.class);
		System.out.println("Failed Tests:");
		//Print out failure tests
		List <Failure> failedList4 = result4.getFailures();
		failedList4.forEach(System.out::println);
		System.out.println("Number of Failed Tests = " + failedList4.size());
	}
}