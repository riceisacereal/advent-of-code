import java.util.ArrayList;

public class Workflow {
    String name;
    ArrayList<Condition> conds;

    public Workflow(String line) {
        String[] l = line.split("[{}]");
        this.name = l[0];
        conds = new ArrayList<>();
        for (String c : l[1].split(",")) {
            conds.add(new Condition(c));
        }
    }

    public String getResult(Part p) {
        for (Condition c : conds) {
            switch (c.condType) {
                case 2 -> {
                    return c.result;
                }
                case 0 -> { // Less than
                    if (p.xmas.get(c.xmas) < c.thresh) {
                        return c.result;
                    }
                }
                case 1 -> { // Larger than
                    if (p.xmas.get(c.xmas) > c.thresh) {
                        return c.result;
                    }
                }
            }
        }
        return "";
    }
}
