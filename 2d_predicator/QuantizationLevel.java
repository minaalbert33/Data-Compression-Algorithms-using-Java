public class QuantizationLevel {
    private int Q;
    private int Start;
    private int End;
    private int Q_;

    public QuantizationLevel() {
        this.Q = 0;
        this.Start = 0;
        this.End = 0;
        this.Q_ = 0;
    }

    public QuantizationLevel(int Q, int LowerBound, int UpperBound, int _Q) {
        this.Q = Q;
        this.Start = LowerBound;
        this.End = UpperBound;
        this.Q_ = _Q;
    }


    public void print(){
        System.out.println(Q + " " + Start + " " + End + " " + Q_ );
    }
    // Getter and setter for Q
    public int getQ() {
        return Q;
    }

    public void setQ(int q) {
        Q = q;
    }

    // Getter and setter for LowerBound
    public int getStart() {
        return Start;
    }

    public void setStart(int start) {
        Start = start;
    }

    // Getter and setter for UpperBound
    public int getEnd() {
        return End;
    }

    public void setEnd(int end) {
        End = end;
    }

    // Getter and setter for _Q
    public int getQ_() {
        return Q_;
    }

    public void setQ_(int _Q) {
        this.Q_ = _Q;
    }
}
