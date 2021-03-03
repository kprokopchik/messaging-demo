# Requirements
* Python 3

# Install Locust

https://docs.locust.io/en/stable/installation.html

```
pip3 install locust
```

# Write test

`locustfile.py`

# Execute test

- Execute `locust` command in the directory where you put `locustfile.py`
- Open http://localhost:8089/ in browser
- Enter parameters
    * Total number of users: 1000
    * Spawn users per second: 100
    * Server address: http://localhost:8082
- Check test result (stats, charts)

