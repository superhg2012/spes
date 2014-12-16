package org.spes.test;

import org.spes.bean.WindowParam;

public class TestWindowParam {

	public static void main(String[] args) {
		WindowParam wp = new WindowParam();
		wp.setItemId(1);
		wp.setItemName("gaga");
		wp.setItemValue(2.0);
		wp.setWindowId(1);
		wp.setCenterId(1);
		
		WindowParam wp2 = new WindowParam();
		wp2.setItemId(1);
		wp2.setItemName("gaga");
		wp2.setItemValue(2.0);
		wp2.setWindowId(1);
		wp2.setCenterId(1);
		
		System.out.println(wp.equals(wp2));
	}
}
