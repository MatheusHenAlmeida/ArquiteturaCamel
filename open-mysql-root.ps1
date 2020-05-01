$dominio = $Args[0]

$dominios = "atendente", "prestador", "cliente", "ordem-servico"

docker exec -it ($dominio + "-db") mysql -u root -p
