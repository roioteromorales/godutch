db = connect("localhost:27020/godutch-db");
db.accounts.createIndex( { "email": 1 }, { unique: true } )