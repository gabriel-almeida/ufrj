function []=desenhaHipercubo(v1,v2)
    H=[0,0,0,0;0,0,0,1;0,0,1,0;0,0,1,1;0,1,0,0;0,1,0,1;0,1,1,0;0,1,1,1;1,0,0,0;1,0,0,1;1,0,1,0;1,0,1,1;1,1,0,0;1,1,0,1;1,1,1,0;1,1,1,1]'
    QR=qr([v1,v2])
    u1=QR(:,1)
    u2=QR(:,2)
    P=u1*u1'+u2*u2'
    PH=P*H/4
    a=get("current_axes")
    a.data_bounds=[-0.5,-0.5;0.5,0.5]
    a.isoview="on"
    
    for i = 1:16
        for j = (i+1):16
            if norm(H(:,i) - H(:,j)) == 1 then
                
                xsegs([PH(:,i)'*u1, PH(:,j)'*u1],[PH(:,i)'*u2, PH(:,j)'*u2])
                
            end
        end
        
    end
endfunction
function []=teste()
    a=rand(4,1)
    b=rand(4,1)
    for i=1:1000
        clf()
        a=rand(4,1)
        b=rand(4,1)
        desenhaHipercubo(a+i*b,b-i*a)
        sleep(500)
    end
endfunction