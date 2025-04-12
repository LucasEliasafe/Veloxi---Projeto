package br.com.skeleton.web.bean;

import br.com.skeleton.business.entity.Produto;
import br.com.skeleton.business.facade.ProdutoFacade;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class ProdutoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(ProdutoBean.class.getName());

    @EJB
    private ProdutoFacade produtoFacade;
    private Produto produtoSelecionado;
    private Produto produto;
    private Long produtoId;
    private Produto produtoParaAtualizar;
    private List<Produto> produtos;


    private void limparFormulario() {
        produto = new Produto();
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }


    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Produto getProdutoSelecionado() {
        return produtoSelecionado;
    }

    public void setProdutoSelecionado(Produto produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }

    public Long getProdutoId() {
        return produtoId;
    }


    @PostConstruct
    public void init() {
        produtos = new ArrayList<>();
        produto = new Produto();
        produtoParaAtualizar = new Produto();

        try {
            if (produtoFacade == null) {
                LOGGER.log(Level.SEVERE, "Erro: produtoFacade não foi injetado corretamente!");
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na inicialização", "produtoFacade não foi injetado."));
            } else {
                LOGGER.info("Injeção bem-sucedida!");
                produtos = produtoFacade.findAll();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro durante a inicialização do bean", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na inicialização", e.getMessage()));
        }
    }

    public void cadastrar() {
        try {
            System.out.println(">>> Chamando cadastrar() em ProdutoBean");
            validarDatas(produto.getData(), produto.getValidade());
            produtoFacade.cadastrar(produto);
            produtos.add(produto);
            limparFormulario();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Produto salvo com sucesso!"));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar o produto", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar o produto: " + e.getMessage()));
        }
    }


    public void deletar(Long id) {
        try {
            if (id == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "ID do produto inválido!"));
                return;
            }
            produtoFacade.deletar(id);
            produtos.removeIf(p -> p.getId().equals(id));
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Produto deletado com sucesso!"));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao deletar o produto", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao deletar o produto: " + e.getMessage()));
        }
    }


    public void atualizar(Produto produtoSelecionado) {
        this.produto = produtoSelecionado;
        if (produto.getId() != null) {
            produtoFacade.atualizar(produto);
            FacesMessage msg = new FacesMessage("Produto atualizado com sucesso!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }


    private void validarDatas(LocalDate data, LocalDate validade) {
        if (data == null || validade == null) {
            throw new IllegalArgumentException("As datas de chegada e validade não podem ser nulas.");
        }
        if (data.isAfter(validade)) {
            throw new IllegalArgumentException("A data de chegada não pode ser posterior à validade.");
        }
        if (data.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data de chegada não pode ser anterior à data atual.");
        }
    }

}












