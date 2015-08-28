# MCrypt
And RSA encryption/decryption solution that works with java (Android) and csharp

It's just a couple o classes to be used to implement a RSA cryptography comunication between an android app and a .Net c# web service

Use:

WebService CSharp

```csharp
//The public/private RSA keys
string keys = MCrypt.GenerateRSAKeys();

//The public key, you must share this with the android app
string pubkey = MCrypt.GetRSAPublicKey(keys);

string androidPubKey = "RSA PUB Key SENT BY THE ANDROID APP";

//Encrypted string ready to be sent to the java android app
string cypher = MCrypt.EncryptRSAToJava(androidPubKey, "Hello World!");
```

Android App Side

```java
//The public/private RSA keys
Hashtable<String, String> keys = MCrypt.generateRSAKeys();

//The public key, you must share this with the csharp webservice
String pubkey = MCrypt.getPublicKey(Context, keys.get("publicKey"));

//Get the public key string
String csharpPubKeyString = "RSA PUB Key SENT BY THE CSHARP WEB SERVICE";
//Converts into RSAPublicKey object
RSAPublicKey csharpPubKey = MCrypt.getPublicKey(Context, csharpPubKey);

//Encrypted string ready to be sent to the csharp Web Service
String cypher = MCrypt.encryptRSABlock(csharpPubKey, "Hello World!");
```