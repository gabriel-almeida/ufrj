#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include "header.h"

//variáveis que podem ser acessadas por todas as threads mas não geram problemas de concorrência
int **matriz;
RESULTADO *r; //matriz de resultados, onde cada thread acesas apenas a sua posição
int tamMatriz; //tamanho do lado da matriz quadrada
int quantThread;
int quantQuadrante;//quantidade de quadrantes numa direção (ex: uma matriz de tamanho 32x32 terá um total de 9 quadrantes de tamanho 8, mas apenas 3 quadrantes na horizontal)


//Variáveis relacionadas ao funcionamento e sincronização das Threads
pthread_t *tid;
int esperando;
sem_t chegada, partida;

void sincBarreira()
{
	sem_wait(&chegada);
	esperando++;
	if (esperando<quantThread)
	{
		sem_post(&chegada);
	}
	else
	{
		sem_post(&partida);
	}

	sem_wait(&partida);
	esperando--;
	if (esperando>0)
	{
		sem_post(&partida);
	}
	else
	{
		sem_post(&chegada);
	}
}
void* runnable(void* arg)
{
	//como o ID é passado como argumento do tipo ponteiro para void, faço um typecast
	int id= (int) arg;
	int pivoX=( id / quantQuadrante ) * QUADRANTE;
	int pivoY=( id % quantQuadrante ) * QUADRANTE;
	
	int meio = quantThread;
	r[id]=calculaArea(matriz, pivoX, pivoY, QUADRANTE); //armazena o primeiro resultado na posiçao relativa a esta thread na matriz de resultados
	while(meio != 1)
	{
		sincBarreira();
		if ( (meio%2 != 0) && (id == 0) )
		{
			//se tenho um numero impar de threads, o trabalho nao será divido igualmente, então a thread 0 deve normalizar a situação.
			r[0] = operacao(r[0], r[meio-1]);
		}
		
		meio = meio/2;
		if(id < meio)
		{
			//se esta thread estiver na primeira metade, ela ainda deve realizar mais reduçoes
			r[id] = operacao(r[id], r[id+meio]);
		}	
	}

	pthread_exit(NULL);

}
void inicializacaoGlobal()
{
	quantQuadrante = tamMatriz / QUADRANTE;
	//quantidade de threads cresce de modo quadrático de acordo com a quantidade de quadrantes numa direção
	quantThread = quantQuadrante * quantQuadrante;

	tid = (pthread_t *) malloc( sizeof (pthread_t) * quantThread);
	r = (RESULTADO *) malloc( sizeof (RESULTADO) * quantThread);
	
	if (tid==NULL || r ==NULL)
	{
		printf("ERRO: Falha na alocação de memória.\n");
		exit(-1);
	}

	sem_init(&chegada,0,1);
	sem_init(&partida,0,0);
	esperando=0;
}

RESULTADO mainConc(int** m, int tam)
{
	int i;
	unsigned int t;
	if ( (tam%QUADRANTE) != 0)
	{
		printf("Matriz deve ser múltipla de %d.\n", QUADRANTE);
		exit(-1);
	}
	tamMatriz=tam;
	matriz = m;
	
	if (matriz==NULL)
	{
		exit(-1);
	}

	inicializacaoGlobal();

	//dispara as threads
	for (i=0; i<quantThread;i++)
	{	
		//passo o ID como argumento da função que cria threads e checo os erros
		if (pthread_create(&tid[i], NULL, runnable, (void*) i) )
		{
			printf("ERRO: Thread %d não criada\n", i);
		  	exit(-1);
		}
	}
	
	//seguindo a lógica do Redutor, o resultado estará na posição 0 do vetor de resultados e só preciso esperar a thread 0 acabar
	pthread_join(tid[0], NULL);
	
  	return r[0];
	
}
