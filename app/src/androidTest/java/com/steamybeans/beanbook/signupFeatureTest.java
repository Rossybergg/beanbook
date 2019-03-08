package com.steamybeans.beanbook;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
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
        Thread.sleep(500);
        onView(withId(R.id.ETemail)).perform(typeText("joe.blogs@test.com"));
        Thread.sleep(500);
        onView(withId(R.id.ETpassword)).perform(typeText("password"));
        Thread.sleep(500);
        onView(withId(R.id.BTNsignUp)).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.BTNlogIn)).check(matches(isDisplayed()));

        FirebaseConnection connection = new FirebaseConnection();
        connection.deleteFromDb("joe,blogs@test,com");
    }

}
