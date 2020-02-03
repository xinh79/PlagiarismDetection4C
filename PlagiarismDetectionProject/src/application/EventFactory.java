package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class EventFactory {
	/*
	 * 看下能不能设置统一的响应事件，通过接受引用值，然后为其设置响应事件
	 * 鼠标进入事件：改变矩形的颜色，透明度
	 */
	public void setRectangleMouseEnter(Rectangle rectangle, Color color, 
			double opacity, ImageView iv, Image image
			,Text text, Color textColor){
		rectangle.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			if(null != text){
				text.setFill(textColor);
			}
			if(null != rectangle){
				rectangle.setFill(color);
				rectangle.setOpacity(opacity);
			}
			if(null != iv){
				iv.setImage(image);
			}			
		});
	}
	public void setRectangleMouseExit(Rectangle rectangle, Color color,
			double opacity, ImageView iv, Image image
			,Text text, Color textColor){
		rectangle.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			if(null != text){
				text.setFill(textColor);
			}
			if(null != rectangle){
				rectangle.setFill(color);
				rectangle.setOpacity(opacity);
			}
			if(null != iv){
				iv.setImage(image);
			}
		});
	}
	
	/*
	 * 图片的事件处理方法
	 * 主要就是改变图片的样式
	 */
	public void setIVMouseEnter(Rectangle rectangle, Color color,
			double opacity, ImageView iv, Image image
			,Text text, Color textColor){
		iv.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			if(null != text){
				text.setFill(textColor);
			}
			if(null != rectangle){
				rectangle.setFill(color);
				rectangle.setOpacity(opacity);
			}
			if(null != iv){
				iv.setImage(image);
			}
		});
	}
	public void setIVMouseExit(Rectangle rectangle, Color color,
			double opacity, ImageView iv, Image image
			,Text text, Color textColor){
		iv.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			if(null != text){
				text.setFill(textColor);
			}
			if(null != rectangle){
				rectangle.setFill(color);
				rectangle.setOpacity(opacity);
			}
			if(null != iv){
				iv.setImage(image);
			}
		});
	}
	
	/*
	 * 文本框的鼠标响应事件
	 */
	public void setTextMouseEnter(Rectangle rectangle, Color color,
			double opacity, ImageView iv, Image image
			,Text text, Color textColor){
		text.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			if(null != text){
				text.setFill(textColor);
			}
			if(null != rectangle){
				rectangle.setFill(color);
				rectangle.setOpacity(opacity);
			}
			if(null != iv){
				iv.setImage(image);
			}
		});
	}
	public void setTextMouseExit(Rectangle rectangle, Color color,
			double opacity, ImageView iv, Image image
			,Text text, Color textColor){
		text.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			if(null != text){
				text.setFill(textColor);
			}
			if(null != rectangle){
				rectangle.setFill(color);
				rectangle.setOpacity(opacity);
			}
			if(null != iv){
				iv.setImage(image);
			}
		});
	}
}
