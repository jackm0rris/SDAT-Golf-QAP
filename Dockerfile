FROM ubuntu:latest
LABEL authors="student"

ENTRYPOINT ["top", "-b"]