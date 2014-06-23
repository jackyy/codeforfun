import java.util.*;

public class HelloWorld{

     public static void main(String []args){
        char [][] problem1= {
             {'5','3','.','.','7','.','.','.','.'},
             {'6','.','.','1','9','5','.','.','.'},
             {'.','9','8','.','.','.','.','6','.'},
             {'8','.','.','.','6','.','.','.','3'},
             {'4','.','.','8','.','3','.','.','1'},
             {'7','.','.','.','2','.','.','.','6'},
             {'.','6','.','.','.','.','2','8','.'},
             {'.','.','.','4','1','9','.','.','5'},
             {'.','.','.','.','8','.','.','7','9'}
            };

        char [][] problem2= {
    		{'.','.','9','7','4','8','.','.','.'},
			{'7','.','.','.','.','.','.','.','.'},
			{'.','2','.','1','.','9','.','.','.'},
			{'.','.','7','.','.','.','2','4','.'},
			{'.','6','4','.','1','.','5','9','.'},
			{'.','9','8','.','.','.','3','.','.'},
			{'.','.','.','8','.','3','.','2','.'},
			{'.','.','.','.','.','.','.','.','6'},
            {'.','.','.','2','7','5','9','.','.'}
		};
		
		char [][] problem= {
			{'.','.','.','2','.','.','.','6','3'},
			{'3','.','.','.','.','5','4','.','1'},
			{'.','.','1','.','.','3','9','8','.'},
			{'.','.','.','.','.','.','.','9','.'},
			{'.','.','.','5','3','8','.','.','.'},
			{'.','3','.','.','.','.','.','.','.'},
			{'.','2','6','3','.','.','5','.','.'},
			{'5','.','3','7','.','.','.','.','8'},
			{'4','7','.','.','.','1','.','.','.'}
		};

        Solution s=new Solution();

        s.solveSudoku(problem);
                
        s.PrintBoard();
     }
}

class Solution {
    public static int A1 = 1<<4;
    public static int A2 = 1<<5;
    public static int A3 = 1<<6;
    public static int A4 = 1<<7;
    public static int A5 = 1<<8;
    public static int A6 = 1<<9;
    public static int A7 = 1<<10;
    public static int A8 = 1<<11;
    public static int A9 = 1<<12;
    public static int All = A1 | A2 | A3 | A4 | A5 | A6 | A7 | A8 | A9;
    public static int[] Num = {0, A1, A2, A3, A4, A5, A6, A7, A8, A9};
    
    int[][] solution;
    char[][] board;
    
    public void solveSudoku(char[][] board) {
        this.board=board;
        this.solution=new int[board.length][board.length];
        
        for(int i=0;i<board.length;i++)
            for(int j=0;j<board.length;j++){
                solution[i][j]=All;
            }
        
        for(int i=0;i<board.length;i++)
            for(int j=0;j<board.length;j++){
                if(board[i][j]!='.'){
                    if(solve(i,j,(int)(board[i][j]-'0'))<0){
                        System.out.println("Wrong input");
                        return;
                    }
                }
            }
        
		//heuristic
		searchsolution(0);
		
		UpdateBoard();
    }
	
	int searchsolution(int index){
		if(index>=board.length*board.length)return 1;
		
		int x=index/board.length;
		int y=index % board.length;
		
		if(solution[x][y]<16){
			return searchsolution(index+1);
		}else{
			int[][] backup=CreateBackup();
			List<Integer> anws=getval(solution[x][y]);
			for(int anw:anws){
				if(solve(x,y,anw)>0 && searchsolution(index+1)>0){
					return 1;
				}else{
					RestoreBackup(backup);
				}
			}
		}
        return 0;
	}
    
    int[][] CreateBackup(){
        int[][] backupSolution=new int[board.length][board.length];
        for(int i=0;i<board.length;i++)
            for(int j=0;j<board.length;j++)
                backupSolution[i][j]=solution[i][j];
		
		return backupSolution;
    }
    
    void RestoreBackup(int[][] backupSolution){
        for(int i=0;i<board.length;i++)
            for(int j=0;j<board.length;j++)
                solution[i][j]=backupSolution[i][j];
    }
    
    void PrintSolution(){
        System.out.println("Solution:");
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++)
                System.out.print(solution[i][j]+" ");
            System.out.println("");
        }
        System.out.println("");
    }
    
    void PrintBoard(){
        UpdateBoard();
        System.out.println("Board:");
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++)
                System.out.print(board[i][j]);
            System.out.println("");
        }
        System.out.println("");
    }
    
    void UpdateBoard(){
        for(int i=0;i<board.length;i++)
            for(int j=0;j<board.length;j++){
                if(solution[i][j]>15){
                    board[i][j]='.';
                }else{
                    board[i][j]=(char)(solution[i][j]+'0');
                }
            }    
    }
    
    public int solve(int x, int y, int val){
        
        solution[x][y]=val;
        		
        int cubx=x/3;
        int cuby=y/3;
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
			{
				int tmpx=cubx*3+i;
				int tmpy=cuby*3+j;
				if(tmpx!=x || tmpy!=y){
					if(update(tmpx,tmpy, val)<0){
                        return -1;
					}
				}
			}
			
		for(int i=0;i<9;i++){
			if(i!=y){
				if(update(x, i, val)<0 ){
                    return -1;
				}
			}

			if(i!=x){
				if(update(i, y, val)<0){
                    return -1;
				}
			}
		}
				
        return 1;
    }    	
    
    public int update(int x, int y, int val){
        if(solution[x][y]<16){
			if(solution[x][y]==val){
				//found conflict
				return -1;
			}
			return 0;
		}
        solution[x][y]=unset_num(solution[x][y], val);
        int ans = haveAnswer(solution[x][y]);
        if(ans>0){
            return solve(x, y, ans);			
        }else{
			return 0;
		}
    }
    
    public int set_num(int val, int num)
    {
        if(num>9 || num<1){
            // error
            return -1;
        }
        return val | Num[num];
    }
    
    public int unset_num(int val, int num){
        if(num>9 || num<1){
            // error
            return -1;
        }
        return val & ~Num[num];
    }
	
    public int haveAnswer(int val){
        int count=0;
        int index=-4;
        while(val>0)
        {
            if((val&1)>0){
                count++;
            }
            val=val>>1;
            index++;
        }
        if(count>1){
            return -1;
        }else if(count==0){
            return 0;
        }else{
            return index;
        }
    }
	
	public List<Integer> getval(int val){
		List<Integer> results=new ArrayList<Integer>();
		int index=-3;
		while(val>0){
			if((val&1)>0){
				results.add(index);
			}
			val=val>>1;
			index++;
		}
		return results;
	}
}
