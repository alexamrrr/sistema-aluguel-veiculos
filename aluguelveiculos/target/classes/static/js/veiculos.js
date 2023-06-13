// Variável para armazenar o ID do veículo em edição
let veiculoEmEdicaoId = null;

// Função para obter todos os veículos
function getVeiculos() {
  axios.get('http://localhost:8080/veiculos')
    .then(response => {
      const veiculos = response.data;
      const veiculosTable = document.getElementById('veiculos-table');
      const tbody = veiculosTable.querySelector('tbody');
      tbody.innerHTML = '';

      veiculos.forEach(veiculo => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
          <td>${veiculo.id}</td>
          <td>${veiculo.modelo}</td>
          <td>${veiculo.marca}</td>
          <td>${veiculo.placa}</td>
          <td>${veiculo.valorDiaria}</td>
          <td>
            <button onclick="editVeiculo(${veiculo.id})">Editar</button>
            <button onclick="deleteVeiculo(${veiculo.id})">Excluir</button>
          </td>
        `;
        tbody.appendChild(tr);
      });
    })
    .catch(error => {
      console.error('Erro ao obter os veículos:', error);
    });
}

// Função para adicionar um veículo
function addVeiculo(event) {
  event.preventDefault();

  const modeloInput = document.getElementById('modelo');
  const marcaInput = document.getElementById('marca');
  const placaInput = document.getElementById('placa');
  const valorDiariaInput = document.getElementById('valor-diaria');

  const veiculo = {
    modelo: modeloInput.value,
    marca: marcaInput.value,
    placa: placaInput.value,
    valorDiaria: parseFloat(valorDiariaInput.value)
  };

  axios.post('http://localhost:8080/veiculos', veiculo)
    .then(() => {
      modeloInput.value = '';
      marcaInput.value = '';
      placaInput.value = '';
      valorDiariaInput.value = '';

      getVeiculos();
    })
    .catch(error => {
      console.error('Erro ao adicionar veículo:', error);
    });
}

// Função para editar um veículo
function editVeiculo(id) {
  veiculoEmEdicaoId = id;

  const submitButton = document.querySelector('#add-veiculo-form button[type="submit"]');
  submitButton.textContent = 'Salvar';
  submitButton.onclick = updateVeiculo;

  axios.get(`http://localhost:8080/veiculos/${id}`)
    .then(response => {
      const veiculo = response.data;
      const modeloInput = document.getElementById('modelo');
      const marcaInput = document.getElementById('marca');
      const placaInput = document.getElementById('placa');
      const valorDiariaInput = document.getElementById('valor-diaria');

      modeloInput.value = veiculo.modelo;
      marcaInput.value = veiculo.marca;
      placaInput.value = veiculo.placa;
      valorDiariaInput.value = veiculo.valorDiaria;
    })
    .catch(error => {
      console.error('Erro ao obter veículo para edição:', error);
    });
}

// Função para atualizar um veículo
function updateVeiculo(event) {
  event.preventDefault();

  const modeloInput = document.getElementById('modelo');
  const marcaInput = document.getElementById('marca');
  const placaInput = document.getElementById('placa');
  const valorDiariaInput = document.getElementById('valor-diaria');

  const veiculo = {
    modelo: modeloInput.value,
    marca: marcaInput.value,
    placa: placaInput.value,
    valorDiaria: parseFloat(valorDiariaInput.value)
  };

  axios.put(`http://localhost:8080/veiculos/${veiculoEmEdicaoId}`, veiculo)
    .then(() => {
      modeloInput.value = '';
      marcaInput.value = '';
      placaInput.value = '';
      valorDiariaInput.value = '';

      const submitButton = document.querySelector('#add-veiculo-form button[type="submit"]');
      submitButton.textContent = 'Adicionar';
      submitButton.onclick = addVeiculo;

      veiculoEmEdicaoId = null;

      getVeiculos();
    })
    .catch(error => {
      console.error('Erro ao atualizar veículo:', error);
    });
}

// Função para excluir um veículo
function deleteVeiculo(id) {
  if (confirm('Deseja excluir o veículo?')) {
    axios.delete(`http://localhost:8080/veiculos/${id}`)
      .then(() => {
        getVeiculos();
      })
      .catch(error => {
        console.error('Erro ao excluir veículo:', error);
      });
  }
}

// Função para limpar os campos do formulário
function limparCampos() {
  const modeloInput = document.getElementById('modelo');
  const marcaInput = document.getElementById('marca');
  const placaInput = document.getElementById('placa');
  const valorDiariaInput = document.getElementById('valor-diaria');

  modeloInput.value = '';
  marcaInput.value = '';
  placaInput.value = '';
  valorDiariaInput.value = '';
}

// Função para inicializar o aplicativo
function init() {
  const addVeiculoForm = document.getElementById('add-veiculo-form');
  const limparButton = document.getElementById('limpar-btn');

  if (addVeiculoForm && limparButton) {
    addVeiculoForm.addEventListener('submit', addVeiculo);
    limparButton.addEventListener('click', limparCampos);
  }

  getVeiculos();
}

// Inicialização do aplicativo
init();
