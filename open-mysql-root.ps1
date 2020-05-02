$dominio = $Args[0]

docker exec -it ($dominio + "-db") mysql -u root -p
