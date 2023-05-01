package RandomLunch;

public class RLunchBox {

	private int lunch_num;
	private String lunch_sicdang;
	private String lunch_kind;
	private String lunch_menu;
	private int lunch_ref;
	private int lunch_seq;
	private int lunch_lev;

	RLunchBox() {
	}

	public RLunchBox(String menu, String menukind) {
		this.lunch_menu = menu;
		this.lunch_kind = menukind;

	}

	public int getLunch_num() {
		return lunch_num;
	}

	@Override
	public String toString() {
		String re = "";
		if (lunch_lev > 0) {
			re += " ";

		}
		return String.format("%s\t%s\t%s\t%s", lunch_num, lunch_menu, lunch_kind, (re + lunch_sicdang));
	}

	public void setLunch_num(int lunch_num) {
		this.lunch_num = lunch_num;
	}

	public String getLunch_sicdang() {
		return lunch_sicdang;
	}

	public void setLunch_sicdang(String lunch_sicdang) {
		this.lunch_sicdang = lunch_sicdang;
	}

	public String getLunch_kind() {
		return lunch_kind;
	}

	public void setLunch_kind(String lunch_kind) {
		this.lunch_kind = lunch_kind;
	}

	public String getLunch_menu() {
		return lunch_menu;
	}

	public void setLunch_menu(String lunch_menu) {
		this.lunch_menu = lunch_menu;
	}

	public int getLunch_ref() {
		return lunch_ref;
	}

	public void setLunch_ref(int lunch_ref) {
		this.lunch_ref = lunch_ref;
	}

	public int getLunch_seq() {
		return lunch_seq;
	}

	public void setLunch_seq(int lunch_seq) {
		this.lunch_seq = lunch_seq;
	}

	public int getLunch_lev() {
		return lunch_lev;
	}

	public void setLunch_lev(int lunch_lev) {
		this.lunch_lev = lunch_lev;
	}

}
