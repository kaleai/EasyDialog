package kale.ui.view.dialog;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kale
 * @date 2018/8/17
 */
public class ReplaceValueByActivityTest {

    @Test
    public void listener_isNull_return_null() {
        TestModel model = new TestModel();
        Serializable value = EasyDialogListeners.replaceValueByActivity(null, Serializable.class, model);
        Assert.assertNull(value);
    }

    @Test
    public void listener_notNull_return_notNull() {
        TestModel person = new TestModel();
        Object value = EasyDialogListeners.replaceValueByActivity("listener", Serializable.class, person);
        Assert.assertEquals(person, value);
    }

    @Test
    public void listener_not_interface_return_null() {
        Kale kale = new Kale();
        Object value = EasyDialogListeners.replaceValueByActivity("listener", Serializable.class, kale);
        Assert.assertNull(value);
    }

    private static class Kale {

    }
}