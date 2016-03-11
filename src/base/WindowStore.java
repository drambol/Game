package base;

import legend.LegendView;
import legend.MessageView;
import legend.WarehouseView;
import shuihu.MainView;

public class WindowStore {
	
	public static final ThreadLocal<MainView> mainViewTL = new ThreadLocal<MainView>();
	public static final ThreadLocal<LegendView> legendViewTL = new ThreadLocal<LegendView>();
	public static final ThreadLocal<MessageView> messageViewTL = new ThreadLocal<MessageView>();
	public static final ThreadLocal<WarehouseView> warehouseViewTL = new ThreadLocal<WarehouseView>();
	
}
