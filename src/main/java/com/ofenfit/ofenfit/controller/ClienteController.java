package com.ofenfit.ofenfit.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ofenfit.ofenfit.model.ClienteModel;
import com.ofenfit.ofenfit.repository.ClienteRepository;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Cadastrar um novo cliente
    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<ClienteModel> cadastrarCliente(@RequestBody @Valid ClienteModel cliente) {
        ClienteModel novoCliente = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    // Alterar dados de um cliente existente
    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteModel> alterarCliente(@PathVariable Long id, @RequestBody @Valid ClienteModel cliente) {
        Optional<ClienteModel> clienteExistente = clienteRepository.findById(id);
        if (clienteExistente.isPresent()) {
            cliente.setId(id);
            ClienteModel clienteAtualizado = clienteRepository.save(cliente);
            return ResponseEntity.ok(clienteAtualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Excluir um cliente existente
    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Buscar um cliente pelo nome e/ou CPF
    @CrossOrigin(origins = "*")
    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteModel>> buscarCliente(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf) {

        // Se o nome for vazio ou null, passamos null para a query personalizada
        nome = (nome != null && !nome.isEmpty()) ? nome : null;
        cpf = (cpf != null && !cpf.isEmpty()) ? cpf : null;

        List<ClienteModel> clientes = clienteRepository.buscarPorNomeOuCpf(nome, cpf);
        if (!clientes.isEmpty()) {
            return ResponseEntity.ok(clientes);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Listar todos os clientes cadastrados
    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity<List<ClienteModel>> listarTodos() {
        List<ClienteModel> clientes = clienteRepository.findAll();
        return ResponseEntity.ok(clientes);
    }

    //Alterar o valor de congelado
    @CrossOrigin(origins = "*")
    @PatchMapping("/{id}/congelado")
    public ResponseEntity<ClienteModel> alterarCongelado(@PathVariable Long id, @RequestParam boolean congelado) {

        Optional<ClienteModel> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isPresent()) {
            ClienteModel cliente = clienteOpt.get();
            cliente.setCongelado(congelado);
            clienteRepository.save(cliente);
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //zerar os dias de ofensiva
    @CrossOrigin(origins = "*")
    @PatchMapping("/{id}/diasDeOfensiva/zerar")
    public ResponseEntity<ClienteModel> zerarDiasDeOfensiva(@PathVariable Long id) {

        Optional<ClienteModel> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isPresent()) {
            ClienteModel cliente = clienteOpt.get();
            cliente.setDiasDeOfensiva(0);
            clienteRepository.save(cliente);
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //aumentar o número de dias de ofensiva
    @CrossOrigin(origins = "*")
    @PatchMapping("/{id}/diasDeOfensiva/aumentar")
    public ResponseEntity<ClienteModel> aumentarDiasDeOfensiva(@PathVariable Long id) {
        Optional<ClienteModel> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isPresent()) {
            ClienteModel cliente = clienteOpt.get();
            cliente.setDiasDeOfensiva(cliente.getDiasDeOfensiva() + 1); // Adiciona um dia
            clienteRepository.save(cliente);
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
