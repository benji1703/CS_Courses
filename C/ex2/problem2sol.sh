cp /share/ex_data/ex2/peter-pan.txt ./
cat peter-pan.txt |tr -s [[:space:][:punct:]] '\n' |grep ^[^0-9]|tr [:upper:] [:lower:]|sort |uniq -c |grep [0-9][0-9]|sed 's/........//'
cat peter-pan.txt | tr [[:punct:]]+ ' '| tr [[:space:]]+ '\n' | grep ^[^0-9] - | tr [a-z] [A-Z] | sort | uniq | sed 's/\(^[A-Z]\)[A-Z]*/\1/' - | sort | uniq -c | sort -nr
