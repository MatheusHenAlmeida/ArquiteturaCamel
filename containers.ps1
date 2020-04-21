$command = $Args[0]

$dominios = "atendente", "prestador", "cliente"

Foreach ($dominio in $dominios) {
    docker ($command) ($dominio + "-db")
}