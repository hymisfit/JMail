# JMail

##This project using Java call smtp.google.com send mail (have attachments)


Browse URL https://www.google.com/settings/security/lesssecureapps to turn on


##Prepair enviroment
```bash
mkdir libs
mkdir classes
mkdir dist
cd dist
ln -s ../libs libs
cd ..
```

###Downloads libaries
```bash
cd libs
curl -L -o commons-io-2.5-bin.zip http://mirrors.viethosting.vn/apache//commons/io/binaries/commons-io-2.5-bin.zip
unzip commons-io-2.5-bin.zip
mv ./commons-io-2.5/commons-io-2.5.jar commons-io-2.5.jar
rm -rf commons-io-2.5
rm commons-io-2.5-bin.zip
curl -L -o mail-1.4.7.jar http://central.maven.org/maven2/javax/mail/mail/1.4.7/mail-1.4.7.jar
cd ..
```

###Compile .java to .class
```bash
# clean old compile
rm -rf ./classes/*
rm dist/JMail.jar
# compile code
javac -d ./classes -classpath ./libs/mail-1.4.7.jar:./libs/commons-io-2.5.jar ./src/jmail/JMail.java 
```

###Compile to dist/JMail.jar file
```bash
cd ./classes
jar cfm ../dist/JMail.jar ../Manifest.txt ./jmail/*
cd ..
```

###Call send mail
```bash
# read file message or attach files from current dir of dist/JMail.jar file
java -jar dist/JMail.jar "<Email Subject>" "<email form>" "<email form password>" "<emailTo>" "<message file or message string>" ["<Path to file attachments>" [...] ]
java -jar dist/JMail.jar "<Email Subject>" "<email form>" "<email form password>" "<emailTo:CC=ccEmail1:CC=ccEmail2>" "<message file or message string>" ["<Path to file attachments>" [...] ]

# read file message or attach files from WORK_DIR enviroment
WORK_DIR=`pwd` java -jar dist/JMail.jar "<Email Subject>" "<email form>" "<email form password>" "<emailTo>" "<message file or message string>" ["<Path to file attachments>" [...] ]
WORK_DIR=`pwd` java -jar dist/JMail.jar "<Email Subject>" "<email form>" "<email form password>" "<emailTo:CC=ccEmail1:CC=ccEmail2>" "<message file or message string>" ["<Path to file attachments>" [...] ]
```

###Can download JMail.jar
```bash
curl -L -o JMail.jar https://raw.githubusercontent.com/hymisfit/JMail/master/dist/JMail.jar
```
