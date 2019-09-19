package com.permenko.converter.currency.ui.rates

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.permenko.converter.currency.AppFake
import com.permenko.converter.currency.R
import com.permenko.converter.currency.utils.atPosition
import com.permenko.converter.currency.ui.MainActivity
import org.junit.Rule
import org.junit.Test

class CurrencyRatesTests {
  @get:Rule val rule = ActivityTestRule(MainActivity::class.java)

  @Test
  fun app() {
    assert(rule.activity.application is AppFake)
  }

  @Test
  fun initialLoading() {
    onView(withId(R.id.loadingView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
  }

  @Test
  fun hideLoading() {
    val idlingResource = RecyclerViewNotEmptyIdling(rule.activity.findViewById(R.id.rv_currency_rates))
    IdlingRegistry.getInstance().register(idlingResource)
    onView(withId(R.id.loadingView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    IdlingRegistry.getInstance().unregister(idlingResource)
  }

  @Test
  fun updateBaseAmount() {
    hideLoading()
    onView(withId(R.id.rv_currency_rates)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    onView(withId(R.id.et_overlay_amount)).perform(replaceText("40")).perform(pressImeActionButton())
    onView(withId(R.id.rv_currency_rates))
      .check(matches(atPosition(0, hasDescendant(withText("40.00")))))
  }
}
