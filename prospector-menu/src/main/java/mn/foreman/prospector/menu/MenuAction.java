package mn.foreman.prospector.menu;

/**
 * A {@link MenuAction} provides an action that's executed when an {@link
 * ActionableMenuItem} is selected.
 */
public interface MenuAction {

    /** Runs the action. */
    void selected();
}