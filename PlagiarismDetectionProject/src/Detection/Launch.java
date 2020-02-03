package Detection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/*
 * 链接后台程序
 * 属性：
 * 	读取指定位置的文件夹内的内容--字符串
 * 	持有一个检测程序的引用
 * 方法：
 * 	读取指定目录下的文件名称
 * 	读取指定文件的内容
 * 	
 */
public class Launch {

	public static void main(String args[]) {

		Launch launch = new Launch();
		launch.start("");
		Map<Integer, String> map = launch.getProgrameInfo();
		Iterator<Map.Entry<Integer, String>> it2 = map.entrySet().iterator();

		while (it2.hasNext()) {
			Map.Entry<Integer, String> entry = it2.next();
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
	}

	// 读取指定位置的文件夹内的内容
	public static String DefaultPath = "D:/0SaveUploadFile";

	/*
	 * 由于应用需求，多出一个设置方法，将默认路径清空
	 */
	public void setDefaultPath(String defaultPath) {
		DefaultPath = defaultPath;
	}

	// 持有一个检测程序的引用
	public PlagiarismDetection pd = new PlagiarismDetection();

	/*
	 * 用于启动检测程序，并且待会结果 接受的值为文件名称，用于添加进比较队列之首，方便检测
	 */
	public boolean start(String fileName) {
		/*
		 * if(fileName.isEmpty()){ this.readDirctories(fileName); return
		 * this.pd.launchSimiMeas(); }
		 */
//		if(fileName.equals("") || null == fileName){
//			this.readDirctories(fileName);
//			return this.pd.launchSimiMeas();
//		} else {
			// 为了避免躲避多次比较，将上传者的文件放在比较队列的最前端
			this.addNewFile(fileName);
			// 已近将文件上传后，开始读取文件的内容
			this.readDirctories(fileName);
			// 直接调用检测程序，返回接受的返回的值
			return this.pd.launchSimiMeas();
//		}	
	}

	/*
	 * 将上传者的文件放在比较队列的最前端 接受的字符串为用户上传的文件名称
	 */
	protected void addNewFile(String fileName) {
		this.readFile(fileName);
	}

	/*
	 * 两个文件内容的读取与比较，主要是针对可视化的应用 接受两个文件的地址，然后根据地址中的值进行读取文件的内容
	 */
	public void readFileForApp(String fileName1, String fileName2) {

	}

	/*
	 * 文件读取方法 在指定的文件夹下根据指定的名称读取文件内容 出错： 当读取嵌套的文件夹的内容的时候，会存在的文件会说不存在，找不到文件
	 * 
	 * programeInfo变量用于临时存储程序信息,方便desktopapplication调用
	 */
	Map<Integer, String> programeInfo = new TreeMap<Integer, String>();

	public void readFile(String name) {
		String filePath = Launch.DefaultPath + "\\" + name;
		File file = new File(filePath);

		System.out.println("文件名称=" + filePath);

		// 判断文件是否存在，如果不存在则报错，退出操作
		if (file.exists()) {
			/*
			 * 判断接受的内容是普通的文件还是一个目录，如果是目录则再次调用文件夹方法
			 */
			try {
				/*
				 * 如果是个普通文件，则开始对其进行文件读取
				 */
				if (true == file.isFile()) {
					// 创建各种管道，方便读取文件的内容
					FileInputStream in = new FileInputStream(file);
					InputStreamReader ipr = new InputStreamReader(in, "GBK");
					BufferedReader bf = new BufferedReader(ipr);
					// 用于临时存储读取的一行文件内容
					String str = "";
					// 测试显示效果
//					System.out.println("====Launch: file name:" + file.getName() + " ====");
					// 创建一个新的对象，避免所计算的值一直重复
					SourcePrograme sp = new SourcePrograme();
					// 每次读取一行，如果此行有数据则进行下面的操作，如果没有则跳过
					while ((str = bf.readLine()) != null) {
						sp.lineCount++;
						// 接受处理过后的字符串
						sp.codeKeyWordCount(str);
					}
					/*
					 * 此处是新增内容 基于图形化界面，需要将整个源程序的处理结果获取到 因此将Map内的值取出
					 */
					this.programeInfo = sp.programeInfo;
					/*
					 * 唯恐出现意外错误，保险起见，此处判断一个文件结束后，大括号层级关系是否为0
					 * 如果不为0，则其中计算必定出错，此时应该报错，退出程序
					 */
					if (sp.enterBrackets != 0) {
						System.out.println("对不起，系统出现严重错误，作业请您手动提交！" + sp.enterBrackets);
					}
					// 设置源程序类的信息：学生的姓名，即上传的文件名称
					sp.setNameOfStudent(file.getName());

					// 将所得到的代码信息连通文件路径一起存入代码信息的数组之中
					pd.addIn_spList(sp);

					// 一次关闭各种管道
					bf.close();
					ipr.close();
					in.close();
					/*
					 * 如果是个文件夹，则应该报错，因为我不会处理=。=
					 */
				} else if (true == file.isDirectory()) {
					this.readDirctories(filePath);
				} else {
					System.out.println("whats the fuck!");
				}
			} catch (FileNotFoundException e) {
				System.out.println("文件没有找到！系统退出！");
				System.exit(0);
			} catch (IOException e) {
				System.out.println("打开打开失败！系统推出！");
				System.exit(0);
			}
		} else {
			System.out.println("错误：指定文件不存在！");
		}
	}

	/*
	 * 为了让其他类能够获得programeInfo
	 */
	public Map<Integer, String> getProgrameInfo() {
		return this.programeInfo;
	}

	/*
	 * 文件夹中文件名称获取方法 指定文件夹内文件读取（不知处递归读取，请勿嵌套文件夹） 根据读取出来的文件名称，然后再调用
	 */
	public boolean readDirctories(String newFileName) {
		// 根据指定的文件地址读取名称
		File directorie = new File(Launch.DefaultPath);
		/*
		 * 利用list方法，返回文件夹下的所有文件的名称（结果为一个String数组）
		 */
		String[] fileList = directorie.list();

		// System.out.println("newfilename:"+newFileName);

		// 依次拿出文件夹中的文件然后进行读取
		for (String s : fileList) {
			// 如果是新文件，则无需进行读取，之前已经将其放在比较队列第一位
			if (false == s.equals(newFileName)) {
				System.out.println("filename:" + newFileName);
				System.out.println("string s:" + s);
				readFile(s);
			}
		}
		return true;
	}
	
	public void readDirctoriesWithAll() {
		// 根据指定的文件地址读取名称
		File directorie = new File(Launch.DefaultPath);
		/*
		 * 利用list方法，返回文件夹下的所有文件的名称（结果为一个String数组）
		 */
		String[] fileList = directorie.list();

		// System.out.println("newfilename:"+newFileName);

		// 依次拿出文件夹中的文件然后进行读取
		for (String s : fileList) {
			// 如果是新文件，则无需进行读取，之前已经将其放在比较队列第一位
			//System.out.println("string s:" + s);
			if(!s.endsWith(".pd")){
				readFile(s);
			}
		}
	}
	
}
