package com.ofenfit.ofenfit.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ofenfit.ofenfit.model.ClienteModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;

//@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {

      @Query("SELECT c FROM ClienteModel c WHERE " +
              "(:nome IS NULL OR c.nome LIKE %:nome%) AND " +
              "(:cpf IS NULL OR c.cpf = :cpf)")
      List<ClienteModel> buscarPorNomeOuCpf(@Param("nome") String nome, @Param("cpf") String cpf);
}
