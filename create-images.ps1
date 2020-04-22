$dominios = "atendente", "prestador", "cliente", "ordem-servico"

Foreach ($dominio in $dominios) {
    docker build ("./" + $dominio + "-server") -t ($dominio + "-db")
}