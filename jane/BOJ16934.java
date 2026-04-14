import java.util.*;
import java.io.*;

public class Main {
	
	public static class Node {
		Node[] children;
		
		Node() {
			children = new Node[26];
		}
	}
	
	static Node root;
	
	public static String insert(String name) {
		Node node = root;
		StringBuilder sb = new StringBuilder();
		boolean flag = true;
		
		for(int i = 0; i < name.length(); i++) {
			int idx = name.charAt(i) - 'a';
			if (flag) {
				sb.append(name.charAt(i));
			}
			
			if (node.children[idx] == null) {
				node.children[idx] = new Node();
				flag = false;
			}
			
			node = node.children[idx];
			
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder answer = new StringBuilder();
		
		int n = Integer.parseInt(br.readLine());
		root = new Node();
		HashMap<String, Integer> map = new HashMap<>();
		
		for (int i = 0; i < n; i++) {
			String name = br.readLine();
			if (!map.containsKey(name)) {
				map.put(name, 1);
				String nickname = insert(name);
				answer.append(nickname).append("\n");
			}
			else {
				map.put(name, map.get(name) + 1);
				answer.append(name).append(map.get(name)).append("\n");
			}
		}
		
		System.out.print(answer);
	}
}