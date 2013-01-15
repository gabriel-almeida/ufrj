import java.util.Random;
import java.util.Scanner;

public class main {
	static final int MAX_DELAY=200;
	public static void main(String[] args) {
		Scanner sc= new Scanner(System.in);
		int c=sc.nextInt();
		int java= sc.nextInt();
		int i, j;
		i=j=0;
		Barco b = new Barco(c, java);
		Random r=new Random();
		while (i+j<c+java)
		{
			if (i<c){new ProgramadorC(b,i+j,r.nextInt(MAX_DELAY)).start();i++;}
			if (j<java){new ProgramadorJava(b, i+j, r.nextInt(MAX_DELAY)).start(); j++;}
		}
	}

}
