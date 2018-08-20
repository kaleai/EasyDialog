package kale.ui.view.dialog;

import junit.framework.Assert;

import android.app.Activity;

import org.junit.Test;

/**
 * @author Kale
 * @date 2018/8/17
 */
public class ReplaceActivityByValueTest {

    private static TestModel staticTestModel = new TestModel();

    private static MyActivity staticActivity = new MyActivity();

    @Test
    public void listenerIsNull_return_null() {
        String value = EasyDialogListeners.replaceActivityByValue(null, "kale");
        Assert.assertNull(value);
    }

    @Test
    public void listenerNotActivity_return_null() {
        TestModel value = EasyDialogListeners.replaceActivityByValue(new TestModel(), staticTestModel);
        Assert.assertNull(value);
    }

    @Test
    public void listenerIsActivity_return_activity() {
        MyActivity value = EasyDialogListeners.replaceActivityByValue(new MyActivity(), staticActivity);

        Assert.assertNotNull(value);
        
        Assert.assertTrue(Activity.class.isInstance(value));
    }

    public static class MyActivity extends Activity {

    }

}