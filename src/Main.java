public class Main {
    public static void main(String[] args) {
        BinaryTree<Integer> root = new BinaryTree<>(1);
        BinaryTree<Integer> left = new BinaryTree<>(2);
        BinaryTree<Integer> right = new BinaryTree<>(3);
        BinaryTree<Integer> leftLeft = new BinaryTree<>(4);
        BinaryTree<Integer> leftRight = new BinaryTree<>(5);
        BinaryTree<Integer> rightLeft = new BinaryTree<>(6);
        BinaryTree<Integer> rightRight = new BinaryTree<>(7);

        root.setLeft(left);
        root.setRight(right);
        left.setLeft(leftLeft);
        left.setRight(leftRight);
        right.setLeft(rightLeft);
        right.setRight(rightRight);

        root.printTree();

        System.out.println("In-order traversal:");
        root.inOrder().forEach(tree -> System.out.print(tree.getKey() + " "));
        System.out.println("\nIndented Pre-order:");
        System.out.println(root.asIndentedPreOrder(0));

        System.out.println("Indented Post-order:");
        root.postOrder().forEach(tree -> System.out.print(tree.getKey() + " "));

        System.out.println("\nDFS traversal:");
        root.dfs().forEach(tree -> System.out.print(tree.getKey() + " "));

        System.out.println("\nBFS traversal:");
        root.bfs().forEach(tree -> System.out.print(tree.getKey() + " "));

        System.out.println("\n");


        root.printTree();


        System.out.println("\n\n\n\n");

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);
        bst.insert(60);
        bst.insert(80);

        System.out.println("Contains 30: " + bst.contains(30)); // true
        System.out.println("Contains 90: " + bst.contains(90)); // false

        AbstractBinarySearchTree<Integer> subtree = bst.search(30);
        System.out.println("Subtree root value: " + subtree.getValue()); // 30

        System.out.println("\n\n\n");

        BinarySearchTree<Double> bstt = new BinarySearchTree<>();
        bstt.insert(-2.32);
        bstt.insert(-1.323);
        bstt.insert(1.3233);
        bstt.insert(-10.0);
        bstt.insert(-20.0);
        bstt.insert(-5.0);
        bstt.insert(-0.0);
        bstt.insert(8.0);


        System.out.println("Before removing negative nodes:");
        BinarySearchTree.printTree(bstt.getRoot());

        bstt.removeNegativeNodes();

        System.out.println("\nAfter removing negative nodes:");
        BinarySearchTree.printTree(bstt.getRoot());
        System.out.println("\n");
        System.out.println("\n Print tree");
        bstt.printTree();
    }
}
