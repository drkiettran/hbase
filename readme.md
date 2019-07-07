# HBase sample program
Sample HBase program based on HBase in Action by Dimiduk & Khurana (2013)

## HBase shell

```
create 'users', 'info'

put 'users', 'GranpaD', 'info:name', 'Mark Twain'
put 'users', 'GrandpaD', 'info:password', 'abcdef1234'
put 'users', 'GrandpaD', 'info:email', 'samuel@clemens.org'

put 'users', 'TheRealMT', 'info:name', 'Sir Arthur Conan Doyle'
put 'users', 'TheRealMT', 'info:email', 'art@TheQueenMen.co.uk'
put 'users', 'TheRealMT', 'info:password', 'lance1234'

put 'users', 'HMS_Surprise', 'info:name', 'Patrick OBrien'
put 'users', 'HMS_Surprise', 'info:email', 'aubrey@sea.com'
put 'users', 'HMS_Surprise', 'info:password', 'bcdef1234'

put 'users', 'Sir Doyle', 'info:name', 'Fyodor Dostoyevasky'
put 'users', 'Sir Doyle', 'info:email', 'fyodor@brothers.net'
put 'users', 'Sir Doyle', 'info:password', 'cdef1234'

scan 'users'

disable 'users'
delete 'users'

enable 'users'  
```

## Build & Run program

```
mvn clean package
java -cp ./target/hbase-jar-with-dependencies.jar com.drkiettran.hbase.Main

```