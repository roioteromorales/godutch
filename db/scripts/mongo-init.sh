#!/bin/bash
echo "Running Mongo Init Script"

mongo localhost:27017 create/mongo-create.js
mongo localhost:27017 insert/mongo-insert.js

echo "Mongo Init Script Finished"