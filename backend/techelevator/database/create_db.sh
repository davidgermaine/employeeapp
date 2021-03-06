#!/bin/bash
BASEDIR=$(dirname $0)
DATABASE=employeeapp
psql -U postgres -f "$BASEDIR/dropdb.sql" &&
createdb -U postgres $DATABASE &&
psql -U postgres -d $DATABASE -f "$BASEDIR/schema.sql"
psql -U postgres -d $DATABASE -f "$BASEDIR/sampledata.sql"