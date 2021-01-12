import java.awt.dnd.*;
import java.awt.datatransfer.*;
import javax.swing.*;

public class DragList extends JList implements DragGestureListener, DragSourceListener{
	
	DragSource dragSource=DragSource.getDefaultDragSource(); //拖动源
	public DragList(Object[] data){  //构造函数
		super(data); 
		int action = DnDConstants.ACTION_COPY_OR_MOVE; //拖动类型
		dragSource.createDefaultDragGestureRecognizer(this,action,this); //创建拖动识别
	}
	
	public void dragGestureRecognized(DragGestureEvent dge) {
		try{
			Transferable trans = new StringSelection(this.getSelectedValue().toString()); //得到拖动的Transferable对象
			dge.startDrag(DragSource.DefaultCopyNoDrop,trans,this);  //开始拖动操作
		}catch(Exception err){
			err.printStackTrace();  //输出错误信息
		}
	}
	
	public void dragEnter(DragSourceDragEvent dragSourcede) {  //拖动进入处理
		DragSourceContext dragSourceContext = dragSourcede.getDragSourceContext(); //得到拖动上下文对象
		int action = dragSourcede.getDropAction(); //得到拖动命令
		if ((action&DnDConstants.ACTION_COPY)!=0)  //判断命令类型
			dragSourceContext.setCursor(DragSource.DefaultCopyDrop);  //设置光标类型
		else
			dragSourceContext.setCursor(DragSource.DefaultCopyNoDrop);
	}
	public void dragOver(DragSourceDragEvent dragSourcede) {
	}
	public void dropActionChanged(DragSourceDragEvent dragSourcede) {
	}
	public void dragExit(DragSourceEvent dragSourcee) {
	}
	public void dragDropEnd(DragSourceDropEvent dragSourcede) {
	}
}
