$dominios = "atendente", "prestador", "cliente"

Foreach ($dominio in $dominios) {
    docker build ("./" + $dominio + "-server") -t ($dominio + "-db")
}