package br.com.skeleton.business.facade;

import br.com.skeleton.business.entity.Produto;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

@Stateless
public class ProdutoFacadeJpa implements ProdutoFacade {

  @PersistenceContext(unitName = "KPI")
  private EntityManager em;
  private static final Logger LOGGER = Logger.getLogger(ProdutoFacadeJpa.class.getName());

  @Override
  public void cadastrar(Produto produto) {
    if (produto == null) {
      throw new IllegalArgumentException("Produto não pode ser nulo.");
    }
    em.persist(produto);
    em.flush();
    LOGGER.info("Produto cadastrado com sucesso: " + produto.getNome());
  }



}