package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
/*
 * 文本比较类
 * 此类主要是用来存储处理过后的源程序的信息（两个Map值）
 * 根据其中的值，进行相应的比较
 */
public class CompareForApp {
	
		public static void main(String args[]){
//			int[][] a = {
//					{1,2},{2,3},{3,4}
//				};
			String[][] a = new String[3][2];
			System.out.println(a.length);
			int b = a.length;
			for (int i = 0; i < b; i++){
				System.out.println("内维a[" + i + "]长度为" + a[i].length);
			}
			
			List<String> list = new ArrayList<String>();
			list.add("qwe");
			list.add(null);
			list.add("asd");
			list.add(0, "dasda");
			System.out.println(list.get(1));
		}
		/*
		 * 因为一次仅显示两个文件的比较情况，此时定义两个变量用于Map变量，用于存储相关的信息
		 * 存放处理后的源程序的所有信息
		 * 对应真实文本的行数，绝大部分值
		 */
		Map<Integer, String> programeInfo1 = new TreeMap<Integer, String>();
		Map<Integer, String> programeInfo2 = new TreeMap<Integer, String>();
		
		/*
		 * 根据最新的思路设计如下判断函数：
		 * 	接受的参数为一个二维字符串数组
		 * 	接受过来的参数一行一行进行比较，为一对多比较，每一次比较都是与同级或是下级的字符串比较
		 * 	先尝试返回一个二维数组算了--方便起见
		 * 	发现返回有一个二维数组最重要的行数无法确定，此时返回一个ArrayList比较合适
		 * 	list:这个ArrayList用于标识每个维度的意义
		 * 	numList：用于创建一个临时数组，利用上述的数字标识表示每一行的作用
		 * 		比较方法：
		 * 			将接受过来的二维数组中的值进行比较，第0列与同层级和低层级（往下）的字符串比较
		 * 			第1列与同层级和高层级的字符串比较（往上），将比较的结果记录在数组中
		 * 
		 * 	利用的数字解释：
		 * 		-1表示没有找到匹配的字符串,0~n表示找到的匹配串的数组下标
		 */
		public List<Integer> compareStringWithList(List<String> proList1, List<String> proList2){
//			System.out.println(proList1.size() + " " + proList2.size());
//			for(int i = 0; i < proList1.size(); i++){
//				System.out.println("proList1 = "+proList1.get(i));
//				System.out.println("proList2 = "+proList2.get(i));
//			}
			/*
			 * 创建一个标识，便于操作，这个标识不断的增大（十的倍数）
			 * 之后转换成字符串存入数组中，数字的作用与之前所说的一致
			 */
			List<Integer> numList1 = new ArrayList<Integer>();
			List<Integer> numList2 = new ArrayList<Integer>();
			/*
			 * 两个数组的初始化，默认的值均为-1，即未找到
			 * 初始化次数为之前设定的行数值，默认值为3
			 */
			for(int i = 0; i < proList1.size(); i++) {
				numList1.add(-1);
				numList2.add(-1);
			}
			/*
			 * 首先取出二维字符串数组的行数
			 */
			int row = proList1.size();
			/*
			 * 两层循环，用于判断字符串是否相等
			 * 判断的对象为同层次的字符串与下层字符串
			 * 
			 * 此时实际上是生成中间数组，方便之后根据此数组生成最后的创建数组
			 */
			for(int i = 0; i < row; i++){
				/*
				 * 从最外层进入里层进行扫描式的比较
				 */
				for(int j = i; j < row; j++){
					/*
					 * 判断对等或是以下层级的是否相等
					 * 如果相等，则需要break调此次比较
					 */
					if(proList1.get(i).equals(proList2.get(j))){
						/*
						 * 同时将左列的下标与找到对应右列的下标存入数组中
						 */
						numList1.set(i, j);
						/*
						 * break是为了不想影响出现相同的语句对下次的判断产生影响
						 */
						break;
					}
				}
				/*
				 * 如果经过一轮的循环后发现没有,无需操作，因为之前已经对数组进行初始化，默认就是没找到
				 */
			}
			/*
			 * 经过上面一轮的查找，已近将第一列寻找完毕
			 * 现在开始从第二列开始寻找：此时的寻找规则为：
			 * 	比较同级的字符串与同级以上（向上比较），然后再将下标记录下来
			 */
			for(int i = 0; i < row; i++){
				/*
				 * 由于只和同级和同级以上的比较，因此最大值亦不可超过此时比较的等级
				 */
				for(int j = i; j >= 0; j--){
					/*
					 * 判断对等或是以上层级出现相等，则需要将相应下标对应的值置位
					 * 同时break调此次比较
					 */
					if(proList2.get(i).equals(proList1.get(j))){
						/*
						 * 如果找到相等的值则需要将相应位置的值设置为对应的下标值
						 */
						numList2.set(i, j);
						break;
					}
				}
			}
			/*
			 * 经过上述的两次比较，已近产生出了两个数组，其中标识查找的信息
			 */
			return createLabelInfor2(numList1, numList2);
		}
		/*
		 * 又一个新方法。。。。。
		 * 接受参数为左右列字符串匹配下标数组
		 * 返回值为标签生成数组
		 */
		public List<Integer> createLabelInfor2(List<Integer> numList1, List<Integer> numList2){
			adjustArrayList(numList1,numList2);
			
//			for(int i = 0; i < numList1.size(); i++){
//				System.out.print("numlist1 = " + numList1.get(i) + " numlist2 = ");
//				System.out.println(numList2.get(i));
//			}
//			System.out.println("a arraylist with after adjust");
			/*
			 * rightUp用于判断左右列的移动差距，即右列移动一次就加一，左列移动一次就减一
			 * move判断是否为第一次移动，因为如果根据算法，如果是第一次移动，并且遇到-1
			 * 则会默认添加2-1，而非2-2
			 */
			int rightUp = 0;
			boolean move = false;
			/*
			 * 创建一个数组用于存放需要返回的值
			 * 其中数组表示含义：0填入原值，即相等标签，1表示空白标签，2表示不相等的标签
			 */
			List<Integer> stringList = new ArrayList<Integer>();
			/*
			 * 从中一次取值
			 */
			for(int i = 0; i < numList1.size(); i++){
				/*
				 * 判断其是否为-1，即是否在右列有匹配值
				 */
				if(-1 != numList1.get(i)){
					/*
					 * 最新判断公式：
					 * 	晓白定理：number = value - index - rightUP
					 * 	number	表示可以增加空行数
					 * 	value	表示其值
					 * 	index	表示value值的下标
					 * 	rightUp	表示左列已近增加的空行数，初值为0
					 * 用于判断可以增加的空行数目
					 */
					int count = numList1.get(i) - i - rightUp;
					if(count < 0){
						System.out.println("count meet error in createLabelInfor2");
						System.exit(0);
					}
					if(0 != count ){
						/*
						 * 根据计算结果，循环增加空行数目，也就是上移右列
						 * 上移的同时增加rightUp的值，并且置为move，表明已经移动过数组
						 */
						for(int j = 0; j < count; j++){
							stringList.add(1);
							stringList.add(2);
							rightUp++;
							move = true;
						}
						/*
						 * 移动完成之后，右列的数值已近与下标为i的值相同，此时即可设置为0-0
						 */
						stringList.add(0);
						stringList.add(0);
					} else {
						/*
						 * 如果为0，表明无需移动，现在已近匹配到了相同的字符串
						 */
						stringList.add(0);
						stringList.add(0);
					}			
				/*
				 * 如果为-1，则表明没有找到匹配的字符串
				 * 此时可能有两种情况：
				 * 	第一：右列移动
				 *  第二：均设置为2，表明代码不同
				 */
				} else {
					/*
					 * 判断是否是第一次移动数组，如果移动过即可再次移动
					 */
					if(move){
						/*
						 * rightUp为0表示左右列已近同步，则无需移动，直接设置为2
						 */
						if( 0 == rightUp){
							stringList.add(2);
							stringList.add(2);
						/*
						 * 不为0说明左右列没有同步，考虑到可能有相同的值需要比对
						 * 此时同步优先，将左列相对上移--为右列增加空表标签
						 * 同时将rightUp值减1，表明同步过一次。
						 */
						} else {
							stringList.add(2);
							stringList.add(1);
							rightUp--;
						}
					/*
					 * 之前没有移动过数组，直接将其设置为2，表明代码不同
					 */
					} else {
						stringList.add(2);
						stringList.add(2);
					}
					
				}
			}
			
//			for(int i = 0; i < stringList.size(); i++){
//				System.out.println("stringList = " + stringList.get(i));
//			}
//			System.out.println("a arraylist with after adjust");
			/*
			 * 如果其值不为0，则表明同步过程出错，之后的过程铁定出错
			 */
			if(0 != rightUp){
				System.out.println("rightUp is not zero!");
				System.exit(0);
			}
			return stringList;
		}
		/*
		 * 移动左边，右边的次数,规定哪边增加空白行，哪边就算是移动一次
		 */
		int moveRight = 0;
		int moveLeft = 0;
		public List<Integer> createLabelInfoWithList(List<Integer> numList1, List<Integer> numList2){
//			System.out.println("createLabelInfo function!----start");
//			for(int i = 0; i < numList1.size(); i++){
//				System.out.print("numlist1 = " + numList1.get(i) + " numlist2 = ");
//				System.out.println(numList2.get(i));
//			}
//			System.out.println("createLabelInfo function!----over");
			/*
			 * 将两个数组进行调整
			 */
			adjustArrayList(numList1,numList2);
			
//			for(int i = 0; i < numList1.size(); i++){
//				System.out.print("numlist1 = " + numList1.get(i) + " numlist2 = ");
//				System.out.println(numList2.get(i));
//			}
//			System.out.println("a arraylist with after adjust");
			/*
			 * 创建一个数组用于存放需要返回的值
			 * 其中数组表示含义：0填入原值，即相等标签，1表示空白标签，2表示不相等的标签
			 */
			List<Integer> stringList = new ArrayList<Integer>();
			/*
			 * 获得两个数组后，循环取出其值，判断是否需要进行比较
			 */
			if(numList1.size() != numList2.size()){
				System.out.println("System Account Error!numList'size not equals!");
				System.exit(0);
			}
			/*
			 * 新增判断条件：如果出现位置不够用，即在前面出现了空白标签，导致右列的数值下移
			 * 而直接导致对应行的数值下移，无法匹配，如果匹配，中间就得新增空白行
			 * 
			 * 增加一个标志位，用于判断是否可以增加空白行
			 * 对于三行的来说，再一次遍历中只能增加一次空白行，因为每一次增加空行都会消耗一次count值
			 * 即必须对应第二列的真实数值，而最后就无法将其值加入其中
			 */
//			int emptyCount = 0;
//			int notEmptyCount = 0;
			moveRight = 0;
			moveLeft = 0;
			for(int i = 0; i < numList1.size(); i++){
				/*
				 * 如果等于-1则表明没有找到相应的值，直接比较下一个
				 * 如果找到了，则需要进行判断，中间有多少个间隔需要填充空标签
				 */
				if(-1 != numList1.get(i)){
					/*
					 * 判断是否需要对左列进行移动，主要是为了实现左右列同步
					 */
					int right = numList1.get(i) - i;
					if(0 != right){
						for(int j = 0; j < right; j++){
							stringList.add(1);
							stringList.add(2);
							moveLeft++;
						}
					} else {
						stringList.add(0);
						stringList.add(0);
					}
				/*
				 * 如果比较出来发现不相等在，则需要将相应为位置直接置为不等2
				 */
				} else {
					/*
					 * 不可以简单的判断一边，还需要判断另外一列
					 */
					if(i + moveRight < numList2.size()){
						if(numList2.get(i + moveRight) != -1){
							stringList.add(2);
							stringList.add(1);
							moveRight++;
						} else {
							stringList.add(2);
							stringList.add(2);
						}
					} else {
						stringList.add(2);
						stringList.add(2);
						moveLeft++;
					}
					
					/*
					 * 判断对于第二列是添加1还是添加2
					 * 判断条件：
					 * DesktopApplication.getStringNum：预定义的行数
					 * notEmptyCount：非空行数
					 * removeNullNum：取消掉的null行数
					 */
//					if( numList1.size() == notEmptyCount ){
//						stringList.add(2);
//						stringList.add(1);
//						moveRight++;
//					} else {
//						stringList.add(2);
//						stringList.add(2);
//						notEmptyCount++;
//					}
				}
			}
//			System.out.println(moveRight + "   " +moveLeft);
//			if(moveRight != moveLeft){
//				System.out.println("not equals!");
//			}
			return stringList;
		}

		
		/*
		 * 产生一个可以直接利用其产生标签的数组
		 * 接受的参数为上述产生的两个存储了比较信息的数组
		 * 
		 * 移除的unll值数目，一次移除两个null值，表明少了一行
		 * 则对应的当值为-1时，可以表明是该增加1-2还是增加2-2
		 */
		int removeNullNum = 0;
		public List<Integer> createLabelInfo(List<Integer> numList1, List<Integer> numList2){
//			System.out.println("createLabelInfo function!----start");
//			for(int i = 0; i < numList1.size(); i++){
//				System.out.print("numlist1 = " + numList1.get(i) + " numlist2 = ");
//				System.out.println(numList2.get(i));
//			}
//			System.out.println("createLabelInfo function!----over");
			/*
			 * 将两个数组进行调整
			 */
			adjustArrayList(numList1,numList2);
			
//			for(int i = 0; i < numList1.size(); i++){
//				System.out.print("numlist1 = " + numList1.get(i) + " numlist2 = ");
//				System.out.println(numList2.get(i));
//			}
//			System.out.println("a arraylist with after adjust");
			/*
			 * 创建一个数组用于存放需要返回的值
			 * 其中数组表示含义：0填入原值，即相等标签，1表示空白标签，2表示不相等的标签
			 */
			List<Integer> stringList = new ArrayList<Integer>();
			/*
			 * 获得两个数组后，循环取出其值，判断是否需要进行比较
			 */
			if(numList1.size() != numList2.size()){
				System.out.println("System Account Error!numList'size not equals!");
				System.exit(0);
			}
			/*
			 * 新增判断条件：如果出现位置不够用，即在前面出现了空白标签，导致右列的数值下移
			 * 而直接导致对应行的数值下移，无法匹配，如果匹配，中间就得新增空白行
			 * 
			 * 增加一个标志位，用于判断是否可以增加空白行
			 * 对于三行的来说，再一次遍历中只能增加一次空白行，因为每一次增加空行都会消耗一次count值
			 * 即必须对应第二列的真实数值，而最后就无法将其值加入其中
			 */
			int emptyCount = 0;
			int notEmptyCount = 0;
			moveRight = 0;
			moveLeft = 0;
			for(int i = 0; i < numList1.size(); i++){
				/*
				 * 如果等于-1则表明没有找到相应的值，直接比较下一个
				 * 如果找到了，则需要进行判断，中间有多少个间隔需要填充空标签
				 */
				if(-1 != numList1.get(i)){
					/*
					 * 判断是否需要对左列进行移动，主要是为了实现左右列同步
					 */
					int right = numList1.get(i) - i;
					if(0 != right){
						for(int j = 0; j < right; j++){
							stringList.add(1);
							stringList.add(2);
							moveLeft++;
						}
						continue;
					}
					/*
					 * 判断下移了多少格，如果所匹配的字符串的下标已近移除指定长度外则不予匹配
					 */
					if(numList1.get(i) - (numList1.size() - emptyCount) > 0){
						stringList.add(2);
						stringList.add(1);
						moveRight++;
						continue;
					}
					/*
					 * 提前判断是否有空位可以添加必要信息
					 */
					if( 0 == ShowWithDetail.getStringNum - notEmptyCount - removeNullNum ){
						stringList.add(2);
						stringList.add(1);
						moveRight++;
						continue;
					}
					
					int index = i - numList1.get(i);
					index = Math.abs(index);
					/*
					 * index表示中间间隔是多少，用于天空另外一边对应的空标签
					 */
					if(0 == index){
						/*
						 * 如果找到并且中间间隔为0，则表示是对等层相等
						 * 直接填入两个0，届时读取的时候依旧是顺序扫描
						 */
						stringList.add(0);
						stringList.add(0);
						notEmptyCount++;
					/*
					 * 如果index不等于0.说明中间有间隔
					 */
					} else {
						/*
						 * 最新判断公式：
						 * 	晓白定理：number = value - index - emptyCount
						 * 	number	表示可以增加空行数
						 * 	value	表示其值
						 * 	index	表示value值的下标
						 * 	emptyCount	表示左列已近增加的空行数，初值为0
						 */
						int number = numList1.get(i) - i - emptyCount;
						if(0 == number){
							stringList.add(0);
							stringList.add(0);
							notEmptyCount++;
						} else {
							for(int j = 0; j < number; j++){
								emptyCount++;
								stringList.add(1);
								stringList.add(2);
								notEmptyCount++;
								moveLeft++;
							}
							stringList.add(0);
							stringList.add(0);
							notEmptyCount++;
						}	
					}
				/*
				 * 如果比较出来发现不相等在，则需要将相应为位置直接置为不等2
				 */
				} else {
					/*
					 * 不可以简单的判断一边，还需要判断另外一列
					 */
//					if(i + moveRight < numList2.size()){
//						if(numList2.get(i + moveRight) != -1){
//							stringList.add(2);
//							stringList.add(1);
//							moveRight++;
//							continue;
//						}
//					}
					
					/*
					 * 判断对于第二列是添加1还是添加2
					 * 判断条件：
					 * DesktopApplication.getStringNum：预定义的行数
					 * notEmptyCount：非空行数
					 * removeNullNum：取消掉的null行数
					 */
					if( numList1.size() == notEmptyCount ){
						stringList.add(2);
						stringList.add(1);
						moveRight++;
					} else {
						stringList.add(2);
						stringList.add(2);
						notEmptyCount++;
					}
				}
			}
//			System.out.println(moveRight + "   " +moveLeft);
//			if(moveRight != moveLeft){
//				System.out.println("not equals!");
//			}
			return stringList;
		}
		
		/*
		 * 为了避免当同时操作的行数增大而出现数组下标越界，中间数组比对值出现大面积跨界
		 * 调整方法：接受参数为中间数组，作用为修正中间数组，返回修正后的值
		 * 
		 * 首先接收等待修正的数组，然后扫描左列，即第一列
		 * 当扫描到非-1值，即在右列有对应的匹配字符串时，则从右列的index-1处开始扫描
		 * 到value值所对应的右列下标处即rightIndex = value处。
		 * 如果中途出现了非-1值，则立即停止扫描，并且设置value为-1
		 * 若扫描完成后仍然没有发生越界情况，则不做任何处理，继续扫描左列数值
		 */
		public void adjustArrayList(List<Integer> numList1, List<Integer> numList2){
			/*
			 * 用来记录已近出想过的匹配下标
			 */
			List<Integer> numList = new ArrayList<Integer>();
			/*
			 * 从第一个值开始进行扫描
			 */
			for(int i = 0; i < numList1.size(); i++) {
				/*
				 * 判断是否出现非-1的值
				 */
				if(-1 != numList1.get(i)){
					/*
					 * 如果匹配的字符串在之前出现过，则直接将其置为-1表示不再匹配
					 */
					if(numList.contains(numList1.get(i))){
						numList1.set(i, -1);
						//numList2.set(numList1.get(i), -1);
						continue;
					} else {
						numList.add(numList1.get(i));
					}
					/*
					 * 如果出现了非-1的值，则需要扫描第二列的指定位置
					 * 从index+1 至 value
					 */
					for(int j = i+1; j < numList1.get(i); j++){
						/*
						 * 如果出现了非-1项在扫描范围内，则立马跳出循环，并且左列表置为-1
						 */
						if(-1 != numList2.get(j) && i < numList2.get(j)){
							numList1.set(i, -1);
							//numList2.set(numList1.get(i), -1);
							break;
						}
					}
				}
			}
		}
		
		public List<Integer> compareString(String[][] string){
//			System.out.println("compareString function!----start\n接受的字符串数组");
//			System.out.println(string.length);
//			for (int i = 0; i < string.length; i++){
//				for(int j = 0; j < string[i].length; j++){
//					System.out.println(string[i][j]);
//				}
//			}
//			System.out.println("compareString function!----end");
			/*
			 * 创建一个标识，便于操作，这个标识不断的增大（十的倍数）
			 * 之后转换成字符串存入数组中，数字的作用与之前所说的一致
			 */
			//String numStr = "";
			List<Integer> numList1 = new ArrayList<Integer>();
			List<Integer> numList2 = new ArrayList<Integer>();
			/*
			 * 两个数组的初始化，默认的值均为-1，即未找到
			 * 初始化次数为之前设定的行数值，默认值为3
			 */
//			for(int i = 0; i < proList1.size(); i++) {
//				numList1.add(-1);
//				numList2.add(-1);
//			}
			for(int i = 0; i < string.length; i++){
				numList1.add(-1);
				numList2.add(-1);
			}
			/*
			 * 首先取出二维字符串数组的行数
			 */
			int row = string.length;
//			int row = proList1.size();
			/*
			 * 两层循环，用于判断字符串是否相等
			 * 判断的对象为同层次的字符串与下层字符串
			 * 
			 * 此时实际上是生成中间数组，方便之后根据此数组生成最后的创建数组
			 */
			for(int i = 0; i < row; i++){
				/*
				 * 从最外层进入里层进行扫描式的比较
				 */
				for(int j = i; j < row; j++){
					/*
					 * 如果出现了unll值，说明之后已近没有此列的源代码
					 */
					if(null == string[i][0]){
						break;
					}
					/*
					 * 判断对等或是以下层级的是否相等
					 * 如果相等，则需要break调此次比较
					 */
					if(string[i][0].equals(string[j][1])){
						/*
						 * 同时将左列的下标与找到对应右列的下标存入数组中
						 */
						numList1.set(i, j);
						/*
						 * break是为了不想影响出现相同的语句对下次的判断产生影响
						 */
						break;
					}
				}
				/*
				 * 如果经过一轮的循环后发现没有,无需操作，因为之前已经对数组进行初始化，默认就是没找到
				 */
			}
			/*
			 * 经过上面一轮的查找，已近将第一列寻找完毕
			 * 现在开始从第二列开始寻找：此时的寻找规则为：
			 * 	比较同级的字符串与同级以上（向上比较），然后再将下标记录下来
			 */
			for(int i = 0; i < row; i++){
				/*
				 * 由于只和同级和同级以上的比较，因此最大值亦不可超过此时比较的等级
				 */
				for(int j = i; j >= 0; j--){
					/*
					 * 如果出现了unll值，说明之后已近没有此列的源代码
					 */
					if(null == string[i][0]){
						//numList2.set(i, -2);
						break;
					}
					/*
					 * 判断对等或是以上层级出现相等，则需要将相应下标对应的值置位
					 * 同时break调此次比较
					 */
					if(string[i][1].equals(string[j][0])){
						/*
						 * 如果找到相等的值则需要将相应位置的值设置为对应的下标值
						 */
						numList2.set(i, j);
						break;
					}
				}
			}
			/*
			 * 经过上述的两次比较，已近产生出了两个数组，其中标识查找的信息
			 */
			return createLabelInfo(numList1, numList2);
		}
		
		/*
		 * 此方法用于判断传入进来的两个参数Map中的值“相等”与否
		 * 
		 * 以下思路作废：
		 * 返回的值为数组，用于表示哪些行是相等的
		 * 根据笔者对ultraCompare的使用，发现其可能是对两边的文本内容同时进行比较
		 * 当发现与右边的存在相同的值才会继续进行比较，否则继续读取下去，直到读取完毕
		 * 而另外一边则是被动的等待，仅有相同的比较结果出现时才会同步推进
		 * 
		 * 新思路：
		 * 将其简化，方法返回三个值，0,1
		 * 0代表相等，1代表不相等
		 */
		/*
		 * 设置一个ArrayList，用于存放比较后相等的值的下标
		 */
		//ArrayList<Integer> sameList = new ArrayList<Integer>();
		public int compareEqual(String str1, String str2){
			
			/*
			 * 首先去除接收的字符串的空格，因为在设计的时候，为了显示效果增加空格
			 */
			str1 = str1.replace(" ", "");
			str2 = str2.replace(" ", "");
			/*
			 * 去掉空格后的字符串进行严格的比对
			 * 此处比对的方法即为equals方法
			 */
			if(str1.equals(str2)){
//				System.out.println(str1 + " = " +str2);
				return 0;
			} else {
				return 1;
			}
		}
	}
