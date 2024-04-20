package Main;

import java.util.concurrent.TimeUnit;

public class Graphics {
    public static void displayTitle() {
        System.out.println("\nHello, and Welcome to...");
        String[] title = new String[6];
        title[0] = " __  __ _____ _   _ ______  _______          ________ ______ _____  ______ _____  ";
        title[1] = "|  \\/  |_   _| \\ | |  ____|/ ____\\ \\        / /  ____|  ____|  __ \\|  ____|  __ \\ ";
        title[2] = "| \\  / | | | |  \\| | |__  | (___  \\ \\  /\\  / /| |__  | |__  | |__) | |__  | |__) |";
        title[3] = "| |\\/| | | | | . ` |  __|  \\___ \\  \\ \\/  \\/ / |  __| |  __| |  ___/|  __| |  _  / ";
        title[4] = "| |  | |_| |_| |\\  | |____ ____) |  \\  /\\  /  | |____| |____| |    | |____| | \\ \\ ";
        title[5] = "|_|  |_|_____|_| \\_|______|_____/    \\/  \\/   |______|______|_|    |______|_|  \\_\\";
        printWallOfText(title, 50);
    }

    public static void displayRules() {
        System.out.println("\n|| HOW TO PLAY ||\n" +
                "There are three different actions you can take:\nF - Flags the spot\nU - Unflags the spot if flagged\nB - Reveals the spot.  If it is a mine, game over!\n"
                +
                "Enter your action, then add the row and column for the spot you chose.\n" +
                "Example: \"F 1,5\" - adds a flag at (1,5), or the 1st column and 5th row\n\n" +
                "Type anything to start: ");

    }

    public static void displayLoseMessage() {
        String[] msg = new String[6];
        System.out.println("Oops!");
        msg[0] = "   _____          __  __ ______    ______      ________ _____  ";
        msg[1] = "  / ____|   /\\   |  \\/  |  ____|  / __ \\ \\    / /  ____|  __ \\ ";
        msg[2] = " | |  __   /  \\  | \\  / | |__    | |  | \\ \\  / /| |__  | |__) |";
        msg[3] = " | | |_ | / /\\ \\ | |\\/| |  __|   | |  | |\\ \\/ / |  __| |  _  / ";
        msg[4] = " | |__| |/ ____ \\| |  | | |____  | |__| | \\  /  | |____| | \\ \\ ";
        msg[5] = "  \\_____/_/    \\_\\_|  |_|______|  \\____/   \\/   |______|_|  \\_\\";
        printWallOfText(msg, 500);
    }

    public static void displayWinMessage() {
        String[] msg = new String[6];
        System.out.println("Amazing!\n");
        msg[0] = " __     __           _____  _     _   _____ _   _ ";
        msg[1] = " \\ \\   / /          |  __ \\(_)   | | |_   _| | | |";
        msg[2] = "  \\ \\_/ /__  _   _  | |  | |_  __| |   | | | |_| |";
        msg[3] = "   \\   / _ \\| | | | | |  | | |/ _` |   | | | __| |";
        msg[4] = "    | | (_) | |_| | | |__| | | (_| |  _| |_| |_|_|";
        msg[5] = "    |_|\\___/ \\__,_| |_____/|_|\\__,_| |_____|\\__(_)";
        printWallOfText(msg, 500);
    }

    private static void printWallOfText(String[] messages, int time) {
        for (String line : messages) {
            System.out.println(line);
            try {
                TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }

}
