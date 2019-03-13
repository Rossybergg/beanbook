package com.steamybeans.beanbook;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class addFriendFeatureTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    @Test
    public void clickLoginButton_toOpenShowSuccess() throws Exception {
        // goes to signup page
        onView(withId(R.id.BTNsignUp)).perform(click());
        onView(withId(R.id.ETfullName)).perform(typeText("Joe Bloggs"));
        onView(withId(R.id.ETfullName)).perform(closeSoftKeyboard());
        Thread.sleep(500);
        onView(withId(R.id.ETemail)).perform(typeText("joe.blogs@test.com"));
        onView(withId(R.id.ETemail)).perform(closeSoftKeyboard());
        Thread.sleep(500);
        onView(withId(R.id.ETpassword)).perform(typeText("password"));
        onView(withId(R.id.ETpassword)).perform(closeSoftKeyboard());
        Thread.sleep(500);
        onView(withId(R.id.BTNsignUp)).perform(click());
        Thread.sleep(2000);
        //adds to database and returns to login view
        onView(withId(R.id.ETemail)).perform(typeText("joe.blogs@test.com"));
        onView(withId(R.id.ETemail)).perform(closeSoftKeyboard());
        Thread.sleep(500);
        onView(withId(R.id.ETpassword)).perform(typeText("password"));
        onView(withId(R.id.ETpassword)).perform(closeSoftKeyboard());
        Thread.sleep(500);
        onView(withId(R.id.BTNlogIn)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.TVmessage)).check(matches(withText("Success")));

        //this parts fails. who knows why
        FirebaseConnection connection = new FirebaseConnection();
        connection.deleteFromDb("joe,blogs@test,com");

    }
}
