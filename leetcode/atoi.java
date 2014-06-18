public class Solution {
    
    
     public static void main(String []args){
         Solution s=new Solution();
         System.out.println(s.atoi("-0x111e"));
         System.out.println(-0x111e);
     }
    
    String input;
    int position;
    
	
	void skipwhitespace(){
		while(input.length()>position){
			char c=input.charAt(position);
			if(c==' ' || c=='\t' || c=='\n' || c=='\r'){
				position++;
			}else{
				break;
			}
		}
	}
	
    int readSign()
    {
        char c=input.charAt(position);
        if(c=='-' || c=='+')
        {
            position++;
            if(c=='-')
            {
                return -1;
            }else{
                return 1;
            }
        }
        return 1;
    }
    
    int readBase(){
		if(input.length()<=position)
		{
			return 1;
		}
        char c=input.charAt(position);
        if(c=='0'){
            position++;
			if(input.length()<=position){
				return 1;
			}
            c=input.charAt(position);
            if(c=='x' || c=='X'){
                position++;
                return 16;
            }else if(c=='b' || c=='B'){
                position++;
                return 2;
            }else{
                return 8;
            }
        }else{
            return 10;
        }
    }
    
    long readBinary(){
        long result=0;
        for(int i=position;i<input.length();i++){
            char c=input.charAt(i);
            switch(c){
                case '0':
                    result=result*2;
                    break;
                case '1':
                    result=result*2+1;
                    break;
				case ' ':
					break;
                default:
                    // error out
					return 0;
            }
        }
        return result;
    }
    
    long readHex(){
        long result=0;
        for(int i=position;i<input.length();i++){
            char c=input.charAt(i);
            switch(c){
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    result=result*16+(int)c-(int)'0';
                    break;
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                    result=result*16+(int)c-(int)'a'+10;
                    break;
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                    result=result*16+(int)c-(int)'A'+10;
                    break;
				case ' ':
					break;

                default:
                    // error out
					return 0;
            }
        }
        return result;
    }
    
    long readOctal(){
        long result=0;
        for(int i=position;i<input.length();i++){
            char c=input.charAt(i);
            switch(c){
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    result=result*8+(int)c-(int)'0';
                    break;
				case ' ':
					break;
                default:
                    // error out
					return 0;
            }
        }
        return result;
    }
    
    long readDecimal(){
        long result=0;
        Boolean readingDecimal=false;
        int decimal=0;
        Boolean readingExponential=false;
    	int exponential=0;
        for(int i=position;i<input.length();i++){
            char c=input.charAt(i);
            switch(c){
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
					if(!readingExponential){
						if(readingDecimal){
							decimal++;
						}
						result=result*10+(int)c-(int)'0';
					}else{
						exponential=exponential*10+(int)c-(int)'0';
					}
                    break;
				case '.':
					readingDecimal=true;
					break;
				//case ',':
				//case ' ':
				//	break;
				//case 'e':
				//case 'E':
				//	 readingExponential=true;
				//	break;
                default:
                    // error out
					return (long)(result*Math.pow(10,exponential-decimal));
            }
        }
        return (long)(result*Math.pow(10,exponential-decimal));
    }
    
    public int atoi(String str) {
	
		if(str.length()==0){
			return 0;
		}
		
        input=str;
        position=0;
        
		skipwhitespace();
        int sign=readSign();
		int base=readBase();
		if(base==8 && (input.indexOf('E')>0 || input.indexOf('e')>0)){
			base = 10;
		}
		base=10;
		
		long result=0;
		switch(base){
			case 2:
				result=readBinary();
				break;
			case 8:
				result=readOctal();
				break;
			case 10:
				result=readDecimal();
				break;
			case 16:
				result=readHex();
				break;
        }
		long ret= result*sign;
		if(ret>Integer.MAX_VALUE)ret=Integer.MAX_VALUE;
		if(ret<Integer.MIN_VALUE)ret=Integer.MIN_VALUE;
		return (int)ret;
    }
}