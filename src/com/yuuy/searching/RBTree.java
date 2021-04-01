package com.yuuy.searching;

import com.yuuy.basic.CircleQueue;

import java.util.Random;

/**
 * 红黑树
 *
 * 差不多是这几个里面最难的一部分了，
 * 要理解红黑树的插入，必须先搞清楚它所对应的2-3树是如何操作的。
 * 2-3树其实就是一种只有2节点和3节点的完全平衡的树。
 * 为了保证它的平衡，在插入时我们不能轻易的将节点放到叶子节点。
 * 而是将它与已有的节点合并。
 * 如果合并前节点是一个2节点，那么合并后它会变成一个3节点。
 * 如果合并前节点是一个3节点，那么合并后它会变成一个临时的4节点。
 * 而我们是不允许4节点的存在的，因此我们将它拆分为两个2节点。
 * 并将一个节点传递到父节点中，此时如果父节点原来是2节点，就变成3节点。
 * 如果父节点是3节点，就重复向上传递，直到根节点。
 *
 * 再回到红黑树，
 * 如何用二叉树表示一个2-3树呢，
 * 2节点比较容易，直接用普通节点就能表示，
 * 3节点呢，其实也不难，我们可以用一个父节点和它的子节点来表示。
 * 为了便于描述，子节点我们单指左子节点，
 * 指向它的链接我们染成红色，称为红链接，该节点称为红节点。
 * 其他节点和链接统称为黑链接和黑节点。
 * 计算该红黑树的高度时，我们统统只计算黑色部分。
 *
 * 当我们插入一个元素的时候，把该节点标红。
 * 这样插入一个元素就不会增加红黑树的高度。
 * 但是这样会扰乱红黑树的秩序。
 *
 * 为了保证红黑树不乱，我们必须对某些节点进行旋转，
 * 节点的旋转分为左旋和右旋，
 * 左旋就是把右子节点变成父节点，把父节点变成它的左子节点。
 * 同时把原来的左子节点变成现在左子节点的父节点。
 * 这样做最直观的结果就是把原来红色的右节点变成了红色的左节点。
 *
 * 右旋则刚好相反，就不赘述了。
 *
 * 红黑树的删除算法则更为复杂，
 * 为了删除一个红黑树里的节点而不破坏它的结构。
 * 我们必须构造临时的4节点，这牵涉到2-3-4树，
 * 当我们删除2-3-4树里3或4节点时，该树的平衡性不会发生变化。
 * 首先，我们来讨论删除最小元素。
 * 要删除一个最小元素，我们只需要不停的访问左子节点到叶子节点，
 * 此时如果节点是一个等价的三节点的一部分，或者是一个等价的四节点的一部分，
 * 那么就可以直接删除，而不用担心破坏树的完整性。
 *
 * 如果节点是一个等价二节点，那么就必须将它转换为一个高节点。
 * 或者说直接向父节点借一个元素，这样就必须得保证父节点是三或者四节点。
 * 如果不是，那再向它的父节点借一个元素，层层递推上去，
 * 最终如果根节点是一个三节点或者四节点，那么整个过程就能顺利的进行下去。
 * 当根节点是一个等价三节点时，我们不用担心，
 * 但当根节点是一个二节点时，我们就必须将它和它的两个子节点结合成一个等价的四节点。
 * 这个过程也不难，直接将两个子节点变红就行了。
 * 通过这样层层递归，最终子节点被删除，再回去将之前临时的四节点全部消除。
 *
 * 删除最大的元素也是这样进行，不过是反过来了而已。
 *
 * 反映在红黑树上，其实就是保证每次访问到一个节点时，指向它的链接始终都是红色。
 *
 * 而删除红黑树的任意一个节点也是这样，如果访问的键值和红黑树的值不等，那么就依条件访问它的左右节点，
 * 只要保证访问的节点是红节点就好。
 * 如果访问到的键值刚好和红黑树的某个节点相等，在底部的话其实就和删除最小最大类似。
 * 在中间的话就把它和它的后继节点交换，这样只需要对以右子节点为根的子树进行删除最小元素的操作就行了。
 */
public class RBTree<Key extends Comparable<Key>, Value> implements OrderedST<Key, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        Key key;
        Value value;
        Node leftChild;
        Node rightChild;
        int N;
        boolean color;

        public Node(Key key, Value value, int n, boolean color) {
            this.key = key;
            this.value = value;
            N = n;
            this.color = color;
        }
    }

    private boolean isRed(Node node) {
        if (node == null) return BLACK;
        return node.color;
    }

    private Node root;

    @Override
    public Key min() {
        if (root == null) return null;
        return min(root);
    }

    private Key min(Node node) {
        if (node.leftChild == null) return node.key;
        return min(node.leftChild);
    }

    @Override
    public Key max() {
        if (root == null) return null;
        return max(root);
    }

    private Key max(Node node) {
        if (node.rightChild == null) return node.key;
        return max(node.rightChild);
    }

    @Override
    public Key floor(Key key) {
        return floor(key, root);
    }

    private Key floor(Key key, Node node) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        Key k;
        if (cmp == 0) return node.key;
        else if (cmp < 0) return floor(key, node.leftChild);
        else {
            k = floor(key, node.rightChild);
        }
        if (k == null) return node.key;
        else return k;
    }

    @Override
    public Key ceiling(Key key) {
        return null;
    }

    @Override
    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node node) {
        if (node == null) return 0;
        int t;
        if (node.leftChild == null) t = 0;
        else t = size(node.leftChild);
        int cmp = key.compareTo(node.key);
        if (cmp < 0) return rank(key, node.leftChild);
        else if (cmp > 0) return t + 1 + rank(key, node.rightChild);
        else return t;
    }

    @Override
    public Key select(int k) {
        return select(k, root);
    }

    private Key select(int k, Node node) {
        if (node == null) return null;
        int t;
        if (node.leftChild == null) t = 0;
        else t = size(node.leftChild);
        if (k < t) return select(k, node.leftChild);
        else if (k == t) return node.key;
        else return select(k - t - 1, node.rightChild);
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        CircleQueue<Key> queue = new CircleQueue<Key>();
        keys(lo, hi, root, queue);
        return queue;
    }

    private void keys(Key lo, Key hi, Node node, CircleQueue<Key> queue) {
        if (node == null) return;
        int cmplo = node.key.compareTo(lo);
        int cmphi = node.key.compareTo(hi);
        if (cmphi <= 0)
            keys(lo, hi, node.leftChild, queue);
        if (cmphi <= 0 && cmplo >= 0) queue.enqueue(node.key);
        if (cmplo >= 0)
            keys(lo, hi, node.rightChild, queue);
    }

    private int size(Node node) {
        if (node == null) return 0;
        return node.N;
    }

    @Override
    public void put(Key key, Value value) {
        root = put(key, value, root);
        root.color = BLACK;
    }

    private Node put(Key key, Value value, Node node) {
        if (node == null) return new Node(key, value, 1, RED);

        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.leftChild = put(key, value, node.leftChild);
        else if (cmp > 0) node.rightChild = put(key, value, node.rightChild);
        else node.value = value;

        if (!isRed(node.leftChild) && isRed(node.rightChild)) node = rotateLeft(node);
        if (isRed(node.leftChild) && isRed(node.leftChild.leftChild)) node = rotateRight(node);
        if (isRed(node.leftChild) && isRed(node.rightChild)) flipColors(node);

        node.N = size(node.leftChild) + size(node.rightChild) + 1;
        return node;
    }

    private void flipColors(Node node) {
        node.color = !node.color;
        node.leftChild.color = !node.leftChild.color;
        node.rightChild.color = !node.rightChild.color;
    }

    private Node rotateRight(Node node) {
        Node x = node.leftChild;
        node.leftChild = x.rightChild;
        x.rightChild = node;
        x.color = node.color;
        node.color = RED;
        x.N = node.N;
        node.N = 1 + size(node.rightChild) + size(node.leftChild);
        return x;
    }

    private Node rotateLeft(Node node) {
        Node x = node.rightChild;
        node.rightChild = x.leftChild;
        x.leftChild = node;
        x.color = node.color;
        node.color = RED;
        x.N = node.N;
        node.N = 1 + size(node.leftChild) + size(node.rightChild);
        return x;
    }

    @Override
    public Value get(Key key) {
        return get(key, root);
    }

    private Value get(Key key, Node node) {
        if (node == null) return null;
        else if (key.compareTo(node.key) > 0) {
            get(key, node.rightChild);
        } else if (key.compareTo(node.key) < 0) {
            get(key, node.leftChild);
        }
        return node.value;
    }

    @Override
    public void deleteMax() {
        if (!isRed(root) && !isRed(root))
            root.color = RED;
        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMax(Node x) {
        if (isRed(x.leftChild)) x=rotateRight(x);
        if (x.leftChild == null) return null;
        if (!isRed(x.rightChild) && !isRed(x.rightChild.leftChild)) {
            x = moveRightRed(x);
        }
        x.rightChild = deleteMax(x.rightChild);
        return balance(x);
    }

    private Node moveRightRed(Node x) {
        flipColors(x);
        if (!isRed(x.leftChild.leftChild)){
            x = rotateRight(x);
        }

        return x;
    }

    @Override
    public void deleteMin() {
        if (!isRed(root) && !isRed(root))
            root.color = RED;
        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMin(Node x) {
        if (x.leftChild == null) return null;
        if (!isRed(x.leftChild) && !isRed(x.leftChild.leftChild)) {
            x = moveLeftRed(x);
        }
        x.leftChild = deleteMin(x.leftChild);
        return balance(x);
    }

    private Node balance(Node x) {
        if (isRed(x.rightChild)) x = rotateLeft(x);
//        if (!isRed(x.leftChild) && isRed(x.rightChild)) x = rotateLeft(x);
        if (isRed(x.leftChild) && isRed(x.leftChild.leftChild)) x = rotateRight(x);
        if (isRed(x.leftChild) && isRed(x.rightChild)) flipColors(x);

        x.N = size(x.leftChild) + size(x.rightChild) + 1;
        return x;
    }

    private Node moveLeftRed(Node x) {
        flipColors(x);
        if (isRed(x.rightChild.leftChild)) {
            x.rightChild = rotateRight(x.rightChild);
            x = rotateLeft(x);
        }
        return x;
    }

    @Override
    public void delete(Key key) {
        if(!isRed(root.leftChild) && !isRed(root.rightChild))
            root.color = RED;
        root = delete(root, key);
        if(!isEmpty()) root.color = BLACK;
    }


    private Node delete(Node x, Key key) {
        if (key.compareTo(x.key)<0) {
            if (!isRed(x.leftChild) && !isRed(x.leftChild.leftChild))
                x =moveLeftRed(x);
            x.leftChild = delete(x.leftChild,key);
        } else {
            if (isRed(x.leftChild)) x = rotateRight(x);
            if (key.compareTo(x.key) == 0 && (x.rightChild == null))
                return null;
            if (!isRed(x.rightChild) && !isRed(x.rightChild.leftChild))
                x=moveRightRed(x);
            if (key.compareTo(x.key) == 0) {
                x.value = get(min(x.rightChild), x.rightChild);
                x.key = min(x.rightChild);
                x.rightChild = deleteMin(x.rightChild);
            } else x.rightChild = delete(x.rightChild, key);
        }
        return balance(x);
    }

    @Override
    public int size() {
        return size(root);
    }

    public static void main(String[] args) {
        OrderedST<Integer, Integer> symbolTree = new RBTree<>();
        Random rand = new Random(1234);
        int[] keys = new int[10];

        for (int i = 0; i < 10; i++) {
            keys[i] = rand.nextInt(10);
            System.out.printf("%d ", keys[i]);
        }

        for (int i = 0; i < 10; i++) {
            if (symbolTree.contains(keys[i])) symbolTree.put(keys[i], symbolTree.get(keys[i]) + 1);
            else symbolTree.put(keys[i], 1);
        }
        System.out.printf("符号表的大小是%d\n", symbolTree.size());

        System.out.printf("向下取小于2的第一各整数是%d\n", symbolTree.floor(2));

        System.out.printf("rank为2的整数的键值是%d\n", symbolTree.select(2));

        System.out.printf("7在符号表中的rank为%d\n", symbolTree.rank(7));

        System.out.printf("最大元素为%d，最小元素为%d\n", symbolTree.max(), symbolTree.min());

        System.out.printf("删除键为7的元素...\n");
        symbolTree.delete(7);

//        System.out.printf("删除键最小的元素...\n");
//        symbolTree.deleteMin();
//        System.out.printf("删除键最大的元素...\n");
//        symbolTree.deleteMax();

        Iterable<Integer> keyIter = symbolTree.keys();
        for (Integer key :
                keyIter) {
            System.out.printf("%d:%d ", key, symbolTree.get(key));
        }
    }
}
