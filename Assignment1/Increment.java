class Increment {
    private int count = 2;

    public synchronized int getNext() {
        int temp = count;
        count = count + 1;
        return temp;
    }
}
