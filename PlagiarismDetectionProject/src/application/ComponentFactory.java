package application;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ComponentFactory {
	
	/*
	 * 矩形制作工厂
	 * 接受参数：正方形的width，hight，position（x,y）,颜色，不透明度
	 * 返回生成后的结果
	 */
	public Rectangle rectangleFactory(double width, double hight,
			double layoutX, double layoutY, Color color, double opacity){
		Rectangle retangle = new Rectangle();
		retangle.setHeight(hight);
		retangle.setWidth(width);
		retangle.setLayoutX(layoutX);
		retangle.setLayoutY(layoutY);
		retangle.setFill(color);
		retangle.setOpacity(opacity);
		return retangle;
	}
	/*
	 * 生成矩形后需要作出变换效果，因此将相应的矩形作为参数传入，然后改变其值，从而产生简单特效
	 * 接受参数：矩形的引用，改变的颜色，不透明度
	 */
	public void changeRectangle(Rectangle rectangle, Color color, double opacity){
		rectangle.setFill(color);
		rectangle.setOpacity(opacity);
	}

	/*
	 * 可视图片生成工厂
	 * 接受参数：图片的width，hight，position（x,y）
	 */
	public ImageView graphFactory(double width, double hight,
			double layoutX, double layoutY, Image image){
		ImageView iv = new ImageView(image);
		iv.setFitHeight(hight);
		iv.setFitWidth(width);
		iv.setLayoutX(layoutX);
		iv.setLayoutY(layoutY);
		return iv;
	}
	/*
	 * 有时需要更改图片的样式，此时传入更改的图片即可
	 */
	public void changeImageView(ImageView iv, Image image){
		iv.setImage(image);
	}
	/*
	 * 文本框生成工厂
	 */
	public Text textFactory(String content, Font font, Color color,
			double layoutX, double layoutY){
		Text text = new Text(content);
		text.setFont(font);
		text.setFill(color);
		text.setLayoutX(layoutX);
		text.setLayoutY(layoutY);
		text.setTextOrigin(VPos.TOP);
		return text;
	}
	
	/*
	 * 基础按键生成：关闭，缩小
	 * 接受参数：主平台（用于事件处理：关闭），待增加的面板，面板的大小，组件的大小
	 * 默认生成位置为右上角
	 */
	EventFactory eventFactory = new EventFactory();
	public void component(Stage primaryStage, Group root,
			double width, double hight, double widthSize, double hightSize){
		/*
		 * 为了美观，新增缩小按钮
		 */
		Rectangle suoxiaoBackground = this.rectangleFactory(widthSize, hightSize,
				width - 2*widthSize, 0, Style.blue, 0);
		root.getChildren().add(suoxiaoBackground);
		ImageView ivSuoXiao = this.graphFactory(40, 40,
				width - 2*widthSize +30, 30,
				new Image("image/suoxiao1.png"));
		root.getChildren().add(ivSuoXiao);
		/*
		 * 为矩形增加鼠标处理事件
		 */
		eventFactory.setRectangleMouseEnter(suoxiaoBackground, Style.gray, 0.3,
				ivSuoXiao, new Image("image/suoxiao2.png"), null, null);
		eventFactory.setRectangleMouseExit(suoxiaoBackground, Style.gray, 0,
				ivSuoXiao, new Image("image/suoxiao1.png"), null, null);
		/*
		 * 为图片增加鼠标处理事件
		 */
		eventFactory.setIVMouseEnter(suoxiaoBackground, Style.gray, 0.3,
				ivSuoXiao, new Image("image/suoxiao2.png"), null, null);
		eventFactory.setIVMouseExit(suoxiaoBackground, Style.gray, 0,
				ivSuoXiao, new Image("image/suoxiao1.png"), null, null);
		
		/*
		 * 左上角的关闭按钮
		 * 由矩形作为背景，一个图片作为显示图标
		 */
		Rectangle closeBackground = this.rectangleFactory(widthSize, hightSize,
				width - widthSize, 0, Style.blue, 0);
		closeBackground.setArcWidth(0);
		closeBackground.setArcHeight(0);
		
		root.getChildren().add(closeBackground);
		ImageView ivClose = this.graphFactory(40, 40, 
				width - widthSize + 30, 30,
				new Image("image/close1.png"));
		root.getChildren().add(ivClose);
		/*
		 * 为矩形增加鼠标处理事件
		 */
		eventFactory.setRectangleMouseEnter(closeBackground, Style.gray, 0.3,
				ivClose, new Image("image/close2.png"), null, null);
		eventFactory.setRectangleMouseExit(closeBackground, Style.gray, 0,
				ivClose, new Image("image/close1.png"), null, null);
		closeBackground.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			primaryStage.close();
		});
		/*
		 * 为图片增加鼠标处理事件
		 */
		eventFactory.setIVMouseEnter(closeBackground, Style.gray, 0.3,ivClose, 
				new Image("image/close2.png"), null, null);
		eventFactory.setIVMouseExit(closeBackground, Style.gray, 0.3,ivClose, 
				new Image("image/close1.png"), null, null);
		ivClose.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			primaryStage.close();
		});
	}
	
}