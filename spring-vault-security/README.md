# Spring Vault Demo
This demo shows how to use spring vault project together with spring security
to encrypt/decrypt password using password encoder and new VaultBytesEncryptor.


## Preparation

1. Startup vault using `vault server -dev`
2. Add root key `vault token create -id=myroot -ttl=60m` as defined in property _vault.token_ in `application.yml`
3. Mount vault transit backend `vault secrets enable transit`
4. Start the application via `./gradlew bootRun`

## Rest endpoints

* Access the server:

  [GET /](http://localhost:8080)

* Log in using 'user' and 'password'.


