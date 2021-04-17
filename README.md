## Notes

- Consider using the new Hazelcast Docker image, which can be heavily customized: 
[Hazelcast Docker](https://hub.docker.com/r/hazelcast/hazelcast).

- External STOMP Broker:
[External Broker at Spring docs](https://docs.spring.io/spring-framework/docs/4.3.x/spring-framework-reference/html/websocket.html#websocket-stomp-handle-broker-relay)
  
### Commands

- Start Vagrant environment
```bash
vagrant up
```

- Reprovision Vagrant environment and force reprovisining
```bash
vagrant reload --provision
```

- Stop Vagrant environment
```bash
vagrant halt
```

- SSH into Vagrant box _backend1_
```bash
vagrant ssh backend1
```

- Display status of Vagrant boxes
```bash
vagrant status
```

### RabbitMQ

- Management UI: [http://172.28.128.16:15672/](http://172.28.128.16:15672/)

  _Username:_ slondono  
  _Password:_ h0j4SolCopa
  
q