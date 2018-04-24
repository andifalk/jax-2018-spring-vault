# Spring Reactive Vault Demo
This demo shows how to use spring vault project together with 
reactive spring web (flux) and reactive spring security.
During startup the demo application already creates some book test data and
initializes an in-memory user store with secret password stored in vault.

## Preparation

1. Startup vault using `vault server -dev`
2. Add root key `vault token create -id=myroot -ttl=60m` as defined in property _vault.token_ in `application.yml`
3. Remove default kv secret backend: `vault secrets disable /secret` (spring vault not campatible to vault 0.10)
4. Add v1 kv secret backend: `vault secrets enable -path=secret kv-v1` (spring vault is not campatible to vault 0.10)
5. Add the secrets: `vault kv put secret/reactive-spring-vault userpass=password adminpass=secret`
6. Start mongodb (for reactive data access) on localhost
7. Start the application via `./gradlew bootRun`

## Rest endpoints

* List all books:

  [GET /api/books](http://localhost:8080/api/books)
  
Please use following login data:

Standard user
* User=user
* Password=password

Admin user
* User=admin
* Password=secret