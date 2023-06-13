document.addEventListener("DOMContentLoaded", function () {
  const form = document.querySelector("#cliente-form");
  const tableBody = document.querySelector("#clientes-table tbody");

  // Função para buscar todos os clientes da API
  function getAllClientes() {
    axios.get("http://localhost:8080/clientes")
      .then(function (response) {
        const clientes = response.data;
        renderClientes(clientes);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  // Função para renderizar os clientes na tabela
  function renderClientes(clientes) {
    tableBody.innerHTML = "";

    clientes.forEach(function (cliente) {
      const row = document.createElement("tr");
      row.innerHTML = `
          <td>${cliente.id}</td>
          <td>${cliente.nomeCompleto}</td>
          <td>${cliente.cpf}</td>
          <td>${cliente.email}</td>
          <td>${cliente.endereco}</td>
          <td class="actions">
            <button class="edit-btn" data-id="${cliente.id}">Editar</button>
            <button class="delete-btn" data-id="${cliente.id}">Excluir</button>
          </td>
        `;

      tableBody.appendChild(row);
    });
  }

  // Função para limpar o formulário
  function clearForm() {
    form.reset();
    document.querySelector("#cliente-id").value = "";
  }

  // Função para preencher o formulário com os dados do cliente selecionado para edição
  function fillForm(cliente) {
    document.querySelector("#cliente-id").value = cliente.id;
    document.querySelector("#nome-completo").value = cliente.nomeCompleto;
    document.querySelector("#cpf").value = cliente.cpf;
    document.querySelector("#email").value = cliente.email;
    document.querySelector("#endereco").value = cliente.endereco;
  }

  // Função para cadastrar ou atualizar um cliente
  function saveCliente(event) {
    event.preventDefault();

    const clienteId = document.querySelector("#cliente-id").value;
    const nomeCompleto = document.querySelector("#nome-completo").value;
    const cpf = document.querySelector("#cpf").value;
    const email = document.querySelector("#email").value;
    const endereco = document.querySelector("#endereco").value;

    const cliente = {
      nomeCompleto,
      cpf,
      email,
      endereco
    };

    if (clienteId) {
      // Atualizar cliente existente
      axios.put(`http://localhost:8080/clientes/${clienteId}`, cliente)
        .then(function (response) {
          console.log("Cliente atualizado com sucesso!");
          getAllClientes();
          clearForm();
        })
        .catch(function (error) {
          console.log(error);
        });
    } else {
      // Criar novo cliente
      axios.post("http://localhost:8080/clientes", cliente)
        .then(function (response) {
          console.log("Cliente criado com sucesso!");
          getAllClientes();
          clearForm();
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  }

  // Função para excluir um cliente
  function deleteCliente(id) {
    if (confirm('Deseja excluir o cliente?')) {
      axios.delete(`http://localhost:8080/clientes/${id}`)
        .then(() => {
          getAllClientes();
        })
        .catch(error => {
          console.error('Erro ao excluir cliente:', error);
        });
    }
  }

  // Função para preencher o formulário com os dados do cliente selecionado para edição
  function editCliente(event) {
    const clienteId = event.target.getAttribute("data-id");

    axios.get(`http://localhost:8080/clientes/${clienteId}`)
      .then(function (response) {
        const cliente = response.data;
        fillForm(cliente);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  // Event listener para o formulário
  form.addEventListener("submit", saveCliente);
  document.querySelector("#clear-btn").addEventListener("click", clearForm);

  // Event listener para os botões de edição e exclusão
  tableBody.addEventListener("click", function (event) {
    if (event.target.classList.contains("edit-btn")) {
      editCliente(event);
    } else if (event.target.classList.contains("delete-btn")) {
      const clienteId = event.target.getAttribute("data-id");
      deleteCliente(clienteId);
    }
  });

  // Chamada inicial para buscar todos os clientes ao carregar a página
  getAllClientes();
});
