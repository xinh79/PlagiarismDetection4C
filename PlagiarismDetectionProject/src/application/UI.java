package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class UI extends Application {

	ShowWithGraph swg = new ShowWithGraph();
	/*
	 * 新建的工厂
	 */
	ComponentFactory factory = new ComponentFactory();
	EventFactory eventFactory = new EventFactory();
	SetGraph setGraph = new SetGraph();
	ShowWithDetail swd = new ShowWithDetail();
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setHeight(500);
		primaryStage.setWidth(500);
		Group root = new Group();
		Scene scene = new Scene(root, 500, 500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("代码比较查看器");
		primaryStage.show();
		/*
		 * 整体的背景样式
		 */
		ImageView ivBackground = new ImageView(new Image("image/48.jpg"));
		ivBackground.setFitWidth(500);
		ivBackground.setFitHeight(500);
		ivBackground.setLayoutX(0);
		ivBackground.setLayoutY(0);
		root.getChildren().add(ivBackground);
		//为程序增加logo
		ImageView ivLogo = factory.graphFactory(
				100, 100, 50, 50, (new Image("image/logo.png")));
		root.getChildren().add(ivLogo);
		/*
		 * 增加文字--标题
		 */
		Text title = factory.textFactory(
				"相似度查看器", Style.font, Style.red, 180, 80);
		root.getChildren().add(title);
		Rectangle titleRec = factory.rectangleFactory(500, 100,
				0, 200, Style.blue, 0);
		root.getChildren().add(titleRec);
		/*
		 * 增加三个用户选项
		 * 详细代码比较查看选项
		 * 图表比较查看
		 * 程序退出按钮
		 */
		Rectangle detailBackground = factory.rectangleFactory(500, 100,
				0, 200, Style.blue, 0);
		root.getChildren().add(detailBackground);
		ImageView ivDetail = factory.graphFactory(60, 60, 80, 
				220, new Image("image/bijiao1.png"));
		root.getChildren().add(ivDetail);
		Text detailInfo = factory.textFactory("代码比较详细", Style.font,
				Style.gray, 160, 235);
		root.getChildren().add(detailInfo);
		/*
		 * 为整个矩形增加鼠标响应事件
		 */
		eventFactory.setRectangleMouseEnter(detailBackground, Style.blue, 0.3, ivDetail, 
				new Image("image/bijiao2.png"), detailInfo, Style.blue);
		eventFactory.setRectangleMouseExit(detailBackground, Style.blue, 0, ivDetail, 
				new Image("image/bijiao1.png"), detailInfo, Style.gray);
		/*
		 * 为图片增加鼠标响应事件
		 */
		eventFactory.setIVMouseEnter(detailBackground, Style.blue, 0.3, ivDetail, 
				new Image("image/bijiao2.png"), detailInfo, Style.blue);
		eventFactory.setIVMouseExit(detailBackground, Style.blue, 0, ivDetail, 
				new Image("image/bijiao1.png"), detailInfo, Style.gray);
		/*
		 * 为文本框增加鼠标响应事件
		 */
		eventFactory.setTextMouseEnter(detailBackground, Style.blue, 0.3, ivDetail, 
				new Image("image/bijiao2.png"), detailInfo, Style.blue);
		eventFactory.setTextMouseExit(detailBackground, Style.blue, 0, ivDetail, 
				new Image("image/bijiao1.png"), detailInfo, Style.gray);
		detailBackground.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			swd.showWithDetail1();
		});
		detailInfo.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			swd.showWithDetail1();
		});
		ivDetail.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			swd.showWithDetail1();
		});
		/*
		 * 用户选择：图表查看
		 */
		Rectangle graphShowBackground = factory.rectangleFactory(
				500, 100, 0, 300, Style.blue, 0);
		root.getChildren().add(graphShowBackground);
		ImageView ivGraph = factory.graphFactory(
				60, 60, 80, 320, new Image("image/graph1.png"));
		root.getChildren().add(ivGraph);
		Text graphShow = factory.textFactory(
				"图表查看结果", Style.font, Style.gray, 160, 330);
		root.getChildren().add(graphShow);
		/*
		 * 添加响应事件
		 */
		eventFactory.setRectangleMouseEnter(graphShowBackground, Style.blue, 0.3, ivGraph, 
				new Image("image/graph2.png"), graphShow, Style.blue);
		eventFactory.setRectangleMouseExit(graphShowBackground, Style.blue, 0, ivGraph, 
				new Image("image/graph1.png"), graphShow, Style.gray);
		eventFactory.setIVMouseEnter(graphShowBackground, Style.blue, 0.3, ivGraph, 
				new Image("image/graph2.png"), graphShow, Style.blue);
		eventFactory.setIVMouseExit(graphShowBackground, Style.blue, 0, ivGraph, 
				new Image("image/graph1.png"), graphShow, Style.gray);
		eventFactory.setTextMouseEnter(graphShowBackground, Style.blue, 0.3, ivGraph, 
				new Image("image/graph2.png"), graphShow, Style.blue);
		eventFactory.setTextMouseExit(graphShowBackground, Style.blue, 0, ivGraph, 
				new Image("image/graph1.png"), graphShow, Style.gray);
		graphShowBackground.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			swg.graphShow();
		});
		ivGraph.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			swg.graphShow();
		});
		graphShow.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			swg.graphShow();
		});
		/*
		 * 选项：离开，用户退出程序的按钮
		 */
		Rectangle quitBackground = factory.rectangleFactory(
				500, 100, 0, 400, Style.blue, 0);
		root.getChildren().add(quitBackground);
		Text quit = factory.textFactory(
				"退出程序", Style.font, Style.gray, 190, 430);
		root.getChildren().add(quit);
		ImageView ivQuit = factory.graphFactory(
				60, 60, 75, 420, new Image("image/quit3.png"));
		root.getChildren().add(ivQuit);
		/*
		 * 添加鼠标响应事件
		 */
		eventFactory.setRectangleMouseEnter(quitBackground, Style.blue, 0.6, ivQuit, 
				new Image("image/quit4.png"), quit, Style.red);
		eventFactory.setRectangleMouseExit(quitBackground, Style.red, 0, ivQuit, 
				new Image("image/quit3.png"), quit, Style.gray);
		eventFactory.setIVMouseEnter(quitBackground, Style.blue, 0.6, ivQuit, 
				new Image("image/quit4.png"), quit, Style.red);
		eventFactory.setIVMouseExit(quitBackground, Style.red, 0, ivQuit, 
				new Image("image/quit3.png"), quit, Style.gray);
		eventFactory.setTextMouseEnter(quitBackground, Style.blue, 0.6, ivQuit, 
				new Image("image/quit4.png"), quit, Style.red);
		eventFactory.setTextMouseExit(quitBackground, Style.red, 0, ivQuit, 
				new Image("image/quit3.png"), quit, Style.gray);
		quitBackground.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			primaryStage.close();
		});
		quit.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			primaryStage.close();
		});
		ivQuit.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			primaryStage.close();
		});
		
		/*
		 * 为了美观，新增缩小按钮
		 */
		Rectangle suoxiaoBackground = factory.rectangleFactory(60, 60,
				380, 0, Style.blue, 0);
		root.getChildren().add(suoxiaoBackground);
		ImageView ivSuoXiao = factory.graphFactory(30, 30, 395, 15,
				new Image("image/suoxiao1.png"));
		root.getChildren().add(ivSuoXiao);
		/*
		 * 为矩形增加鼠标处理事件
		 */
		eventFactory.setRectangleMouseEnter(suoxiaoBackground, Style.blue, 0.3,
				ivSuoXiao, new Image("image/suoxiao2.png"), null, null);
		eventFactory.setRectangleMouseExit(suoxiaoBackground, Style.blue, 0,
				ivSuoXiao, new Image("image/suoxiao1.png"), null, null);
		/*
		 * 为图片增加鼠标处理事件
		 */
		eventFactory.setIVMouseEnter(suoxiaoBackground, Style.blue, 0.3,
				ivSuoXiao, new Image("image/suoxiao2.png"), null, null);
		eventFactory.setIVMouseExit(suoxiaoBackground, Style.blue, 0,
				ivSuoXiao, new Image("image/suoxiao1.png"), null, null);
		/*
		 * 左上角的关闭按钮
		 * 由矩形作为背景，一个图片作为显示图标
		 */
		Rectangle closeBackground = factory.rectangleFactory(60, 60,
				440, 0, Style.blue, 0);
		closeBackground.setArcWidth(0);
		closeBackground.setArcHeight(0);
		
		root.getChildren().add(closeBackground);
		ImageView ivClose = factory.graphFactory(30, 30, 455, 15,
				new Image("image/close1.png"));
		root.getChildren().add(ivClose);
		/*
		 * 为矩形增加鼠标处理事件
		 */
		eventFactory.setRectangleMouseEnter(closeBackground, Style.blue, 0.3,
				ivClose, new Image("image/close2.png"), null, null);
		eventFactory.setRectangleMouseExit(closeBackground, Style.blue, 0,
				ivClose, new Image("image/close1.png"), null, null);
		closeBackground.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			primaryStage.close();
		});
		/*
		 * 为图片增加鼠标处理事件
		 */
		eventFactory.setIVMouseEnter(closeBackground, Style.blue, 0.3,ivClose, 
				new Image("image/close2.png"), null, null);
		eventFactory.setIVMouseExit(closeBackground, Style.blue, 0.3,ivClose, 
				new Image("image/close1.png"), null, null);
		ivClose.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			primaryStage.close();
		});
		
	}
	
//	/*
//	 * 用户选择图表显示后调用此函数，此函数可以根据用户选择的文件来显示相应的图形
//	 */
//	public void graphShow(){
//		
//		Stage stage = new Stage();
//		stage.initStyle(StageStyle.UNDECORATED);
//		stage.setHeight(800);
//		stage.setWidth(800);
//		Group root = new Group();
//		Scene scene = new Scene(root, 800, 800);
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//		stage.setResizable(false);
//		stage.setScene(scene);
//		stage.setTitle("图表显示器");
//		stage.show();
//		
//		//添加UI接背景
//		ImageView ivBackground = factory.graphFactory(
//				800, 800, 0, 0, new Image("image/48.jpg"));
//		root.getChildren().add(ivBackground);
//		ImageView ivLogoBackground = factory.graphFactory(
//				600, 600, 150, 150, new Image("image/logo.png"));
//		ivLogoBackground.setOpacity(1);
//		root.getChildren().add(ivLogoBackground);
//		//添加幕布，用于暂时遮蔽饼图
////		Rectangle graphBackground1 = factory.rectangleFactory(
////				700, 700, 100, 100, Style.gray, 0.3);
////		root.getChildren().add(graphBackground1);
////		Rectangle graphBackground2 = factory.rectangleFactory(
////				100, 600, 0, 100, Style.gray, 0.3);
////		root.getChildren().add(graphBackground2);
//		//为程序增加logo
//		ImageView ivLogo = new ImageView(new Image("image/logo.png"));
//		ivLogo.setFitHeight(100);
//		ivLogo.setFitWidth(100);
//		ivLogo.setLayoutX(0);
//		ivLogo.setLayoutY(0);
//		root.getChildren().add(ivLogo);
//		/*
//		 * 增加一个文本框用于显示正在执行的文件名称
//		 */
//		Text currentFile = factory.textFactory(
//				"等待文件输入...", Style.font, Style.gray, 345, 40);
//		Font currentFont = new Font("微软雅黑", 20);
//		currentFile.setFont(currentFont);
//		root.getChildren().add(currentFile);
//		//提供用户选择文件的按钮----文件选择器
//		Rectangle choiseFile = factory.rectangleFactory(
//				100, 100, 240, 0, Style.gray, 0);
//		root.getChildren().add(choiseFile);
//		ImageView ivChoise = factory.graphFactory(
//				80, 60, 255, 22, new Image("image/fileopen1.png"));
//		root.getChildren().add(ivChoise);
//		Rectangle choiseTextRec = factory.rectangleFactory(
//				140, 100, 100, 0, Style.gray, 0);
//		root.getChildren().add(choiseTextRec);
//		Text choiseText = factory.textFactory(
//				"更新文件", Style.font, Style.gray, 115, 35);
//		root.getChildren().add(choiseText);
//		/*
//		 * 实例化文件选择器
//		 */
//		FileChooser fc = new FileChooser();
//		File file = new File("D:\\");
//		/*
//		 * 设置文件选择器的标题
//		 */
//		fc.setTitle("选择文件");
//		/*
//		 * 设置文件选择器的默认选择路径
//		 */
//		fc.setInitialDirectory(file);
//		
//		choiseFile.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
//			File f = fc.showOpenDialog(stage);
//			if (null != f) {
//				currentFile.setText(f.getName());
//				absPath = f.getAbsolutePath();
//			}
//			
//		});
//		ivChoise.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
//			File f = fc.showOpenDialog(stage);
//			if (null != f) {
//				currentFile.setText(f.getName());
//				absPath = f.getAbsolutePath();
//			}
//		});
//		eventFactory.setRectangleMouseEnter(
//				choiseFile, Style.gray, 0.3, ivChoise, 
//				new Image("image/fileopen2.png"), null, Style.blue);
//		eventFactory.setRectangleMouseExit(
//				choiseFile, Style.gray, 0, ivChoise, 
//				new Image("image/fileopen1.png"), null, Style.gray);
//		eventFactory.setIVMouseEnter(
//				choiseFile, Style.gray, 0.3, ivChoise, 
//				new Image("image/fileopen2.png"), null, Style.blue);
//		eventFactory.setIVMouseExit(
//				choiseFile, Style.gray, 0, ivChoise, 
//				new Image("image/fileopen1.png"), null, Style.gray);
//		//用户选择开始测试的按钮
//		Rectangle start = factory.rectangleFactory(
//				100, 100, 500, 0, Style.gray, 0);
//		root.getChildren().add(start);
//		ImageView ivStart = factory.graphFactory(
//				60, 60, 520, 20, new Image("image/start1.png"));
//		root.getChildren().add(ivStart);
//		eventFactory.setRectangleMouseEnter(
//				start, Style.gray, 0.3, ivStart, 
//				new Image("image/start2.png"), null, null);
//		eventFactory.setRectangleMouseExit(
//				start, Style.gray, 0, ivStart, 
//				new Image("image/start1.png"), null, null);
//		
//		start.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
//			chart = setGraph.dynaGraph(absPath);
//			if(null != chart){
//				root.getChildren().add(chart);
//				root.getChildren().remove(ivLogoBackground);
//			}
//			/*
//			 * 使用完毕后，让其指向空，避免产生多个饼图
//			 */
//			absPath = null;
//		});
//		eventFactory.setIVMouseEnter(
//				start, Style.gray, 0.3, ivStart, 
//				new Image("image/start2.png"), null, null);
//		eventFactory.setIVMouseExit(
//				start, Style.gray, 0, ivStart, 
//				new Image("image/start1.png"), null, null);
//		ivStart.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
//			chart = setGraph.dynaGraph(absPath);
//			if(null != chart){
//				root.getChildren().add(chart);
//				root.getChildren().remove(ivLogoBackground);
//			}
//			absPath = null;
//		});
//		/*
//		 * 为了美观，新增关闭，缩小按钮键
//		 */
//		factory.component(stage, root, 800, 800, 100, 100);
//		//程序退出键
//		Rectangle quit = factory.rectangleFactory(
//				100, 100, 0, 700, Style.gray, 0);
//		root.getChildren().add(quit);
//		ImageView ivQuit = factory.graphFactory(
//				60, 60, 15, 720, new Image("image/quit3.png"));
//		root.getChildren().add(ivQuit);
//		eventFactory.setRectangleMouseEnter(
//				quit, Style.gray, 0.3, ivQuit, 
//				new Image("image/quit4.png"), null, null);
//		eventFactory.setRectangleMouseExit(
//				quit, Style.gray, 0, ivQuit, 
//				new Image("image/quit3.png"), null, null);
//		quit.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
//			stage.close();
//		});
//		eventFactory.setIVMouseEnter(
//				quit, Style.gray, 0.3, ivQuit, 
//				new Image("image/quit4.png"), null, null);
//		eventFactory.setIVMouseExit(
//				quit, Style.gray, 0, ivQuit, 
//				new Image("image/quit3.png"), null, null);
//		ivQuit.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
//			stage.close();
//		});
//		
//	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
