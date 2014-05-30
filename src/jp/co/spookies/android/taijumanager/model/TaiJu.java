package jp.co.spookies.android.taijumanager.model;

public class TaiJu {

	private long created; // 作成日
	private float weight; // 体重
	private String memo; // ひとことメモ

	public void setDate(long time) {
		this.created = time;
	}

	public long getDate() {
		return created;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getWeight() {
		return weight;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMemo() {
		return memo;
	}

	// validationチェック
	public boolean validate() {
		// 負の数ははじく
		if (created < 0) {
			return false;
		}
		if (weight < 0) {
			return false;
		}
		return true;
	}
}
