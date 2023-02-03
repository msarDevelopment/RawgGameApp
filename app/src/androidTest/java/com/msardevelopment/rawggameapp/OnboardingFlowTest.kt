package com.msardevelopment.rawggameapp

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.Before
import org.junit.Test

//set up SharedPreferences with onboardingDone = false
//start MainActivity and since setting onboardingDone to false simulates first launch, FragmentOnboarding should be loaded
//press Continue button (that will change value of onboardingDone to true)
//close MainActivity
//open MainActivity again
//now DiscoverGamesFragment should be loaded
//check is tvDiscoverGamesLabel element visible (it should be, because it is the element of DiscoverGamesFragment)
class OnboardingFlowTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp(){
        setSharedPreferences()
        scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    private fun setSharedPreferences() {
        val targetContext = getInstrumentation().targetContext
        val editSharedPref = targetContext.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
        editSharedPref.clear()
        editSharedPref.putBoolean("onboardingDone", false)
        editSharedPref.commit()
    }

    @Test
    fun completeOnboarding_onNextStartExpectDiscoverGamesFragment(){
        Thread.sleep(3000L)
        Espresso.onView(ViewMatchers.withId(R.id.genreSelectionFab)).perform(ViewActions.click())
        Thread.sleep(3000L)
        scenario.close()
        Thread.sleep(3000L)
        scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
        Thread.sleep(3000L)
        Espresso.onView(ViewMatchers.withId(R.id.tvDiscoverGamesLabel))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
}