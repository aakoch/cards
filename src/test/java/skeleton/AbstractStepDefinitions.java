package skeleton;

import com.adamkoch.cards.loveletter.Action;

/**
 * <p>Created by aakoch on 2017-10-08.</p>
 *
 * @author aakoch
 * @since 1.0.0
 */
public class AbstractStepDefinitions {
    public Class<? extends Action> getActionClass(String actionClassName) {
        try {
            return (Class<? extends Action>) Class.forName("com.adamkoch.cards.loveletter." + actionClassName);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
