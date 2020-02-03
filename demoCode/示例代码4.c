#include<stdio.h>
#define LIST_INIT_SIZE 100
struct list{
int *data;
int length;
int listsize;
};
typedef struct list sqlist;
int InitList_sqlist(sqlist *);   /*构造一个新的顺序表 */
int ListSert(sqlist *,int,int);  /*插入新的元素*/
int delete_data(sqlist *);        /*删除重复的元素*/
int daoxu(sqlist *);              /*逆置*/
void printlist_sqlist(sqlist);    /* 显示*/
int main()
{
  sqlist la;
  InitList_sqlist(&la);
   ListSert(&la,1,10);
   ListSert(&la,2,9);
   ListSert(&la,3,9);
   ListSert(&la,4,8);
   ListSert(&la,5,7);
   ListSert(&la,6,6);
   ListSert(&la,7,6);
   ListSert(&la,8,5);
    printf("display the list:\n");
    printlist_sqlist(la);

   printf("delete the same data:\n");
   delete_data(&la);
   printlist_sqlist(la);
   printf("contrary the data:\n");

    daoxu(&la);
    printlist_sqlist(la);
     getch();
     return 0;
}
int InitList_sqlist(sqlist *L)
{
  L->data=(int *)malloc(LIST_INIT_SIZE*sizeof(int));
  if(!L->data) exit(0);
  L->length=0;
  L->listsize=LIST_INIT_SIZE;
  return 1;
}
int delete_data(sqlist *L)
{
 int i=1;
 int *p,*q,*l;
 p=&(L->data[i-1]);
 q=p+1;
 for(p;q<=&(L->data[L->length-1]);q++)
 {  if(*p==*q)  {

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

void printlist_sqlist(sqlist L)
{
  int i;
  for(i=0;i<L.length;i++)
  {
    printf("%d ",L.data[i]);
  }
  printf("\n");
}
int ListSert(sqlist *L,int i,int e)
{
  int *p,*q;
  q=&(L->data[i-1]);
  for(p=&L->data[L->length-1];p>q;--p)
  *(p+1)=*p;
  *q=e;
  L->length++;
  return 1;
}
int daoxu(sqlist *L)
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