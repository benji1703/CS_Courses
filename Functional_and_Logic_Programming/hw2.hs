-- Benjamin Arbibe
-- 323795633

-- https://wiki.haskell.org/How_to_work_on_lists
-- http://hackage.haskell.org/package/base-4.11.1.0/docs/Prelude.html
-- http://www.cse.unsw.edu.au/~en1000/haskell/inbuilt.html

-- 1.
-- a)
replaceElement :: (Int,x) -> [x] -> [x]
replaceElement (_,_) [] = []
replaceElement (index,y) xs = if ( index < 0 || index > length xs) then xs else (take index xs) ++ [y] ++ (drop index xs)

-- b)
replaceElements :: [(Int,x)] -> [x] -> [x]
replaceElements [] xs = xs
replaceElements (x:xs) ys = replaceElements xs (replaceElement x ys)

-- 2.
-- a)
addItem :: (String,a) -> [(String,a)] -> [(String,a)]
addItem x xs = x : xs

-- b)
subsetByKey :: String -> [(String,a)] -> [a]
subsetByKey key [] = []
subsetByKey key (x:xs) = if (getFirst x) == key then getSecond x : subsetByKey key xs else subsetByKey key xs 

-- Creating own method, even though fst and snd exists.
getFirst :: (a,b) -> a
getFirst (x,_) =  x
getSecond :: (a,b) -> b
getSecond (_,y) =  y

-- c)
subsetByKeys :: [String]-> [(String,a)] -> [a]
subsetByKeys [] _ = []
subsetByKeys (x:xs) y = subsetByKey x y ++ subsetByKeys xs y

-- d)
getKeys :: [(String,a)] -> [String]
getKeys [] = []
getKeys ((x,_) : y) = if notElem x (getKeys y) then x : getKeys y else getKeys y

-- e)
groupByKeys :: [(String,a)] -> [(String,[a])]
groupByKeys [] = []
groupByKeys xs = zip (getKeys xs) (groupByKeys' (getKeys xs) xs)

groupByKeys' :: [String] -> [(String,a)] -> [[a]]
groupByKeys' [] _ = []
groupByKeys' (k:keys) xs = [subsetByKey k xs] ++ (groupByKeys' keys xs)

-- 3.
-- a)
createMatrix :: Int -> Int -> [a] -> [[a]]
createMatrix _ _ [] = []
createMatrix 1 n xs = [xs]
createMatrix m n xs = take n xs : createMatrix (m-1) n (drop n xs)

-- b)
getElementInCell :: Int -> Int -> [[a]] -> a
getElementInCell m n x = x !! m !! n

-- c)
appendH :: [[a]] -> [[a]] -> [[a]]
appendH [] [] = []
appendH x y = zipWith (++) x y 

-- d)
appendV :: [[a]] -> [[a]] -> [[a]]
appendV [] [] = []
appendV x y = x ++ y 

-- e)
addMatrices :: [[Int]] -> [[Int]] -> [[Int]]
addMatrices [] [] = []
addMatrices (x:xs) (y:ys) = zipWith (+) x y : addMatrices xs ys

pairup :: [a] -> [b] -> [(a, b)]
pairup [] [] = []
pairup (x:xs)(y:ys) = (x, y) : pairup xs ys

adjacent_pairs :: [a] -> [(a, a)]
adjacent_pairs xs = zip xs (tail xs)