package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//import Detection.Launch;
import Detection.*;

/*
 * 此类用于图标信息的提供，从指定的文件处读取文件的内容，然后将其转换
 * 转换为图表所需要的表达形式
 */
public class GetData {

	// public static void main(String args[]){
	// GetData gd = new GetData();
	// gd.getSimNum();
	// }
	/*
	 * 对话框的创建
	 */
	Dialog dialog = new Dialog();
	/*
	 * 标准的使用方法，持有启动方法的引用，通过启动方法来获取其中的数据
	 */
	Launch launch = new Launch();

	/*
	 * 获取比较的相似度的信息
	 */
	public void getSimNum() {
		/*
		 * 将阈值设置为-1，则每次检测的数据均会存入到指定文件中
		 */
		launch.pd.setTHRESHOLD(-1);
		launch.setDefaultPath("D:/00");
		launch.readDirctoriesWithAll();
		launch.pd.launchSimiMeasWithAll();
		this.writeFile();
		System.out.println("*******************************************");
		this.readFile("D:\\00\\142071.pd");
	}

	/*
	 * 当用户查看详细信息后，用户还可以继续点击信息，查看更加详细的信息
	 */
	public List<String> getMore(int index) {
		List<String> list = new ArrayList<String>();
		switch (index) {
		case 1:
			setSimpleList(1.0, 0.95, list);
			break;
		case 2:
			setSimpleList(0.95, 0.9, list);
			break;
		case 3:
			setSimpleList(0.9, 0.8, list);
			break;
		case 4:
			setSimpleList(0.8, 0.7, list);
			break;
		case 5:
			setSimpleList(0.7, 0.6, list);
			break;
		case 6:
			setSimpleList(0.6, -1.0, list);
			break;
		default:
			break;
		}
		return list;

	}

	public void setSimpleList(double d1, double d2, List<String> list) {
		for (int i = 0; i < simpleSimInfo.size(); i += 3) {
			/*
			 * 遍历整个相似度信息存储的数组，然后比较数组中的信息
			 */
			if (Double.valueOf(this.simpleSimInfo.get(i + 2)) > d2
					&& Double.valueOf(this.simpleSimInfo.get(i + 2)) <= d1) {
				list.add(simpleSimInfo.get(i));
				list.add(simpleSimInfo.get(i + 1));
				list.add(simpleSimInfo.get(i + 2));
			}
		}
	}

	/*
	 * 新创建的数组，其中存放了相似度比较的简要信息
	 */
	List<String> simpleSimInfo = new ArrayList<String>();
	List<Integer> simpleSimList = new ArrayList<Integer>();

	/*
	 * 发现数据量灰常大，而且很多数据可能是无用的，考虑到将来，还是将其存储起来 但是需要将存有信息的各种数组改变其值
	 * 两两比较的结果很多，此时选取的只是针对某个同学的最大值
	 */
	public void simpleSimList() {
		String name1 = null;
		String name2 = null;
		double result = 0;
		/*
		 * 首先根据simInfo的值取出第一位同学的最大相似度
		 */
		for (int i = 0; i < simInfo.size();) {
			/*
			 * 首先默认取出一个第一个值 此值 代表一个同学，根据在siminfo的位置判断
			 */
			name1 = simInfo.get(i);
			name2 = simInfo.get(i + 1);
			result = Double.valueOf(simInfo.get(i + 2));
			/*
			 * 首先吧第一位同学的信息存入至数组中
			 */
			simpleSimInfo.add(name1);
			simpleSimInfo.add(name2);
			simpleSimInfo.add(String.valueOf(result));
			/*
			 * 三个三个一组，取出第一组的值然后进行判断
			 */
			int j = i + 3;
			for (; j < simInfo.size(); j += 3) {
				if (!simInfo.get(j).equals(name1)) {
					/*
					 * 如果比较厚不相等，说明一个同学的遍历已近完成
					 */
					i = j;
					break;
				}
				/*
				 * 如果相等，则说明仍是同一个同学，此时需要对其相似度的值进行比较
				 * 如果与之前的相似度相比更大，则将result的值与name1的值根更新
				 */
				if (result < Double.valueOf(simInfo.get(j + 2))) {
					result = Double.valueOf(simInfo.get(j + 2));
					name2 = simInfo.get(j + 1);
					/*
					 * 将之前存入的学生的信息删除，再将此处的更新后的信息存入进去
					 */
					int size = simpleSimInfo.size();
					simpleSimInfo.remove(size - 1);
					simpleSimInfo.remove(size - 2);
					simpleSimInfo.add(name2);
					simpleSimInfo.add(String.valueOf(result));
				}
			}
			if (j >= simInfo.size()) {
				break;
			}
		}
	}

	/*
	 * 根据简化后的信息简化统计数组
	 */
	public void initSimpleList() {

		for (int i = 0; i < this.range.length; i++) {
			simpleSimList.add(0);
		}

		for (int i = 0; i < simpleSimInfo.size(); i += 3) {
			if (Double.valueOf(simpleSimInfo.get(i + 2)) > 0.95) {
				simpleSimList.set(0, simpleSimList.get(0) + 1);
			} else if (Double.valueOf(simpleSimInfo.get(i + 2)) > 0.90) {
				simpleSimList.set(1, simpleSimList.get(1) + 1);
			} else if (Double.valueOf(simpleSimInfo.get(i + 2)) > 0.80) {
				simpleSimList.set(2, simpleSimList.get(2) + 1);
			} else if (Double.valueOf(simpleSimInfo.get(i + 2)) > 0.70) {
				simpleSimList.set(3, simpleSimList.get(3) + 1);
			} else if (Double.valueOf(simpleSimInfo.get(i + 2)) > 0.60) {
				simpleSimList.set(4, simpleSimList.get(4) + 1);
			} else {
				simpleSimList.set(5, simpleSimList.get(5) + 1);
			}
		}

		System.out.println(simpleSimList);
	}

	public void showSimpleList() {
		for (int i = 0; i < simpleSimInfo.size(); i += 3) {
			System.out.print(simpleSimInfo.get(i) + " & ");
			System.out.print(simpleSimInfo.get(i + 1) + " @ ");
			System.out.println(simpleSimInfo.get(i + 2));
		}
	}

	/*
	 * 使用的时候，一定需要初始化这个数组，因此直接调用
	 */
	public GetData() {
		this.initSimMap();
		this.initSimList();
	}

	/*
	 * 获取出指定范围的值
	 */
	public void getListInfo(double num1, double num2) {
		if (num1 < num2) {
			System.out.println("相似度查看范围有误！");
			return;
		}
		/*
		 * simInfo数组中存放了所有的比较信息，每三个为一组
		 */
		for (int i = 0; i < simInfo.size();) {
			double d = Double.valueOf(simInfo.get(i + 2));
			if (d >= num2 && d <= num1) {
				System.out.println("Get the value:" + simInfo.get(i) + " and " + simInfo.get(i + 1) + "" + d);
			}
			i++;
			i++;
			i++;
		}
	}

	/*
	 * 新增统计变量，阈值范围的统计 数组用于存放范围内的值 map用于存放相应范围的下标
	 */
	public List<Integer> simList = new ArrayList<Integer>();
	Map<Integer, Double> simMap = new TreeMap<Integer, Double>();
	/*
	 * 根据需求，用户可能需要查看在某区间范围内的具体信息 此时通过读取这个数组，从而获取具体信息，显示在UI上。
	 */
	public List<String> simInfo = new ArrayList<String>();
	/*
	 * 判断的范围--统计的区间
	 */
	double[] range = { 0.95, 0.90, 0.80, 0.70, 0.60, -1 };

	/*
	 * 初始化map，即存储信息的统计数组的下标值
	 */
	public void initSimMap() {
		for (int i = 0; i < range.length; i++) {
			simMap.put(i, range[i]);
		}
	}

	/*
	 * 初始化统计数组--统计相似度不同范围内的值
	 */
	public void initSimList() {
		for (int i = 0; i < range.length; i++) {
			simList.add(0);
		}
	}

	/*
	 * 根据比较的结果，给指定下标的数组增加1，从而进行统计 同时将结果存入到数组中，用于之后的读取工作
	 */
	public void setSimList(String name1, String name2, double d) {
		for (int i = 0; i < simList.size(); i++) {
			/*
			 * 比较结果大于0.95，对应位置的数值加1，以此类推
			 */
			if (d >= simMap.get(i)) {
				simList.set(i, simList.get(i) + 1);
				simInfo.add(name1);
				simInfo.add(name2);
				simInfo.add(String.valueOf(d));
				return;
			}
		}
	}

	/*
	 * 显示详细信息--饼图所显示的各个区间的详细信息 接受参数：指定的.pd文件的路径
	 */
	public void showDetialInfo(String path) {

		if (null == path) {
			dialog.showDialog("请您选择文件！");
			return;
		}
		/*
		 * 通过读取指定的文件，从而初始化两个数组 simList和simInfo
		 */
		this.simpleSimInfo.clear();
		this.simpleSimList.clear();
		this.readFile(path);
		/*
		 * 初始化完毕后，可以获得相应的数值，显示出来
		 */
		this.simpleSimList();
		// this.showSimpleList();
		this.initSimpleList();
		// String info1 = null;
		// String info2 = null;
		// String info3 = null;
		// String info4 = null;
		// String info5 = null;
		// String info6 = null;
		// for(int i = 0; i < 6; i++){
		// this.simList
		// }
		dialog.showDetailDialog(this, 
				"1.0 -0.95->" + this.simpleSimList.get(0) +"组",
				"0.95- 0.9->" + this.simpleSimList.get(1) +"组",
				"0.9 - 0.8->" + this.simpleSimList.get(2) +"组",
				"0.8 - 0.7->" + this.simpleSimList.get(3) +"组", 
				"0.7 - 0.6->" + this.simpleSimList.get(4) +"组",
				"0.6 - 0.0->" + this.simpleSimList.get(5) +"组");

	}

	/*
	 * 去读指定文件的内容,同时将所有的内容依次存入至simList数组中 在读取文件内容的同时，设置simList中的数值
	 */
	public String readFile(String path) {
		String str = "name&name@result";
		File file = new File(path);
		simList.clear();
		simInfo.clear();
		initSimList();
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
					// 用于判断是否是指定的文件
					if (!str.equals(bf.readLine())) {
						bf.close();
						ipr.close();
						in.close();
						return "这不是指定的文件！";
					}
					// 每次读取一行，如果此行有数据则进行下面的操作，如果没有则跳过
					while ((str = bf.readLine()) != null) {
						String name1;
						String name2;
						double result;
						name1 = str.substring(0, str.indexOf("&"));
						name2 = str.substring(str.indexOf("&") + 1, str.indexOf("@"));
						result = Double.valueOf(str.substring(str.indexOf("@") + 1, str.length()));
						// System.out.println(name1 + "&" + name2 + "@" +
						// result);
						simInfo.add(name1);
						simInfo.add(name2);
						simInfo.add(String.valueOf(result));
						setSimList(name1, name2, result);
					}
					// 一次关闭各种管道
					bf.close();
					ipr.close();
					in.close();
					/*
					 * 如果是个文件夹，则应该报错，因为我不会处理=。=
					 */
				} else if (true == file.isDirectory()) {
					System.out.println("这是个文件夹哦！");
					return "这是个文件夹哦！";
				} else {
					System.out.println("whats the fuck!");
					return "whats the fuck!";
				}
			} catch (FileNotFoundException e) {
				System.out.println("文件没有找到！系统退出！");
				return "文件没有找到！系统退出！";
			} catch (IOException e) {
				System.out.println("打开打开失败！系统退出！");
				return "打开打开失败！系统退出！";
			}
		} else {
			System.out.println("错误：指定文件不存在！");
			return "错误：指定文件不存在！";
		}
		return "文件读取完毕";
	}

	/*
	 * 新增方法，用于将比较后的结果--阈值存入到指定的文件中 存储格式：文件名1&文件名2@比较结果
	 */
	public void writeFile() {
		File file = null;
		if (!this.launch.pd.info.isEmpty()) {
			// System.out.println(this.launch.pd.info.get(0).substring(
			// 0, 8));
			file = crateFile(this.launch.pd.info.get(0).substring(0, 6));
		} else {
			return;
		}
		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new FileWriter(file));
			output.append("name&name@result\n");
			for (int i = 0; i < this.launch.pd.info.size();) {
				output.append(this.launch.pd.info.get(i++));
				output.append("&");
				output.append(this.launch.pd.info.get(i++));
				output.append("@");
				setSimList(this.launch.pd.info.get(i - 2), this.launch.pd.info.get(i - 1),
						Double.valueOf(this.launch.pd.info.get(i)));
				output.append(this.launch.pd.info.get(i++));
				output.append("\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (null != output) {
					output.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/*
	 * 在默认的路径下创建文件
	 */
	public File crateFile(String name) {

		File file = new File(Launch.DefaultPath + "/" + name + ".pd");
		try {
			if (!file.exists()) {
				file.createNewFile();
				return file;
			} else {
				file.delete();
				return file;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

}
