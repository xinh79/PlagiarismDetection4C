package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import Detection.Launch;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ShowWithDetail {
	/*
	 * 创建一个文本比较类的引用
	 */
	CompareForApp cfa = new CompareForApp();
	/*
	 * 为文件选择器设置默认的选择路径
	 */
	File defaultFilePath = new File("D:");
	/*
	 * 持有一个启动方法的引用，便于之后的后续操作
	 */
	Launch launch = new Launch();

	/*
	 * 设置桌面应用的长和宽,其中的像素随系统改变而改变
	 */
	int applicationWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width - 900;
	int applicationHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 200;
	/*
	 * 设置GridPane的各个模块的边距
	 */
	int gridPaneHgap = 20;
	int gridPaneVgap = 20;
	/*
	 * 设置字体
	 */
	Font font = new Font("Arial", 22);
	/*
	 * 创建两个字符串，用于保存选择文件的绝对路径 由于之前按键响应事件为内部类，不得不将两个路径存储在全部变量处
	 */
	String file1Path = "";
	String file2Path = "";
	/*
	 * 新建的工厂
	 */
	ComponentFactory factory = new ComponentFactory();
	EventFactory eventFactory = new EventFactory();
	Dialog dialog = new Dialog();
	SetGraph setGraph = new SetGraph();
	GetData getData = new GetData();
	
	public void showWithDetail1(){
		
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setHeight(800);
		stage.setWidth(900);
		Group root = new Group();
		Scene scene = new Scene(root, 900, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("代码详细显示");
		stage.show();		
		//添加UI接背景
		ImageView ivBackground = factory.graphFactory(
				900, 800, 0, 0, new Image("image/48.jpg"));
		root.getChildren().add(ivBackground);
		ImageView ivLogoBackground = factory.graphFactory(
				600, 600, 180, 150, new Image("image/logo2.png"));
		ivLogoBackground.setOpacity(0.5);
		root.getChildren().add(ivLogoBackground);
		//为程序增加logo
		ImageView ivLogo = new ImageView(new Image("image/logo.png"));
		ivLogo.setFitHeight(100);
		ivLogo.setFitWidth(100);
		ivLogo.setLayoutX(0);
		ivLogo.setLayoutY(0);
		root.getChildren().add(ivLogo);
		//提供用户选择文件的按钮----文件选择器
		Rectangle choiseFile1 = factory.rectangleFactory(
				100, 100, 0, 100, Style.gray, 0);
		root.getChildren().add(choiseFile1);
		ImageView ivChoise1 = factory.graphFactory(
				80, 60, 15, 122, new Image("image/fileopen1.png"));
		root.getChildren().add(ivChoise1);
		/*
		 * 增加一个文本框用于显示正在执行的文件名称
		 */
		Text currentFile1 = factory.textFactory(
				"等待文件\n  输入...", Style.font, Style.gray, 3, 230);
		Font currentFont1 = new Font("微软雅黑", 20);
		currentFile1.setFont(currentFont1);
		root.getChildren().add(currentFile1);
		/*
		 * 实例化文件选择器
		 */
		FileChooser fc1 = new FileChooser();
		File file1 = new File("D:\\");
		/*
		 * 设置文件选择器的标题
		 */
		fc1.setTitle("选择文件");
		/*
		 * 设置文件选择器的默认选择路径
		 */
		fc1.setInitialDirectory(file1);
		String str = "dsjfhdsjkf";
		str.substring(0, 8);
		choiseFile1.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			File f = fc1.showOpenDialog(stage);
			if (null != f) {
				currentFile1.setText(f.getName().substring(0,6)
						+"\n"+f.getName().substring(6,f.getName().length()));
				this.file1Path = f.getAbsolutePath();
			}
			
		});
		ivChoise1.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			File f = fc1.showOpenDialog(stage);
			if (null != f) {
				currentFile1.setText(f.getName().substring(0,6)
						+"\n"+f.getName().substring(6,f.getName().length()));
				this.file1Path = f.getAbsolutePath();
			}
		});
		eventFactory.setRectangleMouseEnter(
				choiseFile1, Style.gray, 0.3, ivChoise1, 
				new Image("image/fileopen2.png"), null, Style.blue);
		eventFactory.setRectangleMouseExit(
				choiseFile1, Style.gray, 0, ivChoise1, 
				new Image("image/fileopen1.png"), null, Style.gray);
		eventFactory.setIVMouseEnter(
				choiseFile1, Style.gray, 0.3, ivChoise1, 
				new Image("image/fileopen2.png"), null, Style.blue);
		eventFactory.setIVMouseExit(
				choiseFile1, Style.gray, 0, ivChoise1, 
				new Image("image/fileopen1.png"), null, Style.gray);
		/*
		 * 第二个文件选择器
		 */
		//提供用户选择文件的按钮----文件选择器
		Rectangle choiseFile2 = factory.rectangleFactory(
				100, 100, 0, 300, Style.gray, 0);
		root.getChildren().add(choiseFile2);
		ImageView ivChoise2 = factory.graphFactory(
				80, 60, 15, 322, new Image("image/fileopen1.png"));
		root.getChildren().add(ivChoise2);
		eventFactory.setRectangleMouseEnter(
				choiseFile2, Style.gray, 0.3, ivChoise2, 
				new Image("image/fileopen2.png"), null, Style.blue);
		eventFactory.setRectangleMouseExit(
				choiseFile2, Style.gray, 0, ivChoise2, 
				new Image("image/fileopen1.png"), null, Style.gray);
		eventFactory.setIVMouseEnter(
				choiseFile2, Style.gray, 0.3, ivChoise2, 
				new Image("image/fileopen2.png"), null, Style.blue);
		eventFactory.setIVMouseExit(
				choiseFile2, Style.gray, 0, ivChoise2, 
				new Image("image/fileopen1.png"), null, Style.gray);
		/*
		 * 增加一个文本框用于显示正在执行的文件名称
		 */
		Text currentFile2 = factory.textFactory(
				"等待文件\n  输入...", Style.font, Style.gray, 3, 430);
		Font currentFont2 = new Font("微软雅黑", 20);
		currentFile2.setFont(currentFont2);
		root.getChildren().add(currentFile2);
		/*
		 * 实例化文件选择器
		 */
		FileChooser fc2 = new FileChooser();
		File file2 = new File("D:\\");
		/*
		 * 设置文件选择器的标题
		 */
		fc2.setTitle("选择文件");
		/*
		 * 设置文件选择器的默认选择路径
		 */
		fc2.setInitialDirectory(file2);
		/*
		 * 设置响应事件，当按下文件选择button时，会弹出选择页面供用户选择 当用户选择相应的文件后，会获得文件的绝对路径，显示文件名称
		 */
		choiseFile2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			File f = fc2.showOpenDialog(stage);
			if (null != f) {
				currentFile2.setText(f.getName().substring(0,6)
						+"\n"+f.getName().substring(6,f.getName().length()));
				this.file2Path = f.getAbsolutePath();
			}	
		});
		ivChoise2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			File f = fc1.showOpenDialog(stage);
			if (null != f) {
				currentFile2.setText(f.getName().substring(0,6)
						+"\n"+f.getName().substring(6,f.getName().length()));
				this.file2Path = f.getAbsolutePath();
			}
		});
		
		/*
		 * 为了显示两份代码的内容，此处增加两个sp
		 * 设置滚动条，代码太多，一个页面存不下
		 */
		ScrollPane sp1 = new ScrollPane();
		ScrollPane sp2 = new ScrollPane();
		/*
		 * 设置滚动条其只可以上下移动
		 */
		sp1.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp1.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp1.setMinSize(400, 800);
		sp1.setMaxSize(400, 800);
		sp1.setLayoutX(100);
		sp1.setLayoutY(0);
		sp1.setOpacity(0.7);
		sp2.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp2.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp2.setMinSize(400, 800);
		sp2.setMaxSize(400, 800);
		sp2.setLayoutX(500);
		sp2.setLayoutY(0);
		sp2.setOpacity(0.7);
		root.getChildren().add(sp1);
		root.getChildren().add(sp2);
		/*
		 * 设置两个滚动条同时滚动
		 * 为滚动条添加监听事件，如果一个滚动，则同时滚动，方便查看
		 */
		sp1.vvalueProperty().addListener((ObservableValue<? extends Number> ov, 
			    Number old_val, Number new_val) -> {
			    	/*
			    	 * 滚动sp1面板时，同时将值赋值给面板2，实现同步滚动
			    	 */
			    	sp2.setHvalue(sp1.getHvalue());
			    	sp2.setVvalue(sp1.getVvalue());
			});
		sp2.vvalueProperty().addListener((ObservableValue<? extends Number> ov, 
			    Number old_val, Number new_val) -> {
			    	sp1.setHvalue(sp2.getHvalue());
			    	sp1.setVvalue(sp2.getVvalue());
			});
		
		//用户选择开始测试的按钮
		Rectangle start = factory.rectangleFactory(
				100, 100, 0, 500, Style.gray, 0);
		root.getChildren().add(start);
		ImageView ivStart = factory.graphFactory(
				60, 60, 20, 520, new Image("image/start1.png"));
		root.getChildren().add(ivStart);
		eventFactory.setRectangleMouseEnter(
				start, Style.gray, 0.3, ivStart, 
				new Image("image/start2.png"), null, null);
		eventFactory.setRectangleMouseExit(
				start, Style.gray, 0, ivStart, 
				new Image("image/start1.png"), null, null);
		
		start.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			/*
			 * 交换值的初始化
			 */
			this.exchangeInt = 0;
			this.exchangeBoolean = true;
			/*
			 * 再次按下的时候，没有变化，尝试清空一些Map
			 */
			this.cfa.programeInfo1.clear();
			this.cfa.programeInfo2.clear();
			/*
			 * 按下按钮时，将两个文件的绝对路径传入进去，获取处理过后的源程序信息 即初始化之前的Map变量
			 */
//			getAndSetProgrameInfo(this.file1Path, this.file2Path);
			if(
				getAndSetProgrameInfo(this.file1Path, this.file2Path).equals("file1")
					){
				dialog.showDialog("文件-1还未选择！");
			} else if(
				getAndSetProgrameInfo(this.file1Path, this.file2Path).equals("file2")){
				dialog.showDialog("文件-2还未选择！");
			} else {
				this.file1Path = "";
				this.file2Path = "";
				ivLogoBackground.setImage(new Image("image/logo.png"));
				/*
				 * 初始化变量之后需要调用面板，将面板作为参数传入进去 再对面板进行相应的修改
				 */
				compareString(sp1, sp2);
			}
		});
		eventFactory.setIVMouseEnter(
				start, Style.gray, 0.3, ivStart, 
				new Image("image/start2.png"), null, null);
		eventFactory.setIVMouseExit(
				start, Style.gray, 0, ivStart, 
				new Image("image/start1.png"), null, null);
		ivStart.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			ivLogoBackground.setImage(new Image("image/logo4.png"));
			/*
			 * 交换值的初始化
			 */
			this.exchangeInt = 0;
			this.exchangeBoolean = true;
			/*
			 * 再次按下的时候，没有变化，尝试清空一些Map
			 */
			this.cfa.programeInfo1.clear();
			this.cfa.programeInfo2.clear();
			/*
			 * 按下按钮时，将两个文件的绝对路径传入进去，获取处理过后的源程序信息 即初始化之前的Map变量
			 */
//			getAndSetProgrameInfo(this.file1Path, this.file2Path);
			if(
					getAndSetProgrameInfo(this.file1Path, this.file2Path).equals("file1")
						){
					dialog.showDialog("文件-1还未选择！");
				} else if(
					getAndSetProgrameInfo(this.file1Path, this.file2Path).equals("file2")){
					dialog.showDialog("文件-2还未选择！");
				} else {
					this.file1Path = "";
					this.file2Path = "";
					ivLogoBackground.setImage(new Image("image/logo.png"));
					/*
					 * 初始化变量之后需要调用面板，将面板作为参数传入进去 再对面板进行相应的修改
					 */
					compareString(sp1, sp2);
				}
		});
		/*
		 * 增加详细信息显示
		 */
		Rectangle showDetail1 = factory.rectangleFactory(
				100, 100, 0, 600, Style.gray, 0);
		root.getChildren().add(showDetail1);
		ImageView ivShowDetail1 = factory.graphFactory(
				80, 80, 10, 610, new Image("image/detail1.png"));
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
			//getData.showDetialInfo(absPath);
		});
		ivShowDetail1.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			//getData.showDetialInfo(absPath);
		});
		
		/*
		 * 为了美观，新增关闭，缩小按钮键
		 */
		//factory.component(stage, root, 900, 800, 100, 100);
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
	/*
	 * 当两个文件选择输入完成后，则读取这两个文件的内容，并且获得处理后的源代码 分别存入这个类中 接受参数：两个文件的绝对路径
	 */
	public String getAndSetProgrameInfo(String fileAbPath1, String fileAbPath2) {
		this.launch.setDefaultPath("");
		// 判断传入的参数，即路径是否为空，如果为空则表示没有输入值，则
		if (!fileAbPath1.equals("")) {
			this.launch.readFile(fileAbPath1);
			this.cfa.programeInfo1 = this.launch.getProgrameInfo();
		} else {
//			System.out.println("file1 is empty!");
			return "file1";
		}
		if (!fileAbPath2.equals("")) {
			this.launch.readFile(fileAbPath2);
			this.cfa.programeInfo2 = this.launch.getProgrameInfo();
		} else {
//			System.out.println("file2 is empty!");
			return "file2";
		}
		return "";
	}
	
	/*
	 * 根据对UC的仔细观察与增添删除比较，发现如下方法：
	 * 		可以在两个文本中一次取出三行--此时是已近处理过的三行代码，可以抛开空行等影响
	 * 			如果要谈影响的话，那就是大括号了，怕有些同学改变代码书写格式，将大括号单独成行
	 * 		然后对取出的三行代码一对多的比较，即查看是否在其中另外的三行代码中含有
	 * 		同时为了避免层级的混乱，我规定字符串只能从对应层级往下比较，直到找到相同的字符串位置
	 * 		如果没有找到，并且其对应层级的字符串也没有找到相等的字符串，则两个同时置位different
	 * 		如果同级的两个字符串之间有且仅有一个字符串找到相同字符串，则另一边的字符串对应空白标签
	 * 		如果相等则两边的字符串就置位相等标签
	 * 实现步骤：
	 * 	1.从中Map中提取出三行代码
	 * 	2.将三行代码处理后传入比较函数内
	 * 		处理方法：可以以字符串数组的形式进行添加，然后传入一个字符串二维数组
	 * 		接收二维字符串数组的方法进过一系列的判断处理函数后返回一个一维数组
	 * 		（起初考虑返回一个多一阶的二维数组，但是考虑到操作性，还是返回一个一维数组）
	 * 		其值含义：0,代表两个相等，1代表两个不相等，2代表左边填入空行，3代表右边填入空行
	 * 		
	 * 		修正，经过实践与考虑，还是返回一个修改过的二维字符串数组
	 * 	3.根据比较函数返回的结果进行盒子内部组件的添加操作
	 * 		即根据二维字符串数组的下标找到相应的一维数组的下标的值，然后根据相应数值
	 * 		制造出不同的标签，最后添加入滚动面板内。
	 */
	/*
	 * getStringNum的值为数组的字符串数组的维度
	 */
	final static int getStringNum = 60;
	public void compareString(ScrollPane sp1, ScrollPane sp2){
		/*
		 * 现在将利用label代替textarea 多行文本显示的大小难以设置，倒不如以\n回车符来代替
		 */
		VBox vboxBig1 = new VBox();
		VBox vboxBig2 = new VBox();
		/*
		 * 设置VBox内部组件的上下间距
		 */
		vboxBig1.setSpacing(0);
		vboxBig2.setSpacing(0);
		
		/*
		 * 正式的比较代码
		 * 首先提取出三行代码
		 * 
		 * 申明两个游标用于遍历Map
		 */
		Iterator<Map.Entry<Integer, String>> it1 = this.cfa.programeInfo1.entrySet().iterator();
		Iterator<Map.Entry<Integer, String>> it2 = this.cfa.programeInfo2.entrySet().iterator();
		/*
		 * 先从中取出两个的值进行比较
		 */
		Map.Entry<Integer, String> entry1 = null;
		Map.Entry<Integer, String> entry2 = null;

		while(it1.hasNext() && it2.hasNext()){
			/*
			 * 尝试利用ArrayList来代替二维数组
			 */
			List<Integer> numInfo1 = new ArrayList<Integer>();
			List<Integer> numInfo2 = new ArrayList<Integer>();
			List<String> proInfo1 = new ArrayList<String>();
			List<String> proInfo2 = new ArrayList<String>();
			/*
			 * 为了创建标签，还需要行号
			 */
			//Integer[][] numInfo = new Integer[DesktopApplication.getStringNum][2];
			/*
			 * 先从中取出两个的值进行比较
			 * 同时创建二维数组用于存放字符串
			 */
			//String[][] proInfo = new String[DesktopApplication.getStringNum][2];
			/*
			 * 从中取值n次，每次取值完毕后都要重新判断是否有值
			 */
			for(int i = 0; i < ShowWithDetail.getStringNum; i++){
				/*
				 * 每次取值之前需要判断有没有值
				 */
				if(it1.hasNext() && it2.hasNext()){
					entry1 = it1.next();
					entry2 = it2.next();
				/*
				 * 如果没有值，则直接退出
				 */
				} else {
					break;
				}
				/*
				 * 传入值的之后，将空白去掉（其实可以不要，因为之前已近处理过）
				 */
				proInfo1.add(entry1.getValue().replace(" ", ""));
				proInfo2.add(entry2.getValue().replace(" ", ""));
				/*
				 * 新增的下标保存数组，用于标签的生成
				 */
				numInfo1.add(entry1.getKey());
				numInfo2.add(entry2.getKey());
			}
//			for(int j = 0; j < proInfo1.size(); j++){
//				System.out.print(numInfo1.get(j) + " : " + proInfo1.get(j));
//				System.out.println("          "
//						+ "           " + numInfo2.get(j) + " : " + proInfo2.get(j));
//			}
			/*
			 * 垂直盒子，用于装左右列的盒子 
			 */
			VBox[] vbox = createHBox(
					this.cfa.compareStringWithList(proInfo1, proInfo2), proInfo1, proInfo2,
					numInfo1, numInfo2);
			vboxBig1.getChildren().add(vbox[0]);
			vboxBig2.getChildren().add(vbox[1]);
		}
		/*
		 * 考虑代码长度不一，剩下的代码直接标志位2-1或是1-2
		 */
		while(it1.hasNext()) {
			entry1 = it1.next();
			vboxBig1.getChildren().add(
					differentHBoxFactory(entry1.getKey().toString(), entry1.getValue())
				);
			vboxBig2.getChildren().add(
					emptyHBoxFactory()
				);
		}
		while(it2.hasNext()){
			entry2 = it2.next();
			vboxBig2.getChildren().add(
					differentHBoxFactory(entry2.getKey().toString(), entry2.getValue())
				);
			vboxBig1.getChildren().add(
					emptyHBoxFactory()
				);
		}
		/*
		 * 将其加入到滚动面板用于显示
		 */
		sp1.setContent(vboxBig1);
		sp2.setContent(vboxBig2);
		/*
		 * 取值完成后，可以形成一个二维字符串数组
		 */
		//HBox[] hbox = createHBox(this.cfa.compareString(proInfo));
		/*
		 * 将两个盒子加入到滚动条处
		 */
//		sp1.setContent(hbox[0]);
//		sp2.setContent(hbox[1]);
	}
	
	/*
	 * 根据返回的结果，生成不同的标签存入盒子之中
	 */
	public VBox[] createHBox(List<Integer> list,
			List<String> proInfo1, List<String> proInfo2
			,List<Integer> numInfo1, List<Integer> numInfo2){
		/*
		 * 现在将利用label代替textarea 多行文本显示的大小难以设置，倒不如以\n回车符来代替
		 */
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox();
		/*
		 * 设置VBox内部组件的上下间距
		 */
		vbox1.setSpacing(10);
		vbox2.setSpacing(10);
		/*
		 * 将数组中的值按顺序取出，并且生成相应的标签，生成规则如下：
		 * 		0：生成带颜色的标签
		 * 		1：生成空白标签
		 * 		2：生成不带颜色的标签
		 */
//		for(int i = 0; i < list.size(); i++){
//			System.out.print(list.get(i) + " ");
//		}
//		System.out.println("\naccording to this number create label!");
		/*
		 * 两个下标值分别表示两个列取值到什么位置了
		 */
		int index1 = 0;
		int index2 = 0;
		/*
		 * 此处循环递增2，并且从0开始，表示值增加或是改变第一列的值
		 */
		for(int i = 0; i < list.size(); i+=2){
			if(null == list.get(i)){
				continue;
			}
			switch(list.get(i)){
			case 0:
				/*
				 * 值为零，表示为需要添加一个有色标签
				 */
//				vbox1.getChildren().add(
//					sameHBoxFactory(numInfo[index1][0].toString(), proInfo[index1][0])
//				);
				vbox1.getChildren().add(
						sameHBoxFactory(numInfo1.get(index1).toString(), proInfo1.get(index1))		
				);
				/*
				 * index++，说明已近从中取值过，指针应该移动
				 */
				index1++;
				break;
			case 1:
				//System.out.println("index1 = " + index1 + " i = " + i);
				/*
				 * 值为1，表示需要添加一个空白标签
				 */
//				vbox1.getChildren().add(
//					emptyHBoxFactory()
//				);
				vbox1.getChildren().add(emptyHBoxFactory());
				break;
			case 2:
				//System.out.println("index1 = " + index1 + " i = " + i);
				/*
				 * 值为2，表示需要填入一个不同的标签，即两个值不相等
				 */
//				vbox1.getChildren().add(
//					differentHBoxFactory(numInfo[index1][0].toString(), proInfo[index1][0])
//				);
				vbox1.getChildren().add(
						differentHBoxFactory(numInfo1.get(index1).toString(), proInfo1.get(index1))
					);
				index1++;
				break;
			default:
				System.out.println("System Account Error!"+list.get(i));
				break;
				//System.exit(0);
			}
		}
		/*
		 * 此处的循环从1开始，每次递增2，表示是操作第二列
		 */
		for(int i = 1; i < list.size(); i+=2){
			switch(list.get(i)){
			case 0:
				//System.out.println("index2 = " + index2 + " i = " + i);
				/*
				 * 值为零，表示为需要添加一个有色标签
				 */
//				vbox2.getChildren().add(
//					sameHBoxFactory(numInfo[index2][1].toString(), proInfo[index2][1])
//				);
				vbox2.getChildren().add(
					sameHBoxFactory(numInfo2.get(index2).toString(), proInfo2.get(index2))
				);
				/*
				 * index++，说明已近从中取值过，指针应该移动
				 */
				index2++;
				break;
			case 1:
				//System.out.println("index2 = " + index2 + " i = " + i);
				/*
				 * 值为1，表示需要添加一个空白标签
				 */
				vbox2.getChildren().add(
					emptyHBoxFactory()
				);
				break;
			case 2:
				//System.out.println("index2 = " + index2 + " i = " + i);
				/*
				 * 值为2，表示需要填入一个不同的标签，即两个值不相等
				 */
//				vbox2.getChildren().add(
//					differentHBoxFactory(numInfo[index2][1].toString(), proInfo[index2][1])
//				);
				vbox2.getChildren().add(
						differentHBoxFactory(numInfo2.get(index2).toString(), proInfo2.get(index2))
					);
				index2++;
				break;
			default:
				System.out.println("System Account Error!"+list.get(i));
				break;
				//System.exit(0);
			}
		}
		VBox[] vbox = new VBox[2];
		vbox[0] = vbox1;
		vbox[1] = vbox2;
		return vbox;
	}
	
	
	/*
	 * 尝试一种新的方法，也是最简单的方法，直接将两个标签同步移动
	 * 在纷繁复杂的表象中，往往最简单的那个才是真理：
	 * 		对于一般的抄袭而言，改动的可能性一般只存在于增加，则不存在于删除
	 * 现在的简单方式为同步移动，由于是经过处理的代码，所以比较的值不存在空白行。
	 */
	public void compareString1(ScrollPane sp1, ScrollPane sp2) {
		
		/*
		 * 现在将利用label代替textarea 多行文本显示的大小难以设置，倒不如以\n回车符来代替
		 */
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox();
		/*
		 * 设置VBox内部组件的上下间距
		 */
		vbox1.setSpacing(10);
		vbox2.setSpacing(10);
		/*
		 * 申明两个游标用于遍历Map
		 */
		Iterator<Map.Entry<Integer, String>> it1 = this.cfa.programeInfo1.entrySet().iterator();
		Iterator<Map.Entry<Integer, String>> it2 = this.cfa.programeInfo2.entrySet().iterator();
		/*
		 * 先从中取出两个的值进行比较
		 */
		Map.Entry<Integer, String> entry1 = null;
		Map.Entry<Integer, String> entry2 = null;
		/*
		 * 先从中取出两个的值进行比较
		 */
		while(it1.hasNext() && it2.hasNext()){
			entry1 = it1.next();
			entry2 = it2.next();
			/*
			 * 如果返回值为0，这说明两个字符串相等
			 * 标签染色后就可以将他们加入到盒子中去
			 */
			if(0 == this.cfa.compareEqual(entry1.getValue(), entry2.getValue())){
				vbox1.getChildren().add(sameHBoxFactory(entry1.getKey().toString(), entry1.getValue()));
				vbox2.getChildren().add(sameHBoxFactory(entry2.getKey().toString(), entry2.getValue()));
			/*
			 * 如果两个字符串不相等
			 * 标签无需染色，直接加入盒子中
			 */
			} else {
				vbox1.getChildren().add(differentHBoxFactory(entry1.getKey().toString(), entry1.getValue()));
				vbox2.getChildren().add(differentHBoxFactory(entry2.getKey().toString(), entry2.getValue()));
			}
		}
		/*
		 * 将两个盒子加入到滚动条处
		 */
		sp1.setContent(vbox1);
		sp2.setContent(vbox2);
		
	}
	
	/*
	 * 利用标签显示在面板上 根据programe里面的值进行标签的创建，然后填入面板中
	 * 同时会调用字符串比较函数，用于判断两个标签是否需要改变颜色
	 * 在外部申明一个变量用于判断是第几次两边的变量-字符串，相等
	 * 每两次就交换移动，直到出现两次完全匹配
	 */
	/*
	 * 当且仅当匹配的结果均符合equals时才会加1
	 * 同时Boolean的值会根据int的取模值的改变而改变
	 * true代表左边，false代表右边 
	 */
	int exchangeInt = 0;
	boolean exchangeBoolean = true;
	public void compareString0(ScrollPane sp1, ScrollPane sp2) {
		/*
		 * 现在将利用label代替textarea 多行文本显示的大小难以设置，倒不如以\n回车符来代替
		 */
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox();
		/*
		 * 设置VBox内部组件的上下间距
		 */
		vbox1.setSpacing(10);
		vbox2.setSpacing(10);
		/*
		 * 申明两个游标用于遍历Map
		 */
		Iterator<Map.Entry<Integer, String>> it1 = this.cfa.programeInfo1.entrySet().iterator();
		Iterator<Map.Entry<Integer, String>> it2 = this.cfa.programeInfo2.entrySet().iterator();
		/*
		 * 先从中取出两个的值进行比较
		 */
		Map.Entry<Integer, String> entry1 = null;
		Map.Entry<Integer, String> entry2 = null;
		if(it1.hasNext() && it2.hasNext()){
			entry1 = it1.next();
			entry2 = it2.next();
			/*
			 * 获取两个值后，然后调用比较方法进行比较
			 */
			
			int result = this.cfa.compareEqual(entry1.getValue(), entry2.getValue());
			/*
			 * 返回值为0，说明两个字符串相等
			 * 则需要将标志位自增1,同时需要两边同时设置带颜色的标签
			 * 同时设置移动位，表明下次比较，两边的文档都需要移动
			 */
			if(0 == result){
				this.exchangeInt++;
				vbox1.getChildren().add(sameHBoxFactory(entry1.getKey().toString(), entry1.getValue()));
				vbox2.getChildren().add(sameHBoxFactory(entry2.getKey().toString(), entry2.getValue()));
				this.exchangeBoolean = true;
			/*
			 * 如果不相等，则默认是从左边，即一号位置开始进行查询的
			 * 则一号位置增加一个普通的标签，二号位置增加一个空白标签，以示不同
			 * 同时设置标志位，表明下次移动比较时，仅有一边的文档需要移动，而另一边不动
			 */
			} else {
				this.exchangeInt++;
				vbox1.getChildren().add(differentHBoxFactory(entry1.getKey().toString(), entry1.getValue()));
				vbox2.getChildren().add(emptyHBoxFactory());
				this.exchangeBoolean = false;
			}
		}
		/*
		 * 根据键值来来判断是否需要将这个标签染红
		 * 两个游标同时移动，同时创建label
		 */
		while (it1.hasNext() && it2.hasNext()) {
			/*
			 * 判断exchange的值，如果小于三，则表明需要移动的仍旧是左边的文档
			 */
			if(3 > this.exchangeInt){
				/*
				 * 继续判断，上一次判断的时候，是否出现过完全匹配即，这次是否需要移动
				 */
				if(this.exchangeBoolean){
					/*
					 * 如果这个条件为真，则表示此次比较需要两边同时移动
					 */
					entry1 = it1.next();
					entry2 = it2.next();
					/*
					 * 获取两个值后，然后调用比较方法进行比较
					 */
					int result = this.cfa.compareEqual(entry1.getValue(), entry2.getValue());
					/*
					 * 返回值为0，说明两个字符串相等
					 * 则需要将标志位自增1,同时需要两边同时设置带颜色的标签
					 * 同时设置移动位，表明下次比较，两边的文档都需要移动
					 */
					if(0 == result){
						this.exchangeInt++;
						vbox1.getChildren().add(sameHBoxFactory(entry1.getKey().toString(), entry1.getValue()));
						vbox2.getChildren().add(sameHBoxFactory(entry2.getKey().toString(), entry2.getValue()));
						this.exchangeBoolean = true;
					/*
					 * 如果不相等，则默认是从左边，即一号位置开始进行查询的
					 * 则一号位置增加一个普通的标签，二号位置增加一个空白标签，以示不同
					 * 同时设置标志位，表明下次移动比较时，仅有一边的文档需要移动，而另一边不动
					 */
					} else {
						vbox1.getChildren().add(differentHBoxFactory(entry1.getKey().toString(), entry1.getValue()));
						vbox2.getChildren().add(emptyHBoxFactory());
						this.exchangeBoolean = false;
						this.exchangeInt++;
					}
				/*
				 * 标志位为false则表明不需要同步移动，仍然只需要移动一边
				 * 此时循环的次数小于三次，单边移动的仍是左边
				 */
				} else {
					entry1 = it1.next();
					int result = this.cfa.compareEqual(entry1.getValue(), entry2.getValue());
					if(0 == result){
						this.exchangeInt++;
						vbox1.getChildren().add(sameHBoxFactory(entry1.getKey().toString(), entry1.getValue()));
						vbox2.getChildren().add(sameHBoxFactory(entry2.getKey().toString(), entry2.getValue()));
						this.exchangeBoolean = true;
					} else {
						vbox1.getChildren().add(differentHBoxFactory(entry1.getKey().toString(), entry1.getValue()));
						vbox2.getChildren().add(emptyHBoxFactory());
						this.exchangeBoolean = false;
						this.exchangeInt++;
					}
					//entry1 = it1.next();
				}
			/*
			 * 判断exchange的值，如果大于三，则表明需要移动的是右边的文档
			 */
			} else {
				/*
				 * 首先判断标志位是否为6，如果达到了6，则需要将其置为0
				 */
				if(6 == this.exchangeInt){
					this.exchangeInt = 0;
				}
				/*
				 * 继续判断，上一次判断的时候，是否出现过完全匹配即，这次是否需要移动
				 */
				if(this.exchangeBoolean){
					/*
					 * 如果这个条件为真，则表示此次比较需要两边同时移动
					 */
					entry1 = it1.next();
					entry2 = it2.next();
					/*
					 * 获取两个值后，然后调用比较方法进行比较
					 */
					int result = this.cfa.compareEqual(entry1.getValue(), entry2.getValue());
					/*
					 * 返回值为0，说明两个字符串相等
					 * 则需要将标志位自增1,同时需要两边同时设置带颜色的标签
					 * 同时设置移动位，表明下次比较，两边的文档都需要移动
					 */
					if(0 == result){
						this.exchangeInt++;
						vbox1.getChildren().add(sameHBoxFactory(entry1.getKey().toString(), entry1.getValue()));
						vbox2.getChildren().add(sameHBoxFactory(entry2.getKey().toString(), entry2.getValue()));
						this.exchangeBoolean = true;
					/*
					 * 如果不相等，则默认是从左边，即一号位置开始进行查询的
					 * 则一号位置增加一个普通的标签，二号位置增加一个空白标签，以示不同
					 * 同时设置标志位，表明下次移动比较时，仅有一边的文档需要移动，而另一边不动
					 */
					} else {
						vbox2.getChildren().add(differentHBoxFactory(entry2.getKey().toString(), entry2.getValue()));
						vbox1.getChildren().add(emptyHBoxFactory());
						this.exchangeBoolean = false;
						this.exchangeInt++;
					}
				/*
				 * 标志位为false则表明不需要同步移动，仍然只需要移动一边
				 * 此时循环的次数大于三次，单边移动的右边
				 */
				} else {
					entry2 = it2.next();
					int result = this.cfa.compareEqual(entry1.getValue(), entry2.getValue());
					if(0 == result){
						this.exchangeInt++;
						vbox1.getChildren().add(sameHBoxFactory(entry1.getKey().toString(), entry1.getValue()));
						vbox2.getChildren().add(sameHBoxFactory(entry2.getKey().toString(), entry2.getValue()));
						this.exchangeBoolean = true;
					} else {
						vbox2.getChildren().add(differentHBoxFactory(entry2.getKey().toString(), entry2.getValue()));
						vbox1.getChildren().add(emptyHBoxFactory());
						this.exchangeBoolean = false;
						this.exchangeInt++;
					}
					//entry2 = it2.next();
				}
			}
		}
		/*
		 * 无论是谁被剩下，则都是不同的部分，因此只需调用不同的盒子工程即可
		 */
		while (it1.hasNext()) {
			Map.Entry<Integer, String> entry = it1.next();
			vbox1.getChildren().add(differentHBoxFactory(entry.getKey().toString(), entry.getValue()));
			vbox2.getChildren().add(emptyHBoxFactory());
		}
		while (it2.hasNext()) {
			Map.Entry<Integer, String> entry = it2.next();
			vbox2.getChildren().add(differentHBoxFactory(entry.getKey().toString(), entry.getValue()));
			vbox1.getChildren().add(emptyHBoxFactory());
		}
		/*
		 * 将两个盒子加入到滚动条处
		 */
		sp1.setContent(vbox1);
		sp2.setContent(vbox2);
	}
	
	/*
	 * 创建一个空盒子，为了方便两边的同步显示
	 */
	public HBox emptyHBoxFactory(){
		HBox hbox = new HBox();
		Label emptyLabel = new Label();
		emptyLabel.setText("--_--_--_--_--_--_--_--_--_--_--_--_--_--");
		hbox.getChildren().add(emptyLabel);
		return hbox;
	}
	
	
	/*
	 * 每一句话都要作为一个申请两个label，将这两个label存入盒子中 key是Map中的键，value是Map中的值
	 * 此方法放回的是一个填写好的HBox
	 */
	/*
	 * 添加进label中的最短字符串长度
	 */
	int minLength = 4;
	public HBox differentHBoxFactory(String key, String value) {
		HBox hbox = new HBox();
		// Label labelProNum = new Label(key);
		/*
		 * 改变key字符串的长度，方便显示 同时取消了
		 */
		if (key.length() < this.minLength) {
			int temp = this.minLength - key.length();
			for (int i = 0; i < temp; i++) {
				key += "  ";
			}
		}
		Label labelProInfo = new Label(key + value);
		// labelProNum.setMinWidth(20);
		// hbox.setSpacing(20);
		// hbox.getChildren().add(labelProNum);
		hbox.getChildren().add(labelProInfo);
		return hbox;
	}
	/*
	 * key代表行号，value代表相应行的代码句
	 */
	public HBox sameHBoxFactory(String key, String value) {
		HBox hbox = new HBox();
		// Label labelProNum = new Label(key);
		/*
		 * 改变key字符串的长度，方便显示 同时取消了
		 */
		if (key.length() < this.minLength) {
			int temp = this.minLength - key.length();
			for (int i = 0; i < temp; i++) {
				key += "  ";
			}
		}
		Label labelProInfo = new Label(key + value);
		labelProInfo.setTextFill(Color.RED);
		// labelProNum.setMinWidth(20);
		// hbox.setSpacing(20);
		// hbox.getChildren().add(labelProNum);
		hbox.getChildren().add(labelProInfo);
		return hbox;
		// HBox hbox = new HBox();
		// Label labelProNum = new Label(key);
		// Label labelProInfo = new Label(value);
		// labelProNum.setMinWidth(20);
		// labelProInfo.setTextFill(Color.RED);
		// hbox.setSpacing(20);
		// hbox.getChildren().add(labelProNum);
		// hbox.getChildren().add(labelProInfo);
		// return hbox;
	}
}
