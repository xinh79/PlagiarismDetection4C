package Detection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

/*
 * 这是一个源程序类，每一个学生上传的代码视为一个源程序，存入的是学生的文件信息，
 * 以及代码的各种信息。
 * 
 * 属性：
 * 	接受的字符串的分割方式
 * 	学生姓名
 * 	关键字统计数组（整型）
 * 	语言信息类，用于初始化关键字Map
 * 	存储指定的文件夹下的所有文件名称的数组
 * 	将指定文件夹下根据文件名称读取文件内容
 * 	持有一个自己的引用，便于加入检测队列
 * 	大括号层级关系下的变量是否应该增加的一个数组（用0/1代替布尔类型）
 * 
 * 方法：
 * 	源代码信息初始化：	
 * 		关键字出现次数初始化
 * 		初始化8个系统申明的变量
 * 	设置学生的姓名
 * 	获得学生的姓名
 * 	字符串分割函数
 * 	关键字统计方法
 * 	大括号层级关系方法--2
 * 	改变大括号层级标志位的方法
 * 	字符串出现次数增加方法
 * 	判断语句之后是否有注释，同时去除注释（注意，去除的仅是单行后置注释）
 * 
 */
public class SourcePrograme {
	//为了实现可视化效果，需要将处理后的源代码保存起来源代码保存起来(400行处对其进行处理)
	Map<Integer, String> programeInfo = new TreeMap<Integer, String>();
	// 字符串的分割方式
	private static final String regex = "\\:|\\,|\\s|;|\\(|\\)|\\&|\\|";
	// 学生姓名
	private String nameOfStudent;
	// 关键字统计数组List
	List<Integer> keyWordCountList = new ArrayList<Integer>();
	// 语言信息类，创建一次，执行其静态方法，避免重复创建
	LanguageInformation li = new LanguageInformation();
	//在大括号的层级关系条件下，判断读取的指定字符串是否应该进行加1操作
	List<Integer> variableList = new ArrayList<Integer>();
	/*
	 * 关键字统计函数
	 * 接受字符串，实际上是处理过后的字符串，然后统计其中的关键字
	 */
	//说明：需要判断是否进入一个括号内，进入一层则加1，退出一层则减1
	int enterBrackets = 0;
	protected void codeKeyWordCount(String str) {
		//实例化一个字符串数组用于存放需要统计的字符串
		List<String> strList = new ArrayList<String>();
		//将初始的字符串进行分割处理，接受返回值
		strList = this.segmentString(str);
		//如果返回为空：空白行或是注释，直接跳过，不予处理
		if (null == strList ){
			return ;
		}
		
//System.out.println("大括号层级关系：   "+this.enterBrackets);
//System.out.println("马上要处理的字符串数组："+strList);	

		//创建一个游标用于遍历其中各个元素
		Iterator<String> it = strList.iterator();
		//当没有到末尾时，则进行匹配操作
		while(it.hasNext()){
			/*
			 * 获得游标右边的字符串
			 * 此处使用数组的原因：同学代码不规范，其中可能包含大括号
			 * 如果其中包含，为了统计，需要将分割后的字符串存入数组中进行关键字统计
			 */
			String s = it.next();
				//判断字符串是否为大括号，或是其中是否包含大括号
			if(s.equals("{")){
				//如果是左大括号，则进入一层，层标志位加1
				this.enterBrackets++;
				//满足equals条件，后面的无趣进行判断，直接判断下一个字符串
				continue;
			} else if(s.equals("}")){
				//如果是右大括号，则退出一层，层标志位减1
				this.enterBrackets--;
				continue;
			}
			/*
			 * 判断较为复杂的情况：如果括号就在字符串内，并没有被分割出来
			 * 同时需要考虑有多个大括号连在一起
			 *
			 * 考虑左大括号的复杂情况：
			 * 	左大括号在不规范的代码中经常性的和其他申明语句连在一起使用，中间并不用空格隔开
			 */
			s = this.BracketsClass(s);
			
//System.out.println("处理完一级大括号后：" + s);
			
			/*
			 * 接下来就要对特殊情况进行处理，判断处理过后的字符串中是否含有大括号
			 * 之所以在处理之后进行判断是因为之前可能已经处理了一些简单的情况，而无需复杂处理，可以减少开销
			 */
			//接受BracketsClass2方法返回的字符串
			String[] strArray = this.BracketsClass2(s);
			
//System.out.println("处理完二级大括号后：" + strArray);	
			
			/*
			 * 判断大括号的层级关系是否为0，如果是0，则需要初始化系统变量申明关键字对应的数组
			 * 与方法needAddOrNot相对应，做到一增一减
			 */
			if(0 == this.enterBrackets){
				this.initSysVariables();
			}
			//如果返回的字符串为null，则无需使用数组操作，否则需要多次关键字统计，在本次循环中
			if(strArray == null){
				
//System.out.println("处理过后的字符串："+s);	
				
				addOfKeyWordCountList(s);
			} else {
				
//System.out.println("处理过后的字符串："+strArray);
				
				/*
				 * 说明其中分割过大括号，需要多次统计,统计次数根据字符串数组大小长度而定
				 * 一次统计其中的值,其中的操作就是重复上述操作
				 */
				for (int i = 0; i < strArray.length; i++) {
					addOfKeyWordCountList(strArray[i]);
				}
			}
		}
	}
	
	/*
	 * 字符串出现次数增加方法
	 * 为了减少代码冗余度，独立出方法，用于增加指定字符串的出现次数
	 */
	private void addOfKeyWordCountList(String str) {
		/*
		 * 只需要进行一次关键字统计，同时只需要处理之前的单个字符串，而非数组
		 * 现在需要根据大括号的层级关系来判断关键字的统计次数是否应该增加
		 */
		if(this.needAddOrNot(str)){
			/*
			 * 接受处理的字符是需要统计的字符串
			 * 开始对其进行统计操作
			 * 注意：经过此判断说明是需要统计的字符串，必定在关键字数组中
			 */
			int index = LanguageInformation.keyWordNameMap.get(str);
			//根据位置对其进行加1操作
			this.keyWordCountList.set(index, this.keyWordCountList.get(index) + 1);
		/*
		 * 返回有两种情况：
		 * 	1.需要的统计的字符串已近增加，无需增加
		 * 	2.接受的字符串并不是needAddOrNot方法所能处理的字符串
		 * 
		 * 需要再次判断，是否是需要统计的字符串，同时判断是否不是之前已经检测过的8个指定字符串
		 */
		} else if(LanguageInformation.keyWordNameMap.containsKey(str) 
				&& !LanguageInformation.keyWord_variablesMap.containsKey(str)){
			//如果是：则增加次数
			int index = LanguageInformation.keyWordNameMap.get(str);
			//根据位置对其进行加1操作
			this.keyWordCountList.set(index, this.keyWordCountList.get(index) + 1);
		} else {
			/*
			 * 不是需要统计的字符串
			 */
			
		}
	}
	
	/*
	 * 根据大括号的层级关系来判断关键字的出现次数是否应该增加1
	 */
	protected boolean needAddOrNot(String str) {
		//判断接受的字符串是否是系统变量申明的关键字
		if(LanguageInformation.keyWord_variablesMap.containsKey(str)){
			//获得接受的字符串的位置
			int index = LanguageInformation.keyWord_variablesMap.get(str);
			//如果其是系统变量申明的关键字，则再次判断是否应该进行加1操作
			if(0 == this.variableList.get(index)){
				//将对应的位置替换成1，说明已经开始进行计数操作，在一个大括号的层级关系内
				this.variableList.set(index, 1);
				return true;
			} else {
				//此时说明是1，即已近增加过一次，无需增加第二次
				return false;
			}
		//说明其中不包含系统变量申明的关键字
		} else {
			return false;	
		}
	}
	
	/*
	 * 依旧是大括号层级判断方法，不过是二次判断
	 * 针对某些代码的特殊情况，进行判断大括号否在处理过后的字符串中
	 * 接受等待处理的字符进行（二次）处理，返回一个数组，便于关键字统计
	 */
	private String[] BracketsClass2(String str){
		//判断提交进来的字符串是否包含大括号在其中,在字符串中如果没有找到则返回值为-1
		if(str.indexOf("{") == -1 && str.indexOf("}") == -1){
			return null;
		//接受的字符串中包含左大括号
		} else if(str.indexOf("{") != -1 || str.indexOf("}") != -1){
			Pattern pattern = Pattern.compile("\\{|\\}");
			//创建临时变量，主要用于替换的大括号数目
			String s; 
			s = str.replace("{", "");
			//根据替换的大括号数目，对大括号层级标志位进行加减操作
			for(int i = 0; i < str.length() - s.length(); i++){
				this.enterBrackets++;
			}
			//理由同上，只不过此处是对右大括号进行自减操作，退出N层大括号
			s = str.replace("}", "");
			for(int i = 0; i < str.length() - s.length(); i++){
				this.enterBrackets--;
			}
			
//System.out.println("处理过后的字符串："+pattern.split(str));	
			
			return pattern.split(str);
		//下面的语句为了迎合JAVA虚拟机报错机智而设定，理论上只有上面两种情况的出现
		} else {
			System.out.println("警告：系统遇到严重错误，在BracketsClass2方法中！");
			System.exit(0);
		}
		return null;
	}
	/*
	 * 大括号层级检测方法
	 * 为了减少代码的冗余度，抽象出大括号层级检测方法
	 * 为了防止其有多个括号连续，因此用循环
	 * s.length()-temps.length(),大小代表取消取代的括号数目
	 */
	private String BracketsClass(String str){
		//针对复杂的情况，判断是否含有大括号
		if(str.startsWith("{")){
			String temps = str.replace("{", "");
			this.changeBrackets(str.length()-temps.length(), "{");
			// 将大括号去掉
			return str.replace("{", "");
		} else if(str.startsWith("}")){
			String temps = str.replace("}", "");
			this.changeBrackets(str.length()-temps.length(), "}");
			// 将大括号去掉
			return str.replace("}", "");
		}
		//判断理由及其条件同上
		if(str.endsWith("{")){
			String temps = str.replace("{", "");
			this.changeBrackets(str.length()-temps.length(), "{");
			// 将大括号去掉
			return str.replace("{", "");
		} else if(str.endsWith("}")){
			String temps = str.replace("}", "");
			this.changeBrackets(str.length()-temps.length(), "}");
			// 将大括号去掉
			return str.replace("}", "");
		}
		/*
		 * 都是一些大神啊，有些同学把大括号嵌入在字符串里面eg: do{printf / do{if
		 * 此处不作处理，如果在上一级方法，即codeKeyWordCount中额外的方法，进行检测
		 */
		return str;
	}
	
	/*
	 * 改变大括号层级标志位的方法
	 * 减少代码冗余，从大括号层级检测方法中抽出改变大括号层级标志位的方法
	 * count实际达标大括号的出现次数，str表示未处理的字符串
	 */
	private void changeBrackets(int count,String str){
		//判断是否与大括号匹配
		if(str.equals("{")){
			for(int i = 0; i < count;i++){
				// 进入大括号一层，标志位需要加1
				this.enterBrackets++;	
			}
		}
		if(str.equals("}")){
			for(int i = 0; i < count;i++){
				// 进入大括号一层，标志位需要加1
				this.enterBrackets--;	
			}
		}
	}

	/*
	 * 字符串分割
	 * 分割传入的字符串，并且返回一个处理过的ArrayList数组
	 * 即去掉注释与一些空白行或是无用的头文件等
	 * 
	 * 注意：同时先判断在语句之后是否有注释
	 * 
	 * 对于或行注释问题的解决：
	 * 	设置一个全局变量，初始值为false，如果进入一层注释则相应的值设置为true，退出后则置为false
	 * 
	 * 发现问题：有些注释是跟在大括号之后的
	 */
	boolean enterNotes = false;
	private List<String> segmentString(String str) {
		//用于存放有用的字符串，即处理后的字符串，之后提交给统计部分进行统计
		List<String> strList = new ArrayList<String>();
		//去掉开头和结尾的空白符
		str = str.trim();
		//去掉数字
		//str = str.replaceAll("\\d", "");
		//去掉空白行，同时去掉#include开头的行，引入的相关文件不做处理，直接删除
		if(str.matches("^[\\s&&[^\\n]]*$") || str.matches("^#include.*")){
			return null;
		}
		
//System.out.println("去掉空白字符之前："+str);	
		
		//经过前面的去除空白字符，现在可以直接判断是不是注释代码了，针对单行注释 || (str.startsWith("/*") && str.endsWith("*/")) 
		if( str.startsWith("//")){
			return null;
		}
		
		/*
		 * 针对个别同学将注释跟在大括号后面，导致没有检测出来，此处再次判断一次
		 * 在最初是的字符串就需要判断是否存在后置注释
		 * 注意：此时解决的是单行注释问题且为两个星号的注释
		 */
		str = this.hasComments(str);
		
		//分隔符：  : , * ; ( ) { } & | 空白符 (\\{|\\}|)
		Pattern pattern = Pattern.compile(regex);
		//临时存放string的数组，处理后存入List中
		String[] tempStr = pattern.split(str);

//System.out.println("大括号层级关系：   "+this.enterBrackets);		
//System.out.print("分割之后的字符串数组：");
//for(int i = 0; i < tempStr.length; i++) {
//	System.out.print(tempStr[i]+"  ");
//}
//System.out.println("");

		/*
		 * 判断是否为空，如果为空，直接去掉，不存入数组中
		 * 此时还可以用于判断是否为注释，无论单行注释还是多行注释
		 * 
		 * 注意:Only including +-* / and non-negative Real Numbers
		 * 根据上面的例子，发现有同学在printf语句中写出这样子的语句导致系统认为是注释
		 * 
		 * 解决办法：直接屏蔽，提示用户指定行出现问题，不要将其连在一起。
		 * 返回一个错误代码，其中为为错误代码的行数
		 * 
		 * 注意：//入口和出口设置* /
		 * 上述为某个同学的注释方式，在对其进行debug的时候发现，竟然还有这么注释
		 * 解决办法：同上，直接报错，不能这么不按规则出牌啊，直接报错行数
		 * 
		 */
		for(int i = 0; i < tempStr.length; i++){
			/*
			 * 如果比较结果相等，说明再次碰到
			 * 注意1：for(i=0;i<=(L->length-2)/2;i++){
			 * 上述的为特殊例子，其中将数字分割后发现就是除号，导致系统认为其之后的为代码注释
			 * 直接返回null
			 * 解决方案：将其改为双斜杠//
			 * 注意2：/*	for(i=0;i<=(L->length-2)/2;i++)* /
			 * 上述为特殊例子，分割后仅剩一个斜杠/，导致无法检测是否为多行注释
			 * 解决方案：增加：|| tempStr[i].equals("/*")
			 * 			tempStr[i].equals("* /") 初步判定无效
			 * 应该改为 startwith 和 endswith 
			 * 注意3：for (i = 1; i <= poly_size; ++i) {  // Generator polynomial
			 * 之后的注释代码为单行注释，不应该改变注释标志位
			 * 解决方案：单独判断，然后直接break
			 */
			if(tempStr[i].equals("//")){
				break;
			}
			if(tempStr[i].startsWith("/*") || tempStr[i].endsWith("*/")) {
				this.enterNotes = !this.enterNotes;
				//此时利用continue跳过此符号，避免将其加入数组中
				continue;
			}
			//如果注释标志位不为true说明可能是有效字符，而非注释
			if(false == this.enterNotes){
				//最后判断是否非空，如果不为空则加入进字符串数组
				if(!tempStr[i].equals("")){
					strList.add(tempStr[i]);
				}				
			}
		}

		/*
		 * 此处本来是显示打印信息，打印出处理后的字符串
		 * 
		 * 现在将其遍历，再将一次处理，即处理的一行结果存放在Map中
		 * 同时，Key值为此value对应的实际文本的位置
		 */
		Iterator<String> it = strList.iterator();
		String s = "";
		while(it.hasNext()){
			s += it.next();
			s += "  ";
		}
		/*
		 * 根据比较需要，如果是空白行，则去掉
		 */
		if(s.matches("^[\\s&&[^\\n]]*$")){
			return strList;
		} else {
			programeInfo.put(lineCount, s);
			return strList;
		}
	}
	//用于记录读取文件的第几行了，次变量就在segmentStr方法处利用，并且存入Map中
	int lineCount = 0;
	/*
	 * 判断语句之后是否有注释，同时去除注释
	 * 注意：此时处理的情况是一段不跨行的注释
	 */
	private String hasComments(String str) {
		/*
		 * 判断一个完整的语句之中是否有一段注释
		 * 需要同时找到两个注释标志才说明是一段注释，否则可能是多段注释
		 */
		if(-1 != str.indexOf("/*") && (-1 != str.indexOf("*/"))){
			str = str.substring(0,str.indexOf("/*"));
		}
		return str;
	}

	/*
	 * 初始化用户提交的源代码信息
	 */
	protected void initLanguageInformation() {
		// 为其初始化32次，分别对应32个关键字
		initKeyWordNumber();
		// 为其初始化8此，分别对应大括号层级关系内的值是否应该增加
		initSysVariables();
	}
	
	/*
	 * 初始化32个关键字
	 */
	private void initKeyWordNumber() {
		for (int i = 0; i < LanguageInformation.KEY_WORD_NUMBER; i++) {
			this.keyWordCountList.add(i, 0);
		}
	}
	
	/*
	 * 初始化8个系统申明的变量
	 */
	private void initSysVariables() {
		for (int i = 0; i < LanguageInformation.SYS_VARIABLES; i++) {
			this.variableList.add(i, 0);
		}
	}
	
	/*
	 * 设置学生的姓名
	 */
	public void setNameOfStudent(String nameOfStudent) {
		this.nameOfStudent = nameOfStudent;
	}	
	
	/*
	 * 获得学生姓名
	 */
	public String getNameOfStudent() {
		return nameOfStudent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString() 
	 * 这个是为了方便输出显示效果,直接输出关键字统计结果
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.nameOfStudent + "\n" +this.keyWordCountList.toString();
	}

	/*
	 * 构造方法：每当用户提交源代码的时候应该初始化源代码信息
	 */
	public SourcePrograme() {
		// TODO Auto-generated constructor stub
		//源代码信息初始化
		initLanguageInformation();
	}

}

