function [R]=Householder(A)
    [tamLinha,tamColuna]=size(A)
    for k=1 : tamColuna
        x=A(k:tamLinha, k: tamColuna)
        disp X
        disp (x)
        e1=eye(1,tamLinha-k+1)'
        disp E1
        disp (e1)
        sinal=sign(x(1,1))
        disp UK
        uk=x(:,1) - sinal(1,1)*norm(x)*e1
        disp (uk)
        disp normalizando
        uk=uk/norm(uk)
        disp (uk)
        A(k:tamLinha, k:tamColuna)=A(k:tamLinha, k:tamColuna)-2*uk*uk'*A(k:tamLinha, k:tamColuna)
        disp A
        disp (A)
    end

endfunction