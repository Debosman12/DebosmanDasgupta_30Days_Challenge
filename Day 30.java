    // Complete the insertNodeAtTail function below.

    /*
     * For your reference:
     *
     * SinglyLinkedListNode {
     *     int data;
     *     SinglyLinkedListNode next;
     * }
     *
     */
    static SinglyLinkedListNode insertNodeAtTail(SinglyLinkedListNode head, int data) {

        // Create a new node with the given data
        SinglyLinkedListNode newNode = new SinglyLinkedListNode(data);

        // If the list is empty, the new node becomes the head
        if (head == null) {
            return newNode;
        }

        // Traverse to the last node
        SinglyLinkedListNode current = head;
        while (current.next != null) {
            current = current.next;
        }

        // Link the last node to the new node
        current.next = newNode;

        // Return the original head
        return head;
    }
