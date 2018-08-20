-- Benjamin Arbibe
-- 323795633

-- The following BNF represents arithmetic expressions which have variables in them:
data Expr = Const Value | Add Expr Expr | Mul Expr Expr | Sub Expr Expr | Div Expr Expr | Var Variable 

type Variable = String
type Value = Float

type Dictionary = [(Variable, Value)]
type EvalError = [Variable]
type EvalResult = Either EvalError Value

-- 1.
-- a)

display :: (Expr) -> String
display (Const r) = show r
display (Var x) = x
display (Add x y) = "(" ++ display (x) ++ "+" ++ display (y) ++ ")"
display (Mul x y) = "(" ++ display (x) ++ "*" ++ display (y) ++ ")"
display (Sub x y) = "(" ++ display (x) ++ "-" ++ display (y) ++ ")"
display (Div x y) = "(" ++ display (x) ++ "/" ++ display (y) ++ ")"

-- 1.
-- b)

eval :: Dictionary -> Expr -> EvalResult
eval _ (Const x) = Right x
eval [] (Var x) = Left [x]
eval dictionary (Add x y) = eval' (+) (eval dictionary x) (eval dictionary y)
eval dictionary (Mul x y) = eval' (*) (eval dictionary x) (eval dictionary y)
eval dictionary (Sub x y) = eval' (-) (eval dictionary x) (eval dictionary y)
eval dictionary (Div x y) = eval' (/) (eval dictionary x) (eval dictionary y)
eval ((x,y):indictionary) (Var z) = if x == z then Right y else eval indictionary (Var z)


eval' :: (Value -> Value -> Value) -> EvalResult -> EvalResult -> EvalResult                                     
eval' _ (Left x) (Left y) = Left (x ++ y)
eval' _ (Left x) _ = Left x
eval' _ _ (Left y) = Left y
eval' op (Right x) (Right y) = Right (op x y)

-- Polymorphic tree data type: 
data Tree a b = Leaf b | Node a (Tree a b) (Tree a b) deriving (Show, Eq) 

-- 2.
-- a)
reverseTree :: Tree a b -> Tree a b
reverseTree (Leaf b) = Leaf b
reverseTree (Node a b c) = Node a (reverseTree(c)) (reverseTree(b))

-- b)
isSubtree :: Tree Int Char -> Tree Int Char -> Bool
isSubtree x (Leaf y) = x == Leaf y
isSubtree x (Node a b c) = x == (Node a b c) || isSubtree x b || isSubtree x c

-- The following data type represents tree where each node can have many children:
data MTree a = MTree a [MTree a] deriving (Show)

-- 3.
-- a)
sumMTree :: MTree Int -> Int
sumMTree (MTree a b) = a + sum (map sumMTree b)

-- https://wiki.haskell.org/Let_vs._Where
-- b)
grow :: MTree a -> MTree a
grow (MTree a b) = grow' (MTree a b)
    where 
        grow' (MTree _ []) = (MTree a b)
        grow' (MTree a b) = MTree a (map grow' b)
