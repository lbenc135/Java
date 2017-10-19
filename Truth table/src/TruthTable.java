
public class TruthTable {

	public static void main(String[] args) {
		boolean a=true,b=true;
		System.out.println("A\tB\tA and B\tA or B\tA xor B\tNot B");
		System.out.println(a + "\t" + b + "\t" + (a&b) + "\t" + (a|b) + "\t" + (a^b) + "\t" + !b);
		
		a=false;
		b=true;
		System.out.println(a + "\t" + b + "\t" + (a&b) + "\t" + (a|b) + "\t" + (a^b) + "\t" + !b);
		
		a=true;
		b=false;
		System.out.println(a + "\t" + b + "\t" + (a&b) + "\t" + (a|b) + "\t" + (a^b) + "\t" + !b);
		
		a=false;
		b=false;
		System.out.println(a + "\t" + b + "\t" + (a&b) + "\t" + (a|b) + "\t" + (a^b) + "\t" + !b);
	}

}
