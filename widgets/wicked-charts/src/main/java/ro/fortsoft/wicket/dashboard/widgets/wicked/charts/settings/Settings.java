/*
 * Copyright 2014 Paul Bors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package ro.fortsoft.wicket.dashboard.widgets.wicked.charts.settings;

import com.googlecode.wickedcharts.highcharts.options.SeriesType;

import java.util.Map;

/**
 * @author <a href="http://www.GitHub.com/PaulBors">Paul Bors</a>
 */
public enum Settings {
    seriesType("seriesType");

    private String name;

    Settings(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static SeriesType toSeriesType(Map<String, String> settings) {
        return SeriesType.valueOf(settings.get(seriesType.name()));
    }

}
