import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class CC {

public static void main(String[] args){
	 String[] form = {".*void ", ".*static double ", ".*static int ", ".*static float ", ".*static long ",
            ".*static byte ", ".*static short ", ".*static char ", ".*static String ",
            ".*static boolean ", ".*static ", ".*double ", ".*int ", ".*float ", ".*long ",
            ".*byte ", ".*short ", ".*char ", ".*String ", ".*boolean ", ".*static "};
	 LineNumberReader inputFile = null;
	 String filePath = JOptionPane.showInputDialog("�п�J�ɮ׸��|");
	 String fileName = JOptionPane.showInputDialog("�п�J�ɮצW��");
	 
	 try {//Ū���ɮ�
	        inputFile = new LineNumberReader(new FileReader(filePath+"/"+fileName));
	    } catch (FileNotFoundException ex) {
	 }
	 
	 try {
		    ArrayList<String> methodName = new ArrayList<String>();//�s��C��k���W��
		 	ArrayList<Integer> methodList = new ArrayList<Integer>();//�s��C��k���̰��`��������
	    	int cCount = 0;//�p��`��������
	    	int uF = 0;//�p�� {�ƥ�
	    	int dF = 0;//�p�� }�ƥ�
	    	
	    	String strData= inputFile.readLine();
	    	char[] ch = strData.toCharArray();
	    	
	    	countCC ccc = new countCC();
	    	countFileLine cFL = new countFileLine();
	    	
	    	int fileLine = cFL.countLine(filePath+"/"+fileName);
	    	System.out.println("fileLine: "+fileLine);
	    		int line = 0;//�ΥH�p����
	    		
		    	while(line<fileLine) {
		    		for (String s : form) {
		                if (strData.matches(s + ".*(.*).*") && !strData.matches(".*;.*")) {
		                	getMethodName gmn = new getMethodName();
		                	String name =  gmn.getName(ch);
		                	methodName.add(name);                	
		                	do{
		                	
		                		for(int j=0;j<ch.length;j++){
			 		            		if(ch[j]=='{')
			 		            			uF++;
			 		            		else if(ch[j]=='}')
			 		            			dF++;	
			 		            	}
		                			cCount += ccc.countCondition(0, ch, strData);
			 		            	line++;
			 		            	strData = inputFile.readLine();
			 		            	ch = strData.toCharArray();
			 		            	
			 		            	System.out.println("Line:"+line);
		 		            	}while(uF!=dF || uF==0);
		                	 	methodList.add(cCount);
		                	 	//��l�Ƽƭ�
		                	 	cCount=0;
		                	 	uF=0;
		                	 	dF=0;
		                }
		            }
	    		        
			            line++;
			            strData = inputFile.readLine();
			            if(strData!=null){
			    			ch = strData.toCharArray();
			            	}
		    	}//End of while
		    		for(String str:methodName){
		    			System.out.print(str+" ");
		    		}
		    		System.out.println("");
		    		for(Integer num:methodList){

		    			System.out.print(num+" ");
		    		}
		    		System.out.println("");
	    }catch(IOException IOe){
	    	
	    }
 }
}
