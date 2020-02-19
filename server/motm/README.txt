
Be in the root dir of the project!

To compile the server files on Linux (on Windows replace colons with semicolons):
	javac -cp ".:./server/motm/lib/*:./server/motm/servlets/*:./server/motm/database/*:./server/motm/utils/*" server/motm/Server.java

To run the server:
	java  -cp ".:./server/motm/lib/*:./server/motm/servlets/*:./server/motm/database/*:./server/motm/utils/*" server/motm/Server


To test endpoints (via CLI):
	curl --verbose -X GET http://localhost:8080/HelloWorld/ --data '{ "hello": "test" }'




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







