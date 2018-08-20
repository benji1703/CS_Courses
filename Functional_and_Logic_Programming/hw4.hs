-- Benjamin Arbibe
-- 323795633

data Bit = O | I deriving (Eq, Show)

data HuffmanTree = Leaf Char Int | Node Int HuffmanTree HuffmanTree deriving (Eq, Show)

type Code = [Bit]

type FreqTable = [(Char, Int)]

type Dictionary = [(Char, Code)]

type Encoder = Char -> Code 

instance Ord HuffmanTree where
    Leaf a b <= Leaf x y = b <= y
    Leaf a b <= Node x y z = b <= x
    Node a b c <= Leaf x y = a <= y
    Node a b c <= Node x y z = a <= x

-- 1.
-- a)
insert :: Char -> FreqTable -> FreqTable 
insert x [] = [(x,1)]
insert x ys = if isInList x ys then insert' x ys else insert'' x ys

-- if the character exist is the FreqTable
insert' :: Char -> FreqTable -> FreqTable 
insert' x ys = map (\y -> if (fst y) == x then (x, (snd y) + 1) else (fst y, snd y)) ys

-- otherwise
insert'' :: Char -> FreqTable -> FreqTable 
insert'' x ys = ys ++ [(x,1)]

isInList :: Char -> [(Char, Int)] -> Bool
isInList x [] = False
isInList x (y:ys) = if x == (fst y) then True else isInList x ys

-- b)
count :: String -> FreqTable 
count x = count' x []

count' :: String -> FreqTable -> FreqTable
count' [] y = y
count' (x:xs) y = let u = insert x y 
                  in count' xs u

-- 2.
initLeaves :: FreqTable -> [HuffmanTree]
initLeaves x = map initLeaves' x

initLeaves' :: (Char, Int) -> HuffmanTree
initLeaves' x = Leaf (fst x) (snd x)

-- 3.
buildTree :: [HuffmanTree] -> HuffmanTree 
buildTree x = mergeTree (quicksortTree x)

quicksort :: Ord a => [a] -> [a]
quicksort []     = []
quicksort (p:xs) = (quicksort lesser) ++ [p] ++ (quicksort greater)
    where
        lesser  = filter (< p) xs
        greater = filter (>= p) xs

quicksortTree :: [HuffmanTree] -> [HuffmanTree]
quicksortTree a = quicksort a

mergeTree :: [HuffmanTree] -> HuffmanTree
mergeTree (rest : []) = rest
mergeTree ((Leaf a b) : (Leaf c d) : rest) = buildTree (Node (b + d) (Leaf a b) (Leaf c d) : rest)
mergeTree ((Leaf c d) : (Node (i) a b) : rest) = buildTree (Node (i + d) (Leaf c d) (Node (i) a b) : rest)
mergeTree ((Node (i) a b) : (Leaf c d) : rest) = buildTree (Node (i + d) (Leaf c d) (Node (i) a b) : rest)
mergeTree ((Node (i) a b) : (Node (j) c d) : rest) = buildTree (Node (i + j) (Node (i) a b) (Node (j) c d) : rest)

-- 4.
createDictionary :: HuffmanTree -> Dictionary
createDictionary (Leaf x _) = [(x,[])]
createDictionary (Node _ l r) = addDirection O (createDictionary l) ++ addDirection I (createDictionary r) 

addDirection :: Bit -> Dictionary -> Dictionary
addDirection d [] = []
addDirection d ((x,p):ps) = (x, d:p) : addDirection d ps

-- 5.
createEncoder :: Dictionary -> Encoder
createEncoder x y = search y x 

search :: Char -> Dictionary -> Code
search _ [] = []
search x ((y,p):xs) = if x == y then p else search x xs

-- 6.
encode :: String -> (HuffmanTree, Code)
encode x = let u = (buildTree (initLeaves (count x)))
           in (u, encode' u x)

encode' :: HuffmanTree -> [Char] -> Code
encode' x y = concat (map (createEncoder (createDictionary x)) y)

-- 7.
decode :: HuffmanTree -> Code -> String 
decode a c = decode' a c
    where 
        decode' (Leaf x z) [] = [x]
        decode' (Leaf x z) (ys) = [x] ++ decode a ys
        decode' (Node _ x z) (y:ys) = if y == I then decode' z ys else decode' x ys


