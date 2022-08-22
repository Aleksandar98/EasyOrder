package com.example.eeasyorder;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.*;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public ActivityTestRule<MainActivity> activityRule2 = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testLoginSuccessful() throws Exception {
        Espresso.onView(ViewMatchers.withId(R.id.emailEditText)).perform(ViewActions.typeText("test@testmenu.app"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText("test1234"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.signInBtn)).perform(ViewActions.click());
        Espresso.onView(isRoot()).perform(CustomViewAction.waitId(R.id.recyclerView,5000));
        //Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItem(ViewMatchers.withText("Albert Pizza 1"),ViewActions.click()));
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,ViewActions.click()));
        Espresso.onView(ViewMatchers.withText("Albert Pizza 1")).check(matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.logoutBtn)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.signInBtn)).check(matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.emailEditText)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.emailEditText)).perform(ViewActions.typeText("test@testmenu.app"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.clearText());
        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText)).perform(ViewActions.typeText("test1234"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.signInBtn)).perform(ViewActions.click());
        Espresso.onView(isRoot()).perform(CustomViewAction.waitId(R.id.recyclerView,2000));
        activityRule2.finishActivity();
        activityRule2.launchActivity(new Intent());
        Espresso.onView(isRoot()).perform(CustomViewAction.waitId(R.id.recyclerView,2000));
        //Espresso.onView(ViewMatchers.withId(R.id.logoutBtn)).check(matches(ViewMatchers.isDisplayed()));
    }
}
