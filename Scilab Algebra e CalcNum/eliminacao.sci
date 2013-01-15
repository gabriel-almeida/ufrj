function [A]=eliminacao(A)
       [m,n]=size(A)
       k=min(m-1,n-1)
       for j=1:k
           
           //pivoteamento parcial
           if A(j,j)==0 then
               for i=j+1:m
                   //verifico se esse pivo serve
                   if A(i,j)~=0 then
                       temp=A(j,:)
                       A(j,:)=A(i,:)
                       A(i,:)=temp
                       //forço a saida do loop
                       break
                   end
               end
           end
           //fim pivoteamento
           
           for i=j+1:m
               c= -A(i,j)/A(j,j)
               A(i,:) = A(i,:) + c*A(j,:)
           end
       end
endfunction

function [R]=subsreversa(A)
        [quantLinhas, quantColunas]=size(A)
        //R e um vetor coluna com os resultados da substituicao
        R=zeros(quantLinhas,1)
        for i=quantLinhas:-1:1
            resultadoAtu=A(i, quantColunas)/A(i,i)
            R(i,1)=resultadoAtu
            //ja somo esse resultado aos termos independes
            //das outras equacoes, e tambem multiplicando pelo
            //o coeficiente da variavel naquela linha
            for j=i-1:-1:1
                A(j, quantColunas)=A(j, quantColunas) - resultadoAtu* A(j,i)
            end
        end
endfunction
function []=interpola(P)
    [quantPontos,i]=size(P)
    V=zeros(quantPontos, quantPontos)
    for i=1:quantPontos
        V(:,i)=P(:,1)^(i-1)
    end
    S=subsreversa(eliminacao([V, P(:,2)]))
    x=poly(0,'x')
    
    f=0
    for i=1:quantPontos
       f=f+S(i,1)*( x^(i-1) )
    end
    //disp(f)
    plot(P(:,1)', P(:,2)', 'x')
    z=[0:0.1:7]
    plot(z, horner(f,z))
endfunction
function []=aaa()
    P={0,1;1,2;3,9; 4, -10; 5, 9; 6,7; 7, 20}
    interpola(P)
    sleep(1500)
    P={0,1;1,2;3,9; 4, -10; 5, 9; 6,7; 7, 20; 4.5, 1 }
    for i=0:50
        P(8,2)=50-i*1.5
        interpola(P)
        sleep(100)
    end
endfunction