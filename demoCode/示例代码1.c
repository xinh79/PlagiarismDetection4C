#include<stdio.h>
#include<stdlib.h>

typedef struct{
    int *elem;/*顺序表的存储空间基址*/
    int length;/*长度*/
    int listsize;/*存储容量*/
}SQList;

int ListSize;
void Delete_Repeat(SQList *L);/*删除重复项*/
void Inverse(SQList *L);/*逆置*/
void Show(SQList *L);/*输出显示*/
void Init(SQList *L);/*键入顺序表*/
int main(){
    SQList l;
    Init(&l);
    Delete_Repeat(&l);
    Inverse(&l);
    getch();
    return 0;

}/*主函数*/

void Inverse(SQList *L){/*逆置后输出*/
    int i = 0;
    int j = L->length-1;
    int *p = L->elem;
    int temp;
    for(i = 0,j = L->length-1;i<j;i++,j--){
       temp = p[i];
       p[i] = p[j];
       p[j] = temp;
   }
   printf("after INVERSE  ");
   Show(L);
}
void Delete_Repeat(SQList *L){/*删除重复项*/
    int i = 1;
    int *p = L->elem;
    int *q;
    int *t ,*r;
    int k = 0;
    int j;
    int g = 0;
    int h;
    while(i<=L->length){
            t = p+1;
        if(*p == *(t)&&*t != NULL){
            q = p;
            k = 0;
            r = q+1;
            while(*q==*r)
            {
                k++;
                r++;
            }
            for(j = k+i-1;j<=L->length-1;j++)
            {
                L->elem[j-k] = L->elem[j];

            }
            L->length = L->length - k;
        g = L->length;
        for(h = 0 ;h<k;h++)
            {L->elem[g] = NULL;
            g += 1;}
        }

        p++;
        i++;
    }
    printf("after DELETE  ");
    Show(L);
}
void Init(SQList *L){/*键入顺序表*/
    int i = 0;

    printf("the number you want to input:\n");
    scanf("%d",&ListSize);
    *L->elem =(SQList*)malloc(ListSize*sizeof(SQList));

    printf("please input %d number(s)\n",ListSize);
    for(i=0;i<ListSize;i++){
        scanf("%d",&L->elem[i]);
    }

    L->length = ListSize;
    Show(L);
}
void Show(SQList *L){ /*输出*/
    int i = 0;
    printf("the list :\n");
    for(i=0;i<L->length;i++)
        printf("%d  ",L->elem[i]);
    printf("\n");
}
