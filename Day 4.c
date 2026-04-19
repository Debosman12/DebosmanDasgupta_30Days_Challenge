#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int compare(char *a, char *b) {
    return strcmp(a, b);
}

void swap(char **a, char **b) {
    char *temp = *a;
    *a = *b;
    *b = temp;
}

void reverse(char **s, int start, int end) {
    while (start < end) {
        swap(&s[start], &s[end]);
        start++;
        end--;
    }
}

int next_permutation(int n, char **s)
{
    // Step 1: Find the largest index i such that s[i] < s[i+1]
    int i = n - 2;
    while (i >= 0 && compare(s[i], s[i + 1]) >= 0)
        i--;

    // If no such index, no next permutation
    if (i < 0)
        return 0;

    // Step 2: Find the largest index j > i such that s[j] > s[i]
    int j = n - 1;
    while (compare(s[j], s[i]) <= 0)
        j--;

    // Step 3: Swap s[i] and s[j]
    swap(&s[i], &s[j]);

    // Step 4: Reverse from i+1 to end
    reverse(s, i + 1, n - 1);

    return 1;
}
