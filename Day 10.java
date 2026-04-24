
    // Complete the hasCycle function below.
    static boolean hasCycle(SinglyLinkedListNode head) {

        if (head == null) return false;

        SinglyLinkedListNode slow = head;
        SinglyLinkedListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;           // move by 1
            fast = fast.next.next;      // move by 2

            if (slow == fast) {
                return true; // cycle detected
            }
        }

        return false; // no cycle
    }
