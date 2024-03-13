/**
 * BinomialHeap
 *
 * An implementation of binomial heap over non-negative integers.
 * Based on exercise from previous semester.
 */
public class BinomialHeap
{
    public int size;
    public HeapNode first;
    public HeapNode last;
    public HeapNode min;
    public int linksCount;
    public int ranksDeleted;

    public BinomialHeap(){
        size = 0;
        first = new HeapNode();
        last = first;
        last.next = first;
        min = first;
    }
    public BinomialHeap(HeapNode root){
        size = 1;
        first = root;
        last = first;
        last.next = first;
        min = first;
    }

    //Special constructor - private use only, it is used for dealing with RemoveMin, as in the implemention
    //We expect the list of nodes to be ordered by ranks.
    private BinomialHeap(HeapNode root, int size, int treesCnt){
        HeapNode nodes[] = new HeapNode[treesCnt];
        for(int i = 0; i < treesCnt; i++){
            nodes[i] = null;
        }

        this.size = size;
        first = root;
        HeapNode it = root;
        int val = Integer.MAX_VALUE;

        int time = treesCnt;
        while(time-- > 0){
            it.parent = null;
            nodes[it.rank] = it;
            if(it.item.key < val){
                this.min = it;
                val = this.min.item.key;
            }
            it = it.next;
        }
        HeapNode sentinel = new HeapNode();
        HeapNode curr = sentinel;
        for(int i = 0; i < treesCnt; i++){
            if(nodes[i] != null){
                curr.next = nodes[i];
                curr = curr.next;
            }
        }
        curr.next = sentinel.next;

        this.first = sentinel.next;
        this.last = curr;
        //this.treeCount = treesCnt;
    }

    /**
     *
     * pre: key > 0
     *
     * Insert (key,info) into the heap and return the newly generated HeapItem.
     *
     */
    public HeapItem insert(int key, String info)
    {
        HeapItem I = new HeapItem(null,key,info);
        this.meld(new BinomialHeap(new HeapNode(I)));
        return I;
    }

    /**
     *
     * Delete the minimal item
     *
     */
    public void deleteMin()
    {
        ranksDeleted += min.rank;
        HeapNode it = first;
        while(it.next != this.min){
            it = it.next;
        }
        it.next = min.next;
        min.next = null;

        if(first == min){
            first = last.next;
        }
        else if(last == min){
            last = it;
        }

        HeapNode WasMin = this.min;

        int val = Integer.MAX_VALUE;
        HeapNode run = first;
        for(int i = 0; i < numTrees()-1; i++){
            if(run.item.key < val){
                min = run;
                val = run.item.key;
            }
            run = run.next;
        }

        int sz = (int)Math.pow(2,WasMin.rank)-1;
        this.size -= sz+1;

        BinomialHeap heap2 = new BinomialHeap(WasMin.child,sz,WasMin.rank);
        this.meld(heap2);
        return;
    }

    /**
     *
     * Return the minimal HeapItem
     *
     */
    public HeapItem findMin()
    {
        return min.item;
    }

    /**
     *
     * pre: 0 < diff < item.key
     *
     * Decrease the key of item by diff and fix the heap.
     *
     */
    public void decreaseKey(HeapItem item, int diff)
    {
        item.key -= diff;
        HeapNode vertex = item.node;
        HeapNode parent = vertex.parent;
        while(parent != null && item.key < parent.item.key){
            HeapItem it = parent.item;
            parent.item = item;
            item.node = parent;
            it.node = vertex;
            vertex.item = it;

            vertex = parent;
            parent = parent.parent;
        }
        return;
    }

    /**
     *
     * Delete the item from the heap.
     *
     */
    public void delete(HeapItem item)
    {
        this.decreaseKey(item,Integer.MAX_VALUE);
        deleteMin();
        return;
    }

    /**
     *
     * Meld the heap with heap2
     *
     */

    public void meld(BinomialHeap heap2)
    {
        if(this.empty()){
            this.first = heap2.first;
            this.last = heap2.last;
            this.size = heap2.size;
            this.min = heap2.min;
            return;
        }
        if(heap2.empty()){
            return;
        }
        HeapNode sentinel = new HeapNode();
        HeapNode lst = sentinel;

        HeapNode h1 = this.first;
        HeapNode h1_nxt = h1.next;
        HeapNode h2 = heap2.first;
        HeapNode h2_nxt = h2.next;

        HeapNode carry = new HeapNode();

        int time1 = this.numTrees();
        int time2 = heap2.numTrees();
        boolean flag1 = false; //Alerts us if we need to move h1 forward.
        boolean flag2 = false;

        while(time1 > 0 && time2 > 0){
            if(h1.rank == h2.rank){
                if(carry.isReal){
                    lst.next = carry;
                    lst = lst.next;
                    carry = new HeapNode();
                }
                if(h1.item.key < h2.item.key){
                    h1.setChild(h2);
                    carry = h1;
                }
                else{
                    h2.setChild(h1);
                    carry = h2;
                }
                linksCount++;
                flag1 = true;
                flag2 = true;
            }
            else if (h1.rank < h2.rank){
                if(carry.isReal && carry.rank == h1.rank){
                    if(carry.item.key <= h1.item.key){
                        carry.setChild(h1);
                    }
                    else{
                        h1.setChild(carry);
                        carry = h1;
                    }
                    linksCount++;
                }
                else {
                    if(carry.isReal){
                        lst.next = carry;
                        lst = lst.next;
                        carry = new HeapNode();
                    }
                    lst.next = h1;
                    lst = lst.next;
                }

                flag1 = true;
            }
            else{ //h1.rank > h2.rank
                if(carry.isReal && carry.rank == h2.rank){
                    if(carry.item.key <= h2.item.key){
                        carry.setChild(h2);
                    }
                    else{
                        h2.setChild(carry);
                        carry = h2;
                    }
                    linksCount++;
                }
                else {
                    if(carry.isReal){
                        lst.next = carry;
                        lst = lst.next;
                        carry = new HeapNode();
                    }
                    lst.next = h2;
                    lst = lst.next;
                }

                flag2 = true;
            }

            if (flag1){
                h1 = h1_nxt;
                h1_nxt = h1.next;
                time1--;
            }
            if(flag2){
                h2 = h2_nxt;
                h2_nxt = h2.next;
                time2--;
            }
            flag1 = false;
            flag2 = false;
        }

        while (time1>0){
            if (carry.isReal){
                if(carry.rank < h1.rank){
                    lst.next = carry;
                    carry = new HeapNode();
                    lst = lst.next;
                    lst.next = h1;
                    break;
                }
                else{
                    if(carry.item.key <= h1.item.key){
                        carry.setChild(h1);
                    }
                    else{
                        h1.setChild(carry);
                        carry = h1;
                    }
                    linksCount++;
                }
            }
            else{
                lst.next = h1;
                break;
            }

            h1 = h1_nxt;
            h1_nxt = h1.next;
            time1--;
        }
        while (time2>0){
            if (carry.isReal){
                if(carry.rank < h2.rank){
                    lst.next = carry;
                    carry = new HeapNode();
                    lst = lst.next;
                    lst.next = h2;
                    break;
                }
                else{
                    if(carry.item.key <= h2.item.key){
                        carry.setChild(h2);
                    }
                    else{
                        h2.setChild(carry);
                        carry = h2;
                    }
                    linksCount++;
                }
            }
            else{
                lst.next = h2;
                break;
            }

            h2 = h2_nxt;
            h2_nxt = h2.next;
            time2--;
        }

        if(carry.isReal){
            lst.next = carry;
            lst = lst.next;
            this.last = carry;
            carry = new HeapNode();
        }
        else if(this.size < heap2.size()){
            last = heap2.last;
        }

        this.first = sentinel.next;
        last.next = first;
        if(this.size == 0 || this.min.item.key > heap2.min.item.key){
            this.min = heap2.min;
        }
        this.size += heap2.size;
        return; // should be replaced by student code
    }

    /**
     *
     * Return the number of elements in the heap
     *
     */
    public int size()
    {
        return size;
    }

    /**
     *
     * The method returns true if and only if the heap
     * is empty.
     *
     */
    public boolean empty()
    {
        return size == 0;
    }

    /**
     *
     * Return the number of trees in the heap.
     *
     */
    public int numTrees()
    {
        int cnt = 0;
        for(int k = 0; k < 31; k++){
            int tpow = (int)Math.pow(2,k);
            if((tpow&size) != 0){
                cnt++;
            }
        }
        return cnt; // should be replaced by student code
    }

    /**
     * Class implementing a node in a Binomial Heap.
     *
     */
    public class HeapNode{
        public HeapItem item;
        public HeapNode child;
        public HeapNode next;
        public HeapNode parent;
        public int rank;
        public boolean isReal;

        public HeapNode(){
            isReal = false;
            rank = -1;
        }
        public HeapNode(HeapItem item){
            this.item = item;
            item.node = this;
            next = new HeapNode();
            rank = 0;
            isReal = true;
        }

        //Assuming rank(child) == rank(this) & this.min < child.min
        public void setChild(HeapNode child) {
            child.next = this.child;
            this.child = child;
            this.child.parent = this;
            rank++;
        }
    }



    /**
     * Class implementing an item in a Binomial Heap.
     *
     */


    public class HeapItem{
        public HeapNode node;
        public int key;
        public String info;
        public HeapItem(){}
        public HeapItem(HeapNode node, int key, String info){
            this.node = node;
            this.key = key;
            this.info = info;
        }
    }
}
