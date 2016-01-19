package base;

import test.MainView;

public class WindowStore {
	
	public static final ThreadLocal<MainView> mainViewTL = new ThreadLocal<MainView>();

}
