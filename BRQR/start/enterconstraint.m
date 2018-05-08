function [ c,ceq ] = enterconstraint( x )

    n = length(x);
    budget = 20.00;
%     time = [9 3 4 8 7 6 5 9 3 4 8 7 6 5 19 3 4 18 7 6 5 19 3 4 8 37 6 15]; 
    time = [9.0 3.0 4.0 8.0 7.0 6.0 5.0]; 
    p = 0.0;
    
    for i=1:n
        p = p + x( i ) * time( i );
    end
    
    c = p - budget;
    ceq = [];
    
end

