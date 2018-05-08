function [x,fval,exitflag,output,lambda,grad,hessian] = start()
    %% This is an auto generated MATLAB file from Optimization Tool.
    rng(0,'twister');

    %% Start with the default options
    options = optimoptions('fmincon');
    %% Modify options setting
    %%options = optimoptions(options,'Display', 'iter-detailed');
    options = optimoptions(options,'Algorithm', 'interior-point');
    options = optimoptions(options,'PlotFcn', 'optimplotfval');
    n = 3;

    opt = Inf;
    optx = [];

    for j=1:n

        a = 0;
        b = 1;
        x0 = [];
        lb = [];
        ub = [];
        s = 7;
        %%Genrating Random Values for coverage vector.
        for i=1:s    
           lb( i ) = 0;
           ub( i ) = 1;
           x0(i) = (b-a).*rand(1,1) + a;
        end

        fprintf ( 1, ' x1= %10f\n', x0 );
        [x,fval,exitflag,output,lambda,grad,hessian] = ...
        fmincon(@enterFunction,x0,[],[],[],[],lb,ub,@enterconstraint,options);
        optl = enterFunction( x );
        fprintf ( 1, '\n Function Value: %12e\n', optl );
        fprintf ( 1, '\n Coverage Probabilities:   %12e\n', x);
        if opt > optl
            opt = optl;
            optx = x;
        end
    end
    
    fprintf ( 1, '\n Optimal Function Value: %12e\n', opt );
    fprintf ( 1, '\n Optimal Coverage Probabilities:   %12e\n', optx);
    
end