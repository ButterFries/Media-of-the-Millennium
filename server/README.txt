
Be in this dir of the project! `~/project-developers-of-the-millennium/servers`

=== ssl ========================================================================

To generate the RSA key do the following in this directory (you can leave the fields blank if it asks):
	ADDR="localhost"
	keytool -genkeypair -keyalg RSA -alias selfsigned -keystore motm_key.jks -storepass developers_of_the_millenium_password_to_motm -validity 360 -keysize 2048 -dname "CN=$ADDR, OU=Developers_of_the_Millenium, O=UofT:M, L=Mississauga, C=ON, ST=Ontario"
Note: ADDR should be changed from "localhost" to your local IP address when testing through the android emulator
	https://stackoverflow.com/questions/42904584/how-to-connect-to-localhost-from-android-studio-emulator

A selfsigned certificate is sufficient for development purposes, for deployment we would need a CA signed cert.
Note that this will expire in around a year and should be renewed.

https://www.sslsupportdesk.com/java-keytool-commands/

Check stored entry with 
	keytool -list -v -keystore motm_key.jks
	[password]: developers_of_the_millenium_password_to_motm
To export a certificate from the key store
	keytool -export -alias selfsigned -file motm_cert.crt -keystore motm_key.jks
To get the PEM file
	openssl x509 -inform der -in motm_cert.crt -out motm_cert.pem
To check the PEM encoded, x509 certificate (at the bottom ---begin certificate---)
	openssl x509 -text -inform PEM -in motm_cert.pem

You only need to have the PEM on the client side, dont copy anything else.

maybe see the following for details on Android implementation:
	http://oceanairdrop.blogspot.com/2018/05/https-self-signed-certificates-and.html

=== server =====================================================================


To compile the server files on Linux (on Windows replace colons with semicolons):
	javac -cp ".:./server/motm/lib/*:./server/motm/servlets/*:./server/motm/database/*:./server/motm/utils/*" server/motm/Server.java

To run the server:
	java  -cp ".:./server/motm/lib/*:./server/motm/servlets/*:./server/motm/database/*:./server/motm/utils/*" server/motm/Server


To test endpoints (via CLI):
	//curl --verbose -X GET http://localhost:8080/HelloWorld/ --data '{ "hello": "test" }'
	curl --verbose --cacert ./src/main/java/motm/ssl/motm_cert.pem -X POST https://localhost:8080/HelloWorld  --data '{ "hello": "test" }'
If cert is giving issues, use "-k" to ignore validation
	curl --verbose -k -X POST https://localhost:8080/HelloWorld  --data '{ "hello": "test" }'

================================================================================
================================================================================

example of usage on server:
	user@ubuntu:~/project-developers-of-the-millennium$ java -cp ".:./server/motm/lib/*:./server/motm/servlets/*:./server/motm/database/*:./server/motm/utils/*" server/motm/Server
	! Setting up HTTP server on 0.0.0.0:8080
	--setup complete
	! Setting up connection via appDatabase
	--connection established
	Server started on port 8080...

	-Received request [HelloWorld]
	--request type: GET
	--client said hello: test
	--replying with {hello: client}
	--responese :   {"hello":"client"}
	--request fufilled


example of usage on client (testing through CLI):
	user@ubuntu:~$ curl --verbose -X GET http://localhost:8080/HelloWorld/ --data '{ "hello": "test" }'
	*   Trying 127.0.0.1...
	* TCP_NODELAY set
	* Connected to localhost (127.0.0.1) port 8080 (#0)
	> GET /HelloWorld/ HTTP/1.1
	> Host: localhost:8080
	> User-Agent: curl/7.58.0
	> Accept: */*
	> Content-Length: 19
	> Content-Type: application/x-www-form-urlencoded
	> 
	* upload completely sent off: 19 out of 19 bytes
	< HTTP/1.1 200 OK
	< Date: Tue, 18 Feb 2020 19:44:57 GMT
	< Content-length: 19
	< 
	{"hello":"client"}
	* Connection #0 to host localhost left intact


example of use with https server:
	user@ubuntu:~$ curl --verbose --cacert ./src/main/java/motm/ssl/motm_cert.pem -X POST https://localhost:8080/HelloWorld  --data '{ "hello": "test" }'
	Note: Unnecessary use of -X or --request, POST is already inferred.
	*   Trying 127.0.0.1...
	* TCP_NODELAY set
	* Connected to localhost (127.0.0.1) port 8080 (#0)
	* ALPN, offering h2
	* ALPN, offering http/1.1
	* successfully set certificate verify locations:
	*   CAfile: ./src/main/java/motm/ssl/motm_cert.pem
	  CApath: /etc/ssl/certs
	* TLSv1.3 (OUT), TLS handshake, Client hello (1):
	* TLSv1.3 (IN), TLS handshake, Server hello (2):
	* TLSv1.2 (IN), TLS handshake, Certificate (11):
	* TLSv1.2 (IN), TLS handshake, Server key exchange (12):
	* TLSv1.2 (IN), TLS handshake, Server finished (14):
	* TLSv1.2 (OUT), TLS handshake, Client key exchange (16):
	* TLSv1.2 (OUT), TLS change cipher, Client hello (1):
	* TLSv1.2 (OUT), TLS handshake, Finished (20):
	* TLSv1.2 (IN), TLS handshake, Finished (20):
	* SSL connection using TLSv1.2 / ECDHE-RSA-AES256-GCM-SHA384
	* ALPN, server did not agree to a protocol
	* Server certificate:
	*  subject: ST=Ontario; C=ON; L=Mississauga; O=UofT:M; OU=Developers_of_the_Millenium; CN=localhost
	*  start date: Feb 22 20:13:53 2020 GMT
	*  expire date: Feb 16 20:13:53 2021 GMT
	*  common name: localhost (matched)
	*  issuer: ST=Ontario; C=ON; L=Mississauga; O=UofT:M; OU=Developers_of_the_Millenium; CN=localhost
	*  SSL certificate verify ok.
	> POST /HelloWorld HTTP/1.1
	> Host: localhost:8080
	> User-Agent: curl/7.58.0
	> Accept: */*
	> Content-Length: 19
	> Content-Type: application/x-www-form-urlencoded
	> 
	* upload completely sent off: 19 out of 19 bytes
	< HTTP/1.1 200 OK
	< Date: Sat, 22 Feb 2020 20:15:59 GMT
	< Content-length: 19
	< 
	{"hello":"client"}
	* Connection #0 to host localhost left intact






