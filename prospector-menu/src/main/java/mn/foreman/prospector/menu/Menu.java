package mn.foreman.prospector.menu;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * A {@link Menu} provides a {@link MenuItem} implementation that contains
 * multiple child {@link MenuItem MenuItems}.
 */
public class Menu
        extends AbstractMenuItem {

    /** All of the {@link MenuItem MenuItems} in the {@link Menu}. */
    private final List<MenuItem> menuItems;

    /**
     * Constructor.
     *
     * @param builder The reference builder.
     */
    private Menu(final Builder builder) {
        super(builder);
        this.menuItems = builder.menuItems;
    }

    @Override
    public void entered(
            final String input,
            final Scanner scanner) {
        final int menuIndex = Integer.parseInt(input);
        if (menuIndex <= this.menuItems.size() - 1) {
            final MenuItem menuItem =
                    this.menuItems.get(menuIndex);
            menuItem.entered(scanner);
        }
    }

    @Override
    public void entered(final Scanner scanner) {
        System.out.println();
        System.out.println(getDisplayText());
        System.out.println();

        for (int i = 0; i < this.menuItems.size(); i++) {
            final MenuItem menuItem =
                    this.menuItems.get(i);
            printMenuItem(
                    i,
                    menuItem.getDisplayText());
        }

        System.out.println();
        System.out.print(">> Menu choice: ");
        final int menuChoice = scanner.nextInt();
        scanner.nextLine();

        entered(
                Integer.toString(menuChoice),
                scanner);
    }

    /**
     * Utility method to print a menu item.
     *
     * @param index   The menu index.
     * @param display The text to display.
     */
    private static void printMenuItem(
            final int index,
            final String display) {
        System.out.println(
                String.format(
                        "[%d] %s",
                        index,
                        display));
    }

    /** A builder for creating new {@link Menu Menus}. */
    public static class Builder
            extends AbstractMenuItem.AbstractBuilder<
            Builder,
            Menu> {

        /** The items. */
        private final List<MenuItem> menuItems =
                new LinkedList<>();

        /**
         * Adds a {@link MenuItem}.
         *
         * @param menuItem The item to add.
         *
         * @return This builder instance.
         */
        public Builder addMenuItem(final MenuItem menuItem) {
            this.menuItems.add(menuItem);
            return this;
        }

        @Override
        public Menu build() {
            return new Menu(this);
        }

        @Override
        public Builder getThis() {
            return this;
        }
    }
}