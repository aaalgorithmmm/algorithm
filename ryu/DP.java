// 프로그래머스 정수삼각형 (DP동적계획법)

class Solution {
    public int solution(int[][] triangle) {
        int n = triangle.length;
        
        // dp 배열 생성
        int[][] dp = new int[n][n];
        
        dp[0][0] = triangle[0][0];
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                if (j == 0) {
                    // 왼쪽 끝
                    dp[i][j] = dp[i-1][j] + triangle[i][j];
                } else if (j == i) {
                    // 오른쪽 끝
                    dp[i][j] = dp[i-1][j-1] + triangle[i][j];
                } else {
                    // 가운데
                    dp[i][j] = Math.max(dp[i-1][j-1], dp[i-1][j]) + triangle[i][j];
                }
            }
        }
        
        // 마지막 줄에서 최대값 찾기
        int answer = 0;
        for (int i = 0; i < n; i++) {
            answer = Math.max(answer, dp[n-1][i]);
        }
        
        return answer;
    }
}