#define QUADRANTE 8
typedef struct _RESULTADO
{
	long soma;
	int maior;
	int menor;
}RESULTADO;

//prototipos das funcoes usando a estrutura RESULTADO

//operaçao basica entre essas estruturas
RESULTADO operacao(RESULTADO r1, RESULTADO r2);
//calcula uma area quadrada da matriz usando a operacao básica
RESULTADO calculaArea(int** matriz, int pivoX, int pivoY, int tamanho );
void escreveResultado(RESULTADO r);

//protótipo da função que modera a entrada dos dados para a matriz
//ele recebe uma variavel inteira por referencia para retornar também o tamanho da matriz recebida
int** entradaMatriz(int* tamanho);

//funções para medida do tempo
void comecaContagem();
unsigned int paraContagem();

//funcao que calcula os resultados usando a implementação concorrente
RESULTADO mainConc(int** m, int tam);
