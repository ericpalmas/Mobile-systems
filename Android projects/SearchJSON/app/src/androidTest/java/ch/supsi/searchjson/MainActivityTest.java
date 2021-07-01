package ch.supsi.searchjson;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.*;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends TestCase {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void ensureTextChangesWork(){

        // Check that the place text was changed.
        onView(withId(R.id.placeEditText)).perform(typeText("London"));

        // Check that the language text was changed.
        onView(withId(R.id.languageEditText)).perform(typeText("It"));

        // Check that the language text was changed.
        onView(withId(R.id.maxrowsEditText)).perform(typeText("3"));

        // check button click
        onView(withId(R.id.button)).perform(click());

        // check returned list view
        onView(withId(R.id.listViewResult)).check(matches(isDisplayed()));
    }
}