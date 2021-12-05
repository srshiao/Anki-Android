/*
 *  Copyright (c) 2021 David Allison <davidallisongithub@gmail.com>
 *
 *  This program is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software
 *  Foundation; either version 3 of the License, or (at your option) any later
 *  version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY
 *  WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 *  PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with
 *  this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ichi2.anki;

import com.ichi2.anki.exception.ConfirmModSchemaException;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class PreferencesTest extends RobolectricTest {

    @Test
    public void testDayOffsetExhaustive() {
        Preferences preferences = getInstance();
        for (int i = 0; i < 24; i++) {
            preferences.setDayOffset(i);
            assertThat(Preferences.getDayOffset(getCol()), is(i));
        }
    }

    @Test
    public void testDayOffsetExhaustiveV2() throws ConfirmModSchemaException {
        getCol().changeSchedulerVer(2);
        Preferences preferences = getInstance();
        for (int i = 0; i < 24; i++) {
            preferences.setDayOffset(i);
            assertThat(Preferences.getDayOffset(getCol()), is(i));
        }
    }

    @Test
    public void setDayOffsetSetsConfig() throws ConfirmModSchemaException {
        getCol().changeSchedulerVer(2);
        Preferences preferences = getInstance();

        int offset = Preferences.getDayOffset(getCol());
        assertThat("Default offset should be 4", offset, is(4));

        preferences.setDayOffset(2);

        assertThat("rollover config should be set to new value", getCol().get_config("rollover", 4), is(2));
    }

    /**
     * This test will initialize a subscreen with a new AppearanceSettingsFragment. When the fragment is created,
     * the subscreen will be initialized and then we can test that both preferences and asf have been initialized 
     * correctly to not null.
     * @throws ConfirmModSchemaException
     * @Author sshiao2
     */
    //CS427 Issue link: https://github.com/ankidroid/Anki-Android/issues/8602
    @Test
    public void initSubscreenTest() throws ConfirmModSchemaException {
        getCol().changeSchedulerVer(2);
        Preferences preferences = getInstance();
        
        Preferences.AppearanceSettingsFragment asf = new Preferences.AppearanceSettingsFragment();

        assertNotNull(preferences);
        assertNotNull(asf);

    }

    @NonNull
    protected Preferences getInstance() {
        Preferences preferences = new Preferences();
        preferences.attachBaseContext(getTargetContext());
        return preferences;
    }

}
