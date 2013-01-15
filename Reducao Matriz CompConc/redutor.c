#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "header.h"
#define QUANT_TESTE 20
#define MAX 200
#define MIN 0
int main(int argc, char **argv)
{
	RESULTADO r,r1;
	unsigned int t;
	int tamanho;
	int** matriz;
	
	if (argc<2)
	{
		printf("Use: ./redutor <opcao>\nOpcoes:(1) Sequencial (2) Concorrente\n");
		return 0;
	}
	if (argv[1][0]=='1') 
	{
		matriz=entradaMatriz(&tamanho);
		comecaContagem();
		r =calculaArea(matriz,0,0,tamanho);
		t=paraContagem();
		escreveResultado(r);
		printf("A versão sequencial demorou %u microsegundos para essa matriz %dx%d\n", t, tamanho,tamanho);
	}
	else if (argv[1][0]=='2')
	{
		matriz=entradaMatriz(&tamanho);
		comecaContagem();
		r =mainConc(matriz,tamanho);
		t=paraContagem();
		escreveResultado(r);
		printf("A versão concorrente demorou %u microsegundos para essa matriz %dx%d\n", t, tamanho,tamanho);
	}
	else
	{
		printf("Invalido\n");
	}
	return 0;
}
