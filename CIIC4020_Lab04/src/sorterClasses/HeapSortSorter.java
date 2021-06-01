package sorterClasses;

public class HeapSortSorter<E> extends AbstractSorter<E> {
public HeapSortSorter() {
super("The Fast Heap Sort");
}
protected void auxSort() {
// convert to heap
int n = arr.length;
for (int r = (n-2)/2; r>=0; r--)
downHeap(r, n);
for (int i = n-1; i>0; i--) {
swapArrayElements(i, 0);
downHeap(0, i);
}
}
private int left(int r) { return 2*r+1; }
private int right(int r) { return 2*r+2; }
private boolean hasLeft(int r, int n) { return left(r) < n; }
private boolean hasRight(int r, int n) { return right(r) < n; }
private void downHeap(int r, int n) {
// r is a root of a subtree in the complete tree formed
// by the first n elements in dataSet --- 0..n-1
boolean isHeap = false;
while (!isHeap && hasLeft(r, n)) {
int mci = left(r);
if (hasRight(r, n) && -cmp.compare(arr[right(r)], arr[mci]) < 0)
mci = right(r);
if (-cmp.compare(arr[mci], arr[r]) < 0) {
swapArrayElements(r, mci);
r = mci;
}
else
isHeap = true;
}
}
}