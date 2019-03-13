package com.steamybeans.beanbook;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**in
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class signupFeatureTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void clickSignUpButton_toOpenSignUpPage() throws Exception {
        onView(withId(R.id.BTNsignUp)).perform(click());
        onView(withId(R.id.ETfullName)).perform(typeText("Joe Bloggs"));
        onView(withId(R.id.ETfullName)).perform(closeSoftKeyboard());
        onView(withId(R.id.ETemail)).perform(typeText("joe.blogs@test.com"));
        onView(withId(R.id.ETemail)).perform(closeSoftKeyboard());
        onView(withId(R.id.ETpassword)).perform(typeText("password"));
        onView(withId(R.id.ETpassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.BTNsignUp)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.BTNlogIn)).check(matches(isDisplayed()));

        FirebaseConnection connection = new FirebaseConnection();
        connection.deleteFromDb("joe,blogs@test,com");
    }

    @BeforeClass
    public static void tearDown() throws Exception {
        FirebaseConnection connection = new FirebaseConnection();
        connection.deleteFromDb("joe,blogs@test,com");
    }

}
