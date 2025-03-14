import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinarySearchTree<E extends Comparable<E>> implements AbstractBinarySearchTree<E> {

    private Node<E> root;

    @Override
    public void insert(E element) {
        if (root == null) {
            root = new Node<>(element);
            return;
        }

        Node<E> current = root;

        while (true) {
            if (element.compareTo(current.value) < 0) {
                if (current.leftChild == null) {
                    current.leftChild = new Node<>(element);
                    break;
                } else {
                    current = current.leftChild;
                }
            } else if (element.compareTo(current.value) > 0) {
                if (current.rightChild == null) {
                    current.rightChild = new Node<>(element);
                    break;
                } else {
                    current = current.rightChild;
                }
            } else {
                break;
            }
        }
    }

    @Override
    public boolean contains(E element) {
        return containsRec(root, element);
    }

    private boolean containsRec(Node<E> root, E element) {
        if (root == null) {
            return false;
        }

        if (element.compareTo(root.value) == 0) {
            return true;
        }

        return element.compareTo(root.value) < 0 ? containsRec(root.leftChild, element) : containsRec(root.rightChild, element);
    }

    @Override
    public AbstractBinarySearchTree<E> search(E element) {
        Node<E> result = searchRec(root, element);
        BinarySearchTree<E> subtree = new BinarySearchTree<>();
        subtree.root = result;
        return subtree;
    }

    private Node<E> searchRec(Node<E> root, E element) {
        if (root == null || element.compareTo(root.value) == 0) {
            return root;
        }
        return element.compareTo(root.value) < 0 ? searchRec(root.leftChild, element) : searchRec(root.rightChild, element);
    }

    @Override
    public Node<E> getRoot() {
        return root;
    }

    @Override
    public Node<E> getLeft() {
        return root != null ? root.leftChild : null;
    }

    @Override
    public Node<E> getRight() {
        return root != null ? root.rightChild : null;
    }

    @Override
    public E getValue() {
        return root != null ? root.value : null;
    }

    public static <T extends Comparable<T>> void printTree(Node<T> root) { //обход в ширину
        if (root == null) {
            return;
        }

        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node<T> current = queue.poll();
            System.out.print(current.value + " ");

            if (current.leftChild != null) {
                queue.add(current.leftChild);
            }

            if (current.rightChild != null) {
                queue.add(current.rightChild);
            }
        }
    }

    public void removeNegativeNodes() {
        root = removeNegativeNodesRec(root);
    }

    private Node<E> removeNegativeNodesRec(Node<E> root) {
        if (root == null) {
            return null;
        }

        root.leftChild = removeNegativeNodesRec(root.leftChild);
        root.rightChild = removeNegativeNodesRec(root.rightChild);

        if (root.value instanceof Number) {
            double value = ((Number) root.value).doubleValue();
            if (value < 0 && !isLeaf(root)) {
                return deleteNode(root);
            }
        }

        return root;
    }

    private boolean isLeaf(Node<E> node) {
        return node.leftChild == null && node.rightChild == null;
    }

    private Node<E> deleteNode(Node<E> node) {
        if (node.leftChild == null) {
            return node.rightChild;
        } else if (node.rightChild == null) {
            return node.leftChild;
        }

        node.value = minValue(node.rightChild);
        node.rightChild = deleteRec(node.rightChild, node.value);

        return node;
    }

    private Node<E> deleteRec(Node<E> root, E element) {
        if (root == null) {
            return root;
        }

        if (element.compareTo(root.value) < 0) {
            root.leftChild = deleteRec(root.leftChild, element);
        } else if (element.compareTo(root.value) > 0) {
            root.rightChild = deleteRec(root.rightChild, element);
        } else {
            if (root.leftChild == null) {
                return root.rightChild;
            } else if (root.rightChild == null) {
                return root.leftChild;
            }

            root.value = minValue(root.rightChild);
            root.rightChild = deleteRec(root.rightChild, root.value);
        }

        return root;
    }

    private E minValue(Node<E> root) {
        E minv = root.value;
        while (root.leftChild != null) {
            minv = root.leftChild.value;
            root = root.leftChild;
        }
        return minv;
    }

    public void printTree() {
        List<List<String>> levels = new ArrayList<>();
        fillLevels(levels, root, 0);
        int maxLevel = levels.size();
        int nodeWidth = 3; // Фиксированная ширина узла

        for (int i = 0; i < maxLevel; i++) {
            int spaces = (int) Math.pow(2, maxLevel - i - 1) - 1; // Вычисление отступов
            int interNodeSpaces = (int) Math.pow(2, maxLevel - i) - 1; // Вычисление промежутков между узлами

            System.out.print(" ".repeat(spaces * nodeWidth));

            for (String s : levels.get(i)) {
                // Вывод узла с фиксированной шириной
                System.out.print(String.format("%-" + nodeWidth + "s", s));

                // Промежутки между узлами
                System.out.print(" ".repeat(interNodeSpaces * nodeWidth));
            }
            System.out.println();

            // Вывод символов \ и / между узлами
            if (i < maxLevel - 1) {
                System.out.print(" ".repeat(spaces * nodeWidth));
                for (int j = 0; j < levels.get(i).size(); j++) {
                    // Проверка наличия левого дочернего узла
                    if (2 * j < levels.get(i + 1).size() && !levels.get(i + 1).get(2 * j).trim().isEmpty()) {
                        System.out.print("/");
                    } else {
                        System.out.print(" ");
                    }
                    System.out.print(" ".repeat(nodeWidth - 1));

                    // Проверка наличия правого дочернего узла
                    if (2 * j + 1 < levels.get(i + 1).size() && !levels.get(i + 1).get(2 * j + 1).trim().isEmpty()) {
                        System.out.print("\\");
                    } else {
                        System.out.print(" ");
                    }
                    System.out.print(" ".repeat(interNodeSpaces * nodeWidth - 1));
                }
                System.out.println();
            }
        }
    }

    private void fillLevels(List<List<String>> levels, Node<E> node, int depth) {
        if (levels.size() <= depth) {
            levels.add(new ArrayList<>());
        }
        if (node != null) {
            // Преобразуем значение узла в строку фиксированной ширины
            String value = String.format("%-" + 3 + "s", node.value.toString());
            levels.get(depth).add(value);
            fillLevels(levels, node.leftChild, depth + 1);
            fillLevels(levels, node.rightChild, depth + 1);
        } else {
            // Добавляем пустой узел фиксированной ширины
            levels.get(depth).add(" ".repeat(3));
        }
    }
}