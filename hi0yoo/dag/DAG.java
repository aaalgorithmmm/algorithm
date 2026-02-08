package dag;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DAG {
    private final List<List<Integer>> adj;
    // 위상정렬 차수
    private final int[]               indegrees;

    private DAG(List<List<Integer>> adj, int[] indegrees) {
        this.adj = adj;
        this.indegrees = indegrees;
    }

    /**
     * @return 그래프의 노드 개수
     */
    public int getSize() {
        return adj.size();
    }

    /**
     * 특정 노드에서 방문할 수 있는 하위 노드들을 리턴
     *
     * @param node 하위 노드를 가져올 노드 번호
     * @return node 에서 방문할 수 있는 하위 노드 리스트
     */
    public List<Integer> getChildren(int node) {
        return adj.get(node);
    }

    /**
     * @return 위상정렬된 그래프에서 노드의 순서
     */
    public List<Integer> getSortedNodes() {
        List<Integer> result = new ArrayList<>();

        // 차수가 0인 노드부터 방문한다.
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < adj.size(); i++) {
            if (indegrees[i] == 0) queue.add(i);
        }

        while (!queue.isEmpty()) {
            int node = queue.poll();
            result.add(node);

            // 현재 노드에서 갈 수 있는 다음 노드를 방문한다. (해당 노드의 차수를 깎는다)
            for (Integer nextNode : adj.get(node)) {
                indegrees[nextNode]--; // 방문

                // 차수가 0이 되었으면 다음 방문으로 넣는다.
                if (indegrees[nextNode] == 0) queue.add(nextNode);
            }
        }

        return result;
    }

    public static class Builder {
        private final List<List<Integer>> adj;
        private final int[]               indegrees;

        public Builder(int size) {
            if (size <= 0) {
                throw new IllegalArgumentException("size must be > 0");
            }

            this.adj = new ArrayList<>(size);
            for (int i = 0; i < size; i++) adj.add(new ArrayList<>());

            this.indegrees = new int[size];
        }

        public Builder addEdge(int from, int to) {
            adj.get(from).add(to);
            // 차수 추가
            indegrees[to]++;
            return this;
        }

        public DAG build() {
            return new DAG(adj, indegrees);
        }
    }
}
