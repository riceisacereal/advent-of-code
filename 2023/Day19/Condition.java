public class Condition {
    int condType; // 0 < 1 > 2 A or next workflow
    String xmas;
    int thresh;
    String result;

    public Condition(String line) {
        if (line.indexOf("<") > 0) {
            condType = 0;
        } else if (line.indexOf(">") > 0) {
            condType = 1;
        } else {
            condType = 2;
            result = line;
            return;
        }

        String[] l = line.split(":");
        result = l[1];
        String[] cond = l[0].split("[<>]");
        xmas = cond[0];
        thresh = Integer.parseInt(cond[1]);
    }
}
