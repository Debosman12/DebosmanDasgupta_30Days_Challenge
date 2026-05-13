#include <bits/stdc++.h>

using namespace std;

string ltrim(const string &);
string rtrim(const string &);
vector<string> split(const string &);

/*
 * Complete the 'solve' function below.
 *
 * The function is expected to return a LONG_INTEGER.
 * The function accepts INTEGER_ARRAY arr as parameter.
 */

long solve(vector<int> arr) {
    using ll = long long;

    int n = arr.size();
    if (n <= 1) return 0;

    // Convert to 1-based indexing
    vector<int> A(n + 1);
    for (int i = 0; i < n; i++) {
        A[i + 1] = arr[i];
    }

    // ------------------------------------------------------------
    // Sparse Table for Range Maximum Query (returns index of maximum)
    // ------------------------------------------------------------
    vector<int> lg(n + 1, 0);
    for (int i = 2; i <= n; i++) {
        lg[i] = lg[i / 2] + 1;
    }

    int K = lg[n] + 1;
    vector<vector<int>> st(K, vector<int>(n + 1));

    for (int i = 1; i <= n; i++) {
        st[0][i] = i;
    }

    for (int j = 1; j < K; j++) {
        for (int i = 1; i + (1 << j) - 1 <= n; i++) {
            int x = st[j - 1][i];
            int y = st[j - 1][i + (1 << (j - 1))];
            st[j][i] = (A[x] >= A[y]) ? x : y;
        }
    }

    auto rmq = [&](int L, int R) {
        int len = R - L + 1;
        int k = lg[len];
        int x = st[k][L];
        int y = st[k][R - (1 << k) + 1];
        return (A[x] >= A[y]) ? x : y;
    };

    // ------------------------------------------------------------
    // Offline Query Node
    // ------------------------------------------------------------
    struct QueryNode {
        int V;
        int sign;
        int next;

        QueryNode(int _V, int _sign, int _next)
            : V(_V), sign(_sign), next(_next) {
        }
    };

    vector<QueryNode> q_nodes;
    q_nodes.reserve(15000000);

    vector<int> q_head(n + 1, -1);

    // Add query: count of elements <= V in A[L..R]
    auto add_query = [&](int L, int R, int V) {
        if (L > R) return;

        q_nodes.push_back(QueryNode(V, 1, q_head[R]));
        q_head[R] = (int)q_nodes.size() - 1;

        if (L - 1 >= 1) {
            q_nodes.push_back(QueryNode(V, -1, q_head[L - 1]));
            q_head[L - 1] = (int)q_nodes.size() - 1;
        }
    };

    ll direct_ans = 0;

    // ------------------------------------------------------------
    // Iterative Divide and Conquer
    // ------------------------------------------------------------
    struct Job {
        int L, R;

        Job(int _L, int _R) : L(_L), R(_R) {
        }
    };

    vector<Job> stack;
    stack.push_back(Job(1, n));

    while (!stack.empty()) {
        Job curr = stack.back();
        stack.pop_back();

        int L = curr.L;
        int R = curr.R;

        if (L >= R) continue;

        int m = rmq(L, R);

        int left_len = m - L;
        int right_len = R - m;

        // Iterate over smaller half
        if (left_len < right_len) {
            for (int i = L; i <= m - 1; i++) {
                int V = A[m] / A[i];
                add_query(m + 1, R, V);

                // Pair (i, m) is valid iff A[i] == 1
                if (A[i] == 1) direct_ans++;
            }

            // Count values equal to 1 on the right side
            add_query(m + 1, R, 1);
        } else {
            for (int j = m + 1; j <= R; j++) {
                int V = A[m] / A[j];
                add_query(L, m - 1, V);

                // Pair (m, j) is valid iff A[j] == 1
                if (A[j] == 1) direct_ans++;
            }

            // Count values equal to 1 on the left side
            add_query(L, m - 1, 1);
        }

        // Recurse iteratively
        stack.push_back(Job(L, m - 1));
        stack.push_back(Job(m + 1, R));
    }

    // ------------------------------------------------------------
    // Coordinate Compression
    // ------------------------------------------------------------
    vector<int> vals;
    vals.reserve(n);

    for (int i = 1; i <= n; i++) {
        vals.push_back(A[i]);
    }

    sort(vals.begin(), vals.end());
    vals.erase(unique(vals.begin(), vals.end()), vals.end());

    auto get_rank_le = [&](int V) {
        return (int)(upper_bound(vals.begin(), vals.end(), V) - vals.begin());
    };

    // ------------------------------------------------------------
    // Fenwick Tree / Binary Indexed Tree
    // ------------------------------------------------------------
    int MAX_VAL = (int)vals.size();
    vector<int> bit(MAX_VAL + 1, 0);

    auto fenwick_add = [&](int idx, int val) {
        for (; idx <= MAX_VAL; idx += idx & -idx) {
            bit[idx] += val;
        }
    };

    auto fenwick_get = [&](int idx) {
        int sum = 0;
        for (; idx > 0; idx -= idx & -idx) {
            sum += bit[idx];
        }
        return sum;
    };

    // ------------------------------------------------------------
    // Process all offline queries
    // ------------------------------------------------------------
    ll final_ans = direct_ans;

    for (int i = 1; i <= n; i++) {
        int rank = get_rank_le(A[i]);
        fenwick_add(rank, 1);

        for (int curr = q_head[i]; curr != -1; curr = q_nodes[curr].next) {
            int r = get_rank_le(q_nodes[curr].V);
            final_ans += (ll)q_nodes[curr].sign * fenwick_get(r);
        }
    }

    return (long)final_ans;
}

int main()
{
    ofstream fout(getenv("OUTPUT_PATH"));

    string arr_count_temp;
    getline(cin, arr_count_temp);

    int arr_count = stoi(ltrim(rtrim(arr_count_temp)));

    string arr_temp_temp;
    getline(cin, arr_temp_temp);

    vector<string> arr_temp = split(rtrim(arr_temp_temp));

    vector<int> arr(arr_count);

    for (int i = 0; i < arr_count; i++) {
        int arr_item = stoi(arr_temp[i]);
        arr[i] = arr_item;
    }

    long result = solve(arr);

    fout << result << "\n";

    fout.close();

    return 0;
}

string ltrim(const string &str) {
    string s(str);

    s.erase(
        s.begin(),
        find_if(s.begin(), s.end(),
                not1(ptr_fun<int, int>(isspace)))
    );

    return s;
}

string rtrim(const string &str) {
    string s(str);

    s.erase(
        find_if(s.rbegin(), s.rend(),
                not1(ptr_fun<int, int>(isspace))).base(),
        s.end()
    );

    return s;
}

vector<string> split(const string &str) {
    vector<string> tokens;

    string::size_type start = 0;
    string::size_type end = 0;

    while ((end = str.find(" ", start)) != string::npos) {
        tokens.push_back(str.substr(start, end - start));
        start = end + 1;
    }

    tokens.push_back(str.substr(start));

    return tokens;
}
