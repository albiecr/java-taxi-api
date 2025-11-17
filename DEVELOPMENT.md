## 📄 Documentação com Javadoc

Javadoc é o gerador de documentação padrão para a plataforma Java, incluído no JDK. Ele analisa comentários formatados de forma especial (`/** ... */`) diretamente no código-fonte e gera um conjunto de páginas HTML que descrevem a API da aplicação.

### Por que utilizamos no `java-taxi-api`?

Em um projeto de API como este, o código-fonte não é apenas uma implementação, mas também um **contrato**. Manter uma documentação clara é essencial para a manutenibilidade e a colaboração.

1.  **Clareza da API:** Nossos `Controllers` e `Services` expõem a lógica de negócio principal. O Javadoc nos permite definir claramente o que cada endpoint faz, quais parâmetros (DTOs) são esperados, o que é retornado e quais exceções podem ser lançadas.
2.  **Integração com a IDE:** Ferramentas como IntelliJ IDEA e VS Code usam os comentários Javadoc para fornecer ajuda instantânea, acelerando o desenvolvimento.
3.  **Manutenibilidade:** Ajuda a manter o código consistente e facilita a entrada de novos desenvolvedores no projeto.

### Padrão de Tags do Projeto

Utilizamos as seguintes tags para descrever o comportamento do código:

* `@param`: Descreve um parâmetro (argumento) de um método ou construtor.
* `@return`: Descreve o que o método retorna.
* `@throws` (ou `@exception`): Documenta uma exceção que o método pode lançar.
* `@author`: Identifica o autor do código.
* `@version`: Indica a versão da classe ou método.
* `@see`: Cria um link para outra parte da documentação (outra classe, método, etc.).
* `@deprecated`: Marca um método ou classe como obsoleto (não recomendado para uso).

---

#### Exemplo (Service)

Veja como essas tags são aplicadas em uma classe de serviço:

```java
package com.taxi.api.service;

import com.taxi.api.dto.CorridaDTO;
import com.taxi.api.dto.SolicitacaoViagemDTO;
import com.taxi.api.exception.ValidacaoException;

/**
 * Camada de serviço para gerenciar as operações de corrida (Taxi).
 *
 * Esta classe contém as principais regras de negócio relacionadas
 * à criação, consulta e tarifação de corridas.
 *
 * @author [Seu Nome ou Nome da Equipe]
 * @version 1.0.0
 */
public class CorridaService {

    /**
     * Solicita uma nova corrida para um usuário.
     *
     * Este método valida os dados da solicitação e, se corretos,
     * cria um novo registro de corrida com o status "AGUARDANDO_MOTORISTA".
     *
     * @param solicitacaoDTO O DTO (record) contendo os dados da solicitação.
     * @return Um DTO com os detalhes da corrida recém-criada.
     * @throws ValidacaoException Se o ID do usuário for inválido ou os endereços nulos.
     * @see SolicitacaoViagemDTO
     * @see CorridaDTO
     */
    public CorridaDTO solicitarCorrida(SolicitacaoViagemDTO solicitacaoDTO) {
        // ... Lógica de validação e criação da corrida
        // ...
        return null; // Exemplo
    }

    /**
     * [MÉTODO ANTIGO] Calcula o preço de uma corrida com base em km.
     *
     * @deprecated Este método foi substituído por {@link #calcularTarifaDinamica(long)}.
     * O novo método considera tarifas dinâmicas e horários de pico.
     * Não use este método para novas implementações.
     *
     * @param km A distância em quilômetros.
     * @return O preço final da corrida (lógica antiga).
     */
    @Deprecated
    public double calcularPrecoAntigo(double km) {
        return km * 1.5; // Lógica antiga e simplista
    }

    /**
     * Calcula a tarifa dinâmica para uma corrida específica.
     *
     * @param corridaId O ID da corrida.
     * @return O valor da tarifa calculada.
     */
    public double calcularTarifaDinamica(long corridaId) {
        // ... Lógica nova e complexa
        return 0.0;
    }
}
