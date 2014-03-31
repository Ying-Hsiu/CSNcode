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
		 	int ttempCount = 0;//�Ȧs�`��������
		 	int tempCount = 0;//�Ȧs�`�������פ����j��
	    	int cCount = 0;//�`��������
	    	Stack<Character> pStack = new Stack<Character>();
	    	boolean isComment = false;//�O�_������
	    	String tempStr="";
	    	
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
		                	   L1:for(int j=0;j<ch.length;j++){
		                				if(ch[j]=='/'&& j<ch.length-1){
		                					if(ch[j+1]=='/'){
		                						strData = strData.substring(0, j);
		                						break L1;
		                					}
		                					else if(ch[j+1]=='*'){
		                						isComment = true;
		                						//break L1;
		                					}		                					
		                				}
		                				//���ѧ��ڳ���
		                				else if(ch[j]=='*' && ch[j+1]=='/' && isComment==true){
		                					if(j<ch.length-2){
		                						strData = strData.substring(j+2,ch.length-1);
		                					}
		                					isComment = false;
		                				}
		                				//�ư��r��
		                				else if(ch[j]=='"'){
		                					String space = "";
		                					int i=j;
			                				do{
			                					j++;
			                				}while(ch[j]!='"' && j<ch.length-1);
		                					
		                					tempStr = strData.substring(i, j+1);
		                					//System.out.println("tempStr:"+tempStr);
		                					for(int k=0;k<tempStr.length();k++){
		                						space+=",";
		                					}
		                					//System.out.println("space  :"+space);
		                					//System.out.println("StrDataBefore:"+strData);
		                					strData = strData.replace(tempStr,space);
		                					//System.out.println("StrDataAfter :"+strData);
		                					
		                				}
		                				else if(ch[j]=='{')
			 		            			pStack.push('{');
			 		            		else if(ch[j]=='}')
			 		            			pStack.pop();
			 		            		
		                		}
		                			if(isComment==false){
		                				System.out.println("line:"+line);
		                				ttempCount = ccc.countCondition(0, ch, strData);
		                				
		                				
		                				if(ttempCount==-1){//�YŪ�������w�O�_���d��~
		                					if(tempCount>cCount){
		                						cCount=tempCount;
		                						tempCount=0;
		                					}
		                				}
		                				else if(ttempCount==-2){//if�e�̬�if
		                					System.out.println("ifffffffffffffffffffffffffffffffffffff");
		                					System.out.println("count:"+cCount);
		                					if(tempCount>cCount){
		                						cCount=tempCount;
		                						tempCount=0;
		                						
		                					}
		                					tempCount = ccc.countCondition(0, ch, strData);
		                				}
		                				else{
		                					tempCount+=ttempCount;
		                				}
		                			}
		                			
		                			line++;
			 		            	strData = inputFile.readLine();
			 		            	ch = strData.toCharArray();
			 		            	
		 		            	}while(pStack.size()!=0);
			                	if(tempCount>cCount){
	        						cCount=tempCount;
	        					}	 		            	
		                	 	methodList.add(cCount);
		                	 	System.out.println("---------addCount:"+cCount);
		                	 	//��l�Ƽƭ�
		                	 	ttempCount=0;
		                	 	tempCount = 0;
		                	 	cCount = 0;
		                	 	ccc.countCondition(-1, ch, strData);
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
