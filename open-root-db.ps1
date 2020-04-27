$dominio = $Args[0]

docker exec -ti ($dominio + "-db") mysql -u root -p