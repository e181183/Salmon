package be.helmo.salmon


import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingPolicies
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Timer
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

@LargeTest
@RunWith(AndroidJUnit4::class)
class FakePlayActivityTest {

    private lateinit var scenario: ActivityScenario<FakePlayActivity>

    @Before
    fun setup() {
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.RESUMED)
    }



    @Test
    fun enterTheCorrectSequence() {
        Timer().schedule(1000) {
            onView(withId(R.id.red_button)).perform(click())
            onView(withId(R.id.red_button)).perform(click())
            onView(withId(R.id.yellow_button)).perform(click())
            onView(withId(R.id.green_button)).perform(click())
        }

        Timer().schedule(2000) {
            var niveau = 27
            onView(withText(niveau.toString())).check(matches(isDisplayed()))
            var score = 3650
            onView(withText(score.toString())).check(matches(isDisplayed()))
        }

    }

    @Test
    fun enterWrongSequence() {
        Timer().schedule(1000) {
            onView(withId(R.id.red_button)).perform(click())
            onView(withId(R.id.red_button)).perform(click())
            onView(withId(R.id.yellow_button)).perform(click())
            onView(withId(R.id.yellow_button)).perform(click())
        }

        Timer().schedule(2000) {
            var niveau = 26
            var vies = 2
            var score = 3650
            onView(withText(niveau.toString())).check(matches(isDisplayed()))
            onView(withText(vies.toString())).check(matches(isDisplayed()))
        }

    }


    }


