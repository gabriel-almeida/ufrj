function [b]=eliminacao(A,b)
       [m,n]=size(A)
       x=zeros(n,1)
       for k=1:n
           //pivoteamento parcial
           if A(k,k)==0 then
               maiorLinha=k
               //percorro toda a coluna J
               for i=k+1:n
                   //verifico se esse elemento A(i,j) é o maior ate agora
                   if abs(A(i,k)) > abs(A(maiorLinha,k)) then
                       maiorLinha=i
                   end
               end
               temp=A(k,:)
               A(k,:)=A(maiorLinha,:)
               A(maiorLinha,:)=temp
           end
           //fim pivoteamento
           
           for i=k+1:n
               m= A(i,k)/A(k,k)
               A(i,k)=0
               b(i,1)=b(i,1)-m*b(k,1)
               for j=k+1:n
                   A(i,j)=a(i,j)-m*a(k,j)
               end
            end
       end
       x(n,1)=n(n,1)/A(n,n)
       for k=n-1:1
           s=0
           for j=k+1:n
               s=s+a(k,j)*x(j,1)
               
           end
           x(k,1)=(b(k,1)-s)/A(k,k)
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
