#include <stdio.h>
#include <stdlib.h>
#include "header.h"

int** entradaMatriz (int *tamanho)
{
	char str [80]; //string auxiliar usada na entrada
	int i,j;
	int **matriz;

	scanf("%s", str);
	
	//como tamanho é passado por referencia, devo usa operacoes com ponteiro
	
	if (sscanf(str, "%d", tamanho)==0)
	{
		printf("ERRO: Entrada inválida\n");
		exit(-1);
	}
	if (*tamanho % QUADRANTE != 0)
	{
		printf("ERRO: Tamanho da matriz deve ser multipla de %d\n", QUADRANTE);
		exit(-1);
	}
	
	//alocaçao dinâmica para a matriz
	matriz = (int**) malloc( ( *tamanho )*sizeof(int*) );
	if (matriz==NULL)
	{
		printf("ERRO: Falha na Alocaçao de memória");
		exit(-1);
	}
		
	for (i=0; i<*tamanho; i++)
	{
		matriz[i]= (int *) malloc( ( *tamanho ) * sizeof(int) );
		if (matriz[i]==NULL)
		{
			printf("ERRO: Falha na Alocaçao de memória");
			exit(-1);
		}
		
	}
	for ( i=0; i< *tamanho; i++)
	{
		for(j=0; j< (*tamanho); j++)
		{
			if (scanf("%d", &matriz[i][j])<1)
			{
				printf("ERRO: Matriz só deve receber numeros\n");
				exit(-1);
			}
		}
	}
	return matriz;
}
