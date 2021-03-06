package mainClass;

import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class PlotGraph extends ApplicationFrame {

	public PlotGraph(String title, List<Double> rl) {
		super(title);
		final XYSeries series = new XYSeries("Reward Data");
		for (int i = 0; i < rl.size(); i++) {
			series.add(i + 1, rl.get(i));
		}
		final XYSeriesCollection data = new XYSeriesCollection(series);
		final JFreeChart chart = ChartFactory.createXYLineChart("", "Episodes",
				"Moving Average Reward", data, PlotOrientation.VERTICAL, true, true,
				false);

		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);

	}

}
