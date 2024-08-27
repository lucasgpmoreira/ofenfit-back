package com.ofenfit.ofenfit.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ofenfit.ofenfit.model.ClienteModel;
//import org.springframework.stereotype.Repository;

//@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {
      List<ClienteModel> findByNomeContainingIgnoreCaseOrCpf(String nome, String cpf);
}