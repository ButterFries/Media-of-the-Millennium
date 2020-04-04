Instructions to run the server:
	(see server/README.txt for specifics)
=== ssl ========================================================================

To generate the RSA key do the following in this directory (you can leave the fields blank if it asks):
	ADDR="localhost"
	keytool -genkeypair -keyalg RSA -alias selfsigned -keystore motm_key.jks -storepass developers_of_the_millenium_password_to_motm -validity 360 -keysize 2048 -dname "CN=$ADDR, OU=Developers_of_the_Millenium, O=UofT:M, L=Mississauga, C=ON, ST=Ontario"
Note: ADDR should be changed from "localhost" to your local IP address when testing through the android emulator

Check stored entry with 
	keytool -list -v -keystore motm_key.jks
	[password]: developers_of_the_millenium_password_to_motm
To export a certificate from the key store
	keytool -export -alias selfsigned -file motm_cert.crt -keystore motm_key.jks
To get the PEM file
	openssl x509 -inform der -in motm_cert.crt -out motm_cert.pem
To check the PEM encoded, x509 certificate (at the bottom ---begin certificate---)
	openssl x509 -text -inform PEM -in motm_cert.pem

=== server =====================================================================


To compile the server files on Linux (on Windows replace colons with semicolons):
	javac -cp ".:./server/motm/lib/*:./server/motm/servlets/*:./server/motm/database/*:./server/motm/utils/*" server/motm/Server.java

To run the server:
	java  -cp ".:./server/motm/lib/*:./server/motm/servlets/*:./server/motm/database/*:./server/motm/utils/*" server/motm/Server
If there is trouble compiling or running try deleting all class files
	find . -type f -name "*.class"
	find . -type f -name "*.class" -delete

(we could have used maven, but its nice to know how to compile and run things more directly)
================================================================================
================================================================================


Setting up Android client to connect to server:

--------------------------------------------------------------------------------
here's the steps to running the client and connecting to the server:

1. Generate a RSA key and get motm_cert.pem, instructions are in server/README.md

    NOTE: ADDR should be changed from "localhost" to your local IP address
    https://stackoverflow.com/questions/42904584/how-to-connect-to-localhost-from-android-studio-emulator

    You use your computer's IPv4 Address, which can be found using "ipconfig" or "ifconfig"

    NOTE: the motm_key.jks must in ~/project-developers-of-the-millennium/server must be the new one that was generated when setting up SSL for the server, and the motm_cert.pem must be the one that was generated using that motm_key.jks

2. Copy 
motm_cert.pem
 and put it in app/app/src/main/res/raw/motm_cert.pem
    Overwrite the file if one is present

3. Change the 
server_address
 variable in app/app/src/main/res/values/strings.xml to your computer's IPv4 Address


Steps to compiling, running, and cleaning server files is located in server/README.md

NOTE: Sometimes your IPv4 Address will change, if so, repeat all these steps including generating a new RSA key for your new IP

--------------------------------------------------------------------------------

After setup and the server is started you can build the app to run. 
We have not tested it on an actual device, only the android studio emulator.  There should be no difference in being able to connect to the server from an actual android device as long as it is in the same local network.


================================================================================
For publishing the app we would need a domain to host the server on, and with such we could get a free CA signed certificate to use.


