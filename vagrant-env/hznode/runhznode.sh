#!/usr/bin/env bash

nohup java -jar -Dspring.profiles.active=vagrant /opt/hznode/hazelcastnode-0.0.2-SNAPSHOT.jar 1>/var/log/hznode/hznode.out 2>&1 &
