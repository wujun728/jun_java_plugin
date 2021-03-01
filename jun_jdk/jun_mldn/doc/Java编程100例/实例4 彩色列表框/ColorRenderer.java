import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

//彩色列表框的Renderer,须实现接口ListCellRenderer
public class ColorRenderer extends JLabel implements ListCellRenderer {

	//实现接口中的getListCellRendererComponent方法
   public Component getListCellRendererComponent(JList list, Object obj, int row, boolean sel, boolean hasFocus) {
		if (hasFocus || sel) {   //设置选中时的边界
      	setBorder(new MatteBorder(2, 10, 2, 10, list.getSelectionBackground()));
      }
      else {  //设置未选中时的边界
         setBorder(new MatteBorder(2, 10, 2, 10, list.getBackground()));
      }
      Color c=(Color)obj;  //得到该行的颜色值
      setForeground(c);  //设置颜色
      setText(c.toString());  //设置文本
		return this;
	}
}