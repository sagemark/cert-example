# cert-example
Just wrapping my head around some cert stuff.

keytool -export -alias javaclient -keystore client-keystore.jks -storepass password -file javaclient.crt

keytool -import javaclient -file javaclient.crt -keystore /server/truststore.jks -storepass password

keytool -genkey -keyalg RSA -alias selfsigned -keystore /server/need-cert-keystore.jks -storepass password -validity 360
