#!/bin/bash
set -e;

# a default non-root role
MONGO_NON_ROOT_ROLE="${MONGO_NON_ROOT_ROLE:-readWrite}"

if [ -n "${MONGO_NON_ROOT_USERNAME:-}" ] && [ -n "${MONGO_NON_ROOT_PASSWORD:-}" ]; then
	"${mongo[@]}" "$MONGO_INITDB_DATABASE" <<-EOJS
		db.createUser({
			user: $(_js_escape "$MONGO_NON_ROOT_USERNAME"),
			pwd: $(_js_escape "$MONGO_NON_ROOT_PASSWORD"),
			roles: [ { role: $(_js_escape "$MONGO_NON_ROOT_ROLE"), db: $(_js_escape "$MONGO_INITDB_DATABASE") } ]
			})
	EOJS
else
	echo "Something went wrong!"
fi

#mongo -- "$MONGO_INITDB_DATABASE" <<EOF
#    var rootUser = '$MONGO_INITDB_ROOT_USERNAME';
#    var rootPassword = '$MONGO_INITDB_ROOT_PASSWORD';
#    var admin = db.getSiblingDB('admin');
#    admin.auth(rootUser, rootPassword);
#
#    var user = '$MONGO_INITDB_USERNAME';
#    var passwd = '$MONGO_INITDB_PASSWORD';
#    db.createUser({user: user, pwd: passwd, roles: ["readWrite"]});
#EOF

#mongo --eval "db.auth('$MONGO_INITDB_ROOT_USERNAME', '$MONGO_INITDB_ROOT_PASSWORD'); db = db.getSiblingDB('$MONGO_INITDB_DATABASE'); db.createUser({ user: '$MONGO_INITDB_USERNAME', pwd: '$MONGO_INITDB_PASSWORD', roles: [{ role: 'readWrite', db: '$MONGO_INITDB_DATABASE' }] });"