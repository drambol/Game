package base;

import legend.LegendView;
import shuihu.MainView;

public class WindowStore {
	
	public static final ThreadLocal<MainView> mainViewTL = new ThreadLocal<MainView>();
	public static final ThreadLocal<LegendView> legendViewTL = new ThreadLocal<LegendView>();

}
