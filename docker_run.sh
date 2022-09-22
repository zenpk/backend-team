VERSION=$1
docker run --rm -it --name basic_backend -p 8080:8080 --add-host host.docker.internal:host-gateway zenpk/basic:${VERSION}
