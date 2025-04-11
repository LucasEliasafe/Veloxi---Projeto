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
            System.out.println("Chamando cadastrar() em ProdutoBean");
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


}













