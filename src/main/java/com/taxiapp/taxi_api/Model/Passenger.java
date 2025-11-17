package com.taxiapp.taxi_api.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Representa a entidade de um Passageiro (Passenger) no sistema.
 * * Esta classe é mapeada para a tabela "passenger" no banco de dados
 * e armazena as informações pessoais e de conta do usuário passageiro.
 * * @author [Seu Nome/Equipe]
 * @version 1.0.0
 */
@Entity
public class Passenger {

    /**
     * Identificador único do passageiro (Chave Primária).
     * Gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome completo do passageiro.
     * Não pode ser nulo.
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Nome de usuário único para login.
     * Não pode ser nulo e deve ser único.
     */
    @Column(unique = true, nullable = false, length = 50)
    private String username;

    /**
     * Endereço principal do passageiro.
     * Não pode ser nulo.
     */
    @Column(nullable = false, length = 100)
    private String address;

    /**
     * Número de telefone de contato do passageiro.
     * Não pode ser nulo.
     */
    @Column(nullable = false, length = 15)
    private String phone;

    /**
     * Endereço de e-mail único do passageiro.
     * Usado para comunicação e recuperação de conta.
     * Não pode ser nulo e deve ser único.
     */
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    /**
     * Data e hora em que a conta do passageiro foi criada.
     * Gerado automaticamente no momento da criação (INSERT) e não pode ser atualizado.
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Construtor padrão sem argumentos.
     * Exigido pelo JPA para a criação de instâncias da entidade.
     */
    public Passenger() {
    }

    /**
     * Construtor para criar uma nova instância de Passageiro com dados essenciais.
     * * @param name     O nome completo do passageiro.
     * @param username O nome de usuário único.
     * @param adress   O endereço principal do passageiro.
     * @param phone    O número de telefone de contato.
     * @param email    O endereço de e-mail único.
     */
    public Passenger(String name, String username, String adress, String phone, String email) {
        this.name = name;
        this.username = username;
        this.address = adress;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Obtém o ID único do passageiro.
     * * @return O ID do passageiro.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Define o ID único do passageiro.
     * (Geralmente usado apenas pelo JPA ou para testes).
     * * @param id O ID a ser definido.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém o nome completo do passageiro.
     * * @return O nome do passageiro.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Define o nome completo do passageiro.
     * * @param name O nome a ser definido.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtém o nome de usuário (username) do passageiro.
     * * @return O nome de usuário.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Define o nome de usuário (username) do passageiro.
     * * @param username O nome de usuário a ser definido.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtém o endereço principal do passageiro.
     * * @return O endereço do passageiro.
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Define o endereço principal do passageiro.
     * * @param adress O endereço a ser definido.
     */
    public void setAddress(String adress) {
        this.address = adress;
    }

    /**
     * Obtém o número de telefone do passageiro.
     * * @return O número de telefone.
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Define o número de telefone do passageiro.
     * * @param phone O número de telefone a ser definido.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Obtém o e-mail do passageiro.
     * * @return O e-mail do passageiro.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Define o e-mail do passageiro.
     * * @param email O e-mail a ser definido.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtém a data e hora de criação da conta.
     * * @return A data e hora de criação.
     */
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Define a data e hora de criação da conta.
     * * @param createdAt A data e hora a ser definida.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}