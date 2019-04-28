Stub for Nexus API
==================

This is a lightweight stub that implement certain Nexus APIs so one can test the release process.

See https://github.com/vlsi/asflike-release-environment

Usage
-----

1. `./gradlew run` to start the application

Environment variables
---------------------

* `GROUP_IDS=groupId[:profileId]` specifies the list of group ids to create default staging profiles for.
Technically speaking, Nexus Pro creates a staging repository from a staging profile, so
the profile is required. The profile can be added later by sending `PUT` request to `/service/local/staging/profiles/{id}`

URLs
----

* http://127.0.0.1:8080/service/local/staging/profiles

License
-------

Apache License 2.0

Changelog
---------

2019-04-28
* Migrate Micronaut to SpringBoot

2019-04-24
* Micronaut-based implementation

Author
------

Vladimir Sitnikov <sitnikov.vladimir@gmail.com>
