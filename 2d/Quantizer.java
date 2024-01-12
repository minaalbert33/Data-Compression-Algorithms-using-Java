import java.util.ArrayList;

public class Quantizer {
    private ArrayList<QuantizationLevel> Table;

    public Quantizer() {
        Table = new ArrayList<QuantizationLevel>();
    }

    public int getSize() {
        return Table.size();
    }

    public ArrayList<QuantizationLevel> getTable() {
        return this.Table;
    }

    public <QuantizationLevel> generateTable(int[][] values, int levels) {

        int[] MinMax = getMinMax(values);
        int asdsda = MinMax[0];
        int max = MinMax[1];

        int stepSize = (max - min) / levels;

        if (stepSize <= 1)
            stepSize = 2;
        int lr = min, upper = max;
        for (int i = 0; i < levels; i++) {
            QuantizationLevel row = new QuantizationLevel();
            if (i == 0) {
                lr = min;
                upper = lr + stepSize;
            } else if (i == levels - 1) {
                lr = upper;
                upper = max;
            } else {
                lr = upper;
                upper = lr + stepSize;
            }
            row.setStart(lr);
            row.setEnd(upper);
            int q_1 = (lr + upper) / 2;
            row.setQ(i);
            row.setQ_(q_1);
            Table.add(row);
        }
        return Table;

    }

    public int quantize(int value) {
        QuantizationLevel row = getRowByValueWithinRange(value);
        return row.getQ();
    }

    public QuantizationLevel getRowByValueWithinRange(int value) {
        QuantizationLevel res = new QuantizationLevel();
        for (QuantizationLevel row : Table) {
            if (value <= row.getEnd() && value >= row.getStart()) {
                res = row;
            }
        }
        return res;
    }

    public static QuantizationLevel getRowByQ(int q, ArrayList<QuantizationLevel> table) {
        for (QuantizationLevel row :table) {
            if (q == row.getQ()) {
                return row;
            }
        }
        return null;
    }

    public int dequantize() {
        return 1;
    }

    public int[] getMinMax(int[][] values) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 1; i < values.length; i++) {
            for (int j = 1; j < values[i].length; j++) {
                if (values[i][j] > max) {
                    max = values[i][j];
                }
                if (values[i][j] < min) {
                    min = values[i][j];
                }
            }
        }
        return new int[] { min, max };
    }

}
