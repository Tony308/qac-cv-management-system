db = db.getSiblingDB('Customers');
db.createUser(
{ 
	user: "admin",
	pwd: "admin",
	roles: [
		{ role: "dbOwner", db: "Customers" }
	]
});
db.createUser(
{
        user: "mongo",
        pwd: "trivago",
        roles: [
	        { role: "readWrite", db: "Customers" },
        	{ role: "dbAdmin", db: "Customers" }
        ]
});
db = db.getSiblingDB('test');
db.createUser(
{ 
	user: "admin",
	pwd: "admin",
	roles: [
		{ role: "dbOwner", db: "test" }
	]
});
db.createUser(
{
        user: "mongo",
        pwd: "trivago",
        roles: [
 	       { role: "readWrite", db: "test" },
        	{ role: "dbAdmin", db: "test" }
        ]
});
