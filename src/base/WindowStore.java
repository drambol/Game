package base;

import legend.LegendView;
import legend.MessageView;
import legend.ShopView;
import legend.WarehouseView;
import legend.WeaponUpgradeView;
import shuihu.MainView;

public class WindowStore {
	
	public static final ThreadLocal<MainView> mainViewTL = new ThreadLocal<MainView>();
	public static final ThreadLocal<LegendView> legendViewTL = new ThreadLocal<LegendView>();
	public static final ThreadLocal<MessageView> messageViewTL = new ThreadLocal<MessageView>();
	public static final ThreadLocal<WarehouseView> warehouseViewTL = new ThreadLocal<WarehouseView>();
	public static final ThreadLocal<ShopView> shopViewTL = new ThreadLocal<ShopView>();
	public static final ThreadLocal<WeaponUpgradeView> weaponUpgradeViewTL = new ThreadLocal<WeaponUpgradeView>();
	
}
