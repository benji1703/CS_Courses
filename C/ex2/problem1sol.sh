cp /share/ex_data/ex2/phone_dir.txt ./
grep '6[[:digit:]]*6$' phone_dir.txt | sed 's/\(.*\), \(.*\) /\2 \1 /' | cut -d" " -f1,2 > phone_6x6.txt
cut -f1 -d"," phone_dir.txt | tr "A-Z" "a-z" | paste phone_dir.txt - | sed 's/[[:space:]]/,/g'| cut -f1,5 -d"," | sed 's/\([[:alpha:]]\)\([[:alpha:]]*,[[:alpha:]]\)\([[:alpha:]]*\)/\1\3/' > last_names.txt
cut  -f1 -d"," phone_dir.txt | tr "A-Z" "a-z" | paste -d" " phone_dir.txt - | grep '[[:space:]][6][0-9]*6[[:space:]]' | sed 's/[[:space:]]/,/g' | cut -f1,3,5 -d"," | sed 's/\([[:alpha:]]\)\([[:alpha:]]*,\)\([[:alpha:]]*,\)\([[:alpha:]]\)\([[:alpha:]]*\)/\3\1\5/' | tr "," " " > last_names_6x6.txt  
