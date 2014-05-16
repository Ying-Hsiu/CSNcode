import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class CyclomaticComplexity {

	public static void main(String[] args) throws Exception{
		 String[] form = {".*void ", ".*static double ", ".*static int ", ".*static float ", ".*static long ",
	            ".*static byte ", ".*static short ", ".*static char ", ".*static String ",
	            ".*static boolean ", ".*static ", ".*double ", ".*int ", ".*float ", ".*long ",
	            ".*byte ", ".*short ", ".*char ", ".*String ", ".*boolean ", ".*static "};
		 LineNumberReader inputFile = null;
		 String filePath = JOptionPane.showInputDialog("�п�J�ɮ׸��|");
		 listFile lf = new listFile();
		 String[] allFiles = lf.listFile(filePath);
		 String fileName;
		 ExeclOutput opt = new ExeclOutput();
 		 opt.createFile("D:/output1.xls");
		 
 		 /*�v�@���opackage�U��.java��*/
		 for(int lfi=0;lfi<allFiles.length;lfi++){
			 if(allFiles[lfi].matches(".*.java")){
			 fileName=allFiles[lfi];
		 
		 try {//Ū���ɮ�
		        inputFile = new LineNumberReader(new FileReader(filePath+"/"+fileName));
		    } catch (FileNotFoundException ex) {
		 }
		 
		 try {
			    ArrayList<String> methodName = new ArrayList<String>();//�s��C��k���W��
			 	ArrayList<Integer> methodList = new ArrayList<Integer>();//�s��C��k���̰��`��������
			 	ArrayList<Integer> methodObj = new ArrayList<Integer>();//�s��C��k�I�s�������
			 	int ttempCount = 0;//�Ȧs�`��������
			 	int tempCount = 0;//�Ȧs�`�������פ����j��
		    	int cCount = 0;//�̲״`��������
		    	int cObject = 0;//�p�⪫��ƥ�
		    	
		    	Stack<Character> pStack = new Stack<Character>();
		    	boolean isComment = false;//�O�_������
		    	
		    	String strData= inputFile.readLine();
		    	char[] ch = strData.toCharArray();
		    	
		    	countCC ccc = new countCC();
		    	countFileLine cFL = new countFileLine();
		    	exclude exc = new exclude();
		    	countObject cObj = new countObject();
		    	Object[] rec;
		    	
		    	int fileLine = cFL.countLine(filePath+"/"+fileName);
		    	CountSubMethodTest csop=new CountSubMethodTest(filePath+"/"+fileName); //�I�s�p��l��kCLASS
                csop.show_methodName();
                ArrayList<Double>countSubMethodList=csop.countSubMethodList; //�s��l��k��
                ArrayList<String>  methodNameSub= csop.methodList;    //�s��l��k���W��
                ArrayList<String>  subHighLowList=csop.subhighlowList; //�s��l��k�ư��C
		    	System.out.println("ClassName:"+fileName+",fileLine: "+fileLine);
		    		int line = 0;//�ΥH�p����
		    		
			    	while(line<fileLine) {
			    		
			    			//�ư����Ѧr��r��
			    			rec = (Object[]) exc.exclude(strData,ch,isComment);
			    			isComment=(boolean) rec[1];
			    			strData=(String) rec[0]+"";
			    			ch=strData.toCharArray();
			    			//System.out.println(strData);
			    			
			    		for (String s : form) {
			                if (strData.matches(s + ".*(.*).*") && !strData.matches(".*;.*")) {
			                	getMethodName gmn = new getMethodName();
			                	String name =  gmn.getName(ch);
			                	methodName.add(name);                	
			                	do{
			                		for(int j=0;j<ch.length;j++){
			                				if(ch[j]=='{')
				 		            			pStack.push('{');
				 		            		else if(ch[j]=='}')
				 		            			pStack.pop();
			                		}
			                			if(isComment==false){
			                				System.out.println("line:"+line);
			                				
			                				cObject += cObj.countObjNew(0, ch, strData);
			                				cObject += cObj.countObjPara(0, strData, ch);
			                				ttempCount = ccc.countCondition(0, ch, strData);
			                				
			                				
			                				if(ttempCount==-1){//�YŪ�������w�O�_���d��~
			                					if(tempCount>cCount){
			                						cCount=tempCount;
			                						tempCount=0;
			                					}
			                				}
			                				else if(ttempCount==-2){//if�e�̬�if
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
				 		            	strData = inputFile.readLine()+"";
				 		            	ch = strData.toCharArray();
				 		            
				 		            	//�ư����Ѧr��r��
				 		            	rec = (Object[]) exc.exclude(strData,ch,isComment);
						    			isComment=(boolean) rec[1];
						    			strData=(String) rec[0]+"";
						    			ch=strData.toCharArray();
				 		            	
				 		            	
			 		            	}while(pStack.size()!=0&&line<fileLine);
				                	if(tempCount>cCount){
		        						cCount=tempCount;
		        					}	 		            	
			                	 	methodList.add(cCount);
			                	 	methodObj.add(cObject);
			                	 	System.out.println("---------addCount:"+cCount);
			                	 	//��l�Ƽƭ�
			                	 	ttempCount=0;
			                	 	tempCount = 0;
			                	 	cCount = 0;
			                	 	cObject = 0;
			                	 	ccc.countCondition(-1, ch, strData);
			                }
			            }
		    		        
				            line++;
				            strData = inputFile.readLine()+"";
				    		ch = strData.toCharArray();
				            
			    	}//End of while
			    	
			    		for(String str:methodName){
			    			System.out.print(str+" ");
			    		}
			    		System.out.println("");
			    		for(Integer num:methodList){
			    			if(num<=10){
			    				System.out.print(num+"(�C) ");
			    			}
			    			else{
			    				System.out.print(num+"(��) ");
			    			}
			    		}
			    		System.out.println("");
			    		
			    		
			    		
			    		System.out.println("method        object       type");
                        System.out.println("--------------------------------");
                        
                        //�Ƨ�
                        int a;
                        int cycCount;
                        double sub;
                        String mName;
                        int tmp;
                        int cTemp;
                        String temp;
                         for (int i = 0; i < methodObj.size()-1; i++) {
                            
                             for (int j = i+1; j < methodObj.size(); j++) {
                                 a = methodObj.get(i);
                                 mName= methodName.get(i);
                                 cycCount = methodList.get(i);
                                 
                                 
                                 int b = methodObj.get(j);
                                 int cycCountTemp = methodList.get(j); 
                                 String mNameTemp = methodName.get(j);
                                 
                                  if (a < b) {
                                 
                                  tmp = b;
                                  cTemp = cycCountTemp;
                                  temp= mNameTemp;
                                  methodObj.set(j, a);
                                  methodName.set(j, mName);
                                  methodList.set(j, cycCount);
                                  methodObj.set(i, tmp);
                                  methodName.set(i,temp);
                                  methodList.set(i, cycCountTemp);
                             }
                             }
                         
                         }
                       //�NmethodName�PmethodNameSub������k�W�ٰ����ñN�ۦP������
                         double value;
    	    			 String highlow;
    	    			 String name;
    	    			 
    	    			 double vtemp;
    	    			 String hltemp;
    	    			 String ntemp;
                         for(int k=0;k<methodName.size()-1;k++){
                        	 for(int l=k;l<methodName.size();l++){
                        		 value=countSubMethodList.get(k);
                        		 highlow = subHighLowList.get(k);
                        		 name = methodNameSub.get(k);
                        		 
                        		 double temp_value=countSubMethodList.get(l);
                        		 String temp_highlow=subHighLowList.get(l);
                        		 String temp_name=methodNameSub.get(l);
                        		 if(String.valueOf(methodName.get(k).equals(methodNameSub.get(l))).equals("true")){
                        			 vtemp=temp_value;
                        			 hltemp=temp_highlow;
                        			 ntemp=temp_name;
                        			 
                        			 countSubMethodList.set(l,value);
                        			 subHighLowList.set(l, highlow);
                        			 methodNameSub.set(l, name);
                        			 
                        			 
                        			 countSubMethodList.set(k,vtemp);
                        			 subHighLowList.set(k, hltemp);
                        			 methodNameSub.set(k, ntemp);
                        			 break;
                        		 }
                        	 }
                         }
                         
                         for(int x=0;x<methodName.size();x++){
                        	 System.out.println(methodName.get(x)+":"+countSubMethodList.get(x)+" "+subHighLowList.get(x));
                         }
                         
                          //����� 80/20
                        opt.addClassName(fileName);
                        for(int i=0;i<methodName.size();i++){
                          
                          if(i<=methodName.size()*0.2-1){
                             
                              if(methodObj.get(i)==methodObj.get((methodName.size()-(int)(methodName.size()*0.8)))){
                            	  opt.addData(methodName.get(i), methodList.get(i),(methodList.get(i)>10)?"��":"�C", countSubMethodList.get(i),subHighLowList.get(i), methodObj.get(i),"�C");
                              }else{
                            	  opt.addData(methodName.get(i), methodList.get(i),(methodList.get(i)>10)?"��":"�C", countSubMethodList.get(i),subHighLowList.get(i), methodObj.get(i),"��");
                              }
                             
                          }else {
                        	  opt.addData(methodName.get(i), methodList.get(i),(methodList.get(i)>10)?"��":"�C", countSubMethodList.get(i),subHighLowList.get(i), methodObj.get(i),"�C");
                                }
                       
                            
                       }
		    }catch(IOException IOe){
		    	
		    }
		 
		}
	  }
	 }
	
}
