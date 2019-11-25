package gui;

public final class Launcher {
    private Launcher() {
    }

    /**
     * main method of program, is used to generate a dungeon level
     * with 5 chambers and creates a GUI to interact with this level.
     * @param args arguments to program (not used in this case).
     */
    public static void main(String[] args) {
        DungeonGUI.main(args);
    }
}
