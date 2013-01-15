#include <stdio.h>
#include <stdlib.h>
#define MAX 100
#define MIN 0
int main(int argc, char **argv)
{
	int soma, tamanho, i,j, x=0;
	srand(time(NULL));
	scanf("%d", &tamanho);
	printf("%d\n", tamanho);
	for (i=0; i<tamanho;i++)
	{
		for (j=0;j<tamanho; j++)
		{
			x=MIN + rand() % (MAX + 1);
			printf("%d ", x);
			soma+=x;
		}
		printf("\n");
	}
	//printf("soma=%d\n", soma);
	return 0;
}
