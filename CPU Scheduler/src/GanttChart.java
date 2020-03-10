import java.awt.Color;
import java.awt.Paint;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.GanttRenderer;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.ui.ApplicationFrame;

public class GanttChart extends ApplicationFrame {

    private static final long serialVersionUID = 1L;
    public static final TaskSeriesCollection model = new TaskSeriesCollection();
    private ArrayList<Process> process;
    public GanttChart(final String title, ArrayList<Process> process) {
        super(title);
        this.process = process;
        final IntervalCategoryDataset dataset = createSampleDataset();
        // create the chart...
        final JFreeChart chart = ChartFactory.createGanttChart(
                title, // chart title
                "Processes", // domain axis label
                "Time", // range axis label
                dataset, // data
                false, // include legend
                true, // 
                false // 
        );
        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
        DateAxis range = (DateAxis) plot.getRangeAxis();
        range.setDateFormatOverride(new SimpleDateFormat("SSS"));
        range.setMaximumDate(new Date(maxTime()));

        // add the chart to a panel...
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 270));
        setContentPane(chartPanel);

        //GanttRenderer personnalisé..
        MyRenderer renderer = new MyRenderer(model, process);
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.WHITE);
    }

    /**
     * @see https://stackoverflow.com/questions/8938690
     */
    
    private int maxTime() {
    	int max = 0;
    	for (int i=0 ; i<process.size() ; i++){
    		int timeFrameSize = process.get(i).timeFrames.size();
    		int time = (int)process.get(i).timeFrames.get(timeFrameSize-1).end;
    		if (time > max)
    			max = time;
    	}
    	return max;
    }
    @SuppressWarnings("serial")
	private static class MyRenderer extends GanttRenderer {

        private static final int PASS = 2; // assumes two passes
        private final List<Color> clut = new ArrayList<>();
        private final TaskSeriesCollection model;
        private int row;
        private int col;
        private int index;
        private ArrayList<Process> process;
        public MyRenderer(TaskSeriesCollection model, ArrayList<Process> process) {
        	this.process = process;
            this.model = model;
        }

        @Override
        public Paint getItemPaint(int row, int col) {

            if (clut.isEmpty() || this.row != row || this.col != col) {
                initClut(row, col);
                this.row = row;
                this.col = col;
                index = 0;
            }
            int clutIndex = index++ / PASS;
            return clut.get(clutIndex);
        }

        private void initClut(int row, int col) {
            clut.clear();

            TaskSeries series = (TaskSeries) model.getRowKeys().get(row);
            @SuppressWarnings("unchecked")
			List<Task> tasks = series.getTasks(); // unchecked 

            int taskCount = tasks.get(col).getSubtaskCount();
            taskCount = Math.max(1, taskCount);

            System.out.println("----> " + taskCount);
            String description;
            System.out.println("R: " + row + " C: " + col);
            for (int i = 0; i < taskCount; i++) {
            	
                description = tasks.get(col).getSubtask(i).getDescription();

                for (int j=0 ; j< process.size() ; j++)
                	if (description.equals(process.get(j).processName))
                		clut.add(process.get(j).color);
            }
        }
    }

    private IntervalCategoryDataset createSampleDataset() {
		final TaskSeries taskSeries = new TaskSeries("");
		if (process != null)
    	for (int i=0 ; i<process.size() ; i++) {
    		Process currentProcess = process.get(i);
    		ArrayList<Process.time> frames = currentProcess.timeFrames;
    		Task initial = new Task(currentProcess.processName + ":", new SimpleTimePeriod(0,1));
    		for (int j=0 ; j<frames.size() ; j++) {
    			Task temp = new Task(currentProcess.processName, new SimpleTimePeriod((int)frames.get(j).start, (int)frames.get(j).end));
    			initial.addSubtask(temp);
    		}
    		taskSeries.add(initial);
    	}
        model.add(taskSeries);
        return model;
    }

}