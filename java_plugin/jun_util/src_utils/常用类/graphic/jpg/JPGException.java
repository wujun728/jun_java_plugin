package book.graphic.jpg;

/**
 * JPG缩放时可能出现的异常
 */
public class JPGException extends Exception {
	public JPGException(String msg){
		super(msg);
	}
	public JPGException(Exception e){
		super(e);
	}
	public JPGException(Throwable t){
		super(t);
	}
	public JPGException(String msg, Throwable t){
		super(msg, t);
	}
}
