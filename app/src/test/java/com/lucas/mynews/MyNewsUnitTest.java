package com.lucas.mynews;

import android.widget.CheckBox;

import org.junit.Test;

import static org.junit.Assert.*;

import com.lucas.mynews.Utils.UtilsSingleton;
import com.lucas.mynews.Views.TopStoriesViewHolder;

import butterknife.BindView;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MyNewsUnitTest {

    UtilsSingleton utils_test = UtilsSingleton.getInstance();

    @Test
    public void DisplaySectionAndSubsectionIfNoNull_test() {
        String section_test = "Europe", subsection_test = "France", result;

        result = utils_test.DisplaySectionAndSubsectionIfNoNull(section_test, subsection_test);

        assertEquals("Europe > France", result);
    }

    @Test
    public void getGoodFormatDate_test() {
        String publishedDate_test = "2018-11-09T16:54:48+0000", result;

        result = utils_test.getGoodFormatDate(publishedDate_test);

        assertEquals("18/11/09", result);
    }
}