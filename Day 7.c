#include <stdio.h>
#include <string.h>
#include <math.h>
#include <stdlib.h>
#include <assert.h>

/* Max-heap (lower half) */
int lower[100005];
int lowerSize = 0;

/* Min-heap (upper half) */
int upper[100005];
int upperSize = 0;

/* ---- Max-heap operations ---- */
void lowerPush(int val) {
    lower[lowerSize] = val;
    int i = lowerSize++;
    while (i > 0) {
        int p = (i - 1) / 2;
        if (lower[p] < lower[i]) { int t = lower[p]; lower[p] = lower[i]; lower[i] = t; i = p; }
        else break;
    }
}

int lowerPop() {
    int ret = lower[0];
    lower[0] = lower[--lowerSize];
    int i = 0;
    while (1) {
        int l = 2*i+1, r = 2*i+2, best = i;
        if (l < lowerSize && lower[l] > lower[best]) best = l;
        if (r < lowerSize && lower[r] > lower[best]) best = r;
        if (best == i) break;
        int t = lower[i]; lower[i] = lower[best]; lower[best] = t;
        i = best;
    }
    return ret;
}

int lowerRemove(int val) {
    int idx = -1;
    for (int i = 0; i < lowerSize; i++) if (lower[i] == val) { idx = i; break; }
    if (idx == -1) return 0;
    lower[idx] = lower[--lowerSize];
    /* sift up */
    int i = idx;
    while (i > 0) {
        int p = (i-1)/2;
        if (lower[p] < lower[i]) { int t = lower[p]; lower[p] = lower[i]; lower[i] = t; i = p; }
        else break;
    }
    /* sift down */
    while (1) {
        int l = 2*i+1, r = 2*i+2, best = i;
        if (l < lowerSize && lower[l] > lower[best]) best = l;
        if (r < lowerSize && lower[r] > lower[best]) best = r;
        if (best == i) break;
        int t = lower[i]; lower[i] = lower[best]; lower[best] = t;
        i = best;
    }
    return 1;
}

/* ---- Min-heap operations ---- */
void upperPush(int val) {
    upper[upperSize] = val;
    int i = upperSize++;
    while (i > 0) {
        int p = (i-1)/2;
        if (upper[p] > upper[i]) { int t = upper[p]; upper[p] = upper[i]; upper[i] = t; i = p; }
        else break;
    }
}

int upperPop() {
    int ret = upper[0];
    upper[0] = upper[--upperSize];
    int i = 0;
    while (1) {
        int l = 2*i+1, r = 2*i+2, best = i;
        if (l < upperSize && upper[l] < upper[best]) best = l;
        if (r < upperSize && upper[r] < upper[best]) best = r;
        if (best == i) break;
        int t = upper[i]; upper[i] = upper[best]; upper[best] = t;
        i = best;
    }
    return ret;
}

int upperRemove(int val) {
    int idx = -1;
    for (int i = 0; i < upperSize; i++) if (upper[i] == val) { idx = i; break; }
    if (idx == -1) return 0;
    upper[idx] = upper[--upperSize];
    int i = idx;
    while (i > 0) {
        int p = (i-1)/2;
        if (upper[p] > upper[i]) { int t = upper[p]; upper[p] = upper[i]; upper[i] = t; i = p; }
        else break;
    }
    while (1) {
        int l = 2*i+1, r = 2*i+2, best = i;
        if (l < upperSize && upper[l] < upper[best]) best = l;
        if (r < upperSize && upper[r] < upper[best]) best = r;
        if (best == i) break;
        int t = upper[i]; upper[i] = upper[best]; upper[best] = t;
        i = best;
    }
    return 1;
}

/* ---- Balance so lowerSize == upperSize or lowerSize == upperSize+1 ---- */
void balance() {
    while (lowerSize > upperSize + 1) upperPush(lowerPop());
    while (upperSize > lowerSize)     lowerPush(upperPop());
}

void printMedian() {
    int total = lowerSize + upperSize;
    if (total == 0) { printf("Wrong!\n"); return; }
    if (total % 2 == 1) {
        printf("%d\n", lower[0]);
    } else {
        long long sum = (long long)lower[0] + upper[0];
        if (sum % 2 == 0) printf("%lld\n", sum / 2);
        else printf("%.1f\n", sum / 2.0);
    }
}

void median(int N, char (*s)[3], int *x) {
    for (int i = 0; i < N; i++) {
        if (s[i][0] == 'a') {
            /* Add */
            if (lowerSize == 0 || x[i] <= lower[0]) lowerPush(x[i]);
            else upperPush(x[i]);
            balance();
            printMedian();
        } else {
            /* Remove */
            int total = lowerSize + upperSize;
            if (total == 0) { printf("Wrong!\n"); continue; }
            int found = lowerRemove(x[i]);
            if (!found) found = upperRemove(x[i]);
            if (!found) { printf("Wrong!\n"); continue; }
            balance();
            printMedian();
        }
    }
}

int main() {
    int i, N;
    scanf("%d", &N);
    char s[N][3];
    int x[N];
    for (i = 0; i < N; i++) scanf("%s %d", &(s[i]), &(x[i]));
    median(N, s, x);
}
