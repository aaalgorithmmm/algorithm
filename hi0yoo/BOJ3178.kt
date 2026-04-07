// https://www.acmicpc.net/problem/3178
fun main() {
    val (n, k) = readln()!!.split(" ").map { it.toInt() }

    val prefixTrie = Trie()
    val suffixTrie = Trie()

    repeat(n) {
        val word = readln()!!

        // 앞 K글자
        prefixTrie.insert(word, k)

        // 뒤 K글자
        suffixTrie.insertReversed(word, k)
    }

    val answer = prefixTrie.nodeCount + suffixTrie.nodeCount
    println(answer)
}

class Trie {
    private val next = ArrayList<HashMap<Char, Int>>() // 각 노드의 간선
    var nodeCount = 1 // 0번이 루트
        private set
        get() = nodeCount - 1

    init {
        next.add(HashMap()) // root
    }

    private fun move(cur: Int, ch: Char): Int {
        // 현재 노드에서 갈 수 있는 노드 맵
        val map = next[cur]
        return if (map.containsKey(ch)) {
            map[ch]!!
        }
        // 현재 노드와 이어지지 않았으면 노드 생성
        else {
            val newNode = nodeCount++
            map[ch] = newNode
            next.add(HashMap())
            newNode
        }
    }

    fun insert(word: String, k: Int) {
        var cur = 0
        for (i in 0 until k) {
            cur = move(cur, word[i])
        }
    }

    fun insertReversed(word: String, k: Int) {
        var cur = 0
        for (i in word.length - 1 downTo k) {
            cur = move(cur, word[i])
        }
    }
}