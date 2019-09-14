package br.com.matrix.sgc.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.event.SelectEvent;

import br.com.matrix.sgc.model.Cliente;
import br.com.matrix.sgc.model.FormaPagamento;
import br.com.matrix.sgc.model.Fornecedor;
import br.com.matrix.sgc.model.ItemPedido;
import br.com.matrix.sgc.model.Motorista;
import br.com.matrix.sgc.model.Pedido;
import br.com.matrix.sgc.model.Produto;
import br.com.matrix.sgc.model.Usuario;
import br.com.matrix.sgc.model.Veiculo;
import br.com.matrix.sgc.repository.Clientes;
import br.com.matrix.sgc.repository.Fornecedores;
import br.com.matrix.sgc.repository.Motoristas;
import br.com.matrix.sgc.repository.Produtos;
import br.com.matrix.sgc.repository.Usuarios;
import br.com.matrix.sgc.repository.Veiculos;
import br.com.matrix.sgc.service.CadastroPedidoService;
import br.com.matrix.sgc.service.NegocioException;
import br.com.matrix.sgc.util.jsf.FacesUtil;
import br.com.matrix.sgc.validation.SKU;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;
	
	@Inject
	private Clientes clientes;
	
	@Inject
	private Fornecedores fornecedores;
	
	@Inject
	private Motoristas motoristas;
	
	@Inject
	private Veiculos veiculos;
	
	@Inject
	private Produtos produtos;
	
	@Inject
	private CadastroPedidoService cadastroPedidoService;
	
	private String sku;
	
	private String placaCarreta;	
	
	@Produces
	@PedidoEdicao
	private Pedido pedido;
	
	private List<Usuario> vendedores;
	
	private List<Cliente> listaClientes;
	
	private List<Fornecedor> listaFornecedores;
	
	private List<Motorista> listaMotoristas;
	
	private List<Veiculo> listaVeiculos;
	
	private Produto produtoLinhaEditavel;
	
	public CadastroPedidoBean() {
		limpar();
	}
	
	public void inicializar() {
		if (this.pedido == null) {
			limpar();
		}
		
		this.listaClientes = this.clientes.clientes();
		
		this.vendedores = this.usuarios.vendedores();
		
		this.listaFornecedores = this.fornecedores.fornecedores();
		
		this.listaMotoristas = this.motoristas.motoristas();
		
		this.listaVeiculos = this.veiculos.veiculos();
		
		this.pedido.adicionarItemVazio();
		
		this.recalcularPedido();
	}
	
	public void clienteSelecionado(SelectEvent event) {
		pedido.setCliente((Cliente) event.getObject());
	}
	
	public void veiculoSelecionado(SelectEvent event) {
		pedido.setVeiculo((Veiculo) event.getObject());
	}
	
	private void limpar() {
		pedido = new Pedido();
	}
	
	public void pedidoAlterado(@Observes PedidoAlteradoEvent event) {
		this.pedido = event.getPedido();
	}
	
	public void salvar() {
		this.pedido.removerItemVazio();
		
		try {
			this.pedido = this.cadastroPedidoService.salvar(this.pedido);
		
			FacesUtil.addInfoMessage("Pedido salvo com sucesso!");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		} finally {
			this.pedido.adicionarItemVazio();
		}
	}
	
	public void recalcularPedido() {
		if (this.pedido != null) {
			this.pedido.recalcularValorTotal();
		}
	}
	
	public List<Veiculo> buscaVeiculo() {
		return veiculos.porPlaca(placaCarreta);
	}
		
	public void carregarProdutoPorSku() {
		if (StringUtils.isNotEmpty(this.sku)) {
			this.produtoLinhaEditavel = this.produtos.porSku(this.sku);
			this.carregarProdutoLinhaEditavel();
		}
	}
	
	public void carregarProdutoLinhaEditavel() {
		ItemPedido item = this.pedido.getItens().get(0);
		
		if (this.produtoLinhaEditavel != null) {
			if (this.existeItemComProduto(this.produtoLinhaEditavel)) {
				FacesUtil.addErrorMessage("Já existe um item no pedido com o produto informado.");
			} else {
				item.setProduto(this.produtoLinhaEditavel);
				item.setValorUnitario(this.produtoLinhaEditavel.getValorUnitario());
				
				this.pedido.adicionarItemVazio();
				this.produtoLinhaEditavel = null;
				this.sku = null;
				
				this.pedido.recalcularValorTotal();
			}
		}
	}
	
	private boolean existeItemComProduto(Produto produto) {
		boolean existeItem = false;
		
		for (ItemPedido item : this.getPedido().getItens()) {
			if (produto.equals(item.getProduto())) {
				existeItem = true;
				break;
			}
		}
		
		return existeItem;
	}

	public List<Produto> completarProduto(String nome) {
		return this.produtos.porNome(nome);
	}
	
	public void atualizarQuantidade(ItemPedido item, int linha) {
		if (item.getQuantidade() < 1) {
			if (linha == 0) {
				item.setQuantidade(1);
			} else {
				this.getPedido().getItens().remove(linha);
			}
		}
		
		this.pedido.recalcularValorTotal();
	}
	
	public FormaPagamento[] getFormasPagamento() {
		return FormaPagamento.values();
	}
	
	public List<Cliente> completarCliente(String nome) {
		return this.clientes.porNome(nome);
	}

	public Pedido getPedido() {
		return pedido;
	}
	
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<Usuario> getVendedores() {
		return vendedores;
	}
	
	public Fornecedores getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(Fornecedores fornecedores) {
		this.fornecedores = fornecedores;
	}

	public List<Fornecedor> getListaFornecedores() {
		return listaFornecedores;
	}
	
	public List<Motorista> getListaMotoristas() {
		return listaMotoristas;
	}	

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}	

	public List<Veiculo> getListaVeiculos() {
		return listaVeiculos;
	}

	public boolean isEditando() {
		return this.pedido.getId() != null;
	}

	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}
		
	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}

	@SKU
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public String getPlacaCarreta() {
		return placaCarreta;
	}

	public void setPlacaCarreta(String placaCarreta) {
		this.placaCarreta = placaCarreta;
	}

	@NotBlank
	public String getNomeCliente() {
		return pedido.getCliente() == null ? null : pedido.getCliente().getNome();
	}
	
	@NotBlank
	public String getNomeFornecedor() {
		return pedido.getFornecedor() ==  null ? null : pedido.getFornecedor().getNome();
	}
	
	public void setNomeCliente(String nome) {
	}

}