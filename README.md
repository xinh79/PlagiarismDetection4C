
## 程序代码相似度检测方法研究及应用

#### 项目申报的基本思路与目的

根据程序语言的特性，实现一个程序代码相似度检测的软件系统，可以计算对同一个程序设计题目，每个学生提交的代码与其它学生的代码相似度，达到阀值以上即判定为抄袭。使用该系统，可以有效地防止学生抄袭他人的代码，减轻教师人工判定的劳动强度。

程序的相似度检测与一般文本的相似度检测不同的是：文本主要检测在一段文字范围内文本的重复数目，而学生所书写的代码都较为简单，编程语言的关键字和系统函数名、系统提供的类库中的类名占相当大的比例，而这些不应当做为相似度检测的依据，而应当从程序本身的特征入手。

程序的源代码可以视为视为一种连续的标记串（Token String）。通过比较标记串获取相似程度的信息。对学生提交的电子档程序，两两之间进行雷同检测，最终给出相似度计算结果，再根据给定的阈值判定雷同程序是否为抄袭。

#### 项目的科学性、先进性及独特之处

针对程序源代码的特殊性，本项目主要从以下角度入手：

1. 分析一般的学生代码抄袭手法，找出最合适的样本，做为将来分析和检验的依据。
2. 提取程序源代码的结构特征，将程序按照关键字序列、自定义变量序列、自定义函数序列、函数调用关系、括号匹配关系等分别计算相似度，并利用样本确定各种相似度的权重，最后确定整个代码的权重。
3. 假设一个班有30名学生，则需要比较30*29/2=435次，为提高效率，可以充分利用计算机的多核CPU和采用多线程编程，减少运行时间。
4. 采用现有的较好的文本匹配算法：串匹配算法，该算法具有以下特点：1、速度快：字符匹配速度能以线性速度执行2、内存占用少。
5. 本项目完成后，可进一步搭建作业提交网站，将本项目的程序放置在后台，当学生提交程序代码时，自动检测相似度，如超过阀值，则不能提交。

#### 现实意义

程序源代码相似度检测的意义在于：

1. 可应用于学生程序设计作业的抄袭识别。
2. 可应用于程序设计课程考试的主观题自动批阅中。
3. 可应用于商业软件的剽窃检测。

#### 国内外同类研究综述

早在20世纪70年代，就有学者开始研究防止程序抄袭的软件，至今涌现出了一批具有代表性的系统，下面分别综述国外与国内出现的比较典型的程序源代码剽窃检测系统。

许多国外的大学都已建立了自己的计算机程序抄袭检测系统，如：美国斯坦福大学的MOSS系统、德国卡尔斯鲁厄大学JPlag系统、美国威奇塔州立大学的SIM系统、澳大利亚悉尼大学的YAP3系统等。这些系统都是综合利用程序相似度计算技术中的基于属性计数的方法和基于结构度量的方法来作为实现计算机程序抄袭检测系统的关键技术的。国内对计算机程序抄袭检测、对编程题目及主观题目的相关研究也逐步深入。如：中国人民警官大学的张文典和任冬伟开发了一个PASCAL语言程序抄袭检测系统、北京邮电大学采用了基于SML方法的结构度量方法进行相似度的计算，并实现了相应的实验系统。

从国内外的研究现状可以发现，国内对程序相似度判别的研究做的非常少，大部分集中在对中文分词和语义的研究上。程序代码的抄袭跟普通文本抄袭还有不同，不同的代码可能实现同样的功能，有些聪明的抄袭者会使用一些技巧对代码进行修改，比如for循环变成while循环、添加很多中间变量，这样会降低串匹配算法的有效性。

#### 实施计划与步骤

1. 程序代码预处理：对于系统提交的源代码文件，系统必须过滤掉源代码中对相似度检测没有影响的无关信息，加快系统运行效率，比如：注释，头文件，无用字符串，空格和空行等。这个过程是的源代码文件将大大减小，加快后面过程的处理。预处理过程相对来说比较简单，也可以在预处理的扫描过程中统计一些属性方便后面使用，这并不会增加时间复杂度。
2. 程序代码转换：基于机构化度量技术的代码反抄袭系统的主要步骤就是如何将程序代码转换成具有结构信息的新结构。以字符串匹配算法为核心的检测系统对程序做语法分析时，将代码转换成标记字符串序列，方便进行字符串匹配。以图相似算法为核心的检测系统对某语言程序做语法分析时，将代码转换成图节点，并根据控制流和变量依赖关系添加图节点之间的有向边。
3. 相似度检测：使用字符串匹配算法和图相似算法计算的相似度，从而的出比较的两段代码之间的相似度值sim。
4. 抄袭评估：对于计算出的相似度值sim，判断是否抄袭要根据属性计算法的结构度量法相结合的方法来得出最终结果，系统将给出几个不同判断标准的阈值来判断结果，提高准确性。

#### 程序代码转换

通过Java程序的处理后，可以将无效代码去除，处理程序采用的是边读边处理的办法，即读取一行代码后，立即处理一行。过滤后的有效代码可以进入下一阶段，即代码转换阶段，本程序是将分割后的代码存入`ArrayList`中，这样数组中就存放了该源程序的统计结果，之后将存有统计结果的`ArrayList`存入保存`SourcePrograme`信息的`ArrayList`中，其中存放了等待比较的源程序的所有信息（目前是指源程序中出现C语言的32个系统关键字的次数），为了避免无效比较（存入到指定路径下的文件默认已近通过源程序检测，两两之间的阈值均小于指定的阈值0.90，不存在抄袭现象），将首先读取用户上传的文件，处理完成后直接存放到比较数组的首位，之后依次与文件内的代码比较。分割方式：根据C语言其本身的语言特性进行分割，通过指定正则表达式进行分割，指定内容为：:`  ,  ;  (  )  &  |  空格`

源代码的`ArrayList<Interger>`中的信息，根据程序定义的Map（存放32个关键字，及其对应的数组下标）来定位与赋值操作。每当关键字出现则指定位置的数值增加一，依次类推。

#### 相似度检测

本程序采用修正余弦相似度的计算方法。

AdjustedCosineSimilarity：下式表示将用户`u`对物品i的评价值减去用户`u`对所有物品的评价均值，从而得到修正后的评分。`s(i,j)`表示物品`i`和`j`的相似度，分子表示将同时评价过物品`i`和`j`的用户的修正评分相乘并求和，分母则是对所有的物品的修正评分做一些汇总处理。而代码中的关键字则认为是学生对其的评分，基于上式可以得出两个学生对关键字的评分相似度，即暂时认为是两代码之间的相似度。

$$
s(i,j)=\frac{\sum_{_{u\epsilon U}}^{}(R_{u,i}-\bar{R_{u}})(R_{u,j}-\bar{R_{u}})}{\sqrt{(\sum_{_{u\epsilon U}}^{}(R_{u,i}-\bar{R_{u}}))^{2}}\sqrt{(\sum_{_{u\epsilon U}}^{}(R_{u,j}-\bar{R_{u}}))^{2}}}
$$

![acs.png](/acs.png)

修正的余弦相似度是一种基于模型的协同过滤算法。这种算法的优势之一是扩展性好，对于数据量大而言，运算速度快、占用内存少。

#### 成果展示

通过 Java 能够提取出 C 程序代码的关键字，然后对其进行统计，之后根据`修正余弦相似度的计量方法`，计算出这两份源代码之间的相似度；通过 JavaFX 实现了类似于 UltraCompare（UC）的文本比较，**高亮匹配文本**的功能；在基于 Java 所计算出的相似度结果，以中间文件的形式进行保存后， JavaFX 读取出来能够以饼图的形式显示出来，并且能够查看相似组的信息。

#### 创新点

在原有的研究结果上，引入词频（针对 C 语言的关键字，系统库函数等）的统计，在要求精度比 较底，软件反应速度快的情况下，呈现假阳性的概率极低。较现有的 UC 软件来说可以，本软件具有免费，代码简单，针对性强，且反应速度快等特点。

#### 中期检查存在的问题

**程序代码预处理**

在处理注释信息的时候，本程序采用的是层级关系的判断方式，即，单行注释可以直接被处理，而多行注释，相当于进入一层注释，相关变量置为true，在其置为false之前均认为是注释，在对一次数据结构代码进行测试的时候，笔者发现有一部分同学出现了大括号匹配出错，经查证是在`printf`语句中使用了`*/，eg : printf(“Please input +-*/\n”); `作者本意可能是要表示请用户输入加减乘除，结果在匹配字符串的时候，出现错误。
为了满足同学的一些代码编写风格，判断注释的开始与结束时，本程序有多次判断，上述例子就是在判断是否含有`/*`或是`*/`时，匹配到后截取并且开始之后的统计，导致统计结果出错。类似的还有一次同学竟然在注释之中写到：`/*… … //`嵌套的注释` * /`，由于本程序先判断单行注释，因此直接返回`null`，吞并了最后的注释结束标志，导致统计结果出错。

**相似检测**

在Windows上运行时，提交的代码会依次进行比较，只要得到的判定结果超出阈值，则会立即返回，而在Ubuntu上运行时，会将指定文件夹下的所有的代码依次比较完成才会结束。更多的是按照存入文件下的时间来进行一次比较，而不是名称。

由于笔者知识有限，使用的是较为简单的计量方法，在给出的结果的时候，能够清晰判断的仅仅是相似度在95%以上的代码，至于日后的方法选择上，得根据导师意见与笔者的知识量来决定。

在项目进展的同时，笔者也在学习其他相似度计量方法，比如阈值十分相近的Pearson相关系数。

**抄袭评估**

在没有给出统计分析的前提下，暂时使用认为的数值，指定0.90为抄袭的判定阈值。

**下阶段提高的措施与方案**

1. 提高代码检测的范围，即从32个关键字拓展到系统的函数，与自定义的函数，全局变量等。
2. 提高代码检测的精度，即不断的改进相应的算法，以应对不同的代码与抄袭情况。
3. 阈值的设定，其值应该进一步做分析，根据不同的代码抄袭情况来设定，得出较为准确的值。
4. 提高程序运行效率，笔者需要进一步学习多线程的编程，以充分利用系统CPU，提高程序运行效率。

#### 研究结果

用户操作的选择界面（图1）：用户选择操作的主界面，提供两种相似度查看方法，一种以组为 单位显示所选的两份代码的详细比较信息，另一种以班级为单位，显示整个班级的相似度分布信息。 其中可以查看指定范围内的详细分组信息。 代码比较详细的操作界面（图2模仿UltraCompare）：用户输入两份C语言的源代码，点击开始 按钮后即可观察到相应的比较结果：数字代码此代码在源文件中对应的行数；红色字体代表匹配成 功的文本；黑色文本代表匹配失败的文本；“-_-_-”代表对应另一文本匹配失败的插入行。 以图表信息查看班级相似度分布情况（图3） ：用户选择存放有班级代码两两比较后相应结果的 文本，然后点击开始即可观察显示出的相似度分布饼图，在左边工具栏还可以对每个范围内的具体 情况，如总人数、相似度对应组信息，如：demo1,demo2 相似度为0.91。

![project show](/project-show.jpg)

- **实验测试：** 输入代码规模为，有效代码行为120行：

1. 修改注释、空白行：随机增加注释10行，随机穿插在代码中`result = 1.0`比较耗时`72ms`
2. 标识符重命名；对整份代码中12个`int`型变量重命名`result = 1.0`比较耗时`71ms`
3. 代码块重排序；将一个函数体（16行）移动至`main`函数前：`result = 1.0`比较耗时`55ms`
4. 代码块内重排序：将一个代码块内的10行代码打乱顺序；`result = 1.0`比较耗时：`55ms`
5. 改变表达式中的操作符或者操作数顺序，改变数据类型： 21个`int`随机修改其中4个为double：`result = 0.987`比较耗时`46ms`
6. 改变数据类型，21个`int`随机修改其中10个为`double`：`result = 0.895`比较耗时`53ms`
7. 增加冗余的语句或者变量；随机在函数中增加`double`变量5个：`result = 0.993`比较耗时`53ms`
8. 控制结构的等价替换：实例代码中4个`while`循环： 将其中2个替换为`for`循环：`result = 0.967`比较耗时`46ms` 将其中4个替换为for循环：`result = 0.938`比较耗时`42ms`
9. 代码有效行为平均为120的比较耗时： 2份代码的比较耗时：`103ms` 1份代码与32份代码的比较耗时：`217ms`；1份代码与28份代码进行比较耗时：`209ms`

- **创新之处：** 引入词频的统计方法，在精度要求较低，且待判断的两文件存在较高的相似度时，软件反应迅 速，同时呈现假阳性的概率极低对原代码进行10%-0%甚至以上的简单修改软件均能给出高结果。

- **应用价值：** 学生程序设计作业的抄袭识别；应用于程序设计课程考试的主观题自动批阅中。

#### 项目描述

**situation：** 学校鼓励学生申请创新项目，笔者时任班级学习委员，故想通过程序相似度检测的方法减少程序设计课程上源码相互借鉴的现象。

**task：** 由于时间紧迫，但是申请的过程均由笔者一人完成，后续也是由笔者一人完成此项目。

**action：** 首先通过阅读相关研究的文献综述，了解程序源代码相似度检测的一些常用方法，分析其中利弊，选择合适的方法，针对性的进行研究，并在一年的时间内实现。

**result：** 项目在学校成功立项，在最后并顺利交付。遗憾的是并没有完善、投入使用。

----

## 参考文献

[1]. 田振洲, 刘烃, 郑庆华, et al. 软件抄袭检测研究综述[J]. 信息安全学报, 2016, 1(3).

[2]. 熊浩, 晏海华, 郭涛, et al. 代码相似性检测技术:研究综述[J]. 计算机科学, 2010, 37(8):9-14.

[3]. 王飞. 软件源码相似性分析技术及应用系统研究[D]. 北京邮电大学, 2013.

[4]. 赵彦博. 基于抽象语法树的程序代码抄袭检测技术研究[D]. 2011.

[5]. 朱波. 程序代码相似性度量方法研究[D]. 长春工业大学, 2015.

[6]. 邓爱萍. 程序源代码复制检测技术研究[D]. 湖南大学, 2008.

[7]. 吴斐. 基于N-gram的程序代码抄袭检测方法研究[D]. 2012.

[8]. 王冠. Java代码相似性判定方法的研究[D]. 2008.
