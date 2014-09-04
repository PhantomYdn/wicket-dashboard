/*
 * Copyright 2014 Decebal Suiu
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
package ro.fortsoft.wicket.dashboard.web.layout;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;

import ro.fortsoft.wicket.dashboard.WidgetLocation;

import com.google.gson.Gson;

/**
 * @author Decebal Suiu
 */
public abstract class LayoutAjaxBehavior extends AbstractDefaultAjaxBehavior {

	private static final long serialVersionUID = 1L;

	private static final String JSON_DATA = "data";

    public abstract String getSerializeFunctionName();

	public abstract void onLayoutChanged(AjaxRequestTarget target, Map<String, WidgetLocation> widgetLocations);

    @Override
    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
        super.updateAjaxAttributes(attributes);

        StringBuilder buffer = new StringBuilder();
        buffer.append("var data = " + getSerializeFunctionName() + "();");
        buffer.append("return { '" + JSON_DATA + "': data };");

        attributes.getDynamicExtraParameters().add(buffer);
    }

	@Override
	protected void respond(AjaxRequestTarget target) {
		String jsonData = getComponent().getRequest().getRequestParameters().getParameterValue(JSON_DATA).toString();
		Item[] items = getItems(jsonData);
		Map<String, WidgetLocation> locations = new HashMap<String, WidgetLocation>();
		for (Item item : items) {
			WidgetLocation location = new WidgetLocation(item.column, item.row);
            location.setRowSpan(item.rowSpan);
            location.setColumnSpan(item.columnSpan);
			locations.put(item.widget, location);
		}

		onLayoutChanged(target, locations);
	}

	private Item[] getItems(String jsonData) {
		Gson gson = new Gson();
		Item[] items = gson.fromJson(jsonData, Item[].class);
        /*
		System.out.println(items.length);
		for (Item item : items) {
			System.out.println(item);
		}
		*/

		return items;
	}

	static class Item {

        public String widget;
		public int column;
		public int row;
        public int columnSpan;
        public int rowSpan;

		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			buffer.append("Item[");
			buffer.append("widget = ").append(widget);
			buffer.append(", column = ").append(column);
			buffer.append(", row = ").append(row);
            buffer.append(", columnSpan = ").append(columnSpan);
            buffer.append(", rowSpan = ").append(rowSpan);
			buffer.append("]");

			return buffer.toString();
		}

	}

}
