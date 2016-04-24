git clone https://github.com/hymisfit/JMail.git

cd JMail
#mkdir libs

mkdir classes

#mkdir dist
#cd dist
#ln -s ../libs libs
#cd ..

#cd libs
#curl -L -o commons-io-2.5-bin.zip http://mirrors.viethosting.vn/apache//commons/io/binaries/commons-io-2.5-bin.zip
#unzip commons-io-2.5-bin.zip
#mv ./commons-io-2.5/commons-io-2.5.jar commons-io-2.5.jar
#rm -rf commons-io-2.5
#rm commons-io-2.5-bin.zip
#curl -L -o mail-1.4.7.jar http://central.maven.org/maven2/javax/mail/mail/1.4.7/mail-1.4.7.jar
#cd ..

# clean old compile
rm -rf ./classes/*
rm dist/JMail.jar
# compile code
javac -d ./classes -classpath ./libs/mail-1.4.7.jar:./libs/commons-io-2.5.jar ./src/jmail/JMail.java 


cd ./classes
jar cfm ../dist/JMail.jar ../Manifest.txt ./jmail/*
cd ..

# read file message or attach files from current dir of dist/JMail.jar file
echo 'Please run as 2 ways:'
echo '1: java -jar dist/JMail.jar "<Email Subject>" "<email form>" "<email form password>" "<email to>" "<message file or message string>" ["<Path to file attachments>" [...] ]'

# read file message or attach files from WORK_DIR enviroment
echo '2: WORK_DIR=`pwd` java -jar dist/JMail.jar "<Email Subject>" "<email form>" "<email form password>" "<email to>" "<message file or message string>" ["<Path to file attachments>" [...] ]'

