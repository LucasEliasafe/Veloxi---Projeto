package br.com.skeleton.business.facade;

import java.util.List;
import jakarta.ejb.Local;
import br.com.skeleton.business.entity.Produto;

@Local
public interface ProdutoFacade {

  void cadastrar(Produto produto);

  void deletar(Long id);

  void atualizar(Produto produto);

  Produto findById(Long id);

  List<Produto> findAll();
}
