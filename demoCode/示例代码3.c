#include<stdio.h>                /*头文件*/
#define LIST_INIT_SIZE 100       /*顺序表存储空间的初始分配量*/
struct list{
int *data;                       /*顺序表的基址*/
int length;                      /*当前长度*/
int listsize;                    /*当前分配的存储容量*/
};
typedef struct list sqlist;      /*线形表的顺序存储结构定义*/
int InitList_sqlist(sqlist *);   /*构造一个新的顺序表 */
int ListSert(sqlist *,int,int);  /*插入新的元素*/
int delete_data(sqlist *);       /*删除重复的元素*/
int nixu(sqlist *);              /*逆序*/
void printlist_sqlist(sqlist);   /*输出*/
int main()                       /*定义主函数*/
{
  sqlist la;                     /*顺序表*/
  InitList_sqlist(&la);
   ListSert(&la,1,9);
    ListSert(&la,2,7);
     ListSert(&la,3,6);
      ListSert(&la,4,5);
       ListSert(&la,5,4);
        ListSert(&la,6,4);
         ListSert(&la,7,2);
          ListSert(&la,8,1);
           ListSert(&la,9,1);
            ListSert(&la,10,0);
    printf("display the list:\n");    /*输出显示*/
    printlist_sqlist(la);             /*输出la表*/

   printf("delete the same data:\n"); /*输出显示*/
   delete_data(&la);                  /*删除数据*/
   printlist_sqlist(la);              /*输出la表*/
   printf("contrary the data:\n");    /*输出显示*/

    nixu(&la);
    printlist_sqlist(la);             /*输出逆序表*/
     getch();
     return 0;
}
int InitList_sqlist(sqlist *L)        /*定义一个空顺序表L*/
{
  L->data=(int *)malloc(LIST_INIT_SIZE*sizeof(int));   /*分配内存*/
  if(!L->data) exit(0);                                /*存储分配失败，退出*/
  L->length=0;                                         /*空表长度为0*/
  L->listsize=LIST_INIT_SIZE;                          /*初始存储容量*/
  return 1;
}
int delete_data(sqlist *L)                             /*删除顺序表L中的数*/
{
 int i=1;
 int *p,*q,*l;
 p=&(L->data[i-1]);
 q=p+1;
 for(p;q<=&(L->data[L->length-1]);q++)
 {  if(*p==*q)  {                                      /*若重复*/

  for(p;q<=&(L->data[L->length-1]);q++)
   {

          *p=*q;
          p++;
   }
        p=&(L->data[i-1]); q=p;
   L->length--;
   }
   else {p++; }
   }

}

void printlist_sqlist(sqlist L)                        /*输出顺序表*/
{
  int i;
  for(i=0;i<L.length;i++)
  {
    printf("%d ",L.data[i]);
  }
  printf("\n");
}
int ListSert(sqlist *L,int i,int e)
{                                                      /*插入新的元素*/
  int *p,*q;
  q=&(L->data[i-1]);
  for(p=&L->data[L->length-1];p>q;--p)
  *(p+1)=*p;
  *q=e;
  L->length++;
  return 1;
}
int nixu(sqlist *L)                                    /*逆序*/
{                                                      
   int i=1;
  int *p,*q,*l;
  p=&L->data[i-1];
  q=&L->data[L->length-1];
  if(L->length%2==0)
  for(i=1;i<=(L->length)/2;i++)
  {
    *l=*p;
    *p=*q;

    *q=*l;
    ++p;
    --q;
  }

  else
  for(i=1;i<=(L->length-1)/2;i++)
  {
    *l=*p;
    *p=*q;

    *q=*l;
  }
}
