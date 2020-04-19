FROM mysql:8.0

ENV MYSQL_ROOT_PASSWORD root
ENV MYSQL_DATABASE cliente-db
ENV MYSQL_USER atendimento-system
ENV MYSQL_PASSWORD 12345

COPY ./db/ /docker-entrypoint-initdb.d/