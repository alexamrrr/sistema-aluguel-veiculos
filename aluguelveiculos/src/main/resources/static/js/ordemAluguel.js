document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("#orderForm");
    const tableBody = document.querySelector("#ordensAluguel-table tbody");
  
    // Função para buscar todos os clientes da API
    function getAllClientes() {
      axios.get("http://localhost:8080/clientes")
        .then(function (response) {
          const clientes = response.data;
          renderSelectOptions(clientes, "clienteId", "id", "nomeCompleto");
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  
    // Função para buscar todos os veículos da API
    function getAllVeiculos() {
      axios.get("http://localhost:8080/veiculos")
        .then(function (response) {
          const veiculos = response.data;
          renderSelectOptions(veiculos, "veiculoId", "id", "modelo", "placa");
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  
    // Função para renderizar as opções de select
    function renderSelectOptions(items, selectId, valueField, textField, additionalField) {
      const select = document.querySelector(`#${selectId}`);
      select.innerHTML = "";
  
      items.forEach(function (item) {
        const option = document.createElement("option");
        option.value = item[valueField];
        option.textContent = additionalField ? `${item[valueField]} - ${item[textField]} - ${item[additionalField]}` : `${item[valueField]} - ${item[textField]}`;
        select.appendChild(option);
      });
    }
  
    // Função para buscar todas as ordens de aluguel da API
    function getAllOrdensAluguel() {
      axios.get("http://localhost:8080/ordens-aluguel")
        .then(function (response) {
          const ordensAluguel = response.data;
          renderOrdensAluguel(ordensAluguel);
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  
    // Função para renderizar as ordens de aluguel na tabela
    function renderOrdensAluguel(ordensAluguel) {
      tableBody.innerHTML = "";
  
      ordensAluguel.forEach(function (ordem) {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${ordem.id}</td>
            <td>${ordem.cliente.id} - ${ordem.cliente.nomeCompleto} - ${ordem.cliente.cpf}</td>
            <td>${ordem.veiculo.id} - ${ordem.veiculo.modelo} - ${ordem.veiculo.placa}</td>
            <td>${ordem.quantidadeDias}</td>
            <td>
              <button class="editar-btn" data-id="${ordem.id}">Editar</button>
              <button class="excluir-btn" data-id="${ordem.id}">Excluir</button>
            </td>
          `;
  
        tableBody.appendChild(row);
      });
  
      // Adicionar event listeners aos botões de editar e excluir
      const editarBtns = document.querySelectorAll(".editar-btn");
      const excluirBtns = document.querySelectorAll(".excluir-btn");
  
      editarBtns.forEach(function (btn) {
        btn.addEventListener("click", editarOrdemAluguel);
      });
  
      excluirBtns.forEach(function (btn) {
        btn.addEventListener("click", excluirOrdemAluguel);
      });
    }
  
    // Função para cadastrar uma ordem de aluguel
    function saveOrdemAluguel(event) {
      event.preventDefault();
  
      const clienteId = document.querySelector("#clienteId").value;
      const veiculoId = document.querySelector("#veiculoId").value;
      const quantidadeDias = document.querySelector("#quantidadeDias").value;
  
      const ordemAluguel = {
        cliente: {
          id: clienteId
        },
        veiculo: {
          id: veiculoId
        },
        quantidadeDias
      };
  
      axios.post("http://localhost:8080/ordens-aluguel", ordemAluguel)
        .then(function (response) {
          console.log("Ordem de aluguel cadastrada com sucesso!");
          getAllOrdensAluguel();
          clearForm();
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  
    // Função para editar uma ordem de aluguel
    function editarOrdemAluguel(event) {
      const ordemId = event.target.dataset.id;
      // Buscar a ordem de aluguel pelo ID
      axios.get(`http://localhost:8080/ordens-aluguel/${ordemId}`)
        .then(function (response) {
          const ordemAluguel = response.data;
          // Preencher os campos do formulário com os dados da ordem de aluguel
          document.querySelector("#clienteId").value = ordemAluguel.cliente.id;
          document.querySelector("#veiculoId").value = ordemAluguel.veiculo.id;
          document.querySelector("#quantidadeDias").value = ordemAluguel.quantidadeDias;
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  
    // Função para excluir uma ordem de aluguel
    function excluirOrdemAluguel(event) {
      const ordemId = event.target.dataset.id;
      // Exibir um alerta de confirmação antes de excluir
      const confirmarExclusao = confirm("Tem certeza que deseja excluir esta ordem de aluguel?");
      if (confirmarExclusao) {
        // Implemente o código para excluir a ordem de aluguel com o ID ordemId
        axios.delete(`http://localhost:8080/ordens-aluguel/${ordemId}`)
          .then(function (response) {
            console.log(`Ordem de aluguel com ID ${ordemId} excluída com sucesso!`);
            getAllOrdensAluguel();
          })
          .catch(function (error) {
            console.log(error);
          });
      }
    }
  
    // Função para limpar o formulário
    function clearForm() {
      form.reset();
    }
  
    // Event listener para o formulário
    form.addEventListener("submit", saveOrdemAluguel);
  
    // Event listener para o botão "Limpar Campos"
    const limparBtn = document.querySelector("#limparBtn");
    limparBtn.addEventListener("click", clearForm);
  
    // Chamadas iniciais para buscar clientes, veículos e ordens de aluguel ao carregar a página
    getAllClientes();
    getAllVeiculos();
    getAllOrdensAluguel();
  });
  