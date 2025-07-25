package com.irurueta.android.testutils.test

import android.widget.TextView
import androidx.test.ext.junit.rules.activityScenarioRule
import com.irurueta.android.testutils.InstrumentationTestHelper
import org.junit.After
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class InstrumentationTestHelperTest {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<InstrumentationTestHelperActivity>()

    private var activity: InstrumentationTestHelperActivity? = null
    private var view: TextView? = null

    @Before
    fun setUp() {
        activityScenarioRule.scenario.onActivity { activity ->
            this.activity = activity
            view = activity?.findViewById(R.id.text_view_test)
        }
    }

    @After
    fun tearDown() {
        view = null
        activity = null
    }

    @Test
    fun pinch_executesSuccessfully() {
        val view = this.view ?: return fail()

        val xy = IntArray(2)
        view.getLocationOnScreen(xy)

        val viewLeft = xy[0]
        val viewTop = xy[1]

        val viewWidth = view.width
        val viewHeight = view.height

        val viewCenterX = viewLeft + viewWidth / 2
        val viewCenterY = viewTop + viewHeight / 2

        val viewWidthHalf = viewWidth / 2
        val distance = viewWidthHalf / 3

        val verticalPosition = viewCenterY - distance
        val startX1 = viewCenterX - distance
        val startX2 = viewCenterX + distance
        val endX1 = viewCenterX - 2 * distance
        val endX2 = viewCenterX + 2 * distance

        // pinch in
        InstrumentationTestHelper.pinch(
            startX1, verticalPosition, startX2, verticalPosition,
            endX1, verticalPosition, endX2, verticalPosition
        )
    }

    @Test
    fun drag_executesSuccessfully() {
        val view = this.view ?: return fail()

        val xy = IntArray(2)
        view.getLocationOnScreen(xy)

        val viewLeft = xy[0]
        val viewTop = xy[1]

        val viewWidth = view.width
        val viewHeight = view.height

        val viewCenterX = viewLeft + viewWidth / 2
        val viewCenterY = viewTop + viewHeight / 2

        // drag down
        InstrumentationTestHelper.drag(
            viewCenterX,
            viewCenterY,
            viewCenterX,
            viewTop
        )
    }

    @Test
    fun doubleTap_executesSuccessfully() {
        val view = this.view ?: return fail()

        // double tap on view
        InstrumentationTestHelper.doubleTap(view)
    }
}