/*
 * Copyright 2012 Decebal Suiu
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
package ro.fortsoft.wicket.dashboard.widget.jqplot;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import ro.fortsoft.wicket.dashboard.Dashboard;
import ro.fortsoft.wicket.dashboard.Widget;
import ro.fortsoft.wicket.dashboard.web.DashboardContext;
import ro.fortsoft.wicket.dashboard.web.DashboardContextAware;
import ro.fortsoft.wicket.dashboard.web.DashboardPanel;
import ro.fortsoft.wicket.dashboard.web.WidgetPanel;

/**
 * @author Decebal Suiu
 */
public class JqPlotSettingsPanel extends GenericPanel<JqPlotWidget> implements DashboardContextAware {

	private static final long serialVersionUID = 1L;
	
	private transient DashboardContext dashboardContext;
	private String chartType;
	
	public JqPlotSettingsPanel(String id, IModel<JqPlotWidget> model) {
		super(id, model);
		
		setOutputMarkupPlaceholderTag(true);
		
		Form<Widget> form = new Form<Widget>("form");
		chartType = getModelObject().getSettings().get("chartType");
//		chartType = ChartWidget.BAR_TYPE;
        DropDownChoice<String> choice = new DropDownChoice<String>("chartType", 
        		new PropertyModel<String>(this, "chartType"), JqPlotWidget.TYPES);
        form.add(choice);
        
        form.add(new AjaxSubmitLink("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
            	getModelObject().getSettings().put("chartType", chartType);
				Dashboard dashboard = findParent(DashboardPanel.class).getDashboard();
				dashboardContext.getDashboardPersister().save(dashboard);

            	hideSettingPanel(target);
            	// TODO
				WidgetPanel widgetPanel = findParent(WidgetPanel.class);
				JqPlotWidgetView widgetView = (JqPlotWidgetView) widgetPanel.getWidgetView();
				target.add(widgetView);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
			}

        });
        form.add(new AjaxLink<Void>("cancel") {

            private static final long serialVersionUID = 1L;

			@Override
            public void onClick(AjaxRequestTarget target) {
            	hideSettingPanel(target);
            }
            
        });
        
		add(form);
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	@Override
	public void setDashboardContext(DashboardContext dashboardContext) {
		this.dashboardContext = dashboardContext;
	}
	
	private void hideSettingPanel(AjaxRequestTarget target) {
    	setVisible(false);
    	target.add(this);
	}
	
}
