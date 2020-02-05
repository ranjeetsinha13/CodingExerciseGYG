package com.rs.codingexercisegyg.util

import com.rs.codingexercisegyg.utils.convertStringDateFormat
import org.junit.Assert
import org.junit.Test
import java.text.ParseException

class StringUtilsTest {

    @Test
    fun testConvertStringDateFormatValid() {
        val validDate = "2020-01-11T14:56:03+01:00"
        val result = convertStringDateFormat(validDate)
        Assert.assertTrue(result == "Sat Jan 11 20:26:03 IST 2020")
    }

    @Test(expected = ParseException::class)
    fun testConvertStringDateFormatInValid() {
        convertStringDateFormat("3234")
    }
}