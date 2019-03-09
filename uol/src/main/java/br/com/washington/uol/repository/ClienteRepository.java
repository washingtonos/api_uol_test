package br.com.washington.uol.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.washington.uol.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Cliente findById(long id);

}
