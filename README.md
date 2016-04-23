# JMail

##Prepair enviroment
mkdir libs
mkdir classes
mkdir dist

###Downloads libaries
cd libs
curl -L -o commons-io-2.5-bin.zip http://mirrors.viethosting.vn/apache//commons/io/binaries/commons-io-2.5-bin.zip
unzip commons-io-2.5-bin.zip
mv ./commons-io-2.5/commons-io-2.5.jar commons-io-2.5.jar
rm -rf commons-io-2.5
rm commons-io-2.5-bin.zip
curl -L -o mail-1.4.7.jar http://central.maven.org/maven2/javax/mail/mail/1.4.7/mail-1.4.7.jar
cd ..

#Compile .java to .class
javac -d ./classes -classpath ./libs/mail-1.4.7.jar:./libs/commons-io-2.5.jar ./src/jmail/JMail.java 

#Compile to dist/JMail.jar file
cd ./classes
jar cfm ../dist/JMail.jar ../Manifest.txt ./jmail/*
cd ..

#Call send mail
java -jar dist/JMail.jar "Test Subject 3" "hyselab2@gmail.com"   "hy12345678"   "hy@misfit.com" "test.html"    "/Users/henry/Documents/Works/Misfit/TestTravis/EmailAttachmentSender.java"



