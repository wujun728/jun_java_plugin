import javax.swing.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;

public class DragLabel extends JLabel implements DropTargetListener{
	
	public DragLabel(String str){
		super(str);  //调用父类构造函数
	}
	
	public void dragEnter(DropTargetDragEvent evt) {
	}
	public void dragOver(DropTargetDragEvent evt) {
	}
	public void dropActionChanged(DropTargetDragEvent evt) {
	}
	public void dragExit(DropTargetEvent evt) {
	}
	public void drop(DropTargetDropEvent evt) {  //拖动操作处理
		try{
			Transferable trans = evt.getTransferable(); //得以Transferable对象
			if (evt.isDataFlavorSupported(DataFlavor.stringFlavor)){ //是否支持拖动
				evt.acceptDrop(evt.getDropAction()); //接受拖动
				String s = (String) trans.getTransferData(DataFlavor.stringFlavor); //得到拖动数据
				setText(s); //设置标签的文本
				evt.dropComplete(true); //结束拖动
			}else{
				evt.rejectDrop(); //拒绝托运

			}
		}catch(Exception err){
			err.printStackTrace(); //输出出错信息
		}
	}

}
