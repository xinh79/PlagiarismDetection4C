package application;

import java.io.File;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ShowWithGraph {
	String absPath = null;
	PieChart chart = null;
	/*
	 * 新建的工厂
	 */
	ComponentFactory factory = new ComponentFactory();
	EventFactory eventFactory = new EventFactory();
	Dialog dialog = new Dialog();
	SetGraph setGraph = new SetGraph();
	GetData getData = new GetData();
	//SetGraph sg = new SetGraph();
	/*
	 * 用户选择图表显示后调用此函数，此函数可以根据用户选择的文件来显示相应的图形
	 */
	public void graphShow(){
		
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setHeight(800);
		stage.setWidth(800);
		Group root = new Group();
		Scene scene = new Scene(root, 800, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("图表显示器");
		stage.show();
		
		//添加UI接背景
		ImageView ivBackground = factory.graphFactory(
				800, 800, 0, 0, new Image("image/48.jpg"));
		root.getChildren().add(ivBackground);
		ImageView ivLogoBackground = factory.graphFactory(
				600, 600, 150, 150, new Image("image/logo1.png"));
		ivLogoBackground.setOpacity(0.5);
		root.getChildren().add(ivLogoBackground);
		//添加幕布，用于暂时遮蔽饼图
//		Rectangle graphBackground1 = factory.rectangleFactory(
//				700, 700, 100, 100, Style.gray, 0.3);
//		root.getChildren().add(graphBackground1);
//		Rectangle graphBackground2 = factory.rectangleFactory(
//				100, 600, 0, 100, Style.gray, 0.3);
//		root.getChildren().add(graphBackground2);
		//为程序增加logo
		ImageView ivLogo = new ImageView(new Image("image/logo.png"));
		ivLogo.setFitHeight(100);
		ivLogo.setFitWidth(100);
		ivLogo.setLayoutX(0);
		ivLogo.setLayoutY(0);
		root.getChildren().add(ivLogo);
		/*
		 * 增加一个文本框用于显示正在执行的文件名称
		 */
		Text currentFile = factory.textFactory(
				"等待文件输入...", Style.font, Style.gray, 315, 40);
		Font currentFont = new Font("微软雅黑", 26);
		currentFile.setFont(currentFont);
		root.getChildren().add(currentFile);
		//提供用户选择文件的按钮----文件选择器
		Rectangle choiseFile = factory.rectangleFactory(
				100, 100, 200, 0, Style.gray, 0);
		root.getChildren().add(choiseFile);
		ImageView ivChoise = factory.graphFactory(
				80, 60, 215, 22, new Image("image/fileopen1.png"));
		root.getChildren().add(ivChoise);
		Rectangle updataFileRec = factory.rectangleFactory(
				100, 100, 0, 300, Style.gray, 0);
		root.getChildren().add(updataFileRec);
		ImageView ivFileUpdate = factory.graphFactory(
				75, 75, 10, 315, new Image("image/fileupdate1.png"));
		root.getChildren().add(ivFileUpdate);
		
//		Text choiseText = factory.textFactory(
//				"更新文件", Style.font, Style.gray, 115, 35);
//		root.getChildren().add(choiseText);
		/*
		 * 实例化文件选择器
		 */
		FileChooser fc = new FileChooser();
		File file = new File("D:\\");
		/*
		 * 设置文件选择器的标题
		 */
		fc.setTitle("选择文件");
		/*
		 * 设置文件选择器的默认选择路径
		 */
		fc.setInitialDirectory(file);
		
		choiseFile.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			File f = fc.showOpenDialog(stage);
			if (null != f) {
				currentFile.setText(f.getName());
				absPath = f.getAbsolutePath();
//				System.out.println(absPath);
			}
			
		});
		ivChoise.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			File f = fc.showOpenDialog(stage);
			if (null != f) {
				currentFile.setText(f.getName());
				absPath = f.getAbsolutePath();
//				System.out.println(absPath);
			}
		});
		eventFactory.setRectangleMouseEnter(
				choiseFile, Style.gray, 0.3, ivChoise, 
				new Image("image/fileopen2.png"), null, Style.blue);
		eventFactory.setRectangleMouseExit(
				choiseFile, Style.gray, 0, ivChoise, 
				new Image("image/fileopen1.png"), null, Style.gray);
		eventFactory.setIVMouseEnter(
				choiseFile, Style.gray, 0.3, ivChoise, 
				new Image("image/fileopen2.png"), null, Style.blue);
		eventFactory.setIVMouseExit(
				choiseFile, Style.gray, 0, ivChoise, 
				new Image("image/fileopen1.png"), null, Style.gray);
		eventFactory.setRectangleMouseEnter(
				updataFileRec, Style.gray, 0.3, ivFileUpdate, 
				new Image("image/fileupdate2.png"), null, Style.blue);
		eventFactory.setRectangleMouseExit(
				updataFileRec, Style.gray, 0, ivFileUpdate, 
				new Image("image/fileupdate1.png"), null, Style.gray);
		eventFactory.setIVMouseEnter(
				updataFileRec, Style.gray, 0.3, ivFileUpdate, 
				new Image("image/fileupdate2.png"), null, Style.blue);
		eventFactory.setIVMouseExit(
				updataFileRec, Style.gray, 0, ivFileUpdate, 
				new Image("image/fileupdate1.png"), null, Style.gray);
		updataFileRec.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			if(null == absPath){
				dialog.showDialog("没有选择文件！");
			} else {
				String path = absPath;
				path = absPath.substring(0,absPath.length() - 10);
//				System.out.println(path);
				setGraph.updataFile(path);
				dialog.showDialog("文件已经更新！");
			}
		});
		
		//用户选择开始测试的按钮
		Rectangle start = factory.rectangleFactory(
				100, 100, 0, 400, Style.gray, 0);
		root.getChildren().add(start);
		ImageView ivStart = factory.graphFactory(
				60, 60, 20, 420, new Image("image/start1.png"));
		root.getChildren().add(ivStart);
		eventFactory.setRectangleMouseEnter(
				start, Style.gray, 0.3, ivStart, 
				new Image("image/start2.png"), null, null);
		eventFactory.setRectangleMouseExit(
				start, Style.gray, 0, ivStart, 
				new Image("image/start1.png"), null, null);
		
		start.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			if(null == absPath){
				dialog.showDialog("没有选择文件！");
			} else {
				chart = setGraph.dynaGraph(absPath);
				if(null != chart){
					root.getChildren().add(chart);
					root.getChildren().remove(ivLogoBackground);
				}
			}
		});
		eventFactory.setIVMouseEnter(
				start, Style.gray, 0.3, ivStart, 
				new Image("image/start2.png"), null, null);
		eventFactory.setIVMouseExit(
				start, Style.gray, 0, ivStart, 
				new Image("image/start1.png"), null, null);
		ivStart.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			if(null == absPath){
				dialog.showDialog("没有选择文件！");
			} else {
				chart = setGraph.dynaGraph(absPath);
				if(null != chart){
					root.getChildren().add(chart);
					root.getChildren().remove(ivLogoBackground);
				}
			}
		});
		/*
		 * 增加详细信息显示
		 * 当用户选择显示饼图后，响应的详细信息就显示出来了
		 */
		Rectangle showDetail1 = factory.rectangleFactory(
				100, 100, 0, 100, Style.gray, 0);
		root.getChildren().add(showDetail1);
		ImageView ivShowDetail1 = factory.graphFactory(
				80, 80, 10, 110, new Image("image/detail1.png"));
		root.getChildren().add(ivShowDetail1);
		eventFactory.setRectangleMouseEnter(
				showDetail1, Style.gray, 0.3, ivShowDetail1, 
				new Image("image/detail2.png"), null, Style.blue);
		eventFactory.setRectangleMouseExit(
				showDetail1, Style.gray, 0, ivShowDetail1, 
				new Image("image/detail1.png"), null, Style.gray);
		eventFactory.setIVMouseEnter(
				showDetail1, Style.gray, 0.3, ivShowDetail1, 
				new Image("image/detail2.png"), null, Style.blue);
		eventFactory.setIVMouseExit(
				showDetail1, Style.gray, 0, ivShowDetail1, 
				new Image("image/detail1.png"), null, Style.gray);
		showDetail1.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			getData.showDetialInfo(absPath);
		});
		ivShowDetail1.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			getData.showDetialInfo(absPath);
		});
		
		/*
		 * 给用户提供一个输入的范围，显示其值
		 */
		Rectangle showRange = factory.rectangleFactory(
				100, 100, 0, 200, Style.gray, 0);
		root.getChildren().add(showRange);
		ImageView ivShowRange = factory.graphFactory(
				80, 80, 10, 210, new Image("image/range1.png"));
		root.getChildren().add(ivShowRange);
		eventFactory.setRectangleMouseEnter(
				showRange, Style.gray, 0.3, ivShowRange, 
				new Image("image/range2.png"), null, Style.blue);
		eventFactory.setRectangleMouseExit(
				showRange, Style.gray, 0, ivShowRange, 
				new Image("image/range1.png"), null, Style.gray);
		eventFactory.setIVMouseEnter(
				showRange, Style.gray, 0.3, ivShowRange, 
				new Image("image/range2.png"), null, Style.blue);
		eventFactory.setIVMouseExit(
				showRange, Style.gray, 0, ivShowRange, 
				new Image("image/range1.png"), null, Style.gray);
		showRange.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			//getData.showDetialInfo(absPath);
		});
		ivShowRange.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			//getData.showDetialInfo(absPath);
		});
		/*
		 * 为了美观，新增关闭，缩小按钮键
		 */
		factory.component(stage, root, 800, 800, 100, 100);
		//程序退出键
		Rectangle quit = factory.rectangleFactory(
				100, 100, 0, 700, Style.gray, 0);
		root.getChildren().add(quit);
		ImageView ivQuit = factory.graphFactory(
				60, 60, 15, 720, new Image("image/quit3.png"));
		root.getChildren().add(ivQuit);
		eventFactory.setRectangleMouseEnter(
				quit, Style.gray, 0.3, ivQuit, 
				new Image("image/quit4.png"), null, null);
		eventFactory.setRectangleMouseExit(
				quit, Style.gray, 0, ivQuit, 
				new Image("image/quit3.png"), null, null);
		quit.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			stage.close();
		});
		eventFactory.setIVMouseEnter(
				quit, Style.gray, 0.3, ivQuit, 
				new Image("image/quit4.png"), null, null);
		eventFactory.setIVMouseExit(
				quit, Style.gray, 0, ivQuit, 
				new Image("image/quit3.png"), null, null);
		ivQuit.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			stage.close();
		});
	}
}
