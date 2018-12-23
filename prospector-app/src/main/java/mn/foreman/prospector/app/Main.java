package mn.foreman.prospector.app;

import mn.foreman.prospector.antminer.AntminerProspector;
import mn.foreman.prospector.menu.ActionableMenuItem;
import mn.foreman.prospector.menu.Menu;
import mn.foreman.prospector.menu.MenuItem;
import mn.foreman.prospector.model.Miner;
import mn.foreman.prospector.prospect.Prospector;
import mn.foreman.prospector.scan.ScanningStrategy;
import mn.foreman.prospector.scan.TargetedScanningStrategy;

import java.util.List;
import java.util.Scanner;

/** The application entry point. */
public class Main {

    /**
     * The application entry point.
     *
     * @param args The command line arguments.
     */
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);

        final MenuItem toMainMenu =
                new ActionableMenuItem.Builder()
                        .setDisplayText("Return to main menu")
                        .setMenuAction(() -> {
                        })
                        .build();
        final MenuItem exit =
                new ActionableMenuItem.Builder()
                        .setDisplayText("Exit")
                        .setMenuAction(() -> System.exit(0))
                        .build();

        final Menu menu =
                new Menu.Builder()
                        .setDisplayText("Prospector Main Menu")
                        .addMenuItem(
                                new Menu.Builder()
                                        .setDisplayText("Find ASICs")
                                        .addMenuItem(
                                                createAntminerMenu(
                                                        toMainMenu,
                                                        exit,
                                                        scanner))
                                        .addMenuItem(toMainMenu)
                                        .addMenuItem(exit)
                                        .build())
                        .addMenuItem(exit)
                        .build();

        //noinspection InfiniteLoopStatement
        while (true) {
            // This loop is terminated when the user selects to Exit
            menu.entered(scanner);
        }
    }

    /**
     * Creates the Antminer menu.
     *
     * @param toMainMenu The {@link MenuItem} that returns the user to the main
     *                   menu.
     * @param exit       The {@link MenuItem} that exits the application.
     * @param scanner    The {@link Scanner} for reading input.
     *
     * @return The {@link Menu}.
     */
    private static Menu createAntminerMenu(
            final MenuItem toMainMenu,
            final MenuItem exit,
            final Scanner scanner) {
        return new Menu.Builder()
                .setDisplayText("Antminer")
                .addMenuItem(
                        new ActionableMenuItem.Builder()
                                .setDisplayText("Scan")
                                .setMenuAction(() ->
                                        runScan(
                                                scanner,
                                                new AntminerProspector()))
                                .build())
                .addMenuItem(toMainMenu)
                .addMenuItem(exit)
                .build();
    }

    /**
     * Runs a miner scan.
     *
     * @param scanner    The {@link Scanner} to use for reading input.
     * @param prospector The {@link Prospector} for validation.
     *
     * @return The {@link Miner Miners} that were found, if any.
     */
    private static List<Miner> runScan(
            final Scanner scanner,
            final Prospector prospector) {
        System.out.println();
        System.out.print(">> Enter subnet to scan (example: 192.168.1): ");
        final String subnet = scanner.nextLine();
        System.out.print(">> Enter the miner API port (typically 4028): ");
        final int apiPort = scanner.nextInt();
        scanner.nextLine();

        final ScanningStrategy scanningStrategy =
                new TargetedScanningStrategy(
                        prospector);
        return scanningStrategy.scan(
                subnet,
                apiPort);
    }
}