package com.steamybeans.beanbook;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
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


@RunWith(AndroidJUnit4.class)
public class loginFeatureTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void clickLoginButton_toOpenHomePage() throws Exception {
        // goes to signup page
        onView(withId(R.id.BTNsignUp)).perform(click());
        onView(withId(R.id.ETfullName)).perform(typeText("Joe Bloggs"));
        onView(withId(R.id.ETfullName)).perform(closeSoftKeyboard());
        onView(withId(R.id.ETemail)).perform(typeText("joe.blogs@test.com"));
        onView(withId(R.id.ETemail)).perform(closeSoftKeyboard());
        onView(withId(R.id.ETpassword)).perform(typeText("password"));
        onView(withId(R.id.ETpassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.BTNsignUp)).perform(click());
        //adds to database and returns to login view
        Thread.sleep(1000);
        onView(withId(R.id.ETemail)).perform(typeText("joe.blogs@test.com"));
        onView(withId(R.id.ETemail)).perform(closeSoftKeyboard());
        onView(withId(R.id.ETpassword)).perform(typeText("password"));
        onView(withId(R.id.ETpassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.BTNlogIn)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.BTNrefresh)).check(matches(isDisplayed()));
    }

    @BeforeClass
    public static void tearDown() throws Exception {
        FirebaseConnection connection = new FirebaseConnection();
        connection.deleteFromDb("joe,blogs@test,com");
    }
}
