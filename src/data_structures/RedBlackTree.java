package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

import interfaces.RedBlackI;

public class RedBlackTree<K, V> implements RedBlackI<K, V> {

    Node<K, V> root;
    int size;

    /**
     * RedBlackTree constructor
     */
    public RedBlackTree() {
        root = null;
        size = 0;
    }

    /**
     * The method to add to the RBTree.  It will not allow duplicate additions.
     *
     * @param key   the key to add
     * @param value the value associated with the key
     */
    public void add(K key, V value) {
        Node<K, V> node = new Node<K, V>(key, value);

        if (root == null) {
            root = node;
            root.black = true;
            size++;
            return;
        }

        add(root, node);
        root.black = true;
        size++;
    }

    /**
     * Private method to add to the RBTree.  It will not allow duplicate additions.
     *
     * @param parent  node
     * @param newNode node
     */
    @SuppressWarnings("unchecked")
    private void add(Node<K, V> parent, Node<K, V> newNode) {
        if (((Comparable<K>) newNode.key).compareTo(parent.key) > 0) {
            if (parent.right == null) {
                parent.right = newNode;
                newNode.parent = parent;
                newNode.leftChild = false;

            } else
                add(parent.right, newNode);
        } else {
            if (parent.left == null) {
                parent.left = newNode;
                newNode.parent = parent;
                newNode.leftChild = true;
            } else
                add(parent.left, newNode);
        }
        checkColor(newNode);
    }

    /**
     * The method checks the node colors for violations
     *
     * @param node (the root node)
     */
    public void checkColor(Node<K, V> node) {
        if (node == root) {
            return;
        }

        if (!node.black && !node.parent.black)
            correctTree(node);
        if (node.parent != null)
            checkColor(node.parent);
    }

    /**
     * The method corrects the tree violations
     *
     * @param node (node to check)
     */
    public void correctTree(Node<K, V> node) {
        if (node.parent.leftChild) {
            if (node.parent.parent.right == null || node.parent.parent.right.black)
                rotate(node);
            else {
                if (node.parent.parent.right != null)
                    node.parent.parent.right.black = true;
                node.parent.parent.black = false;
                node.parent.black = true;
            }
        } else {
            if (node.parent.parent.left == null || node.parent.parent.left.black)
                rotate(node);
            else {
                if (node.parent.parent.left != null)
                    node.parent.parent.left.black = true;
                node.parent.parent.black = false;
                node.parent.black = true;
            }
        }
    }

    /**
     * The method determines which rotation method to use
     *
     * @param node (node to check)
     */
    public void rotate(Node<K, V> node) {
        if (node.leftChild) {
            if (node.parent.leftChild) {
                rightRotate(node.parent.parent);
                node.black = false;
                node.parent.black = true;

                if (node.parent.right != null)
                    node.parent.right.black = false;
            } else {
                rightLeftRotate(node.parent.parent);
                node.black = true;
                node.right.black = false;
                node.left.black = false;
            }
        } else {
            if (!node.parent.leftChild) {
                leftRotate(node.parent.parent);
                node.black = false;
                node.parent.black = true;

                if (node.parent.left != null)
                    node.parent.left.black = false;
            } else {
                leftRightRotate(node.parent.parent);
                node.black = true;
                node.right.black = false;
                node.left.black = false;
            }
        }
    }

    /**
     * The method does a left rotation along the given node
     *
     * @param node (node to rotate)
     */
    public void leftRotate(Node<K, V> node) {
        Node<K, V> temp = node.right;
        node.right = temp.left;

        if (node.right != null) {
            node.right.parent = node;
            node.right.leftChild = false;
        }

        if (node.parent == null) {
            root = temp;
            temp.parent = null;
        } else {
            temp.parent = node.parent;
            if (node.leftChild) {
                temp.leftChild = true;
                temp.parent.left = temp;
            } else {
                temp.leftChild = false;
                temp.parent.right = temp;
            }
        }
        temp.left = node;
        node.leftChild = true;
        node.parent = temp;
    }

    /**
     * The method does a right rotation along the given node
     *
     * @param node (node to rotate)
     */
    public void rightRotate(Node<K, V> node) {
        Node<K, V> temp = node.left;
        node.left = temp.right;

        if (node.left != null) {
            node.left.parent = node;
            node.left.leftChild = true;
        }

        if (node.parent == null) {
            root = temp;
            temp.parent = null;
        } else {
            temp.parent = node.parent;
            if (!node.leftChild) {
                temp.leftChild = false;
                temp.parent.right = temp;
            } else {
                temp.leftChild = true;
                temp.parent.left = temp;
            }
        }
        temp.right = node;
        node.leftChild = false;
        node.parent = temp;
    }

    /**
     * The method does a right-left rotation along the given node
     *
     * @param node (node to rotate)
     */
    public void rightLeftRotate(Node<K, V> node) {
        rightRotate(node.right);
        leftRotate(node);
    }

    /**
     * The method does a left-right rotation along the given node
     *
     * @param node (node to rotate)
     */
    public void leftRightRotate(Node<K, V> node) {
        leftRotate(node.left);
        rightRotate(node);
    }

    /**
     * Tests whether the RBTree contains the key
     *
     * @param key the key to look for
     * @return whether the key is found
     */
    public boolean contains(K key) {
        if (find(key, root) == null)
            return false;
        return true;
    }

    /**
     * Private method to search the RBTree.
     *
     * @param key  to find
     * @param root node
     * @return value of key that was found
     */
    @SuppressWarnings("unchecked")
    private V find(K key, Node<K, V> node) {
        if (node == null)
            return null;
        if (((Comparable<K>) key).compareTo(node.key) < 0)
            return find(key, node.left);
        if (((Comparable<K>) key).compareTo(node.key) > 0)
            return find(key, node.right);

        return (V) node.value;
    }

    /**
     * Get the value associated with a given key
     *
     * @param key the key to get the value for
     * @return the current value
     */
    public V getValue(K key) {
        return (find(key, root));
    }

    /**
     * Returns the number of elements in the RBTree
     *
     * @return the number of elements in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Test whether the RBTree is empty
     *
     * @return true if the tree is empty
     * false if the tree is not empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * The height of the tree. Recall that a tree with
     * only a root node has height 0
     *
     * @return the height of the tree at the root node
     */
    public int height() {
        if (root == null)
            return 0;

        return height(root) - 1;
    }

    /**
     * The height of the tree. Recall that a tree with
     * only a root node has height 0
     *
     * @param root node
     * @return the height of the tree at the root node
     */
    private int height(Node<K, V> node) {
        if (node == null)
            return 0;

        int leftHeight = height(node.left) + 1;
        int rightHeight = height(node.right) + 1;

        if (leftHeight > rightHeight)
            return leftHeight;
        return rightHeight;
    }

    /**
     * Check the number of black nodes on each side and check if
     * they are the same
     *
     * @param node (root node)
     * @return number of black nodes
     */
    public int blackNodes(Node<K, V> node) {
        if (node == null)
            return 1;
        int rightBlackNodes = blackNodes(node.right);
        int leftBlackNodes = blackNodes(node.left);
        if (rightBlackNodes != leftBlackNodes) {
            // Handle exception
        }

        if (node.black)
            leftBlackNodes++;

        return leftBlackNodes;
    }

    /**
     * An iterator for all the keys in the RBTree. This will
     * iterate over the keys using In-Order Traversal
     *
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<K> iterator() {
        return new IteratorHelper();
    }

    /**
     * Recursively print the tree. This method should print the
     * entire tree using In-order Traversal to the standard
     * output (i.e. using System.out.println or System.out.print).
     * You can print the tree one node per line, and use periods to
     * note the hierarchy of the tree.
     */
    public void print() {
        print(root, 0);
    }

    /**
     * Recursively print the tree. This method should print the
     * entire tree using In-order Traversal to the standard
     * output (i.e. using System.out.println or System.out.print).
     * You can print the tree one node per line, and use periods to
     * note the hierarchy of the tree.
     *
     * @param root  node
     * @param width
     */
    @SuppressWarnings("unchecked")
    private void print(Node<K, V> node, int width) {
        if (node == null)
            return;

        print(node.left, width + 1);

        for (int i = 0; i < width; i++)
            System.out.print(".");

        String color;
        if (node.black)
            color = "black";
        else
            color = "red";

        if (((Comparable<K>) node.key).compareTo(root.key) == 0)
            System.out.println(node.key + " : " + color + " (root)");
        else
            System.out.println(node.key + " : " + color);

        print(node.right, width + 1);
    }

    @SuppressWarnings("hiding")
    class Node<K, V> {
        K key;
        V value;
        Node<K, V> left, right, parent;
        boolean leftChild, black;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = right = parent = null;
            black = false;
            leftChild = false;
        }
    }

    class IteratorHelper implements Iterator<K> {

        int index;
        K[] array;

        @SuppressWarnings("unchecked")
        public IteratorHelper() {
            index = 0;
            inOrder(root);
            index = 0;
            array = (K[]) new Object[size];
        }

        public boolean hasNext() {
            return index < array.length;
        }

        public void inOrder(Node<K, V> node) {
            if (node != null) {
                inOrder(node.left);
                array[index++] = node.key;
                inOrder(node.right);
            }
        }

        public K next() {
            if (!hasNext())
                throw new NoSuchElementException();

            return array[index++];
        }
    }
}