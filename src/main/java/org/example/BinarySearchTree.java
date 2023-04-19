package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.w3c.dom.Node;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class BinarySearchTree {
    Node root;
    class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    public BinarySearchTree() {
        this.root = null;
    }

    public void insert(int value) {
        this.root = insertRec(this.root, value);
    }

    private Node insertRec(Node root, int value) {
        if (root == null) {
            root = new Node(value);
            return root;
        }

        if (value < root.value) {
            root.left = insertRec(root.left, value);
        } else if (value > root.value) {
            root.right = insertRec(root.right, value);
        }

        return root;
    }

    public boolean search(int value) {
        return searchRec(this.root, value);
    }

    private boolean searchRec(Node root, int value) {
        if (root == null) {
            return false;
        }

        if (root.value == value) {
            return true;
        } else if (root.value > value) {
            return searchRec(root.left, value);
        } else {
            return searchRec(root.right, value);
        }
    }

    public String toJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.set("tree", toJsonRec(this.root));
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    }

    private ObjectNode toJsonRec(Node root) {
        if (root == null) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("value", root.value);
        node.set("left", toJsonRec(root.left));
        node.set("right", toJsonRec(root.right));

        return node;
    }

    public void balance() {
        int[] values = inOrderTraversal(this.root);
        this.root = balanceRec(values, 0, values.length - 1);
    }

    private int[] inOrderTraversal(Node root) {
        if (root == null) {
            return new int[0];
        }

        int[] left = inOrderTraversal(root.left);
        int[] right = inOrderTraversal(root.right);
        int[] result = new int[left.length + right.length + 1];
        System.arraycopy(left, 0, result, 0, left.length);
        result[left.length] = root.value;
        System.arraycopy(right, 0, result, left.length + 1, right.length);

        return result;
    }

    private Node balanceRec(int[] values, int start, int end) {
        if (start > end) {
            return null;
        }

        int mid = (start + end) / 2;
        Node root = new Node(values[mid]);
        root.left = balanceRec(values, start, mid - 1);
        root.right = balanceRec(values, mid + 1, end);

        return root;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void add(int value) {
        this.root = addRec(this.root, value);
    }

    private Node addRec(Node root, int value) {
        if (root == null) {
            return new Node(value);
        }
        if (value < root.value) {
            root.left = addRec(root.left, value);
        } else if (value > root.value) {
            root.right = addRec(root.right, value);
        }
        return root;
    }

    public boolean delete(int value) {
        Node parent = null;
        Node current = this.root;

        while (current != null && current.value != value) {
            parent = current;

            if (value < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        if (current == null) {
            return false;
        }

        if (current.left == null && current.right == null) {
            if (current == this.root) {
                this.root = null;
            } else if (current == parent.left) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        } else if (current.left == null) {
            if (current == this.root) {
                this.root = current.right;
            } else if (current == parent.left) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
        } else if (current.right == null) {
            if (current == this.root) {
                this.root = current.left;
            } else if (current == parent.left) {
                parent.left = current.left;
            } else {
                parent.right = current.left;
            }
        } else {
            Node successorParent = current;
            Node successor = current.right;

            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }

            if (successor != current.right) {
                successorParent.left = successor.right;
                successor.right = current.right;
            }

            if (current == this.root) {
                this.root = successor;
            } else if (current == parent.left) {
                parent.left = successor;
            } else {
                parent.right = successor;
            }

            successor.left = current.left;
        }

        return true;
    }


    private Node deleteRec(Node root, int value) {
        if (root == null) {
            return null;
        }
        if (value < root.value) {
            root.left = deleteRec(root.left, value);
        } else if (value > root.value) {
            root.right = deleteRec(root.right, value);
        } else {
            // Found the node to delete
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                // Node has two children
                Node successor = getMin(root.right);
                root.value = successor.value;
                root.right = deleteRec(root.right, successor.value);
            }
        }
        return root;
    }

    private Node getMin(Node root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    // New method to display the tree
    public void display() {
        int height = getHeight(this.root);
        int maxSpacing = (int) Math.pow(2, height) - 1;

        Queue<Node> queue = new LinkedList<>();
        queue.add(this.root);
        queue.add(null); // Marker for the end of the current level

        int level = 1;
        int spacing = maxSpacing / 2;

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (currentNode == null) {
                System.out.println();
                level++;
                spacing = maxSpacing / ((int) Math.pow(2, level) - 1);

                if (!queue.isEmpty()) {
                    queue.add(null);
                }
            } else {
                printSpaces(spacing);
                System.out.print(currentNode.value);
                printSpaces(spacing);

                if (currentNode.left != null) {
                    queue.add(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.add(currentNode.right);
                }
            }
        }
    }

    private int getHeight(Node root) {
        if (root == null) {
            return 0;
        }

        return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }

    private void printSpaces(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print(" ");
        }
    }

}
