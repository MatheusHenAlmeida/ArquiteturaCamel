docker run -p 3306:3306 -p 33060:33060 --name atendente-db -d atendente-db
docker run -p 3307:3306 -p 33061:33060 --name cliente-db -d cliente-db
docker run -p 3308:3306 -p 33062:33060 --name prestador-db -d prestador-db
docker run -p 3309:3306 -p 33063:33060 --name ordem-servico-db -d ordem-servico-db