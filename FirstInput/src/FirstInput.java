public class FirstInput {
	public static void main(String[] args) {
		System.out.println("Enter a number:");
		int a = Keyboard.readInt ();
		System.out.println("Enter a second number:");
		int b = Keyboard.readInt ();
		System.out.println("Enter a third number:");
		int c = Keyboard.readInt ();
		System.out.println("The average is " + (a+b+c)/3);
	}
}
