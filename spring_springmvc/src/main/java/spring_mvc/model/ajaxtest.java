package spring_mvc.model;

public class ajaxtest {
	private title title;
	private tooltip tooltip;
	private legend legend;
	private xAxis xAxis;
	private yAxis yAxis;
	private series[] series;
	public title getTitle() {
		return title;
	}
	public void setTitle(title title) {
		this.title = title;
	}
	public tooltip getTooltip() {
		return tooltip;
	}
	public void setTooltip(tooltip tooltip) {
		this.tooltip = tooltip;
	}
	public legend getLegend() {
		return legend;
	}
	public void setLegend(legend legend) {
		this.legend = legend;
	}
	public xAxis getxAxis() {
		return xAxis;
	}
	public void setxAxis(xAxis xAxis) {
		this.xAxis = xAxis;
	}
	public yAxis getyAxis() {
		return yAxis;
	}
	public void setyAxis(yAxis yAxis) {
		this.yAxis = yAxis;
	}
	public series[] getSeries() {
		return series;
	}
	public void setSeries(series[] series) {
		this.series = series;
	}
	public ajaxtest(spring_mvc.model.title title, spring_mvc.model.tooltip tooltip, spring_mvc.model.legend legend,
			spring_mvc.model.xAxis xAxis, spring_mvc.model.yAxis yAxis, spring_mvc.model.series[] series) {
		super();
		this.title = title;
		this.tooltip = tooltip;
		this.legend = legend;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.series = series;
	}
	public ajaxtest() {
	}
	
}
