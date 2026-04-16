void reversePrint(SinglyLinkedListNode* llist) {
    if (llist == NULL) {
        return;
    }
    
    // Recursive call
    reversePrint(llist->next);
    
    // Print while returning
    printf("%d\n", llist->data);
}
