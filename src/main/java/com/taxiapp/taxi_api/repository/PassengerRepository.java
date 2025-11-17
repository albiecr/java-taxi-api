package com.taxiapp.taxi_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taxiapp.taxi_api.model.Passenger;

/**
 * Repositório Spring Data JPA para a entidade {@link Passenger}.
 *
 * <p>Esta interface gerencia o acesso aos dados da entidade Passenger,
 * fornecendo todos os métodos CRUD padrão (como save, findById, delete, etc.)
 * ao estender {@link JpaRepository}.</p>
 *
 * <p>Também define métodos de consulta personalizados (query methods) derivados
 * dos nomes dos atributos da entidade Passenger.</p>
 *
 * @see Passenger
 * @see JpaRepository
 * @author [Seu Nome/Equipe]
 * @version 1.0.0
 */
@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    /**
     * Busca um passageiro pelo seu nome completo.
     * <p>
     * Note: O campo 'name' não é definido como único na entidade,
     * este método retornará o primeiro resultado encontrado.
     *
     * @param name O nome completo do passageiro a ser buscado.
     * @return um {@link Optional} contendo o {@link Passenger} encontrado, ou
     * {@link Optional#empty()} se nenhum passageiro for encontrado com esse nome.
     */
    Optional<Passenger> findByName(String name);

    /**
     * Busca um passageiro pelo seu número de telefone.
     *
     * @param phone O número de telefone do passageiro a ser buscado.
     * @return um {@link Optional} contendo o {@link Passenger} encontrado, ou
     * {@link Optional#empty()} se não for encontrado.
     */
    Optional<Passenger> findByPhone(String phone);

    /**
     * Busca um passageiro pelo seu endereço de e-mail (que é único).
     *
     * @param email O e-mail único do passageiro a ser buscado.
     * @return um {@link Optional} contendo o {@link Passenger} encontrado, ou
     * {@link Optional#empty()} se não for encontrado.
     */
    Optional<Passenger> findByEmail(String email);

    /**
     * Busca um passageiro pelo seu nome de usuário (username, que é único).
     *
     * @param username O nome de usuário único do passageiro a ser buscado.
     * @return um {@link Optional} contendo o {@link Passenger} encontrado, ou
     * {@link Optional#empty()} se não for encontrado.
     */
    Optional<Passenger> findByUsername(String username);
}