package mn.foreman.prospector.app;

import mn.foreman.prospector.aixin.AixinProspector;
import mn.foreman.prospector.antminer.AntminerProspector;
import mn.foreman.prospector.avalon.AvalonProspector;
import mn.foreman.prospector.baikal.BaikalProspector;
import mn.foreman.prospector.blackminer.BlackminerProspector;
import mn.foreman.prospector.dayun.DayunProspector;
import mn.foreman.prospector.dragonmint.DragonmintProspector;
import mn.foreman.prospector.menu.ActionableMenuItem;
import mn.foreman.prospector.menu.Menu;
import mn.foreman.prospector.menu.MenuItem;
import mn.foreman.prospector.model.Miner;
import mn.foreman.prospector.prospect.Prospector;
import mn.foreman.prospector.scan.ScanningStrategy;
import mn.foreman.prospector.scan.TargetedScanningStrategy;
import mn.foreman.prospector.whatsminer.WhatsminerProspector;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/** The application runner. */
public class Main {

    /**
     * The application entry point.
     *
     * @param args The command line arguments.
     */
    public static void main(final String[] args) {
        final List<Miner> miners = new LinkedList<>();

        final Scanner scanner = new Scanner(System.in);

        final MenuItem toMainMenu =
                new ActionableMenuItem.Builder()
                        .setTitle("Return to main menu")
                        .setMenuAction(() -> {
                            // Do nothing
                        })
                        .build();
        final MenuItem exit =
                new Menu.Builder()
                        .setTitle("Save/Exit")
                        .addMenuItem(
                                new ActionableMenuItem.Builder()
                                        .setTitle("Save miners and exit")
                                        .setMenuAction(() -> {
                                            saveMiners(miners);
                                            System.exit(0);
                                        })
                                        .build())
                        .addMenuItem(
                                new ActionableMenuItem.Builder()
                                        .setTitle("Exit without saving")
                                        .setMenuAction(() -> System.exit(0))
                                        .build())
                        .build();

        final MenuItem menu =
                new Menu.Builder()
                        .setTitle("Prospector Main Menu")
                        .setCaption(() -> toMinersCaption(miners))
                        .addMenuItem(
                                new Menu.Builder()
                                        .setTitle("Find ASICs")
                                        .addMenuItem(
                                                createMinerMenu(
                                                        "Aixin",
                                                        new AixinProspector(),
                                                        4028,
                                                        scanner,
                                                        miners))
                                        .addMenuItem(
                                                createMinerMenu(
                                                        "Antminer",
                                                        new AntminerProspector(),
                                                        4028,
                                                        scanner,
                                                        miners))
                                        .addMenuItem(
                                                createMinerMenu(
                                                        "Avalon",
                                                        new AvalonProspector(),
                                                        4028,
                                                        scanner,
                                                        miners))
                                        .addMenuItem(
                                                createMinerMenu(
                                                        "Baikal",
                                                        new BaikalProspector(),
                                                        4028,
                                                        scanner,
                                                        miners))
                                        .addMenuItem(
                                                createMinerMenu(
                                                        "Blackminer",
                                                        new BlackminerProspector(),
                                                        4028,
                                                        scanner,
                                                        miners))
                                        .addMenuItem(
                                                createMinerMenu(
                                                        "Dayun",
                                                        new DayunProspector(),
                                                        4028,
                                                        scanner,
                                                        miners))
                                        .addMenuItem(
                                                createDragonMintMenu(
                                                        scanner,
                                                        miners))
                                        .addMenuItem(
                                                createMinerMenu(
                                                        "Whatsminer",
                                                        new WhatsminerProspector(),
                                                        4028,
                                                        scanner,
                                                        miners))
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
     * Creates a miner menu.
     *
     * @param scanner The {@link Scanner} for reading input.
     * @param miners  The destination {@link List} for discovered {@link Miner
     *                Miners}.
     *
     * @return The {@link Menu}.
     */
    private static MenuItem createDragonMintMenu(
            final Scanner scanner,
            final List<Miner> miners) {
        return new ActionableMenuItem.Builder()
                .setTitle("DragonMint")
                .setMenuAction(() -> {
                    System.out.println();
                    System.out.print(">> Enter DragonMint API username (typically 'admin'): ");
                    final String username = scanner.nextLine();
                    System.out.print(">> Enter DragonMint API password (typically 'dragonadmin'): ");
                    final String password = scanner.nextLine();
                    runScan(
                            80,
                            scanner,
                            new DragonmintProspector(
                                    username,
                                    password),
                            miners);
                })
                .build();
    }

    /**
     * Creates a miner menu.
     *
     * @param title       The menu title.
     * @param prospector  The {@link Prospector} to use.
     * @param defaultPort The default API port.
     * @param scanner     The {@link Scanner} for reading input.
     * @param miners      The destination {@link List} for discovered {@link
     *                    Miner Miners}.
     *
     * @return The {@link Menu}.
     */
    private static MenuItem createMinerMenu(
            final String title,
            final Prospector prospector,
            final int defaultPort,
            final Scanner scanner,
            final List<Miner> miners) {
        return new ActionableMenuItem.Builder()
                .setTitle(title)
                .setMenuAction(() ->
                        runScan(
                                defaultPort,
                                scanner,
                                prospector,
                                miners))
                .build();
    }

    /**
     * Runs a miner scan.
     *
     * @param defaultPort The default API port.
     * @param scanner     The {@link Scanner} to use for reading input.
     * @param prospector  The {@link Prospector} for validation.
     * @param miners      The destination {@link List}.
     */
    private static void runScan(
            final int defaultPort,
            final Scanner scanner,
            final Prospector prospector,
            final List<Miner> miners) {
        System.out.println();
        System.out.print(">> Enter subnet to scan (example: 192.168.1): ");
        final String subnet = scanner.nextLine();
        System.out.print(
                String.format(
                        ">> Enter the miner API port (typically: %d): ",
                        defaultPort));
        final int apiPort = scanner.nextInt();
        scanner.nextLine();
        System.out.println();

        final ScanningStrategy scanningStrategy =
                new TargetedScanningStrategy(
                        prospector);
        miners.addAll(
                scanningStrategy.scan(
                        subnet,
                        apiPort));
    }

    /**
     * Writes all of the discovered {@link Miner Miners} to a file.
     *
     * @param miners The {@link Miner Miners} to save.
     */
    private static void saveMiners(final List<Miner> miners) {
        try {
            final ObjectMapper objectMapper =
                    new ObjectMapper();
            final String json =
                    objectMapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(miners);
            FileUtils.writeStringToFile(
                    new File("miners.json"),
                    json,
                    Charset.defaultCharset());
        } catch (final IOException jpe) {
            System.out.println("Failed to write miners to file: " + jpe);
        }
    }

    /**
     * Creates the main menu caption based on the number of found {@link Miner
     * miners}.
     *
     * @param miners The {@link Miner miners}.
     *
     * @return The caption.
     */
    private static String toMinersCaption(final List<Miner> miners) {
        if (miners.size() > 0) {
            return String.format(
                    "You have %d miners ready to be saved!",
                    miners.size());
        }
        return "Once you're done, you must 'Save' or else your miners will be lost!";
    }
}