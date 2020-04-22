$command = $Args[0]

$dominios = "atendente", "prestador", "cliente", "ordem-servico"

Foreach ($dominio in $dominios) {
    docker ($command) ($dominio + "-db")
}