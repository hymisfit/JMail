# JMail
curl -L -o /tmp/commons-io-2.5-bin.zip http://mirrors.viethosting.vn/apache//commons/io/binaries/commons-io-2.5-bin.zip
unzip /tmp/commons-io-2.5-bin.zip
curl -L -o /tmp/mail-1.4.7.jar  http://central.maven.org/maven2/javax/mail/mail/1.4.7/mail-1.4.7.jar
mv /commons-io-2.5/commons-io-2.5.jar /tmp/commons-io-2.5.jar
mkdir jmail
cd jmail

curl -L -o JMail.java 
javac -d ./ -cp .:./tmp/mail-1.4.7.jar:/tmp/commons-io-2.5.jar JMail.java


