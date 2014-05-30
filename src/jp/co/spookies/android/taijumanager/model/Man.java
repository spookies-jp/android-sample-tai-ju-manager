package jp.co.spookies.android.taijumanager.model;

public class Man {
	private String name;
	private int age;
	private int height;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	// validationチェック
	public boolean validate() {
		if (name == null || name.length() == 0) {
			return false;
		}
		if (age < 0) {
			return false;
		}
		if (height < 0) {
			return false;
		}
		return true;
	}
}
