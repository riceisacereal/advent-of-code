class Pipe {
    //    public static HashMap<Character, String> shapeMapConnect = new HashMap() {
//        '|':
//    };
    private char pipe;
    private String connect;
    private Boolean partOfLoop = false;

    /*    N
     * W     E
     *    S
     * */

    public Pipe(char p) {
        this.pipe = p;
        // TODO: make into hashmap
        switch (p) {
            case '|' -> this.connect = "NS";
            case '-' -> this.connect = "EW";
            case 'L' -> this.connect = "NE";
            case 'J' -> this.connect = "NW";
            case '7' -> this.connect = "SW";
            case 'F' -> this.connect = "ES";
            case 'S' -> this.connect = "";
            default -> System.out.println("Unexpected symbol: " + p);
        }
    }

    public void setPartOfLoop() {
        this.partOfLoop = true;
    }

    public Boolean isPartOfLoop() {
        return partOfLoop;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public String getNextDirection(String direction) {
        return this.connect.replaceAll(direction, "");
    }

    @Override
    public String toString() {
        return "" + pipe;
    }
}