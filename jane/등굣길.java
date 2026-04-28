class Solution {
        
    public int solution(int m, int n, int[][] puddles) {
        int maps[][] = new int[m][n];
        
        for(int[] puddle : puddles) {
            maps[puddle[0]-1][puddle[1]-1] = -1;   
        }
        
        maps[0][0] = 1;
        for(int x = 0; x < m; x++) {
            for(int y = 0; y < n; y++) {
                if(maps[x][y] != -1) {
                    if(x > 0 && maps[x - 1][y] != -1) {
                        maps[x][y] += maps[x - 1][y];
                    }
                    
                    if(y > 0 && maps[x][y - 1] != -1) {
                        maps[x][y] += maps[x][y - 1];   
                    }
                    
                    if(maps[x][y] > 1000000007) {
                        maps[x][y] %= 1000000007;
                    }
                }
            }
        }        

        return maps[m-1][n-1] % 1000000007;
    }
}