#!/bin/sh

MYHOME=$HOME

sudo cp docker_vars.sh /etc/profile.d/
sudo chmod +x /etc/profile.d/docker_vars.sh
source /etc/profile

echo "-- Command: --"
echo "sudo docker daemon --tlsverify --tlscacert="$MYHOME/.docker/ca.pem" --tlscert="$MYHOME/.docker/server-cert.pem" --tlskey="$MYHOME/.docker/server-key.pem" -H="localhost:2376""

sudo docker daemon \
	--tlsverify \
	--tlscacert="$MYHOME/.docker/ca.pem" \
	--tlscert="$MYHOME/.docker/server-cert.pem" \
	--tlskey="$MYHOME/.docker/server-key.pem" \
	-H="localhost:2376"
