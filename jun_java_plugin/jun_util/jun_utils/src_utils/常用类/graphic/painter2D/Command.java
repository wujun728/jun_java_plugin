package book.graphic.painter2D;
/**
 * 画板命令。LINE表示画线，CIRCLE表示画圆，RECTANGLE表示画矩形
 */
public interface Command {
	public static final int LINE = 2; 
	public static final int CIRCLE = 4; 
	public static final int RECTANGLE = 8; 
}
