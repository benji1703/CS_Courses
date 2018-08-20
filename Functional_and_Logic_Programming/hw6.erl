% Benjamin Arbibe
% 323795633
-module(hw6).
-compile(export_all).

% Ex 1
take(_, [])			-> [];
take(0, _)			-> [];
take(N, [Hd | Tl])	-> [Hd] ++ take(N-1, Tl).

% Ex 2
applyList(_, [])	-> [];
applyList([], _)	-> [];
applyList([FsHd | FsTl], [XsHd | XsTl]) 
					-> [FsHd(XsHd)] ++ applyList(FsTl, XsTl).

% Ex 3
% 1)
sumTreeSeq({leaf, X}) -> X;
sumTreeSeq({node, L, R}) -> sumTreeSeq(L) + sumTreeSeq(R).

% 2)
sumTreePar({leaf, X}) 		-> X;
sumTreePar({node, L, R})	->
	spawn(hw6, sumProcess, [L, self()]),
	spawn(hw6, sumProcess, [R, self()]),
		receive X 		->
			receive Y 	-> X + Y
			end
		end.

sumProcess(Xs, Caller_PID) 	-> Caller_PID ! sumTreePar(Xs).

% Ex 4
createAccount() -> 
	io:format("The balance is: ~p~n", [0]),
	spawn(hw6, handleOperation, [0]).

handleOperation(MoneyInAcc) ->
	receive
		{deposit, N} -> io:format("The balance is: ~p~n", [MoneyInAcc + N]),
		handleOperation(MoneyInAcc + N);
		
		{withdraw, N} -> io:format("The balance is: ~p~n", [MoneyInAcc - N]),
		handleOperation(MoneyInAcc - N)
	end.