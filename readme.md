# HBase sample program
Sample HBase program based on HBase in Action by Dimiduk & Khurana (2013)

## hbase-env.sh

```bash
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
```

## hbase-site.xml

```xml
<configuration>
  <property>
    <name>hbase.rootdir</name>
    <value>hdfs://localhost:9000/hbase</value>
  </property>
  <property>
    <name>hbase.zookeeper.property.dataDir</name>
    <value>/home/student/zookeeper</value>
  </property>
  <property>
    <name>hbase.unsafe.stream.capability.enforce</name>
    <value>false</value>
    <description>
      Controls whether HBase will check for stream capabilities (hflush/hsync).

      Disable this if you intend to run on LocalFileSystem, denoted by a rootdir
      with the 'file://' scheme, but be mindful of the NOTE below.

      WARNING: Setting this to false blinds you to potential data loss and
      inconsistent system state in the event of process and/or node failures. If
      HBase is complaining of an inability to use hsync or hflush it's most
      likely not a false positive.
    </description>
  </property>

</configuration>


```

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