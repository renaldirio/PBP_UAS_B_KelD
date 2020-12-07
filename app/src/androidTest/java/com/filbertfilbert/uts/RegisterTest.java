package com.filbertfilbert.uts;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegisterTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void registerTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.btn_register), withText("REGISTER"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                5),
                        isDisplayed()));
        materialButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btn_register), withText("REGISTER"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                6),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.input_nama_user),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_nama_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("qwe"), closeSoftKeyboard());


        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btn_register), withText("REGISTER"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                6),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.input_alamat_user),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_alamat_layout),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("qwe"), closeSoftKeyboard());


        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btn_register), withText("REGISTER"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                6),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.input_email_user),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_email_user_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("qwe@sharklasers.com"), closeSoftKeyboard());


        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.btn_register), withText("REGISTER"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                6),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.input_password_user),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_name_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("qweqwe"), closeSoftKeyboard());


        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.btn_register), withText("REGISTER"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                6),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.input_nomortelp_user),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_nomortelp_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText5.perform(replaceText("0812345678"), closeSoftKeyboard());


        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.btn_register), withText("REGISTER"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                6),
                        isDisplayed()));
        materialButton7.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
