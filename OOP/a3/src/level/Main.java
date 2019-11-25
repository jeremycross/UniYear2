package level;


final class Main {
    private Main() {
        //Never called
    }

    /**
     * main method of program, is used to generate a dungeon level
     * with 5 chambers and print out the resulting level.
     * @param args an array of arguments when the program is run
     * (this program requires no arguments to be inputted).
     */
    public static void main(final String[] args) {
        Level level = new Level();

        level.createLevel();
    }


}
