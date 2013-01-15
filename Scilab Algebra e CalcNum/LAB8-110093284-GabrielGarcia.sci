function [R,Rt]=cholesky(A)
    [n,m]=size(A)
    R=zeros(A)
    if (n==2) then
        R(1,1)=sqrt(A(1,1))
        R(2,1)=0
        R(1,2)=A(1,2)/R(1,1)
        R(2,2)=sqrt( A(2,2)-R(1,2)^2 )//S²=B-u²
    else
        r=sqrt(A(1,1))
        v=A(2:n,1)
        u=1/r*v
        B=A(2:n,2:n)
        R(1,1)=r
        R(1,2:n)=u'
        R(2:n,2:n)=cholesky(B-u*u')
    end
    Rt=R'
endfunction