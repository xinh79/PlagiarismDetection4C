#include<stdio.h>
#include<stdlib.h>
typedef struct{
    int *elem;
    int length;
    int listsize;
}SQList;





int ListSize;
void Delete_Repeat(SQList *L);
void Inverse(SQList *L);
void Show(SQList *L);
void Init(SQList *L);



int main(){

    SQList l;
    Init(&l);
    Delete_Repeat(&l);
    Inverse(&l);
    return 0;

}

void Inverse(SQList *L){
    int i = 0;
    int j = L->length-1;
    int *p = L->elem;
   int temp;
   for(i,j;i<j;i++,j--){
    temp = p[i];
    p[i] = p[j];
    p[j] = temp;
   }
   printf("after inverse,\n");
   Show(L);
}
void Delete_Repeat(SQList *L){
    int i = 1;
    int count = 0;
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
            while(*q==*r)    /* 探测本次需要删除多少个元素 */
            {
                k++;

                r++;

            }

               count += k;
            for(j = k+i-1;j<=L->length-1;j++)
            {
                L->elem[j-k] = L->elem[j];


            }



            L->length = L->length - k;
        g = L->length;
        for(h = 0 ;h<k;h++)           /*表尾置空*/
            {L->elem[g] = NULL;
            g += 1;}
        }

        p++;
        i++;
    }
    printf("after deleted repeat\n");
    Show(L);
    printf("count is :%d\n",count);
}
void Init(SQList *L){
    int i = 0;

    printf("input the number of list you  want:\n");
    scanf("%d",&ListSize);
    L->elem = (SQList*)malloc(ListSize*sizeof(int));
    printf("input %d integer:\n",ListSize);
    for(i;i<ListSize;i++)
        scanf("%d",&L->elem[i]);
        L->length = ListSize;
    Show(L);
}
void Show(SQList *L){
    int i = 0;
    printf("your list is :\n");
    for(i;i<L->length;i++)
        printf("  %d",L->elem[i]);
    printf("\n");
    getchar();
}
