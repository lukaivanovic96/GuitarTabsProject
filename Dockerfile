FROM ubuntu:latest
LABEL authors="livanovic"

ENTRYPOINT ["top", "-b"]