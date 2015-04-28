package com.recipspro;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.recipspro.reader.CVSReaderTest;
import com.recipspro.reader.JSONReaderTest;
@RunWith(Suite.class)
@SuiteClasses({ ReciproAppTest.class, RecipeTest.class, CVSReaderTest.class, JSONReaderTest.class })
public class AllTests {
}
