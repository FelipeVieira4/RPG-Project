package item;

import entidy.Entidy;

public class MainItemTest {
	public static void main(String[] args) {
		HealthITem ht = new HealthITem(2,2);
		System.out.println(ht instanceof Entidy);
	}
}
