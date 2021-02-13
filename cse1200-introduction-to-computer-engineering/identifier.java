import java.io.*;
import java.util.*;
public class TunahanCinsoy_150117062 {
public static void main(String[] args) throws FileNotFoundException{
	File f = new File("input.txt");
	Scanner in = new Scanner(f);
	PrintStream out = new PrintStream("output.txt");
	while(in.hasNextLine()) {
		Scanner line = new Scanner(in.nextLine());
		while(line.hasNext()) {
			String bin = line.next();
			String arg = line.next();
			if(arg.equals("UN")) {
				int number = 0;
				for(int i = 15; i>=0; i--) {
					if(bin.charAt(i)=='1')
					number+=Math.pow(2 , 15-i);
					}
				out.println(number);
				}
			else if(arg.equals("S1")) {
				int number = 0;
				for(int i = 15; i>0; i--) {
					if(bin.charAt(i)=='0')
						number+=Math.pow(2,15-i);
					}
				if(bin.startsWith("1")) {
					number=-number;
					}
				out.println(number);
				}
			else if(arg.equals("S2")) {
				int number = 1;
				for(int i = 15; i>0; i--) {
					if(bin.charAt(i)=='0')
						number+=Math.pow(2,15-i);
					}
				if(bin.startsWith("1")) {
					number=-number;
					}
				out.println(number);
				}
			else if (arg.equals("CH")) {
				int ASCII = 0;
				for(int i = 15; i>=0; i--) {
					if(bin.charAt(i)=='1')
					ASCII+=Math.pow(2 , 15-i);
					}
				out.println((char)ASCII);
				}
			else if (arg.equals("INS")) {
				String instruction = bin.substring(0,4);
				if(instruction.equals("0001")) {
					String DR = bin.substring(4 , 7);
					String SR1 = bin.substring(7 , 10) ;
					int dr = 0;
					int sr1 = 0;
					for (int i = 2; i>=0; i--) {
						if(DR.charAt(i)=='1')
							dr+=Math.pow(2, 2-i);
						if(SR1.charAt(i)=='1')
							sr1+=Math.pow(2, 2-i);
					}
					if(bin.charAt(10)=='0') {
						String SR2 = bin.substring(13);
						int sr2 = 0;
						for (int i = 2; i>=0; i--) {
							if(SR2.charAt(i)=='1')
								sr2+=Math.pow(2, 2-i);
						}
						out.println("ADD R"+dr+", R"+sr1+", R"+sr2);
					}
					else {
						String IMM5 = bin.substring(11);
						int imm5 = 1;
						for(int i = 4; i>0; i--) {
							if(IMM5.charAt(i)=='0')
								imm5+=Math.pow(2,4-i);
						}
						if(IMM5.startsWith("1"))
							imm5=-imm5;
						out.println("ADD R"+dr+", R"+sr1+", #"+imm5);
					}
					//add
					}
				else if(instruction.equals("0101")) {
					String DR = bin.substring(4 , 7);
					String SR1 = bin.substring(7 , 10) ;
					int dr = 0;
					int sr1 = 0;
					for (int i = 2; i>=0; i--) {
						if(DR.charAt(i)=='1')
							dr+=Math.pow(2, 2-i);
						if(SR1.charAt(i)=='1')
							sr1+=Math.pow(2, 2-i);
					}
					if(bin.charAt(10)=='0') {
						String SR2 = bin.substring(13);
						int sr2 = 0;
						for (int i = 2; i>=0; i--) {
							if(SR2.charAt(i)=='1')
								sr2+=Math.pow(2, 2-i);
						}
						out.println("AND R"+dr+", R"+sr1+", R"+sr2);
					}
					else {
						String IMM5 = bin.substring(11);
						int imm5 = 1;
						for(int i = 4; i>0; i--) {
							if(IMM5.charAt(i)==0)
								imm5+=Math.pow(2,4-i);
						}
						if(IMM5.startsWith("1"))
							imm5=-imm5;
						out.println("AND R"+dr+", R"+sr1+", #"+imm5);
						//and
					}
				}
				else if(instruction.equals("1001")) {
					//not
					String DR = bin.substring(4 , 7);
					String SR = bin.substring(7 , 10) ;
					int dr = 0;
					int sr = 0;
					for (int i = 2; i>=0; i--) {
						if(DR.charAt(i)=='1')
							dr+=Math.pow(2, 2-i);
						if(SR.charAt(i)=='1')
							sr+=Math.pow(2, 2-i);
						}
					out.println("NOT R"+dr+" , R"+sr);
					}
				else if(instruction.equals("1110")) {
					String DR = bin.substring(4 , 7);
					String OFFSET = bin.substring(7) ;
					int dr = 0;
					int offset = 1;
					for (int i = 2; i>=0; i--) {
						if(DR.charAt(i)=='1')
							dr+=Math.pow(2, 2-i);
					}
					for(int i = 8; i>0; i--) {
						if(OFFSET.charAt(i)=='0')
							offset+=Math.pow(2,8-i);
					}
					if(OFFSET.startsWith("1"))
						offset=-offset;
					out.println("LEA R"+dr+" , #"+offset);
					//lea
				}
					
				}
			}	
		}
	}
}
	
