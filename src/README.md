# Phoebus UX Analytics Service Layer 

## Overview
The purpose of this module is to decouple database access 
from each  individual Phoebus client using the UX analytics
plugin. It provides a REST endpoint for posting graph events
in Neo4J and click events in MariaDB. it is intended to be run
as a single instance on a Glassfish/Payara application server.

## Building
run `mvn clean verify compile war:war` to build the project. 
It will create a war file in the `target` directory.

## External requirements

1. A running [Neo4J](https://neo4j.com/docs/operations-manual/current/installation/) instance, with a user configured for the application server, which must have write access to the database.
2. A running [MariaDB](https://mariadb.com/kb/en/getting-installing-and-upgrading-mariadb/) instance, with a user configured for the application server, which must have write access to the database.

## Application Server configuration
This module requires the following JNDI resources to be configured with the default `org.glassfish.resources.custom.factory.PrimitivesAndStringFactory` with `value=<your value>` in the application server, hopefully self-explanatory:

1. `neo4j/phoebus/uxa/bolt/uri`, e.g. `bolt://myserver:7687`
2. `neo4j/phoebus/uxa/bolt/user`
3. `neo4j/phoebus/uxa/bolt/password`
4. `mariadb/phoebus/uxa/uri`, e.g. `jdbc:mariadb://myserver:3306/your_database_name`
5. `mariadb/phoebus/uxa/user`
6. `mariadb/phoebus/uxa/password`

## Running
Once the JNDI resources are configured, deploy the generated `.war` file to your application server.

## API
(TODO: use swagger to document the API)

This is not meant to be used directly, but can be, if you are debugging something.
There are only two endpoints, one for posting graph events and one for posting click events.
### /analytics/recordNavigation
**POST** request with the following JSON fields:
- `dstType` (String, Required): the type of the destination node (one of `['display', 'pv']`)
- `dstName` (String, Required): the ID of the destination node (unconstrained)
- `srcType` (String, Required): the type of the source node (one of `['display','file_browser','top_resources_list','memento_restored', 'other_source']`)
- `srcName` (String, Required): the ID of the source node (unconstrained)
- `action`  (String, Required): One of (`['wrote_to', 'opened']`)
- `via`     (String, Optional): the ID of the element on the page that initiated this navigation, if applicable (unconstrained)

The timestamp will always be set by the server.

If successful, returns a JSON object with the following JSON fields:
- `nodesCreated` (int): the number of nodes created
- `nodesDeleted` (int): the number of nodes deleted
- `relationshipsCreated` (int): the number of relationships created
- `relationshipsDeleted` (int): the number of relationships deleted
- `propertiesSet` (int): the number of properties set (usually >= 1 since a new timestamp was added)
- `labelsAdded` (int): the number of labels added
- `labelsRemoved` (int): the number of labels removed
- `indexesAdded` (int): the number of indexes added
- `indexesRemoved` (int): the number of indexes removed
- `constraintsAdded` (int): the number of constraints added
- `constraintsRemoved` (int): the number of constraints removed
- `systemUpdates` (int): the number of system updates


### /analytics/recordClick
**POST** request with the following JSON fields:
- `x` (int): the x coordinate of the click
- `y` (int): the y coordinate of the click
- `fileName` (String): the name of the file that was clicked. A new table will be created for each unique file name.

The timestamp will always be set by the server.

If successful, returns a JSON object with the following fields:
- `id` (int): the ID of the inserted row

### /analytics/checkConnection
**GET** request.

If successful, returns a JSON object with the following JSON fields:
- `applicationStatus` (String OK/NOK): whether the application itself is listening (Always true if you got a response)
- `mariaDatabaseStatus` (String OK/NOK): whether the MariaDB connection is working
- `graphDatabaseStatus` (String OK/NOK): whether the Neo4J connection is working