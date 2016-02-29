#!/bin/sh

## Check if $HOME/.docker exists...
if [ ! -d "$HOME/.docker" ]; then
   mkdir -pv $HOME/.docker
fi

## Create a CA, server and client keys with OpenSSL

echo "... openssl genrsa ..."
openssl genrsa -aes256 -out $HOME/.docker/ca-key.pem 4096
echo "$HOME/.docker/ca-key.pem created!"

echo "... openssl req ..."
openssl req -new -x509 -days 365 \
	-key $HOME/.docker/ca-key.pem -sha256 -out $HOME/.docker/ca.pem
echo "$HOME/.docker/ca.pem created!"


echo "... openssl genrsa ..."
openssl genrsa -out $HOME/.docker/server-key.pem 4096
echo "$HOME/.docker/server-key.pem created!"

openssl req -subj "/CN=localhost" -sha256 \
	-new -key $HOME/.docker/server-key.pem \
	-out $HOME/.docker/server.csr

echo "$HOME/.docker/server.csr created!"

echo "subjectAltName = IP:10.10.10.20,IP:127.0.0.1" > $HOME/.docker/extfile.cnf

openssl x509 -req -days 365 -sha256 \
	-in $HOME/.docker/server.csr -CA $HOME/.docker/ca.pem -CAkey $HOME/.docker/ca-key.pem \
	-CAcreateserial -out $HOME/.docker/server-cert.pem \
	-extfile $HOME/.docker/extfile.cnf
echo "$HOME/.docker/server-cert.pem created!"

openssl genrsa -out $HOME/.docker/key.pem 4096

openssl req -subj '/CN=client' \
	-new -key $HOME/.docker/key.pem -out $HOME/.docker/client.csr
echo "$HOME/.docker/client.csr created!"

echo "extendedKeyUsage = clientAuth" > $HOME/.docker/extfile.cnf

openssl x509 -req -days 365 -sha256 \
	-in $HOME/.docker/client.csr -CA $HOME/.docker/ca.pem -CAkey $HOME/.docker/ca-key.pem \
	-CAcreateserial -out $HOME/.docker/cert.pem -extfile $HOME/.docker/extfile.cnf
echo "$HOME/.docker/cert.pem created!"

echo "... Changing keys permissions ..."
chmod -v 0400 $HOME/.docker/ca-key.pem $HOME/.docker/key.pem $HOME/.docker/server-key.pem
chmod -v 0444 $HOME/.docker/ca.pem $HOME/.docker/server-cert.pem $HOME/.docker/cert.pem
echo "Permissions changed!"
