package club.yourbatis.hi.util;

public class LinkStack<T> {
    private class Node<T> {
        T value;
        Node next;

        public Node(T value) {
            this.value = value;
            this.next = null;
        }

        public Node(T value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> topElement;

    public LinkStack() {
    }

    public LinkStack(T value) {
        push(value);
    }

    /**
     * 入栈
     *
     * @param value
     */
    public boolean push(T value) {
        topElement = new Node(value, topElement);
        return true;
    }

    /**
     * 出栈
     *
     * @return
     */
    public T pop() {
        if (topElement == null) {
            return null;
        }
        T value = topElement.value;
        topElement = topElement.next;
        return value;
    }

    /**
     * 返回顶部元素
     *
     * @return
     */
    public T peek() {
        if (empty()) {
            return null;
        }
        return topElement.value;
    }

    public boolean empty() {
        return null == topElement;
    }

    public boolean exists(T value) {
        if (null == value) {
            return false;
        }
        Node node = topElement;
        while (node != null) {
            if (value.equals(node.value)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }
}