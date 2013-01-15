function [Q,R]=gsc(A)
    [tamLinha,tamColuna]=size(A)
    Q=A
    R=zeros(tamColuna,tamColuna)
    for j=1:tamColuna
        Q(:,j)=A(:,j)
        for i=1:j-1
            R(i,j)=Q(:,i)'*A(:,j)
            Q(:,j)=Q(:,j)-R(i,j)*Q(:,i)
        end
        R(j,j)=norm(Q(:,j))
        Q(:,j)=Q(:,j)/R(j,j)
    end
    disp (Q*R==A)
endfunction
function [Q,R]=gsm(A)
    [tamLinha,tamColuna]=size(A)
    
    v=A
    Q=zeros(tamColuna, tamColuna)
    R=zeros(tamColuna, tamColuna)
    for i=1:tamColuna
        R(i,i)=norm(v(:,i))
        Q(:,i)=v(:,i)/R(i,i)
        for (j=i+1:tamColuna)
            R(i,j)=Q(:,i)'*v(:,j)
            Q(:,j)=Q(:,j)-R(i,j)*Q(:,i)
            v(:,j)=v(:,j)-R(i,j)*Q(:,i)
        end
    end
    disp (Q*R==A)
endfunction

function []=teste()
    e=0.00000000000000000001
    A=[1,1,1;e,0,0;0,e,0;0,0,e]
    [Q,R]=gsm(A)
    disp (Q'*Q)
    [Q,R]=gsc(A)
    disp (Q'*Q)
endfunction
    