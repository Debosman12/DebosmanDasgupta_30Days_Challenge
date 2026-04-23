public static DoublyLinkedListNode sortedInsert(DoublyLinkedListNode llist, int data) {

    DoublyLinkedListNode newNode = new DoublyLinkedListNode(data);

    // Case 1: Empty list
    if (llist == null) {
        return newNode;
    }

    // Case 2: Insert at head
    if (data <= llist.data) {
        newNode.next = llist;
        llist.prev = newNode;
        return newNode;
    }

    DoublyLinkedListNode current = llist;

    // Traverse to find position
    while (current.next != null && current.next.data < data) {
        current = current.next;
    }

    // Insert node
    newNode.next = current.next;

    if (current.next != null) {
        current.next.prev = newNode;
    }

    current.next = newNode;
    newNode.prev = current;

    return llist;
}
