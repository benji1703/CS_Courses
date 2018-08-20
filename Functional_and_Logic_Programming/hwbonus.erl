% Benjamin Arbibe
% 323795633
-module(hwbonus).
-compile(export_all).

% Using same ideas from recitation

start(N, M) ->
    createProcesses(N, M, []).

createProcesses(1, M, Processes) ->
    PID = spawn (hwbonus, handleMessages, [self()]),
    sendMessages(M, Processes ++ [PID]);
createProcesses(N, M, Processes) -> 
    PID = spawn (hwbonus, handleMessages, [self()]),
    createProcesses(N-1, M, Processes ++ [PID]).

handleMessages(HubPID) ->
    receive
        hub_closing ->
            io:format("~p received closing message.~n", [self()]),
            HubPID ! closing;
        {hub_message, Message} ->
            io:format("~p received message: ~p~n", [self(), Message]),
            HubPID ! message,
            handleMessages(HubPID)
    end.

% Will be implemented in the order of this function

sendMessages([M | Ms], Ps) ->
    sendMessage(M, Ps),
    messageIntercept(M,Ps),
    sendMessages(Ms, Ps);
sendMessages([], Ps) -> 
    stopProcceses(Ps),
    stopHub(Ps),
    io:format("Hub has finished.~n").


sendMessage(M, [P]) -> 
    io:format("Hub sent ~p the message: ~p~n", [P , M]),
    P ! {hub_message, M},
        receive
            message -> 
                done
        end;
sendMessage(M, [P | Ps]) -> 
    io:format("Hub sent ~p the message: ~p~n", [P , M]),
    P ! {hub_message, M},    
        receive
            message ->
                sendMessage(M , Ps)
        end.

messageIntercept(M, [P]) ->
    io:format("Hub received from ~p the message ~p~n",[P, M]);

messageIntercept(M, [P | Ps]) -> 
    io:format("Hub received from ~p the message ~p~n",[P, M]),
    messageIntercept(M, Ps).


stopProcceses([P]) ->
    io:format("Hub sent closing message to ~p.~n", [P]),
    P ! hub_closing,
        receive
            closing ->
                done
        end;
stopProcceses([P | Ps]) -> 
    io:format("Hub sent closing message to ~p.~n", [P]),
    P ! hub_closing,    
    receive
        closing ->
            stopProcceses(Ps)
    end.

stopHub([P]) ->
    io:format("Hub received closing message from ~p~n",[P]);
stopHub([P | Ps]) -> 
    io:format("Hub received closing message from ~p~n",[P]),
    stopHub(Ps).