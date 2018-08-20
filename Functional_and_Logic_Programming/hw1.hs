-- Benjamin Arbibe
-- 323795633

-- 1.
isPalindrome :: String -> Bool
isPalindrome xs = xs == (reverse xs)

-- 2.
isPrefix :: String -> String -> Bool
isPrefix [] _ =  True
isPrefix _ [] =  False
isPrefix (x:xs) (y:ys) = (x == y) && (isPrefix xs ys)

-- 3.
-- a)
squareList :: Int -> [Int]
squareList 0 = [0]
squareList n = n ^ 2 : (squareList (n - 1))

-- b)
listSquare :: Int -> [Int]
listSquare 0 = [0]
listSquare n =  listSquare (n - 1) ++ [n ^ 2]

-- 4.
fact :: Integer -> Integer
fact 0 = 1
fact 1 = 1
fact n = fact' n 1

-- Helper Function
fact' :: Integer -> Integer -> Integer
fact' 0 acc = acc
fact' n acc = fact' (n - 1) (n * acc)

-- 5.
-- a)
toDigits :: Integer -> [Integer]
toDigits x | x <= 0 = []
toDigits n = toDigits (n `div` 10) ++ [n `mod` 10]

-- b)
doubleEveryOther :: [Integer] -> [Integer]
doubleEveryOther x = reverse (doubleEveryOther' (reverse x))

-- Helper Function
doubleEveryOther' :: [Integer] -> [Integer]
doubleEveryOther' (x:y:xs) = (x : 2*y : doubleEveryOther' xs)
doubleEveryOther' a = a

-- c)
sumDigits :: [Integer] -> Integer
sumDigits [] = 0
sumDigits [x] | x < 10 = x
sumDigits (x:xs) = sumDigits (toDigits x) + sumDigits xs

-- d)
validate :: Integer -> Bool
validate x = sumDigits(doubleEveryOther(toDigits(x))) `mod` 10 == 0


printSame :: a -> b
printSame a = "Benji"