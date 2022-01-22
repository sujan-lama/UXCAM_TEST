package com.sujan.uxcam.data;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sujan.uxcam.data.AppPreference.SEARCH_LIST_KEY;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppPreferenceTest {

    private AppPreference mMockAppPreference;

    private AppPreference mMockBrokenAppPreference;

    @Mock
    SharedPreferences mockSharedPreference;

    @Mock
    SharedPreferences mMockBrokenSharedPreferences;

    @Mock
    SharedPreferences.Editor mMockEditor;

    @Mock
    SharedPreferences.Editor mMockBrokenEditor;

    List<String> testList = Arrays.asList("London", "Kathmandu");
    String testListAsString = "[\"London\",\"Kathmandu\"]";

    @Before
    public void setup() {
        mMockAppPreference = createMockSharedPreference();
        mMockBrokenAppPreference = createBrokenMockSharedPreference();
    }


    @Test
    public void testing_saving_and_reading_search_list() {
        boolean success = mMockAppPreference.setSearchList(testList);
        assertThat("testing search list is stored",
                success, is(true));
        List<String> searchList =
                mMockAppPreference.getSearchList();
        assertThat("testing stored list is matched with test list", testList,
                is(equalTo(searchList)));
    }

    @Test
    public void testing_saving_list_to_broken_preference() {
        List<String> list = new ArrayList<>();
        boolean success = mMockBrokenAppPreference.setSearchList(list);
        assertThat("testing broken preference returns false", success,
                is(false));
    }


    private AppPreference createMockSharedPreference() {

        when(mockSharedPreference.getString(eq(SEARCH_LIST_KEY), anyString()))
                .thenReturn(testListAsString);

        when(mMockEditor.commit()).thenReturn(true);

        when(mockSharedPreference.edit()).thenReturn(mMockEditor);
        return new AppPreference(mockSharedPreference);
    }

    private AppPreference createBrokenMockSharedPreference() {
        when(mMockBrokenEditor.commit()).thenReturn(false);
        when(mMockBrokenSharedPreferences.edit()).thenReturn(mMockBrokenEditor);
        return new AppPreference(mMockBrokenSharedPreferences);
    }
}
