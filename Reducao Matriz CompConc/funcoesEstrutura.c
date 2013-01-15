#include <stdio.h>
#include "header.h"
void escreveResultado(RESULTADO r)
{
	printf("Menor %d\nMaior %d\nSoma %ld\n", r.menor, r.maior, r.soma);
}
RESULTADO operacao(RESULTADO r1, RESULTADO r2)
//operacao básica que é realizada entre as estruturas
{
	RESULTADO novo;
	novo.soma = r1.soma+r2.soma;
	
	if(r1.maior>r2.maior)
	{
		novo.maior=r1.maior;
	}
	else
	{
		novo.maior=r2.maior;
	}
	
	if(r1.menor<r2.menor)
	{
		novo.menor=r1.menor;
	}
	else
	{
		novo.menor=r2.menor;
	}
	
	return novo;
}
RESULTADO calculaArea(int** matriz, int pivoX, int pivoY, int tamanho)
//Dada uma area quadrada da matriz m, essa funcao retorna uma estrutura com os resultados obtidos
{
	int i,j;
	RESULTADO final;
	RESULTADO aux;
	
	final.soma=0;
	final.maior=final.menor=matriz[pivoX][pivoY];
	
	for (i=pivoX; i<tamanho+pivoX; i++)
	{
		for (j=pivoY; j<tamanho+pivoY; j++)
		{
		
			aux.maior=aux.menor=aux.soma=matriz[i][j];
			final=operacao(final, aux);
		}
	}
	
	return final;
}
