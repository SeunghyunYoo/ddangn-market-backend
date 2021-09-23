# Ddangn Market Clone Backend

## Redis server
- Redis install
> docker pull redis
> 
> docker network create redis-net
>
> docker run --name my-redis -p 6379:6379 --network redis-net -d redis redis-server --appendonly yes

- db access
> docker exec -it my-redis redis-cli --raw
