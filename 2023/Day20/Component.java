import java.util.ArrayList;

public class Component {
    String name;
    boolean input;
    char type; // 0 for flip-flop, 1 for NAND
    // % -> 1 nothing
    //   -> 0 toggle previous state
    // & -> multi input NAND (0 if all 1, else 1)
    ArrayList<Component> nandInputs;
    ArrayList<Component> output;

    public Component(String line) {
        input = false;
        output = new ArrayList<>();
        type = line.charAt(0); // broadcast will get type b but that's ok
        switch (line.charAt(0)) {
            case '&':
                nandInputs = new ArrayList<>();
            case '%':
                name = line.substring(1);
                break;
            default: name = line;
        }
    }

    public void addOutput(Component child) {
        output.add(child);
    }

//    public long sendPulse() {
//        int count;
//
//        // Determine output
//        boolean output = input;
//        switch (type) {
//            case '%' -> {
//
//            }
//            case '&' -> {
//
//            }
//        }
//
//        // Send output
//        for (Component o : output) {
//
//        }
//
//    }
}
