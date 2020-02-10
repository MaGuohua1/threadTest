package com.ma.cap5;

public class C02_Permission {

	// 是否允许查询,二进制第一位,0:false,1:true
	public static final int ARROW_SELECT = 1 << 0;
	// 是否允许更新,二进制第二位,0:false,1:true
	public static final int ARROW_UPDATE = 1 << 1;
	// 是否允许添加,二进制第三位,0:false,1:true
	public static final int ARROW_INSERT = 1 << 2;
	// 是否允许删除,二进制第四位,0:false,1:true
	public static final int ARROW_DELETE = 1 << 3;
	// 储存目前的权限状态
	int flag;

	// 设置用户权限
	public void setPer(int flag) {
		this.flag = flag;
	}

	public int getPer() {
		return flag;
	}

	// 添加权限(one or more)
	public void enable(int per) {
		flag |= per;
	}

	// 删除权限(one or more)
	public void disable(int per) {
		flag ^= per;
	}

	// 判断用户权限
	public boolean isArrow(int per) {
		return (flag & per) == per;
	}

	// 判断用户权限
	public boolean isNotArrow(int per) {
		return (flag & per) == 0;
	}

	public static void main(String[] args) {
		int flag = 15;
		C02_Permission permission = new C02_Permission();
		permission.setPer(flag);
		permission.disable(ARROW_DELETE | ARROW_INSERT | ARROW_UPDATE);
		System.out.println("select is " + permission.isArrow(ARROW_SELECT));
		System.out.println("update is " + permission.isArrow(ARROW_UPDATE));
		System.out.println("insert is " + permission.isArrow(ARROW_INSERT));
		System.out.println("delete is " + permission.isArrow(ARROW_DELETE));
		System.out.println("============");
		permission.enable(ARROW_DELETE | ARROW_INSERT);
		System.out.println("select is " + permission.isArrow(ARROW_SELECT));
		System.out.println("update is " + permission.isArrow(ARROW_UPDATE));
		System.out.println("insert is " + permission.isArrow(ARROW_INSERT));
		System.out.println("delete is " + permission.isArrow(ARROW_DELETE));

	}
}
