package http;


public class PassWord {
	private int digit;
	
	private String[] str = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	
	private int[] mark;
	
	public PassWord(int n) {
		this.digit = n;
		mark = new int[this.digit];
		for(int i = 0; i < mark.length; i++) {
			mark[i] = 0;
		}
	}
	
	public String next() {
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < mark.length; i++) {
			buffer.append(str[mark[i]]);
		}
		this.offsetNext();
		return buffer.toString();
	}
	
	private void offsetNext() {
		mark[0]++;
		for(int i = 0; i < mark.length; i++) {
			if(mark[i] >= str.length) {
				mark[i + 1]++;
				mark[i] -= str.length;
			}
		}
	}
	
	public boolean hasNext() {
		for(int i = 0; i < mark.length; i++) {
			if(mark[i] < str.length) {
				return true;
			}
		}
		return false;
	}
}
