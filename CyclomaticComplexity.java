package NSCs;

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
		 String filePath = JOptionPane.showInputDialog("請輸入檔案路徑");
		 String fileName = JOptionPane.showInputDialog("請輸入檔案名稱");
		 
		 try {//讀取檔案
		        inputFile = new LineNumberReader(new FileReader(filePath+"/"+fileName));
		    } catch (FileNotFoundException ex) {
		 }
		 
		 try {
			    ArrayList<String> methodName = new ArrayList<String>();//存放每方法之名稱
			 	ArrayList<Integer> methodList = new ArrayList<Integer>();//存放每方法之最高循環複雜度
			 	ArrayList<Integer> methodObj = new ArrayList<Integer>();//存放每方法呼叫之物件數
			 	int ttempCount = 0;//暫存循環複雜度
			 	int tempCount = 0;//暫存循環複雜度中較大者
		    	int cCount = 0;//最終循環複雜度
		    	int cObject = 0;//計算物件數目
		    	
		    	Stack<Character> pStack = new Stack<Character>();
		    	boolean isComment = false;//是否為註解
		    	
		    	String strData= inputFile.readLine();
		    	char[] ch = strData.toCharArray();
		    	
		    	countCC ccc = new countCC();
		    	countFileLine cFL = new countFileLine();
		    	exclude exc = new exclude();
		    	countObject cObj = new countObject();
		    	Object[] rec;
		    	
		    	int fileLine = cFL.countLine(filePath+"/"+fileName);
		    	CountSubMethodTest csop=new CountSubMethodTest(filePath+"/"+fileName); //呼叫計算子方法CLASS
		    	csop.show_methodName();
		    	ArrayList<Double>countSubMethodList=csop.countSubMethodList; //存放子方法數
		    	ArrayList<String>  methodNameSub= csop.methodList;	//存放子方法之名稱
		    	ArrayList<String>  subHighLowList=csop.subhighlowList; //存放子方法數高低
		    	System.out.println("fileLine: "+fileLine);
		    		int line = 0;//用以計算行數
		    		
			    	while(line<fileLine) {
			    		
			    			//排除註解字串字元
			    			rec = (Object[]) exc.exclude(strData,ch);
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
			                				
			                				
			                				if(ttempCount==-1){//若讀取部分已是巢狀範圍外
			                					if(tempCount>cCount){
			                						cCount=tempCount;
			                						tempCount=0;
			                					}
			                				}
			                				else if(ttempCount==-2){//if前者為if
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
				 		            	
				 		            	//排除註解字串字元
				 		            	rec = (Object[]) exc.exclude(strData,ch);
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
			                	 	//初始化數值
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
			    				System.out.print(num+"(低) ");
			    			}
			    			else{
			    				System.out.print(num+"(高) ");
			    			}
			    		}
			    		System.out.println("");
			    		
			    		
			    		
			    		System.out.println("method        object       type");
                        System.out.println("--------------------------------");
                        
                        
                        //排序
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
                         //將methodName與methodNameSub中的方法名稱做比對並將相同的互換
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
                         
                         
                         //輸出至Excel
                        ExeclOutput opt = new ExeclOutput();
                 		 opt.createFile("D:/output1.xls");
                 		/* for(int i=0;i<methodName.size();i++){
                 			 opt.addData(methodName.get(i), methodList.get(i),(methodList.get(i)>10)?"高":"低", countSubMethodList.get(i),subHighLowList.get(i), methodObj.get(i),"高");
                 		 }*/
                 		 
                          //物件數 80/20
                      
                        for(int i=0;i<methodName.size();i++){
                          
                          if(i<=methodName.size()*0.2-1){
                             
                              if(methodObj.get(i)==methodObj.get((methodName.size()-(int)(methodName.size()*0.8)))){
                            	  opt.addData(methodName.get(i), methodList.get(i),(methodList.get(i)>10)?"高":"低", countSubMethodList.get(i),subHighLowList.get(i), methodObj.get(i),"低");
                              }else{
                            	  opt.addData(methodName.get(i), methodList.get(i),(methodList.get(i)>10)?"高":"低", countSubMethodList.get(i),subHighLowList.get(i), methodObj.get(i),"高");
                              }
                             
                          }else {
                        	  opt.addData(methodName.get(i), methodList.get(i),(methodList.get(i)>10)?"高":"低", countSubMethodList.get(i),subHighLowList.get(i), methodObj.get(i),"低");
                                }
                       
                            
                       }
		    }catch(IOException IOe){
		    	
		    }
	 }
	
}
