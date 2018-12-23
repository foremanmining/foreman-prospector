package mn.foreman.prospector.menu;

import org.apache.commons.lang3.Validate;

import java.util.Scanner;

/**
 * An {@link ActionableMenuItem} provides a {@link MenuItem} implementation
 * that's a leaf item that will perform an action based on the provided {@link
 * MenuAction}.
 */
public class ActionableMenuItem
        extends AbstractMenuItem {

    /** The {@link MenuAction}. */
    private final MenuAction menuAction;

    /**
     * Constructor.
     *
     * @param builder The reference builder.
     */
    private ActionableMenuItem(final Builder builder) {
        super(builder);
        Validate.notNull(
                builder.menuAction,
                "menuAction cannot be null");
        this.menuAction = builder.menuAction;
    }

    @Override
    public void entered(
            final String input,
            final Scanner scanner) {
        this.menuAction.selected();
    }

    @Override
    public void entered(final Scanner scanner) {
        this.menuAction.selected();
    }

    /**
     * A builder for creating new {@link ActionableMenuItem actionable items}.
     */
    public static class Builder
            extends AbstractMenuItem.AbstractBuilder<
            Builder,
            ActionableMenuItem> {

        /** The {@link MenuAction}. */
        private MenuAction menuAction;

        @Override
        public ActionableMenuItem build() {
            return new ActionableMenuItem(this);
        }

        @Override
        public Builder getThis() {
            return this;
        }

        /**
         * Sets the {@link MenuAction}.
         *
         * @param menuAction The {@link MenuAction}.
         *
         * @return This builder instance.
         */
        public Builder setMenuAction(final MenuAction menuAction) {
            this.menuAction = menuAction;
            return this;
        }
    }
}
