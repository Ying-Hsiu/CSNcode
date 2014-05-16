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
	 String filePath = JOptionPane.showInputDialog("請輸入檔案路徑");
	 String fileName = JOptionPane.showInputDialog("請輸入檔案名稱");
	 
	 try {//讀取檔案
	        inputFile = new LineNumberReader(new FileReader(filePath+"/"+fileName));
	    } catch (FileNotFoundException ex) {
	 }
	 
	 try {
		    ArrayList<String> methodName = new ArrayList<String>();//存放每方法之名稱
		 	ArrayList<Integer> methodList = new ArrayList<Integer>();//存放每方法之最高循環複雜度
	    	int cCount = 0;//計算循環複雜度
	    	int uF = 0;//計算 {數目
	    	int dF = 0;//計算 }數目
	    	
	    	String strData= inputFile.readLine();
	    	char[] ch = strData.toCharArray();
	    	
	    	countCC ccc = new countCC();
	    	countFileLine cFL = new countFileLine();
	    	
	    	int fileLine = cFL.countLine(filePath+"/"+fileName);
	    	System.out.println("fileLine: "+fileLine);
	    		int line = 0;//用以計算行數
	    		
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
		                	 	//初始化數值
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
