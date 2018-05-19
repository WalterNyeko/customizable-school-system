package clarion.academics.ui;

public class Test {

	public static void main(String[] args) {

		Test number=new Test();
	System.out.println(number.wordCount("I am a your boss "));
		
//		for(int i=1;i<=1000;i++) {
//			String st=number.TestValues(i);
//			System.out.println(i+ " Value="+i+st);
//			
//		}
	}
	

	public static String TestValues(int value) {

		int remForHun = value % 100;
		int remForTen = value % 10;
		if (remForHun - remForTen == 10) {
			return "th";
		}

		switch (remForTen) {
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";

		}

	}
	
	public static int wordCount(String s) {
		
		if(s==null) {
			return 0;
		}else {
			return s.trim().split("\\s+").length;
		}
		
	}

}
