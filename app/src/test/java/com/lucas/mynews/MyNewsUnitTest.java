package com.lucas.mynews;

import org.junit.Test;

import static org.junit.Assert.*;

import com.lucas.mynews.Utils.UtilsFunction;



public class MyNewsUnitTest {

    @Test
    public void DisplaySectionAndSubsectionIfNoNull_test() {
        String section_test = "Europe", subsection_test = "France", result;

        result = UtilsFunction.DisplaySectionAndSubsectionIfNoNull(section_test, subsection_test);

        assertEquals("Europe > France", result);
    }

    @Test
    public void getGoodFormatDate_test() {
        String publishedDate_test = "2018-11-09T16:54:48+0000", result;

        result = UtilsFunction.getGoodFormatDate(publishedDate_test);

        assertEquals("18/11/09", result);
    }

    @Test
    public void getGoodFormatUrl(){
        String url_test = "http://", result;

        result = UtilsFunction.getGoodFormatUrl(url_test);

        assertEquals("https://", result);
    }
}