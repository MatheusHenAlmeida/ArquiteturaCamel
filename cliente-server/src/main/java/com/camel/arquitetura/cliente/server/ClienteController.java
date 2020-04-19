package com.camel.arquitetura.cliente.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camel.arquitetura.cliente.server.entity.Cliente;
import com.camel.arquitetura.cliente.server.model.dto.AddClienteDTO;
import com.camel.arquitetura.cliente.server.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @GetMapping
    public List<Cliente> getAll() {
        List<Cliente> clientes = new ArrayList<Cliente>();
        Iterable<Cliente> clientesFromRepository = clienteRepository.findAll();
        
        clientesFromRepository.forEach(cliente -> {
            clientes.add(cliente);
        });
        return clientes;
    }
    
    @GetMapping("/{id}")
    public Cliente getById(@PathVariable Long id) {
        List<Cliente> clientes = new ArrayList<Cliente>();
        Optional<Cliente> cliente = clienteRepository.findById(id);
        
        if (cliente.isPresent())
            return cliente.get();
        
        return null;
    }
    
    @PostMapping("/create")
    public Cliente createCliente(@RequestBody AddClienteDTO addClienteDTO) {
        Cliente novoCliente = new Cliente();
        
        if (addClienteDTO.isValid()) {
            novoCliente.setBase(addClienteDTO.getBase());
            novoCliente.setEmail(addClienteDTO.getEmail());
            novoCliente.setEndereco(addClienteDTO.getEndereco());
            novoCliente.setRazaoSocial(addClienteDTO.getRazaoSocial());
            novoCliente.setSegmento(addClienteDTO.getSegmento());
            novoCliente.setTelefone(addClienteDTO.getTelefone());
            
            Cliente clienteRegistrado = clienteRepository.save(novoCliente);
            return clienteRegistrado;
        }
        return null;
    }
    
    @DeleteMapping("/delete/{id}")
    public Cliente removeCliente(@PathVariable Long id) {
        Cliente clienteRemovido = clienteRepository.findById(id).get();
        clienteRepository.deleteById(id);
        return clienteRemovido;
    }

}
