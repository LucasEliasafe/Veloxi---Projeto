package br.com.skeleton.web.facade;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.skeleton.business.entity.Config;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class MBeanEnvio implements Serializable {

  private static final long serialVersionUID = -1526686423522311531L;
  private static final Logger LOGGER = Logger.getLogger(MBeanEnvio.class.getName());


  private Config config;
  private String mensagemRetorno;


  public MBeanEnvio() {
    config = new Config();
  }

  private Config config;

  public Config getConfig() {
    return config;
  }

  public void setConfig(Config config) {
    this.config = config;
  }


  @PostConstruct
  public void init() {
    // config.setPropriedadeUsuario("Teste");
  }

  public void enviar() {
    setMensagemRetorno(config.getMensagem());
  }

  public String getMensagemRetorno() {
    return mensagemRetorno;
  }

  public void setMensagemRetorno(String mensagemRetorno) {
    this.mensagemRetorno = mensagemRetorno;
  }

  public void enviarDados() {
    try {
      if (config == null) {
        LOGGER.warning("Configuração não foi inicializada corretamente.");
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Configuração não foi inicializada."));
        return;
      }

      LOGGER.info("Enviando dados: " + config);
      FacesContext.getCurrentInstance().addMessage(null,
              new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Dados enviados com sucesso!"));

    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Erro ao enviar dados", e);
      FacesContext.getCurrentInstance().addMessage(null,
              new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Falha ao enviar dados. Tente novamente mais tarde."));
    }
  }
}
