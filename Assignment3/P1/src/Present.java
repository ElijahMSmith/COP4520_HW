package src;

public class Present {
    public final int tag;
    public Present prev;
    public Present next;
    public boolean marked = false;

    public Present(int tag) {
        this.tag = tag;
    }
}
