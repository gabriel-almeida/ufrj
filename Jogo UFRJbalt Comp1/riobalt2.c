#include <SDL.h>
#include <SDL_image.h>
#include <SDL_mixer.h>
#include <SDL_ttf.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>

#define SCREEN_W 800
#define SCREEN_H 600

#define GRAVIDADE 1
#define SALTO 20

#define IMAGENS 8
#define ATUALIZACAO 10

typedef struct _FIGURA
{
	SDL_Surface* imagem;
	SDL_Rect coordenadas;
	SDL_Rect origem;
}FIGURA;

typedef struct _TEXTO
{
	TTF_Font* fonte;
	SDL_Surface* imagem;
	SDL_Rect coordenadas;
	SDL_Color cor;
	char mensagem[10];
	int tamanho;
}TEXTO;

/*Variaveis essenciais do SDL*/
SDL_Surface* screen;
Uint32 cor_fundo;
SDL_Event event;

/*Variaveis Globais necessarias para as fases*/
Mix_Music* musica;
SDL_Surface* pivete;
SDL_Rect coordenadas;
SDL_Surface* bancoImagem[IMAGENS];
TEXTO placar;

float velocidadeVertical;
float velocidadeHorizontal;
unsigned int distancia;
int ultimaAtualizacao;
int altura;
int espacamento;

FIGURA plataforma[2];

SDL_Surface* CriaTexto(char* txt, int tam, SDL_Color cor)
{
	/*pega um texto e transforma ele numa superficie para ser impressa na tela*/
	TTF_Font* fonte;
	SDL_Surface* texto;
	fonte = TTF_OpenFont("adler.ttf", tam);
	texto = TTF_RenderText_Solid(fonte, txt, cor);
	return texto;
}

void AtualizaPlacar(void)
{
	SDL_FreeSurface(placar.imagem);
	sprintf(placar.mensagem, "%u", distancia);
	placar.imagem = CriaTexto(placar.mensagem,placar.tamanho,placar.cor);
	placar.coordenadas.x = SCREEN_W - placar.imagem->w-50;
	placar.coordenadas.y = 50;
	placar.coordenadas.w = 0;
	placar.coordenadas.h = 0;
}
int Colide(void)
{
	int diferenca,i;
	for(i=0;i<2;i++)
	{
	/*retorna 1 se houve colisao vertical, porem tambem trabalha com colisoes horizontais*/
		if ((coordenadas.y+coordenadas.h>=plataforma[i].coordenadas.y) && (coordenadas.x<=plataforma[i].coordenadas.x+plataforma[i].coordenadas.w) && (coordenadas.x+coordenadas.w>=plataforma[i].coordenadas.x)) 
		{
			/*sei que estou abaixo de uma plataforma, agora verifico se estou no chao, com uma margem de erro*/
			if ((coordenadas.y+coordenadas.h>=plataforma[i].coordenadas.y) && (coordenadas.y+coordenadas.h <= plataforma[i].coordenadas.y+30))
			{
				/*trava a figura em cima da plataforma que ela colidiu*/
				diferenca=coordenadas.y+coordenadas.h-plataforma[i].coordenadas.y;
				plataforma[0].coordenadas.y+=diferenca;
				plataforma[1].coordenadas.y+=diferenca;
				velocidadeVertical=0;
				return 1;
			}
			else 
			{
				/*esse eh pro caso de ter batido fora do chao. Gera um bug interessante que reduz a velocidade horizontal quando o personagem bate bem na quina*/
				velocidadeHorizontal=0;
				plataforma[i].coordenadas.x=coordenadas.x+coordenadas.w;
			}
		}
	}
	return 0;
}

void Movimentacao(int tempo)
{
	/*ignora a variavel tempo, por enquanto*/
	/*faz toda a parte de deslocamento do cenario*/
	if (!Colide())
	{
		/*Trabalha queda essa parte*/
		velocidadeVertical+=GRAVIDADE*tempo;
		
		plataforma[0].coordenadas.y-=velocidadeVertical*tempo;
		plataforma[1].coordenadas.y=plataforma[0].coordenadas.y + altura;

	}
	else
	{
		velocidadeHorizontal+=0.007*tempo;/*acelera se estiver no solo*/
	}
	
	/*Calcula a distancia percorida*/
	distancia+=velocidadeHorizontal*tempo;
	
	/*desloca as plataformas ou reduz seus tamanhos se suas coordenadas X forem zero*/
	if ((plataforma[0].coordenadas.x<=0) && (plataforma[0].origem.x<plataforma[0].origem.w))
	{
		plataforma[0].origem.x+=velocidadeHorizontal*tempo;
	}
	else
	{
		plataforma[0].coordenadas.x-=velocidadeHorizontal*tempo;
	}
	/*desloca a plataforma 1*/
	plataforma[1].coordenadas.x=plataforma[0].coordenadas.x+plataforma[0].coordenadas.w + espacamento;
	/*ultimaAtualizacao=SDL_GetTicks();*/
}
void Cenario(void)
{
	/*Carrega o cenario*/
	SDL_FreeSurface(screen);
	SDL_FillRect(screen,NULL,cor_fundo);
	SDL_BlitSurface(pivete, NULL, screen, &coordenadas);
	SDL_BlitSurface(plataforma[0].imagem,&plataforma[0].origem,screen, &plataforma[0].coordenadas);
	SDL_BlitSurface(plataforma[1].imagem,&plataforma[1].origem,screen, &plataforma[1].coordenadas);
	/*Carrega HUD*/
	SDL_BlitSurface(placar.imagem, NULL, screen, &placar.coordenadas);
}
int inicializaJogo(void){
	srand(time(NULL));
	velocidadeVertical=2.0;
	velocidadeHorizontal=5.0;
	distancia=0;
	
	SDL_FreeSurface(screen);
	/*Por enquanto é inutil
	ultimaAtualizacao=SDL_GetTicks();*/
	
	/*ja começa no pior caso pra testa*/
	altura=SALTO*SALTO/-2*GRAVIDADE;
	espacamento=velocidadeHorizontal*30;
	
	/*carrega cor e o tamanho do placar*/
	placar.tamanho=30;
	placar.cor.r= 255;
	placar.cor.g= 200;
	placar.cor.b= 0;
	
	/*Imagens disponiveis para as plataformas*/
	bancoImagem[0]=IMG_Load("vermelhinho.png");
	bancoImagem[1]=IMG_Load("bandejao.png");
	bancoImagem[2]=IMG_Load("fau.png");
	bancoImagem[3]=IMG_Load("ccmn.png");
	bancoImagem[4]=IMG_Load("ct.png");
	bancoImagem[5]=IMG_Load("hu.png");
	bancoImagem[6]=IMG_Load("mangue.png");
	bancoImagem[7]=IMG_Load("petrobras.png");
	
	
		
	/*coordenadas iniciais da primeira plataforma*/
	plataforma[0].imagem=bancoImagem[rand()%IMAGENS];
	plataforma[0].coordenadas.x=100;
	plataforma[0].coordenadas.y=200;
	plataforma[0].coordenadas.w=0;
	plataforma[0].coordenadas.h=0;
	plataforma[0].origem.x=0;
	plataforma[0].origem.y=0;
	plataforma[0].origem.w=plataforma[0].imagem->w;
	plataforma[0].origem.h=plataforma[0].imagem->h;
	
	/*coordenadas iniciais do "pivete", baseado na posicao da plataforma 0*/
	pivete=IMG_Load("img.png");
	coordenadas.w=pivete->w;
	coordenadas.h=pivete->h;
	coordenadas.x=plataforma[0].coordenadas.x-coordenadas.w;
	coordenadas.y=plataforma[0].coordenadas.y-coordenadas.h;
	SDL_BlitSurface(pivete,NULL,screen, &coordenadas);
	
	/*posicao inicial da plataforma 1, de acordo com os valores de espacamento e altura relativo a plataforma 0*/
	plataforma[1].imagem=bancoImagem[rand()%5];
	plataforma[1].coordenadas.x=plataforma[0].coordenadas.x+plataforma[0].origem.w+espacamento;
	plataforma[1].coordenadas.y=plataforma[0].coordenadas.y+altura;
	plataforma[1].coordenadas.w=0;
	plataforma[1].coordenadas.h=0;
	plataforma[1].origem.x=0;
	plataforma[1].origem.y=0;
	plataforma[1].origem.w=plataforma[1].imagem->w;
	plataforma[1].origem.h=plataforma[1].imagem->h;
	
	return 0;
}
int EmcimaFigura(FIGURA fig)
{
	/*usando as coordenadas do parametro, essa funcao retorna 1 se o mouse esta em cima da figura e 0 caso contrario*/
	if (event.button.x>=fig.coordenadas.x && event.button.x<=fig.coordenadas.x+fig.imagem->w && event.button.y>=fig.coordenadas.y && event.button.y<=fig.coordenadas.y+fig.imagem->h) return 1;
	return 0;
}
int Menu (void)
{
/*essa funcao coloca o menu inicial na tela e retorna 1 se for para o programa fechar*/
	int sai=0;
	FIGURA comecar, cenario, sair;
	SDL_Color cor={200,65,55};
	comecar.imagem=CriaTexto("CORRER!!", 70, cor);
	comecar.coordenadas.x=100;
	comecar.coordenadas.y=200;
	comecar.coordenadas.w=0;
	comecar.coordenadas.h=0;
	
	sair.imagem=CriaTexto("SAIR", 70, cor);
	sair.coordenadas.x=300;
	sair.coordenadas.y=400;
	sair.coordenadas.w=0;
	sair.coordenadas.h=0;
	
	cenario.imagem=IMG_Load("abertura.png");
	cenario.coordenadas.x=0;
	cenario.coordenadas.y=0;
	cenario.coordenadas.w=0;
	cenario.coordenadas.h=0;
	
	SDL_FreeSurface(screen);
	SDL_BlitSurface(cenario.imagem,NULL,screen, &cenario.coordenadas);
	SDL_BlitSurface(comecar.imagem,NULL,screen, &comecar.coordenadas);
	SDL_BlitSurface(sair.imagem,NULL,screen, &sair.coordenadas);
	SDL_BlitSurface(placar.imagem,NULL,screen, &placar.coordenadas);
	
	SDL_Flip(screen);
	
	while(!sai)
	{
		while (SDL_PollEvent(&event))
		{
			if (event.type==SDL_QUIT) return 1;
			if (event.type==SDL_KEYDOWN)
			{
				if(event.key.keysym.sym==SDLK_RETURN)
				{
					sai=1;
					break;
				}
			}
			if (event.type==SDL_MOUSEBUTTONDOWN)
			{
				if(EmcimaFigura(comecar))
				{
					inicializaJogo(); 
					sai=1;
				}
				if(EmcimaFigura(sair)) return 1;
			}
		}
	}
	SDL_FreeSurface(comecar.imagem);
	SDL_FreeSurface(sair.imagem);
	SDL_FreeSurface(cenario.imagem);
	return 0;
}
int Pause(void)
{
	int sai=0;
	
	/*parte do codigo responsavel por escreve PAUSE na tela*/
	SDL_Color cor={255,0,0};
	SDL_Surface* imagemPause= CriaTexto("PAUSE", 100, cor);
	SDL_Rect posicaoPause={SCREEN_W/2-imagemPause->w/2,SCREEN_H/2-imagemPause->h/2,0,0};
	SDL_BlitSurface(imagemPause, NULL, screen, &posicaoPause);
	SDL_Flip(screen);
	
	while (!sai)
	{
		while (SDL_PollEvent(&event))
		{
			if (event.type==SDL_QUIT) return 1;
			if (event.type==SDL_KEYDOWN)
			{
				if(event.key.keysym.sym==SDLK_RETURN)
				{
					sai=1;
					break;
				}
			}
		}
	}
	SDL_FreeSurface(imagemPause);
	return 0;
}
void GameOver(void)
{
	
}
void ReciclaPlataforma(void)
{
	/*segredo de todo o jogo e seus bugs, as 2 plataformas sao modificadas nessa funçao*/
	plataforma[0]=plataforma[1];
	
	/*pelas minhas contas, essa eh a altura maxima que o objeto alcança, a distancia foi chutando valores mesmo...*/
	altura= pow(-1, rand() % 2) * (rand() % SALTO*SALTO/2*GRAVIDADE);
	espacamento= velocidadeHorizontal*10 + rand() % (int)velocidadeHorizontal*20;
	
	/*sorteia uma imagem das disponiveis*/
	plataforma[1].imagem=bancoImagem[rand()%IMAGENS];
	
	/*posiciona a imagem seguinte de acordo com os valores sorteados*/
	plataforma[1].coordenadas.x=plataforma[0].coordenadas.x+plataforma[0].coordenadas.w +espacamento;
	plataforma[1].coordenadas.y=plataforma[0].coordenadas.y + altura;
	plataforma[1].coordenadas.w=0;
	plataforma[1].coordenadas.h=0;
	plataforma[1].origem.x=0;
	plataforma[1].origem.y=0;
	plataforma[1].origem.w=plataforma[1].imagem->w;
	plataforma[1].origem.h=plataforma[1].imagem->h;
}
int main (int argc, char** argv)
{
	int quit=0,onMenu=1;
	
	/*inicializa video e audio*/
	SDL_Init(SDL_INIT_VIDEO | SDL_INIT_AUDIO | SDL_INIT_TIMER);
	TTF_Init();
	screen=SDL_SetVideoMode(SCREEN_W, SCREEN_H, 16, SDL_SWSURFACE|SDL_DOUBLEBUF|SDL_HWSURFACE); /*configura janela*/
	Mix_OpenAudio(22050,MIX_DEFAULT_FORMAT , 2, 1024); //inicializar SDL_mixer
	Mix_AllocateChannels(1);
	
	musica = Mix_LoadMUS("music.MP3");
	cor_fundo=SDL_MapRGB(screen->format, 150, 200, 98); /*cor de fundo*/
	
	while (!quit)
	{
		if (onMenu)
		{
			if (Menu()) quit++;
			SDL_FreeSurface(screen);
			onMenu=0;
			Mix_PlayMusic(musica, -1);//tocar a musica
		}
		else
		{
			while(SDL_PollEvent(&event)) /*Fila de eventos*/
			{
				switch(event.type)
				{
					case SDL_QUIT: quit++; break;
					case SDL_KEYDOWN:
						if (event.key.keysym.sym==SDLK_SPACE  && velocidadeVertical==0)
						{	
							velocidadeVertical=-SALTO;
							plataforma[0].coordenadas.y++;
							plataforma[1].coordenadas.y++;
						}
					
						/*Pausa o jogo quando der Enter*/
						if (event.key.keysym.sym==SDLK_RETURN)
						{
							Mix_PauseMusic();
							if (Pause()) quit ++;
							Mix_ResumeMusic();
						}
					
						if (event.key.keysym.sym==SDLK_ESCAPE)
						{
							quit=1;
						}
				}
			}
			AtualizaPlacar();
			SDL_Delay(10);
			Movimentacao(1);
			Cenario();
			if (plataforma[0].origem.x >= plataforma[0].origem.w)
			{
				ReciclaPlataforma();
			}
			if ((plataforma[1].coordenadas.y<=0) && (plataforma[0].coordenadas.y<=0))
			{
				Mix_HaltMusic();
				GameOver();
				onMenu=1;
			}
			SDL_Flip(screen);
		}
	}
	Mix_FreeMusic(musica);
	Mix_CloseAudio();
	TTF_Quit();
	SDL_Quit();
	return 0;
}
