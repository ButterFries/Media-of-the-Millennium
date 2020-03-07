this is an example to work off of; starter code

first start the server (see ~/server/motm/README.txt)

to launch the client the current working directory should be ~/server/example_client

to run the example client (using maven)
	mvn -e install exec:java 
	mvn -e exec:java -Dexec.args="0"
	mvn -e exec:java -Dexec.args="1"
	mvn -e exec:java -Dexec.args="2"
	mvn -e exec:java -Dexec.args="3"
	mvn -e exec:java -Dexec.args="4"
	mvn -e exec:java -Dexec.args="5"

if experiencing issues then add "install" like the first one,
but if there is persisting issues then add "clean" like so:
	mvn -e clean install exec:java 
some other commands:
	mvn clean
	mvn clean install
	mvn install

===mode 1=======================================================================

	Client (exmaple) starting up
	-- arg mode :  1
	Creating registerAccount request
	Response{protocol=http/1.1, code=200, message=OK, url=http://0.0.0.0:8080/registerAccount}
	{"error_description":"successfully registered account to database","error_code":0}
	--complete
	--closing

--db----------------------------------------------------------------------------

	sqlite> SELECT * FROM accounts;
	1|example|email|passwordhash
	2|testClient|test@example|1000:5b42403736656338306335:0f54374afa3b2962b9a764b8e5046f6692484359b2dd221b32ba75b24f56542ac62b33885b76d18039ce5088a1233b66ef5538cc1108c03e277dcc16bd32f1c9


--server------------------------------------------------------------------------

	-Received request [registerAccount]
	--request type: PUT
	--client sent u_name: testClient
	--client sent email: test@example
	--client's s_pw: 1000:5b42403736656338306335:0f54374afa3b2962b9a764b8e5046f6692484359b2dd221b32ba75b24f56542ac62b33885b76d18039ce5088a1233b66ef5538cc1108c03e277dcc16bd32f1c9
	--checking if username exists already
	----username not taken
	--checking if username exists already
	----email not taken
	--adding account to database
	----successfully added account to database
	--responese :   {"error_description":"successfully registered account to database","error_code":0}
	--request fufilled


===mode 2=======================================================================

	Client (exmaple) starting up
	-- arg mode :  2
	Creating validateAccount request
	Response{protocol=http/1.1, code=200, message=OK, url=http://0.0.0.0:8080/validateAccount}
	{"error_description":"successfully verified accountID","error_code":0,"hash":"1000:5b42403736656338306335:0f54374afa3b2962b9a764b8e5046f6692484359b2dd221b32ba75b24f56542ac62b33885b76d18039ce5088a1233b66ef5538cc1108c03e277dcc16bd32f1c9"}
	stored_hash::  '1000:5b42403736656338306335:0f54374afa3b2962b9a764b8e5046f6692484359b2dd221b32ba75b24f56542ac62b33885b76d18039ce5088a1233b66ef5538cc1108c03e277dcc16bd32f1c9'
	hashed_password::  '1000:5b42403736656338306335:0f54374afa3b2962b9a764b8e5046f6692484359b2dd221b32ba75b24f56542ac62b33885b76d18039ce5088a1233b66ef5538cc1108c03e277dcc16bd32f1c9'
	Response{protocol=http/1.1, code=200, message=OK, url=http://0.0.0.0:8080/validateAccount}
	{"session_token":"none","error_description":"successfully verified account credentials","error_code":0}
	--complete
	--closing

--server------------------------------------------------------------------------

	-Received request [validateAccount]
	--request type: POST (GET)
	--[Stage 1] Retrieving stored hash
	--client sent u_name: testClient
	--valid account, password verification pending
	--responese :   {"error_description":"successfully verified accountID","error_code":0,"hash":"1000:5b42403736656338306335:0f54374afa3b2962b9a764b8e5046f6692484359b2dd221b32ba75b24f56542ac62b33885b76d18039ce5088a1233b66ef5538cc1108c03e277dcc16bd32f1c9"}
	--request fufilled

	-Received request [validateAccount]
	--request type: POST (GET)
	--[Stage 2] Validating credentials
	--client sent u_name: testClient
	--client sent s_pw: 1000:5b42403736656338306335:0f54374afa3b2962b9a764b8e5046f6692484359b2dd221b32ba75b24f56542ac62b33885b76d18039ce5088a1233b66ef5538cc1108c03e277dcc16bd32f1c9
	--the given account credentials are valid
	--responese :   {"session_token":"none","error_description":"successfully verified account credentials","error_code":0}
	--request fufilled


===mode 3=======================================================================

	Client (exmaple) starting up
	-- arg mode :  3
	Creating validateAccount request
	Response{protocol=http/1.1, code=200, message=OK, url=http://0.0.0.0:8080/validateAccount}
	{"error_description":"successfully verified accountID","error_code":0,"hash":"1000:5b42403736656338306335:0f54374afa3b2962b9a764b8e5046f6692484359b2dd221b32ba75b24f56542ac62b33885b76d18039ce5088a1233b66ef5538cc1108c03e277dcc16bd32f1c9"}
	stored_hash::  '1000:5b42403736656338306335:0f54374afa3b2962b9a764b8e5046f6692484359b2dd221b32ba75b24f56542ac62b33885b76d18039ce5088a1233b66ef5538cc1108c03e277dcc16bd32f1c9'
	hashed_password::  '1000:5b42403736656338306335:0f54374afa3b2962b9a764b8e5046f6692484359b2dd221b32ba75b24f56542ac62b33885b76d18039ce5088a1233b66ef5538cc1108c03e277dcc16bd32f1c9'
	Response{protocol=http/1.1, code=200, message=OK, url=http://0.0.0.0:8080/validateAccount}
	{"session_token":"none","error_description":"successfully verified account credentials","error_code":0}
	--complete
	--closing


--server------------------------------------------------------------------------

	-Received request [validateAccount]
	--request type: POST (GET)
	--[Stage 1] Retrieving stored hash
	--client sent email: test@example
	--valid account, password verification pending
	--responese :   {"error_description":"successfully verified accountID","error_code":0,"hash":"1000:5b42403736656338306335:0f54374afa3b2962b9a764b8e5046f6692484359b2dd221b32ba75b24f56542ac62b33885b76d18039ce5088a1233b66ef5538cc1108c03e277dcc16bd32f1c9"}
	--request fufilled

	-Received request [validateAccount]
	--request type: POST (GET)
	--[Stage 2] Validating credentials
	--client sent email: test@example
	--client sent s_pw: 1000:5b42403736656338306335:0f54374afa3b2962b9a764b8e5046f6692484359b2dd221b32ba75b24f56542ac62b33885b76d18039ce5088a1233b66ef5538cc1108c03e277dcc16bd32f1c9
	--the given account credentials are valid
	--responese :   {"session_token":"none","error_description":"successfully verified account credentials","error_code":0}
	--request fufilled



===mode 4=======================================================================

	Client (exmaple) starting up
	-- arg mode :  4
	Creating validateAccount request
	Response{protocol=http/1.1, code=200, message=OK, url=http://0.0.0.0:8080/validateAccount}
	{"error_description":"successfully verified accountID","error_code":0,"hash":"1000:5b42403736656338306335:0f54374afa3b2962b9a764b8e5046f6692484359b2dd221b32ba75b24f56542ac62b33885b76d18039ce5088a1233b66ef5538cc1108c03e277dcc16bd32f1c9"}
	stored_hash::  '1000:5b42403736656338306335:0f54374afa3b2962b9a764b8e5046f6692484359b2dd221b32ba75b24f56542ac62b33885b76d18039ce5088a1233b66ef5538cc1108c03e277dcc16bd32f1c9'
	hashed_password::  '1000:5b42403736656338306335:0f9e8332bf002c31f52eea3bb024bd7212a4e3b22fe98a5aae7ee6a2df58e73542f04c95102381d52a10ac4174636f6ad706b6557725202b07c6d1fcbbc531a3'
	Response{protocol=http/1.1, code=200, message=OK, url=http://0.0.0.0:8080/validateAccount}
	{"error_description":"invalid account credentials","error_code":3}
	--complete
	--closing


--server------------------------------------------------------------------------

	-Received request [validateAccount]
	--request type: POST (GET)
	--[Stage 1] Retrieving stored hash
	--client sent u_name: testClient
	--valid account, password verification pending
	--responese :   {"error_description":"successfully verified accountID","error_code":0,"hash":"1000:5b42403736656338306335:0f54374afa3b2962b9a764b8e5046f6692484359b2dd221b32ba75b24f56542ac62b33885b76d18039ce5088a1233b66ef5538cc1108c03e277dcc16bd32f1c9"}
	--request fufilled

	-Received request [validateAccount]
	--request type: POST (GET)
	--[Stage 2] Validating credentials
	--client sent u_name: testClient
	--client sent s_pw: 1000:5b42403736656338306335:0f9e8332bf002c31f52eea3bb024bd7212a4e3b22fe98a5aae7ee6a2df58e73542f04c95102381d52a10ac4174636f6ad706b6557725202b07c6d1fcbbc531a3
	--the given account credentials are invalid
	--responese :   {"error_description":"invalid account credentials","error_code":3}
	--request fufilled



===mode 5=======================================================================

	Client (exmaple) starting up
	-- arg mode :  5
	Creating validateAccount request
	Response{protocol=http/1.1, code=200, message=OK, url=http://0.0.0.0:8080/validateAccount}
	{"error_description":"username doesn't exist","error_code":1}
	java.lang.Exception: error code1 ::  username doesn't exist


--server------------------------------------------------------------------------

	-Received request [validateAccount]
	--request type: POST (GET)
	--[Stage 1] Retrieving stored hash
	--client sent u_name: TESTCLIENT
	--username doesn't exist
	--responese :   {"error_description":"username doesn't exist","error_code":1}
	--request fufilled



================================================================================



================================================================================



================================================================================
