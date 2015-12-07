/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tripprogram;

/**
 *
 * @author maine
 */
public class SortedTripList {

    private Node head;
    private Node tail;

    public SortedTripList() {
        head = new Node(null);
        tail = null;
    }

    public Node getTail() {
        return tail;
    }

    public Node getHead() {
        return head.next;
    }

    public void insert(Trip obj) {
        if (head.next == null) {
            head.next = new Node(obj);
            tail = head.next;
        } else {
            Node tmp = head.next;
            Node prev = head;
            Node nodeObj = new Node(obj);
            while (tmp != null) {
                if (obj.getAveTime() <= tmp.getObj().getAveTime()) {
                    prev.setNext(nodeObj);
                    nodeObj.setNext(tmp);
                    return;
                } else {
                    prev = tmp;
                    tmp = tmp.next;
                }
            }
            prev.setNext(nodeObj);
            tail = nodeObj;
            tail.next = null;
        }
    }

    public void excludeFirstAndLast() throws Exception {
        if (this.getSize() >= 3) {
            removeHead();
            removeTail();
        } else {
            throw new Exception("TripList data must be greater than 3.");
        }
    }

    public double computerAverageTime() {

        double sum = 0;
        Node tmp = head.next;
        while (tmp != null) {
            sum = sum + tmp.getObj().getAveTime();
            tmp = tmp.next;
        }
        return sum / getSize();
    }

    private void removeHead() {
        head = head.next;
        head.setObj(null);
    }

    private void removeTail() {
        Node tmp = head.next;
        while (tmp != null) {
            if (tmp.next == tail) {
                tail = tmp;
                tmp.next = null;
                return;
            }
            tmp = tmp.next;
        }
    }

    public int getSize() {
        int i = 0;
        Node tmp = head.next;
        while (tmp != null) {
            i++;
            tmp = tmp.next;
        }
        return i;
    }

    @Override
    public String toString() {
        String res = "head-->\n";
        Node temp = head;

        if (temp.next == null) {
            res = res + "null";
        }

        temp = temp.next;
        while (temp != null) {

            res = res + temp.obj;
            res = res + "-->\n";
            temp = temp.next;
        }

        return res;
    }

    public class Node {

        private Trip obj;
        private Node next;

        public Node(Trip obj) {
            this.obj = obj;
        }

        public Trip getObj() {
            return obj;
        }

        public void setObj(Trip obj) {
            this.obj = obj;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

    }
}
