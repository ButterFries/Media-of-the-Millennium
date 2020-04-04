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
