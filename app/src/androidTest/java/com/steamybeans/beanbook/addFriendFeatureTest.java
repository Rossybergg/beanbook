package com.steamybeans.beanbook;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.StringStartsWith.startsWith;

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
        onView(withId(R.id.ETemail)).perform(typeText("joe.blogs@test.com"));
        onView(withId(R.id.ETemail)).perform(closeSoftKeyboard());
        onView(withId(R.id.ETpassword)).perform(typeText("password"));
        onView(withId(R.id.ETpassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.BTNsignUp)).perform(click());
        Thread.sleep(1000);
        //adds to database and returns to login view
        onView(withId(R.id.ETemail)).perform(typeText("joe.blogs@test.com"));
        onView(withId(R.id.ETemail)).perform(closeSoftKeyboard());
        onView(withId(R.id.ETpassword)).perform(typeText("password"));
        onView(withId(R.id.ETpassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.BTNlogIn)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.LEFT)));
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_add_friend));
        typeInputIntoTextView("jeddarino");
        Thread.sleep(2000);
        onView(withId(200000)).perform(click());
        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.LEFT)));
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_view_friends));
        onView(withText("Jeddarino")).check(matches(isDisplayed()));
    }
    @BeforeClass
    public static void tearDown() throws Exception {
        FirebaseConnection connection = new FirebaseConnection();
        connection.deleteFromDb("joe,blogs@test,com");
    }

    private void typeInputIntoTextView(String text) {
        if (0 != text.length()) {
            Espresso.onView(withId(R.id.ETSearchFriend)).perform(ViewActions.typeText(text.substring(0, 1)));
            for (int i = 1; i < text.length(); i++) {
                Espresso.onView(withId(R.id.ETSearchFriend)).perform(ViewActions.typeTextIntoFocusedView(text.substring(i, i + 1)));
            }
        }
    }
}
