
public class countCC {
	
	String[] keyword = {"[\\W]*if(.*).*",".*else if(.*).*",".*for(.*;.*;.*).*",".*while(.*).*",".*case.*"};	
	char[] operator = {'&','|'};
	
	int countCondition(int count,char[] c,String s){
				for(String str:keyword){
					if( s.matches(str) && !s.matches(".*\".*"+str+".*\".*") 
					&& !(s.matches("[\\W]*//.*"+str+".*")||s.matches("[\\W]*/\\**"+str+".*")||s.matches("[\\W]*"+str+"\\**/.*")) ){
						//排除字串與註解
						if(str.equals(".*else if(.*).*")||str.equals("[\\W]*if(.*).*")||str.equals(".*while(.*).*")){
								count++;
								System.out.println(str);
								for(char Op:operator){
									for(int j=0;j<c.length;j++){
										if((c[j] == Op)&&(c[j+1] == Op)){
											count++;
											j++;
											System.out.println(str);
										}
										else if(c[j] == Op){
											count++;
											System.out.println(str);
										}
									}
								}
						}
						else if(str.equals(".*for(.*;.*;.*).*")){
								count++;
								System.out.println(str);
						}		
						else if(str.matches(".*case.*")){
								count++;
								System.out.println(str);
						}
				}
		}//End of for
		return count;
	}//End of method


}
